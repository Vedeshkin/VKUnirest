package com.github.vedeshkin;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vvedeshkin on 4/24/2017.
 */
public class PropertiesHolder {

    private static PropertiesHolder instance;
    private final Properties confProp = new Properties();
    private final static Logger LOGGER = Logger.getLogger(PropertiesHolder.class.getName());

    private PropertiesHolder() {
            /*TODO:
            /General idea is to load here all properties.
            /So we need to implement mechanism to load all properties files from here.
            */
        try ( FileInputStream in = new FileInputStream("C:/Users/vvedeshkin/IdeaProjects/VKUnirest/src/resources/server.properties")){
            confProp.load(in);
            LOGGER.info("Properties has been loaded");
        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "Properties file not found", ex);
            LOGGER.severe("System shutdown");
            System.exit(1);
        } catch (IOException iex) {
            LOGGER.log(Level.SEVERE, "An error has occurred", iex);
            LOGGER.severe("System shutdown");
            System.exit(1);
        }
    }

    public static PropertiesHolder getInstance() {
        if (instance == null) instance = new PropertiesHolder();
        return instance;
    }

    public String getProperty(String key) {
        return confProp.getProperty(key);
    }

    public boolean containsKey(String key) {
        return confProp.containsKey(key);
    }

    public static void main(String[] args) {
        LoggerConfig.initializeLogger();
        PropertiesHolder ph = PropertiesHolder.getInstance();
        LoggerConfig.initializeLogger();

    }
}
