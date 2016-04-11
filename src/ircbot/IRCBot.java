package ircbot;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

public class IRCBot {
    public static Set<Command> commandList = new HashSet<>();

    @Getter
    public static String channel;

    @Getter
    public static String clientUsername;

    @Getter
    public static String clientPass;

    @Getter
    public static String version;

    public static void main(String[] args) throws Exception {
        // Read username, pass and channel from credentials file
        FileUtils futil = new FileUtils();
        clientUsername = futil.getUsername();
        clientPass = futil.getAuthPass();
        channel = futil.getChannel();
        version = futil.getVersion();

        // Register commands
        new Command(Prefix.QUESTION_MARK, channel, "webchat", "http://webchat.esper.net/?nick=&channels=manuelgu");
        new Command(Prefix.QUESTION_MARK, channel, "ping", Arrays.asList("Ping?", "Pong!"));

        Configuration configuration = new Configuration.Builder()
                .setName(clientUsername)
                .addServer("irc.esper.net")
                .addAutoJoinChannel(channel)
                .addListener(new ChatListener())
                .setVersion(version)
                .setRealName(version)
                .setAutoReconnect(true)
                .setAutoReconnectAttempts(10)
                .setAutoReconnectDelay(20)
                .setLogin(clientUsername)
                .buildConfiguration();

        PircBotX bot = new PircBotX(configuration);
        bot.startBot();
    }
}
