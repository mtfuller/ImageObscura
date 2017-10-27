package plugins;

import org.clevermonkeylabs.obscura.core.KernelPlugin;
import org.clevermonkeylabs.obscura.core.PluginGroup;

import java.util.Arrays;

/**
 * Created by Thomas on 10/27/2017.
 */
public class MedianFilter extends KernelPlugin {
    public MedianFilter() {
        super("Median Filter", PluginGroup.FILTER);
    }

    @Override
    protected int[] kernelOperation(int[][][] kernel, int size) {
        int counter = 0;
        int N = size*size;
        int[] red = new int[N];
        int[] green = new int[N];
        int[] blue = new int[N];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                red[counter] = kernel[row][col][0];
                green[counter] = kernel[row][col][1];
                blue[counter] = kernel[row][col][2];
                counter++;
            }
        }
        Arrays.sort(red);
        Arrays.sort(green);
        Arrays.sort(blue);
        return new int[]{red[N/2], green[N/2], blue[N/2]};
    }
}
