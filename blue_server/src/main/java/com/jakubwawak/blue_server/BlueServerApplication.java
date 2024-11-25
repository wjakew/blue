/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.blue_server;

import com.jakubwawak.blue_server.backend.database.Database;
import com.jakubwawak.blue_server.backend.maintanance.ConsoleColors;
import com.jakubwawak.blue_server.backend.maintanance.Properties;
import com.vaadin.flow.spring.annotation.EnableVaadin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.component.page.AppShellConfigurator;

/**
 * blue_server a server for storing blue notes - out of the blue
 */
@SpringBootApplication
@EnableVaadin({ "com.jakubwawak" })
@Theme("bluetheme")
public class BlueServerApplication implements AppShellConfigurator {

    public static String version = "1.0.0";

    public static String build = "blueS251124REV1";

    public static Properties properties;

    public static Database database;

    public static String serverName;

    /**
     * Function for running the application
     * 
     * @param args
     */
    public static void main(String[] args) {
        showHeader();
        properties = new Properties("blue.properties");
        if (properties.fileExists) {
            properties.parsePropertiesFile();

            // Check for blueSecret and tokenExpiration properties
            String blueSecret = properties.getValue("blueSecret");
            String tokenExpiration = properties.getValue("tokenExpiration");
            if (blueSecret == null || blueSecret.isEmpty() || tokenExpiration == null || tokenExpiration.isEmpty()) {
                System.out.println(ConsoleColors.RED_BOLD
                        + "Values for blueSecret and tokenExpiration must be filled for security reasons."
                        + ConsoleColors.RESET);
                return; // Exit the application
            }

            serverName = properties.getValue("serverName");

            database = new Database();
            database.setDatabase_url(properties.getValue("databaseString"));
            if (database.database_url != null) {
                database.connect();
                if (database.connected) {
                    SpringApplication.run(BlueServerApplication.class, args);
                }
            } else {
                database.log("URL-NOT-SET", "Database URL not set in properties file");
            }
        } else {
            System.out.println(ConsoleColors.RED_BOLD + "Properties file not found, creating..." + ConsoleColors.RESET);
            properties.createPropertiesFile();
        }
    }

    /**
     * Function for showing header
     */
    static void showHeader() {
        String header = " _     _                                             \n"
                + "| |__ | |_   _  ___     ___  ___ _ ____   _____ _ __ \n"
                + "| '_ \\| | | | |/ _ \\   / __|/ _ \\ '__\\ \\ / / _ \\ '__|\n"
                + "| |_) | | |_| |  __/   \\__ \\  __/ |   \\ V /  __/ |   \n"
                + "|_.__/|_|\\__,_|\\___|___|___/\\___|_|    \\_/ \\___|_|   \n" + "                  |_____|  ";
        System.out.println(ConsoleColors.BLUE_BOLD_BRIGHT + header + version + "/" + build + ConsoleColors.RESET);
    }
}
