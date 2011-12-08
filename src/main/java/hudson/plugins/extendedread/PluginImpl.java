/**
 * Copyright (c) 2009 Yahoo! Inc.
 * All rights reserved.
 * The copyrights to the contents of this file are licensed under the MIT License
 * (http://www.opensource.org/licenses/mit-license.php)
 */

package hudson.plugins.extendedread;

import hudson.Plugin;
import hudson.model.Item;

import org.apache.log4j.Logger;

/**
 * @author dty
 */
public class PluginImpl extends Plugin {
    private static final Logger logger = Logger.getLogger(PluginImpl.class.getName());
    static String propertyName = "hudson.security.ExtendedReadPermission";

    @Override
    public void start() throws Exception {
        String propertyValue = System.getProperty(propertyName);
        // Only enable if not explicitly controlled by the system property
        if (propertyValue == null) {
            enablePermission();
        } else {
            logger.info("Controlled by system property (" +
                            propertyName + "=" + propertyValue + ")");
        }
    }

    private void enablePermission() throws Exception {
        try {
            // Force fail if field is not declared, say on earlier versions
            Item.class.getDeclaredField("EXTENDED_READ");
            Item.EXTENDED_READ.setEnabled(true);
        } catch (NoSuchFieldException e) {
            // Quietly ignore non-existence of the permission.
            logger.info("Hudson version older than 1.324");
        }
    }
}
