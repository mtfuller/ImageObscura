package org.clevermonkeylabs.obscura;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.clevermonkeylabs.obscura.controller.MenuController;
import org.clevermonkeylabs.obscura.model.ApplicationModel;
import org.clevermonkeylabs.obscura.view.ApplicationView;

/**
 * Created by Thomas on 10/12/2017.
 */
public class ObscuraApplication {
    private Stage stage;
    private ApplicationView applicationView;
    private ApplicationModel applicationModel;
    private MenuController menuController = new MenuController();
    private final int DEFAULT_WIDTH = 600;
    private final int DEFAULT_HEIGHT = 400;

    public ObscuraApplication(Stage mainStage) {
        stage = mainStage;

        applicationView = new ApplicationView(menuController);
        menuController.setView(applicationView);

        applicationModel = new ApplicationModel(applicationView);
        menuController.setModel(applicationModel);
    }

    public void launch() {
        Parent root = (Parent) applicationView.getRoot();
        Scene scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        applicationView.setScene(scene);
        stage.setScene(scene);
        stage.show();
    }
}
