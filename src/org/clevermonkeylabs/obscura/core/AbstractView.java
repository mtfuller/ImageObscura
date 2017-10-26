package org.clevermonkeylabs.obscura.core;

import javafx.scene.Node;
import javafx.scene.Scene;

/**
 * Created by Thomas on 10/25/2017.
 */
public abstract class AbstractView<C extends AbstractController> {
    private C controller;
    private Node root;

    public AbstractView(C controller) {
        this.controller = controller;
        root = buildScene();
    }

    public C getController() {
        return controller;
    }

    public Node getRoot() {
        return root;
    }

    public void setController(C controller) {
        this.controller = controller;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public void dispose() {
        controller = null;
        root = null;
    }

    public abstract Node buildScene();
}
