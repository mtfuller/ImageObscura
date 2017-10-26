package org.clevermonkeylabs.obscura.util;

/**
 * Created by Thomas on 10/26/2017.
 */
public class RGBValues {
    public static final int[] RED   = {255, 0, 0};
    public static final int[] GREEN = {0, 255, 0};
    public static final int[] BLUE  = {0, 0, 255};


    public static int[] argbToArray(int argb) {
        int[] rgb = new int[4];
        rgb[0] = ((byte) (argb >>> 24)) & 0x000000FF;
        rgb[1] = ((byte) (argb >>> 16)) & 0x000000FF;
        rgb[2] = ((byte) (argb >>> 8)) & 0x000000FF;
        rgb[3] = ((byte) (argb)) & 0x000000FF;
        return rgb;
    }

    public static int arrayToARGB(int[] argb) {
        int value = (byte) argb[0];
        for (int i = 1; i < argb.length; i++){
            value = value << 8;
            value = value | argb[i];
        }
        return value;
    }
}
