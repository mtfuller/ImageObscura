package plugins;

import org.clevermonkeylabs.obscura.core.AbstractPlugin;
import org.clevermonkeylabs.obscura.core.PluginGroup;
import org.clevermonkeylabs.obscura.util.Logger;

/**
 * Created by Thomas on 10/26/2017.
 */
public class TestPlugin extends AbstractPlugin {
    public TestPlugin() {
        super("Test Plugin", PluginGroup.FILTER);
    }

    @Override
    public void run() {
        String temp = "1.few";
        Logger.info("NUMBER: " + Double.parseDouble(temp));
        Logger.info("This is the Test Plugin!");
    }
}
