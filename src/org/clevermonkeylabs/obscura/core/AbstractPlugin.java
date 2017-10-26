package org.clevermonkeylabs.obscura.core;

import org.clevermonkeylabs.obscura.controller.MenuController;
import org.clevermonkeylabs.obscura.model.ApplicationModel;
import org.clevermonkeylabs.obscura.view.ApplicationView;

/**
 * Created by Thomas on 10/26/2017.
 */
public abstract class AbstractPlugin {
    private String name;
    private PluginGroup group;
    private MenuController controller;

    public AbstractPlugin(String name, PluginGroup group) {
        this.name = name;
        this.group = group;
        this.controller = controller;
    }

    public String getName() {
        return name;
    }

    public PluginGroup getGroup() {
        return group;
    }

    public MenuController getController() {
        return controller;
    }

    public void setController(MenuController controller) {
        this.controller = controller;
    }

    public abstract void run();
}
