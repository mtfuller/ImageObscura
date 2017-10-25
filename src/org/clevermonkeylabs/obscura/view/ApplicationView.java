package org.clevermonkeylabs.obscura.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.clevermonkeylabs.obscura.controller.MenuController;
import org.clevermonkeylabs.obscura.core.AbstractView;

import java.io.IOException;

/**
 * Created by Thomas on 9/28/2017.
 */
public class ApplicationView extends AbstractView<MenuController> {
    private Scene scene = null;
    private SplitPane workspace = null;
    private ImageTabWorkspace imageTabWorkspace = null;


    public ApplicationView(MenuController controller) {
        super(controller);
    }

    public void setScene(Scene scene) {
        this.scene = scene;

        workspace = (SplitPane) this.scene.lookup("#applicationWorkspace");

        imageTabWorkspace = new ImageTabWorkspace();

        workspace.getItems().add(imageTabWorkspace);
    }

    public void addImageTab(ImageTabView imageTabView) {
        imageTabWorkspace.addTab(imageTabView.getRoot());
        System.out.println("ADDING... "+imageTabView.toString());
    }

    @Override
    public Node buildScene() {
        Parent root = null;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/application-view.fxml"));
        fxmlLoader.setController(this.getController());

        try {
            fxmlLoader.load();
            root = fxmlLoader.getRoot();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return root;
    }

    class ImageTabWorkspace extends TabPane {
        public void addTab(Node content) {
            Tab imageTab = new Tab();

            imageTab.setText("New Tab");
            imageTab.setContent(content);
            getTabs().add(imageTab);
        }
    }
}
