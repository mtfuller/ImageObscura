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
        Logger.info("This is the Test Plugin!");
    }
}
