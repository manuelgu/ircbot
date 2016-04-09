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
        return readProperty("credentials.properties", "username");
    }

    /**
     * Get the NickServ password specified in the <i>credentials.properties<i>
     * @return NickServ password
     * @throws IOException
     */
    public static String getAuthPass() throws IOException {
        return readProperty("credentials.properties", "pass");
    }

    /**
     * Get the channel specified in the <i>credentials.properties</i>
     * @return Channel name
     * @throws IOException
     */
    public static String getChannel() throws IOException {
        return readProperty("credentials.properties", "channel");
    }

    /**
     * Get the version specified in the <i>credentials.properties</i>
     * @return Version string
     * @throws IOException
     */
    public static String getVersion() throws IOException {
        return readProperty("credentials.properties", "version");
    }

    /**
     * Read from a properties file and get property value
     * @param fileName      filename of .properties file
     * @param property      property key
     * @return              property key, default null
     * @throws IOException
     */
    public static String readProperty(String fileName, String property) throws IOException {
        File file = new File(fileName);
        FileInputStream fileInput = new FileInputStream(file);
        Properties propertis = new Properties();
        propertis.load(fileInput);
        fileInput.close();
        return propertis.getProperty(property, null);
    }

}
