package org.clevermonkeylabs.obscura.controller;

import org.clevermonkeylabs.obscura.core.AbstractController;
import org.clevermonkeylabs.obscura.model.ImageModel;
import org.clevermonkeylabs.obscura.util.InformationDialog;
import org.clevermonkeylabs.obscura.util.Logger;
import org.clevermonkeylabs.obscura.view.ApplicationView;
import org.clevermonkeylabs.obscura.model.ApplicationModel;
import org.clevermonkeylabs.obscura.view.ImageTabView;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Thomas on 10/12/2017.
 */
public class MenuController extends AbstractController<ApplicationModel, ApplicationView> {

    private InformationDialog noImageSelectedAlert = new InformationDialog("No Image Selected", "There is no image selected to close.");

    // =================================================================================================================
    // Menu Bar Actions
    // =================================================================================================================

    /**
     *
     */
    public void open() {
        ImageTabController imageTabController = new ImageTabController();

        Logger.info("Waiting for an image file from the user...");
        File image = getView().promptUserForImage();

        ImageTabView newImageTab = new ImageTabView(imageTabController);
        imageTabController.setView(newImageTab);


        ImageModel imageModel = null;
        try {
            imageModel = new ImageModel(newImageTab, image);
        } catch (FileNotFoundException e) {
            Logger.error("Could not find the file specified.");
            e.printStackTrace();
        }
        imageTabController.setModel(imageModel);

        getView().addImageTab(newImageTab);
        getModel().addImage(imageModel);

        newImageTab.getImageTab().setOnClosed(event -> removeImageModel(newImageTab));

        Logger.info("Successfully added image file to the workspace.");
    }

    /**
     *
     */
    public void save() {

    }

    /**
     *
     */
    public void close() {
        ImageModel selected = getModel().getCurrentImage();
        if(selected == null) noImageSelectedAlert.showAndWait();
        else {
            ImageTabView selectedView = selected.getView();
            getView().removeImageTab(selectedView);
            removeImageModel(selectedView);
        }
    }

    /**
     *
     */
    public void about() {

    }

    public void switchImageTab(ImageTabView imageTabView) {

        ImageModel imageModel = null;
        if (imageTabView != null) imageModel = imageTabView.getController().getModel();
        getModel().setCurrentImage(imageModel);
    }

    public void removeImageModel(ImageTabView imageTabView) {
        Logger.info("Closing the \"" + imageTabView.getImageTab().getText() + "\" image tab.");
        getView().removeImageTab(imageTabView);
        ImageModel imageModel = imageTabView.getController().getModel();
        getModel().removeImage(imageModel);
        imageModel.dispose();
        imageTabView.getController().dispose();
        imageTabView.dispose();
        System.gc();
    }
}
