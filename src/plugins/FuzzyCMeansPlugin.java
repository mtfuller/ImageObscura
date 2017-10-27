package plugins;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import org.clevermonkeylabs.obscura.core.CreateImagePlugin;
import org.clevermonkeylabs.obscura.core.PluginGroup;
import org.clevermonkeylabs.obscura.model.ImageModel;
import org.clevermonkeylabs.obscura.util.Logger;

import java.util.DoubleSummaryStatistics;
import java.util.Observable;
import java.util.Optional;
import java.util.Random;

/**
 * Created by Thomas on 10/27/2017.
 */
public class FuzzyCMeansPlugin extends CreateImagePlugin {
    private CMeansDialog dialog = new CMeansDialog(getName());
    private final int MAX_ITER = 100;

    public FuzzyCMeansPlugin() {
        super("Fuzzy C-Means Segmentation", PluginGroup.IMAGE);
    }

    private void FCM(ImageModel image, int K, double M) {
        int[][] pixelClasses = new int[image.getHeight()][image.getWidth()];

        // Define and generate centroids
        Random random = new Random();
        int[][] centroids = new int[K][3];
        for (int i = 0; i < K; i++) {
            centroids[i] = image.getRGB(random.nextInt(image.getWidth()),random.nextInt(image.getHeight()));
        }

        for (int iter = 0; iter < MAX_ITER; iter++) {
            Logger.info("Running Iteration: "+(iter+1));

            // Classify each pixel to a centroid
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int[] rgb = image.getRGB(x, y);
                    int classification = classify(centroids, rgb);
                    pixelClasses[y][x] = classification;
                }
            }

            // Find new mean for each class
            int[][] means = new int[K][3];
            int[] bins = new int[K];
            for (int i = 0; i < K; i++) {
                means[i] = new int[]{0, 0, 0};
                bins[i] = 0;
            }
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int[] rgb = image.getRGB(x, y);
                    int classification = pixelClasses[y][x] - 1;
                    for (int c = 0; c < 3; c++)
                        means[classification][c] += rgb[c];
                    bins[classification]++;
                }
            }

            for (int i = 0; i < K; i++) {
                for (int c = 0; c < 3; c++) {
                    if (bins[i] == 0) continue;
                    means[i][c] /= bins[i];
                }
            }

            // Compare new means with centroids, if the centroids and means are equal we have converged.
            boolean exit = true;
            for (int i = 0; i < K; i++) {
                if (distance(centroids[i], means[i]) > 0.0) exit = false;
            }
            if(exit) {
                break;
            }

            centroids = means;
        }

        // Paint each pixel the color of the class its associated with
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int classification = pixelClasses[y][x]-1;
                image.setRGB(x,y,centroids[classification]);
            }
        }
    }

    /**
     *
     * @param centroids
     * @param rgb
     * @return
     */
    private int classify(int[][] centroids, int[] rgb) {
        int c = 0;

        double min = 1000000000.0;

        for (int i = 0; i < centroids.length; i++) {
            int[] centroid = centroids[i];
            double distance = distance(centroid, rgb);
            if (distance < min) {
                min = distance;
                c = i + 1;
            }
        }

        return c;
    }

    /**
     *
     * @param a
     * @param b
     * @return
     */
    private double distance(int[] a, int[] b) {
        int diffRed = a[0] - b[0];
        int diffGreen = a[1] - b[1];
        int diffBlue = a[2] - b[2];
        return Math.sqrt(Math.pow(diffRed,2) + Math.pow(diffGreen,2) + Math.pow(diffBlue,2));
    }

    /**
     *
     * @param imageModel
     */
    @Override
    protected void modifyImage(ImageModel imageModel) {
        Optional<Pair<Integer, Double>> choice = dialog.showAndWait();
        if (!choice.isPresent()) {
            Logger.error("Could not perform image modification. The value for K was not present.");
        } else {
            Pair<Integer, Double> pair = choice.get();
            int K = pair.getKey();
            double M = pair.getValue();
            FCM(imageModel, K, M);
        }
    }

    class CMeansDialog extends Dialog<Pair<Integer, Double>> {
        public CMeansDialog(String pluginName) {
            GridPane grid = new GridPane();
            grid.setVgap(10);
            grid.setHgap(10);

            //Setup buttons
            ButtonType runButtonType = new ButtonType("Run", ButtonBar.ButtonData.OK_DONE);
            this.getDialogPane().getButtonTypes().addAll(runButtonType, ButtonType.CANCEL);

            Node runButton = this.getDialogPane().lookupButton(runButtonType);
            runButton.setDisable(true);

            ChoiceBox<Integer> kField = new ChoiceBox<>(FXCollections.observableArrayList(3,4,5,6,7,8,9,10,11,12,13,14,15,16));
            kField.getSelectionModel().selectFirst();
            TextField fuzzifierField = new TextField("0.0");
            fuzzifierField.textProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    double m = Double.parseDouble(newValue);
                    if (m <= 1) throw new Exception();
                    runButton.setDisable(false);
                } catch (Exception e) {
                    runButton.setDisable(true);
                }
            });

            grid.add(new Label("Please select value for K:"), 0, 0);
            grid.add(kField, 1, 0);
            grid.add(new Label("Please enter valid fuzzifier (m):"), 0, 1);
            grid.add(fuzzifierField, 1, 1);

            this.getDialogPane().setContent(grid);
        }
    }
}
