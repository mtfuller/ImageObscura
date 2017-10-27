package plugins;

import javafx.scene.control.ChoiceDialog;
import org.clevermonkeylabs.obscura.core.CreateImagePlugin;
import org.clevermonkeylabs.obscura.core.PluginGroup;
import org.clevermonkeylabs.obscura.model.ImageModel;
import org.clevermonkeylabs.obscura.util.Logger;

import java.util.Optional;
import java.util.Random;

/**
 * Created by Thomas on 10/27/2017.
 */
public class KMeansPlugin extends CreateImagePlugin {
    private KMeansDialog dialog = new KMeansDialog(getName());
    private final int MAX_ITER = 100;

    public KMeansPlugin() {
        super("K-Means Segmentation", PluginGroup.IMAGE);
    }

    @Override
    protected void modifyImage(ImageModel imageModel) {
        Optional<Integer> choice = dialog.showAndWait();
        if (!choice.isPresent()) {
            Logger.error("Could not perform image modification. The value for K was not present.");
        } else {
            int K = choice.get();

            int[][] pixelClasses = new int[imageModel.getHeight()][imageModel.getWidth()];

            // Define and generate centroids
            Random random = new Random();
            int[][] centroids = new int[K][3];
            for (int i = 0; i < K; i++) {
                centroids[i] = imageModel.getRGB(random.nextInt(imageModel.getWidth()),random.nextInt(imageModel.getHeight()));
            }

            for (int iter = 0; iter < MAX_ITER; iter++) {
                Logger.info("Running Iteration: "+(iter+1));

                // Classify each pixel to a centroid
                for (int y = 0; y < imageModel.getHeight(); y++) {
                    for (int x = 0; x < imageModel.getWidth(); x++) {
                        int[] rgb = imageModel.getRGB(x, y);
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
                for (int y = 0; y < imageModel.getHeight(); y++) {
                    for (int x = 0; x < imageModel.getWidth(); x++) {
                        int[] rgb = imageModel.getRGB(x, y);
                        int classification = pixelClasses[y][x] - 1;
                        for (int c = 0; c < 3; c++)
                            means[classification][c] += rgb[c];
                        bins[classification]++;
                    }
                }

                // TODO: Fix the random divide by zero error here. Maybe ensure that a class always has at least one pixel.
                for (int i = 0; i < K; i++) {
                    for (int c = 0; c < 3; c++)
                        means[i][c] /= bins[i];
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
            for (int y = 0; y < imageModel.getHeight(); y++) {
                for (int x = 0; x < imageModel.getWidth(); x++) {
                    int classification = pixelClasses[y][x]-1;
                    imageModel.setRGB(x,y,centroids[classification]);
                }
            }
        }

    }

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

    private double distance(int[] a, int[] b) {
        int diffRed = a[0] - b[0];
        int diffGreen = a[1] - b[1];
        int diffBlue = a[2] - b[2];
        return Math.sqrt(Math.pow(diffRed,2) + Math.pow(diffGreen,2) + Math.pow(diffBlue,2));
    }

    class KMeansDialog extends ChoiceDialog<Integer> {
        public KMeansDialog(String pluginName) {
            super(3);
            for (int i = 3; i <= 16; i++) getItems().add(i);
            setTitle(pluginName+" Plugin");
            setContentText("Please choose the size for each kernel:");
        }
    }
}
