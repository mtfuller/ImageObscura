package plugins;

import org.clevermonkeylabs.obscura.core.KernelPlugin;
import org.clevermonkeylabs.obscura.core.PluginGroup;

/**
 * Created by Thomas on 10/27/2017.
 */
public class AverageFilter extends KernelPlugin {
    public AverageFilter() {
        super("Average Filter", PluginGroup.FILTER);
    }

    @Override
    protected int[] kernelOperation(int[][][] kernel, int size) {
        int red = 0;
        int green = 0;
        int blue = 0;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                red += kernel[row][col][0];
                green += kernel[row][col][1];
                blue += kernel[row][col][2];
            }
        }
        red /= (size*size);
        green /= (size*size);
        blue /= (size*size);
        return new int[]{red, green, blue};
    }
}
