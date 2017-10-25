package org.clevermonkeylabs.obscura.view;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import org.clevermonkeylabs.obscura.controller.ImageTabController;
import org.clevermonkeylabs.obscura.core.AbstractView;

/**
 * Created by Thomas on 10/25/2017.
 */
public class ImageTabView extends AbstractView<ImageTabController> {
    public ImageTabView(ImageTabController controller) {
        super(controller);
    }

    @Override
    public Node buildScene() {
        StackPane stackPane = new StackPane();
        ImageView imageView = new ImageView("pewpewpew.PNG");
        stackPane.getChildren().add(imageView);
        return stackPane;
    }
}
