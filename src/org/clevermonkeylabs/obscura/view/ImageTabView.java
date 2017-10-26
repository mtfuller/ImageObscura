package org.clevermonkeylabs.obscura.view;

import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import org.clevermonkeylabs.obscura.controller.ImageTabController;
import org.clevermonkeylabs.obscura.core.AbstractView;

/**
 * Created by Thomas on 10/25/2017.
 */
public class ImageTabView extends AbstractView<ImageTabController> {
    private ImageView image;
    private Tab imageTab;

    public ImageTabView(ImageTabController controller) {
        super(controller);
        imageTab = new Tab();
        imageTab.setText("New Tab");
        imageTab.setContent(getRoot());
    }

    public ImageView getImage() {
        return this.image;
    }

    public Tab getImageTab() {return this.imageTab; }

    @Override
    public Node buildScene() {
        this.image = new ImageView();
        this.image.setPreserveRatio(true);

        StackPane stackPane = new StackPane(this.image);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(stackPane);

        stackPane.minWidthProperty().bind(Bindings.createDoubleBinding(() ->
                scrollPane.getViewportBounds().getWidth(), scrollPane.viewportBoundsProperty()));

        stackPane.minHeightProperty().bind(Bindings.createDoubleBinding(() ->
                scrollPane.getViewportBounds().getHeight(), scrollPane.viewportBoundsProperty()));

        return scrollPane;
    }
}
