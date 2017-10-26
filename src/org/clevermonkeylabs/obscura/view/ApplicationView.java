package org.clevermonkeylabs.obscura.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.clevermonkeylabs.obscura.controller.MenuController;
import org.clevermonkeylabs.obscura.core.AbstractPlugin;
import org.clevermonkeylabs.obscura.core.AbstractView;
import org.clevermonkeylabs.obscura.util.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by Thomas on 9/28/2017.
 */
public class ApplicationView extends AbstractView<MenuController> {
    private Scene scene = null;

    private MenuBar menuBar;
    private Menu imageMenu = new Menu("Image");
    private Menu transformationMenu = new Menu("Transformation");
    private Menu filterMenu = new Menu("Filter");

    private SplitPane workspace = null;
    private TabPane imageTabWorkspace;
    private HashMap<Tab, ImageTabView> imageTabMap = new HashMap<>();


    public ApplicationView(MenuController controller) {
        super(controller);
    }

    public void setScene(Scene scene) {
        this.scene = scene;

        workspace = (SplitPane) this.scene.lookup("#applicationWorkspace");

        imageTabWorkspace = new TabPane();
        imageTabWorkspace.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            getController().switchImageTab(imageTabMap.get(newValue));
        });

        workspace.getItems().add(imageTabWorkspace);

        menuBar = (MenuBar) this.scene.lookup("#imageMenu");
        menuBar.getMenus().add(1, imageMenu);
        menuBar.getMenus().add(2, transformationMenu);
        menuBar.getMenus().add(3, filterMenu);
    }

    public void addPlugin(AbstractPlugin plugin) {
        Menu chosenMenu;
        switch (plugin.getGroup()) {
            case IMAGE:
                chosenMenu = imageMenu;
                break;
            case TRANSFORMATION:
                chosenMenu = transformationMenu;
                break;
            default:
                chosenMenu = filterMenu;
        }

        MenuItem menuItem = new MenuItem(plugin.getName());
        menuItem.setOnAction(event -> plugin.run());
        chosenMenu.getItems().add(menuItem);
        chosenMenu.getItems().sort(Comparator.comparing(MenuItem::getText));
    }

    public void addImageTab(ImageTabView imageTabView) {
        Tab newImageTab = imageTabView.getImageTab();
        imageTabMap.put(newImageTab, imageTabView);
        imageTabWorkspace.getTabs().add(newImageTab);
    }

    public void removeImageTab(ImageTabView imageTabView) {
        Tab newImageTab = imageTabView.getImageTab();
        imageTabMap.remove(newImageTab);
        imageTabWorkspace.getTabs().remove(newImageTab);
    }

    public File promptUserForImage() {
        Stage stage = (Stage) scene.getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        return fileChooser.showOpenDialog(stage);
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
}
