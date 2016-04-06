package ircbot;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class API {

    /**
     * Get the username of the client specified in the <i>credentials.properties<i>
     * @return IRC username
     * @throws IOException
     */
    public static String getUsername() throws IOException {
        File file = new File("credentials.properties");
        FileInputStream fileInput = new FileInputStream(file);
        Properties properties = new Properties();
        properties.load(fileInput);
        fileInput.close();
        return properties.getProperty("username");
    }

    /**
     * Get the NickServ password specified in the <i>credentials.properties<i>
     * @return NickServ password
     * @throws IOException
     */
    public static String getAuthPass() throws IOException {
        File file = new File("credentials.properties");
        FileInputStream fileInput = new FileInputStream(file);
        Properties properties = new Properties();
        properties.load(fileInput);
        fileInput.close();
        return properties.getProperty("pass");
    }
}
