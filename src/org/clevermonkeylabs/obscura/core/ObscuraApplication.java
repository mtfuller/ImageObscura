package org.clevermonkeylabs.obscura.core;

import javafx.stage.Stage;

/**
 * Created by Thomas on 10/12/2017.
 */
public class ObscuraApplication {
    private ApplicationView applicationView;
    private ApplicationModel applicationModel;

    public ObscuraApplication(Stage mainStage) {
        applicationView = new ApplicationView(mainStage);
        applicationModel = new ApplicationModel();
    }

    public void launch() {
        applicationView.show();
    }
}
