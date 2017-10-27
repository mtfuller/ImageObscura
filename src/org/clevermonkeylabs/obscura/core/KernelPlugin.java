package org.clevermonkeylabs.obscura.core;

import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.image.WritablePixelFormat;
import org.clevermonkeylabs.obscura.core.CreateImagePlugin;
import org.clevermonkeylabs.obscura.core.PluginGroup;
import org.clevermonkeylabs.obscura.model.ImageModel;
import org.clevermonkeylabs.obscura.util.Logger;
import org.clevermonkeylabs.obscura.util.RGBValues;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Thomas on 10/26/2017.
 */
public abstract class KernelPlugin extends CreateImagePlugin {
    private KernelDialog dialog = new KernelDialog(getName());

    public KernelPlugin(String name, PluginGroup group) {
        super(name, group);
    }

    @Override
    protected void modifyImage(ImageModel imageModel) {
        Optional<Integer> choice = dialog.showAndWait();
        if (!choice.isPresent()) {
            Logger.error("Could not perform image modification. Kernel size was not present.");
        } else {
            int size = choice.get();
            int offset = size / 2;

            int[][][] imageBuffer = new int[imageModel.getHeight()][imageModel.getWidth()][3];

            for (int y = offset; y < imageModel.getHeight()-offset; y++) {
                for (int x = offset; x < imageModel.getWidth()-offset; x++) {
                    int[] newRGB = kernelOperation(imageModel.getKernel(x,y,size), size);
                    imageBuffer[y][x] = newRGB;

                }
            }

            for (int y = offset; y < imageModel.getHeight()-offset; y++) {
                for (int x = offset; x < imageModel.getWidth()-offset; x++) {
                    imageModel.setRGB(x,y, imageBuffer[y][x]);
                }
            }
        }
    }

    protected abstract int[] kernelOperation(int[][][] kernel, int size);

    class KernelDialog extends ChoiceDialog<Integer> {
        public KernelDialog(String pluginName) {
            super(3);
            for (int i = 3; i <= 11; i+=2) getItems().add(i);
            setTitle(pluginName+" Plugin");
            setContentText("Please choose the size for each kernel:");
        }
    }
}
