package org.clevermonkeylabs.obscura.core;

/**
 * Created by Thomas on 10/25/2017.
 */
public class AbstractModel<V extends AbstractView> {
    private V view;

    public AbstractModel(V view) {
        this.view = view;
    }

    public V getView() {
        return view;
    }

    public void setView(V view) {
        this.view = view;
    }

    public void dispose() {
        view = null;
    }
}
