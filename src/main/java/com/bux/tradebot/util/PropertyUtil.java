package com.bux.tradebot.util;

import java.util.ResourceBundle;

public class PropertyUtil {
    private static final String bundleName = "application";

    public static String read(String propertyName) {
        return bundle().getString(propertyName);
    }

    private static ResourceBundle bundle() {
        return ResourceBundle.getBundle(bundleName);
    }

}
