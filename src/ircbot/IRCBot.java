package ircbot;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.UtilSSLSocketFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

public class IRCBot {
    public static Set<Command> commandList = new HashSet<>();
    public static Set<String> ops = new HashSet<>();
    public static Set<String> autoJoinChannels = new HashSet<>();

    @Getter
    public static String channel;

    @Getter
    public static String clientUsername;

    @Getter
    public static String clientPass;

    @Getter
    public static String version;

    public static void main(String[] args) throws Exception {
        // Read credentials from properties file
        FileUtils futil = new FileUtils();
        clientUsername = futil.getUsername();
        clientPass = futil.getAuthPass();
        channel = futil.getChannel();
        version = futil.getVersion();

        // Register commands
        new Command(Prefix.QUESTION_MARK, channel, "webchat", "http://webchat.esper.net/?nick=&channels=manuelgu");
        new Command(Prefix.QUESTION_MARK, channel, "ping", Arrays.asList("Ping?", "Pong!"));

        // Add OPs that have full access to the bot
        ops.add("manuelgu");

        // Add auto join channels (w/out #)
        autoJoinChannels.add("manuelgu");

        Configuration configuration = new Configuration.Builder()
                // Set name and details
                .setName(clientUsername)
                .setVersion(version)
                .setRealName(version)
                .setLogin(clientUsername)
                // Use SSL
                .addServer("irc.esper.net", 6697)
                .setSocketFactory(new UtilSSLSocketFactory().trustAllCertificates())
                // Authenticate with NickServ
                .setNickservNick(clientUsername)
                .setNickservPassword(clientPass)
                // Add listener
                .addListener(new ChatListener())
                // Auto reconnect
                .setAutoReconnect(true)
                .setAutoReconnectAttempts(10)
                .setAutoReconnectDelay(20 * 1000)

                .buildConfiguration();

        PircBotX bot = new PircBotX(configuration);
        bot.startBot();
    }
}
