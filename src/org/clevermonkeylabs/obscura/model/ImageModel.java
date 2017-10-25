package org.clevermonkeylabs.obscura.model;

import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import org.clevermonkeylabs.obscura.core.AbstractModel;
import org.clevermonkeylabs.obscura.view.ImageTabView;

/**
 * Created by Thomas on 10/12/2017.
 */
public class ImageModel extends AbstractModel<ImageTabView> {
    private String name;
    private WritableImage image;
    private int[][][] data;
    private int width, height;

    public ImageModel(ImageTabView view) {
        super(view);
    }

//    /**
//     * Constructor to create a new image model using a file location of the image in JPG, PNG, GIF, and BMP format.
//     * @param filePath
//     */
//    public ImageModel(String filePath){
//
//    }
//
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

    }

    /**
     *
     */
    public void save() {

    }


}
