package org.clevermonkeylabs.obscura.core;

import javafx.scene.image.PixelReader;
import org.clevermonkeylabs.obscura.controller.ImageTabController;
import org.clevermonkeylabs.obscura.model.ImageModel;
import org.clevermonkeylabs.obscura.util.Logger;
import org.clevermonkeylabs.obscura.view.ImageTabView;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Thomas on 10/26/2017.
 */
public abstract class CreateImagePlugin extends AbstractPlugin {
    public CreateImagePlugin(String name, PluginGroup group) {
        super(name, group);
    }

    @Override
    public void run() {
        ImageModel currentImage = getController().getModel().getCurrentImage();

        PixelReader currentImageReader = currentImage.getPixelReader();

        ImageTabController imageTabController = new ImageTabController();

        ImageTabView newImageTab = new ImageTabView(imageTabController);
        imageTabController.setView(newImageTab);

        ImageModel imageModel = new ImageModel(newImageTab, currentImageReader, currentImage.getWidth(),currentImage.getHeight());
        imageTabController.setModel(imageModel);

        Logger.info("Running plugin to modify the image...");

        modifyImage(imageModel);

        Logger.info("Finished!");

        getController().getView().addImageTab(newImageTab);
        getController().getModel().addImage(imageModel);

        newImageTab.getImageTab().setOnClosed(event -> getController().removeImageModel(newImageTab));
    }

    protected abstract void modifyImage(ImageModel imageModel);
}
