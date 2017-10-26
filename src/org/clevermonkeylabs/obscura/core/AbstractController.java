package org.clevermonkeylabs.obscura.core;

/**
 * Created by Thomas on 10/25/2017.
 */
public class AbstractController<M extends AbstractModel, V extends AbstractView> {
    private M model;
    private V view;

    public M getModel() {
        return model;
    }

    public V getView() {
        return view;
    }

    public void setModel(M model) {
        this.model = model;
    }

    public void setView(V view) {
        this.view = view;
    }

    public void dispose() {
        model = null;
        view = null;
    }
}
