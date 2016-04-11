package ircbot;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FileUtils {

    /**
     * Get the username of the client specified in the <i>credentials.properties<i>
     * @return IRC username
     * @throws IOException
     */
    public String getUsername() throws IOException {
        return readProperty("credentials.properties", "username");
    }

    /**
     * Get the NickServ password specified in the <i>credentials.properties<i>
     * @return NickServ password
     * @throws IOException
     */
    public String getAuthPass() throws IOException {
        return readProperty("credentials.properties", "pass");
    }

    /**
     * Get the channel specified in the <i>credentials.properties</i>
     * @return channel name
     * @throws IOException
     */
    public String getChannel() throws IOException {
        return readProperty("credentials.properties", "channel");
    }

    /**
     * Get the version specified in the <i>credentials.properties</i>
     * @return version string
     * @throws IOException
     */
    public String getVersion() throws IOException {
        return readProperty("credentials.properties", "version");
    }

    /**
     * Read from a properties file and get property value
     * @param fileName      filename of .properties file
     * @param property      property key
     * @return              property key, default null
     * @throws IOException
     */
    private String readProperty(String fileName, String property) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream fileInput = classLoader.getResourceAsStream(fileName);
        Properties properties = new Properties();
        properties.load(fileInput);
        fileInput.close();
        return properties.getProperty(property, null);
    }
}
