package com.github.vedeshkin;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Created by vvedeshkin on 4/24/2017.
 */
public class LoggerConfig {

    private static boolean isInitialized;
    private static Logger logger = Logger.getLogger(LoggerConfig.class.getName());

    public static void initializeLogger() {
        if (isInitialized) {
            logger.info("Logger already initialized");
            return;
        }
        LogManager manager = LogManager.getLogManager();

        try (InputStream is = new FileInputStream("C:\\Users\\vvedeshkin\\IdeaProjects\\VKUnirest\\src\\resources\\logger.properties")) {
            manager.readConfiguration(is);
            isInitialized = true;
            logger.info("Logger configuration has been loaded.");
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "Unable to locate file", e);
            logger.severe("Initialization of logger has been failed");
            System.exit(1);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "IO error has occurred", e);
            logger.severe("Initialization of logger has been failed");
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        LoggerConfig.initializeLogger();
    }
}
