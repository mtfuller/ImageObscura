package org.clevermonkeylabs.obscura.util;

import javafx.beans.NamedArg;
import javafx.scene.control.Alert;

/**
 * Created by Thomas on 10/26/2017.
 */
public class InformationDialog extends Alert {
    public InformationDialog(String title, String message) {
        super(AlertType.INFORMATION);
        this.setTitle(title);
        this.setHeaderText(title);
        this.setContentText(message);
    }
}
