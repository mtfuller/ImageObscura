package plugins;

import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
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
public class KernelPlugin extends CreateImagePlugin {
    private KernelDialog dialog = new KernelDialog(getName());

    public KernelPlugin() {
        super("Kernel Plugin", PluginGroup.FILTER);
    }

    @Override
    protected void modifyImage(ImageModel imageModel) {
        Optional<Integer> choice = dialog.showAndWait();
        if (!choice.isPresent()) {
            Logger.error("Could not perform image modification. Kernel size was not present.");
        } else {
            int size = choice.get();
            int offset = size/2;

            for (int y = offset; y < imageModel.getHeight()-offset; y++) {
                for (int x = offset; x < imageModel.getWidth()-offset; x++) {
                    imageModel.setRGB(x,y, RGBValues.RED);
                }
            }
        }
    }

    //protected abstract int[] kernelOperation(int[][][] kernel);

    class KernelDialog extends ChoiceDialog<Integer> {
        public KernelDialog(String pluginName) {
            for (int i = 3; i <= 11; i+=2) getItems().add(i);
            setTitle(pluginName+" Plugin");
            setContentText("Please choose the size for each kernel:");
        }
    }
}
