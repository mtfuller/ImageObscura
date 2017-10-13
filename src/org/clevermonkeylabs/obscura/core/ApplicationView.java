package org.clevermonkeylabs.obscura.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Thomas on 9/28/2017.
 */
public class ApplicationView {
    private Stage mainStage;
    private final int DEFAULT_WIDTH = 600;
    private final int DEFAULT_HEIGHT = 400;

    /**
     *
     * @param mainStage
     */
    public ApplicationView(Stage mainStage) {
        this.mainStage = mainStage;
    }

    /**
     *
     */
    public void show() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("views/application-view.fxml"));
        } catch (IOException e) {
            System.err.println("Could not load the application view FXML file.");
            e.printStackTrace();
        }
        this.mainStage.setScene(new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT));
        this.mainStage.show();
    }

    /**
     *
     */
    public void addImageTab() {

    }
}
