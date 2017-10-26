package org.clevermonkeylabs.obscura.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Thomas on 10/25/2017.
 */
public class Logger {
    private static boolean isQuiet = false;

    private static final String DEBUG_FORMAT = "[%21s] - [%s]: %-80s";

    public static boolean isQuiet() {
        return isQuiet;
    }

    public static void setIsQuiet(boolean isQuiet) {
        Logger.isQuiet = isQuiet;
    }

    public static String getTimestamp() {
        return new SimpleDateFormat("MM-dd-yyyy @ HH:mm:ss").format(new Date());

    }

    public static void debug(String level, String message) {
        System.out.println(String.format(DEBUG_FORMAT, getTimestamp(), level, message));
    }

    public static void info(String message) {
        debug("INFO", message);
    }

    public static void error(String message) {
        debug("ERROR", message);
    }

    public static void warn(String message) {
        debug("WARN", message);
    }
}
