package com.glsx.configure;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * Created by xiaolong on 2016/8/19.
 */
public class ConfigureFactory {

    private static CompositeConfiguration conf = null;

    private ConfigureFactory() {
    }

    public static CompositeConfiguration getConf() {
        if (conf == null) {
            synchronized (ConfigureFactory.class) {
                if (conf == null) {
                    conf = new CompositeConfiguration();
                    try {
                        conf.addConfiguration(new PropertiesConfiguration("conf/user.properties"));
                    } catch (ConfigurationException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return conf;
    }

}
