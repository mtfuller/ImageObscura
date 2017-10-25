import javafx.application.Application;
import javafx.stage.Stage;
import org.clevermonkeylabs.obscura.ObscuraApplication;
/**
 * Created by Thomas on 9/28/2017.
 */
public class Launcher extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        ObscuraApplication app = new ObscuraApplication(primaryStage);
        app.launch();
    }

    public static void main (String[] args) {
        launch(args);
    }
}
