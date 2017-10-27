package org.clevermonkeylabs.obscura.model;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.image.WritablePixelFormat;
import org.clevermonkeylabs.obscura.core.AbstractModel;
import org.clevermonkeylabs.obscura.util.Logger;
import org.clevermonkeylabs.obscura.util.RGBValues;
import org.clevermonkeylabs.obscura.view.ImageTabView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Thomas on 10/12/2017.
 */
public class ImageModel extends AbstractModel<ImageTabView> {
    private static int imageCounter = 0;
    private String name;
    private WritableImage image;
    private int[][][] data;
    private int width, height;

    public ImageModel(ImageTabView view, File file) throws FileNotFoundException {
        super(view);
        name = file.getName();
        Image imageFile = new Image(new FileInputStream(file));
        image = new WritableImage(imageFile.getPixelReader(), (int) imageFile.getWidth(), (int) imageFile.getHeight());
        view.getImage().setImage(image);
        view.getImageTab().setText(name+" ("+(++imageCounter)+")");
        width = (int) imageFile.getWidth();
        height = (int) imageFile.getHeight();
        Logger.info(String.format("Dimensions: (%s,%s)", imageFile.getWidth(), imageFile.getHeight()));
    }
    /**
     * Constructor to create a copy of another image with the same width and height
     * @param reader
     * @param width
     * @param height
     */
    public ImageModel(ImageTabView view, String name, PixelReader reader, int width, int height) {
        super(view);
        this.name = name;
        image = new WritableImage(reader, width, height);
        view.getImage().setImage(image);
        view.getImageTab().setText(this.name+" ("+(++imageCounter)+")");
        this.width = width;
        this.height = height;
        Logger.info(String.format("Dimensions: (%s,%s)", width, height));
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the RGB value of the pixel at the (x,y) coordinates.
     * @param x The x-coordinate of the pixel.
     * @param y The y-coordinate of the pixel.
     * @return  An array of 3 integers values of the pixel data, in the format of: [RED, GREEN, BLUE].
     */
    public int[] getRGB(int x, int y) {
        return new int[]{0,0,0};
    }

    /**
     * Returns the entire 2D image matrix, where each element is a pixel with an array of the RGB color values.
     * @return  A 3D image array of raw pixel data.
     */
    public int[][][] getData() {
        return data;
    }


    public int[][][] getKernel(int x, int y, int size) {
        int offset = size/2;
        PixelReader reader = getPixelReader();
        int[][][] kernel = new int[size][size][3];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                int value = reader.getArgb((x-offset)+col, (y-offset)+row);
                int[] argb = RGBValues.argbToArray(value);
                kernel[row][col] = new int[]{argb[1], argb[2], argb[3]};
            }
        }
        return kernel;
    }

    /**
     *
     * @return
     */
    public int getWidth() {
        return width;
    }

    /**
     *
     * @return
     */
    public int getHeight() {
        return height;
    }

    /**
     *
     * @return
     */
    public PixelReader getPixelReader() { return image.getPixelReader(); }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param x
     * @param y
     * @param rgb
     */
    public void setRGB(int x, int y, int[] rgb) {
        int argb[] = colorToArray(image.getPixelReader().getArgb(x,y));
        int color[] = {argb[0], rgb[0], rgb[1], rgb[2]};
        image.getPixelWriter().setArgb(x,y, arrayToColor(color));
    }

    /**
     *
     */
    public void save() {

    }

    @Override
    public void dispose() {
        super.dispose();
        name = null;
        image = null;
        data = null;
    }

    private static int[] colorToArray(int argb) {
        int[] rgb = new int[4];
        rgb[0] = ((byte) (argb >>> 24)) & 0x000000FF;
        rgb[1] = ((byte) (argb >>> 16)) & 0x000000FF;
        rgb[2] = ((byte) (argb >>> 8)) & 0x000000FF;
        rgb[3] = ((byte) (argb)) & 0x000000FF;
        return rgb;
    }

    private static int arrayToColor(int[] argb) {
        int value = (byte) argb[0];
        for (int i = 1; i < argb.length; i++){
            value = value << 8;
            value = value | argb[i];
        }
        return value;
    }

}
