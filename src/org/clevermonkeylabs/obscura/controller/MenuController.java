package org.clevermonkeylabs.obscura.controller;

import org.clevermonkeylabs.obscura.core.AbstractController;
import org.clevermonkeylabs.obscura.model.ImageModel;
import org.clevermonkeylabs.obscura.view.ApplicationView;
import org.clevermonkeylabs.obscura.model.ApplicationModel;
import org.clevermonkeylabs.obscura.view.ImageTabView;

/**
 * Created by Thomas on 10/12/2017.
 */
public class MenuController extends AbstractController<ApplicationModel, ApplicationView> {

    // =================================================================================================================
    // Menu Bar Actions
    // =================================================================================================================

    /**
     *
     */
    public void open() {
        System.out.println("OPENING...");

        ImageTabController imageTabController = new ImageTabController();

        ImageTabView newImageTab = new ImageTabView(imageTabController);
        imageTabController.setView(newImageTab);

        ImageModel imageModel = new ImageModel(newImageTab);
        imageTabController.setModel(imageModel);

        getModel().addImage(imageModel);

        getView().addImageTab(newImageTab);
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

    }

    /**
     *
     */
    public void about() {

    }
}
