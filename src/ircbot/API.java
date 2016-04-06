package ircbot;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class API {

    public static String getAuthPass() throws IOException {
        File file = new File("credentials.properties");
        FileInputStream fileInput = new FileInputStream(file);
        Properties properties = new Properties();
        properties.load(fileInput);
        fileInput.close();
        return properties.getProperty("pass");
    }

    public static String getUsername() throws IOException {
        File file = new File("credentials.properties");
        FileInputStream fileInput = new FileInputStream(file);
        Properties properties = new Properties();
        properties.load(fileInput);
        fileInput.close();
        return properties.getProperty("username");
    }

}
