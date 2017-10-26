package org.clevermonkeylabs.obscura.model;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import org.clevermonkeylabs.obscura.core.AbstractModel;
import org.clevermonkeylabs.obscura.util.Logger;
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
    }
//    /**
//     * Constructor to create a copy of another image with the same width and height
//     * @param reader
//     * @param width
//     * @param height
//     */
//    public ImageModel(PixelReader reader, int width, int height) {
//
//    }

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
        int red[] = {argb[0], 255, 0, 0};
        image.getPixelWriter().setArgb(x,y, arrayToColor(red));
        argb = colorToArray(image.getPixelReader().getArgb(x,y));
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
