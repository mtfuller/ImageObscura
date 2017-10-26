package org.clevermonkeylabs.obscura;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.clevermonkeylabs.obscura.controller.MenuController;
import org.clevermonkeylabs.obscura.core.AbstractPlugin;
import org.clevermonkeylabs.obscura.model.ApplicationModel;
import org.clevermonkeylabs.obscura.util.Logger;
import org.clevermonkeylabs.obscura.view.ApplicationView;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

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

        loadPlugins();
    }

    public void loadPlugins() {
        // Prepare.
        String packageName = "plugins";
        List<Class<AbstractPlugin>> plugins = new ArrayList<>();
        URL root = Thread.currentThread().getContextClassLoader().getResource(packageName.replace(".", "/"));

        // Filter .class files.
        File[] files = new File[0];
        try {
            files = new File(URLDecoder.decode(root.getFile(), "UTF-8")).listFiles((dir, name) -> name.endsWith(".class"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        // Find classes implementing AbstractPlugin.
        for (File file : files) {
            String className = file.getName().replaceAll(".class$", "");
            Class<?> cls = null;
            try {
                cls = Class.forName(packageName + "." + className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (AbstractPlugin.class.isAssignableFrom(cls)) {
                plugins.add((Class<AbstractPlugin>) cls);
            }
        }

        for (Class<AbstractPlugin> p : plugins) {
            try {
                menuController.addPlugin(p);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
            Logger.info("Successfully loaded plugin: "+p.getSimpleName());
        }
    }

    public void launch() {
        Logger.info("Starting application...");
        Parent root = (Parent) applicationView.getRoot();
        Scene scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        applicationView.setScene(scene);
        stage.setScene(scene);
        stage.show();
        Logger.info("Application is running with PID: " + ManagementFactory.getRuntimeMXBean().getName() + ".");
    }
}
