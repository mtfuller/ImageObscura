/*
* Course:       CS 4732
* Student Name: Thomas Fuller
* Student ID:   000678589
* Assignment #: #3
* Due Date:     10/30/2017
*
* Signature:    ______________________________________________________________
*               (The signature means that the work is your own, not
*               from somewhere else.)
*
* Score:        ____________________
* */

package plugins;

// Java core libraries
import javafx.scene.control.ChoiceDialog;
import java.util.Optional;
import java.util.Random;

// Libraries from my own Image Processing platform: ImageObscura
// For more information, please visit: https://github.com/mtfuller/ImageObscura
import org.clevermonkeylabs.obscura.core.CreateImagePlugin;
import org.clevermonkeylabs.obscura.core.PluginGroup;
import org.clevermonkeylabs.obscura.model.ImageModel;
import org.clevermonkeylabs.obscura.util.Logger;

/**
 * The K-Means Segmentation Plugin for ImageObscura
 */
public class KMeansPlugin extends CreateImagePlugin {
    private KMeansDialog dialog = new KMeansDialog(getName());
    private final int MAX_ITER = 100;

    public KMeansPlugin() {
        super("K-Means Segmentation", PluginGroup.IMAGE);
    }

    /**
     * Runs the K-Means clustering algorithm to segment an image for a given K.
     * @param image The image that will be segmented.
     * @param K The value of K used for the K-Means algorithm.
     */
    private void KMeans(ImageModel image, int K) {
        // Creating a classification matrix, one element for each pixel.
        int[][] pixelClasses = new int[image.getHeight()][image.getWidth()];

        // Define and generate centroids
        Random random = new Random();
        int[][] centroids = new int[K][3];
        for (int i = 0; i < K; i++) {
            // Choose a random pixel (RGB vector) for one of the centroids
            centroids[i] = image.getRGB(
                    random.nextInt(image.getWidth()),
                    random.nextInt(image.getHeight())
            );
        }

        // Continue iterating through each mean calculation until the means
        // converge or the maximum number (MAX_ITER) of iterations has been
        // reached.
        for (int iter = 0; iter < MAX_ITER; iter++) {
            Logger.info("Running Iteration: "+(iter+1));

            // Classify each pixel to a centroid
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    // Get an RGB vector from a pixel at (x,y)
                    int[] rgb = image.getRGB(x, y);

                    // Classify the RGB vector
                    int classification = classify(centroids, rgb);

                    // Set the pixel's class in the classification matrix
                    pixelClasses[y][x] = classification;
                }
            }

            // Find new mean for each class
            int[][] means = new int[K][3];
            int[] bins = new int[K];    // Bins is used to count the total
                                        // pixels for each class.

            // Initialize each mean vector and bin
            for (int i = 0; i < K; i++) {
                means[i] = new int[]{0, 0, 0};
                bins[i] = 0;
            }

            // Visit every pixel in the image to get the sum and total for
            // each class
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    // Get an RGB vector from a pixel at (x,y)
                    int[] rgb = image.getRGB(x, y);

                    // If the pixel doesn't have a class, skip
                    if (pixelClasses[y][x] <= 0) continue;

                    // Get the class of the pixel, subtract 1 to get index
                    // of centroid
                    int classification = pixelClasses[y][x] - 1;

                    // Add each color value to the corresponding sum in the
                    // means matrix
                    for (int c = 0; c < 3; c++)
                        means[classification][c] += rgb[c];

                    // Increase the number of pixels for this class by 1
                    bins[classification]++;
                }
            }

            // Calculate the new means
            for (int i = 0; i < K; i++) {
                for (int c = 0; c < 3; c++) {
                    if (bins[i] == 0) continue;
                    means[i][c] /= bins[i];
                }
            }

            // Compare new means with centroids, if the centroids and means
            // are equal we have converged.
            boolean haveConverged = true;
            for (int i = 0; i < K; i++) {
                if (distance(centroids[i], means[i]) > 0.0) {
                    haveConverged = false;
                    break;
                }
            }

            // If the means have converged, stop the K-Means algorithm
            if(haveConverged) break;

            // If they haven't converged the new means become the centroids,
            // and we continue.
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
     * Classifies the given RGB vector given to the class of one of the
     * centroids. Returns an integer of the RGB vectors
     * class.
     * @param centroids An array of each centroid (an RGB vector that
     *                  represents a particular class)
     * @param rgb The RGB vector to be classified.
     * @return An integer that represents the RGB vector's class. Class
     *         "1" refers to centroid at index "0", class "2" refers to
     *         centroid at index "1", and so on. Note, class 0 refers
     *         to no associated class.
     */
    private int classify(int[][] centroids, int[] rgb) {
        int c = 0;

        // Set min to "near-infinity"
        double min = 1000000000.0;

        // Find the centoid that has the least distance to the given RGB
        // vector.
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
     * Calculates the distance between two RGB color vectors.
     * @param a First RGB vector
     * @param b Second RGB Vector
     * @return The magnitude of the resulting vector from a - b.
     */
    private double distance(int[] a, int[] b) {
        int diffRed = a[0] - b[0];
        int diffGreen = a[1] - b[1];
        int diffBlue = a[2] - b[2];
        return Math.sqrt(
                Math.pow(diffRed,2) +
                Math.pow(diffGreen,2) +
                Math.pow(diffBlue,2)
        );
    }

    /**
     * Implementing abstract method from plugin base class, this is called
     * when the menu item is clicked by the user.
     *
     * @param imageModel The image model to be modified by the plugin.
     */
    @Override
    protected void modifyImage(ImageModel imageModel) {
        Optional<Integer> choice = dialog.showAndWait();
        if (!choice.isPresent()) {
            Logger.error("Could not perform image modification. The value " +
                    "for K was not present.");
        } else {
            int K = choice.get();
            KMeans(imageModel, K);
        }
    }

    // Used for dialog popup box
    class KMeansDialog extends ChoiceDialog<Integer> {
        public KMeansDialog(String pluginName) {
            super(2);
            for (int i = 2; i <= 16; i++) getItems().add(i);
            setTitle(pluginName+" Plugin");
            setContentText("Please choose the size for each kernel:");
        }
    }
}
