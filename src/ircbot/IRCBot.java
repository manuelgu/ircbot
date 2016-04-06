package ircbot;

import org.jibble.pircbot.PircBot;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class IRCBot extends PircBot {
    private static IRCBot instance = new IRCBot();
    public static Set<Command> commandList = new HashSet<>();

    private static final String CHANNEL = "#manuelgu";
    private static String clientUsername;
    private static String clientPass;

    public static void main(String[] args) throws Exception {
        // Enable verbose mode
        instance.setVerbose(true);

        // Set login name via reflection
        Field field = PircBot.class.getDeclaredField("_login");
        field.setAccessible(true);
        field.set(instance, clientUsername);

        // Set version
        instance.setVersion("Serving you 24/7");

        // Read username and pass from credentials file
        clientUsername = API.getUsername();
        clientPass = API.getAuthPass();

        // Register commands
        new Command(Prefix.QUESTION_MARK, CHANNEL, "webchat", "http://webchat.esper.net/?nick=&channels=manuelgu");
        new Command(Prefix.QUESTION_MARK, CHANNEL, "ping", Arrays.asList("Ping?", "Pong!"));

        // Connect and authorize
        instance.setName(clientUsername);
        instance.connect("irc.esper.net", 6667);
        instance.identify(clientPass);

        // Join channel
        instance.joinChannel(CHANNEL);
    }

    @Override
    protected void onMessage(String channel, String sender, String login, String hostname, String message) {
        // Command
        if (validateCommand(message)) {
            String prefix = message.substring(0, 1);
            String command = message.substring(1);
            onCommand(Command.fromCommand(prefix, command));
        }

        super.onMessage(channel, sender, login, hostname, message);
    }

    @Override
    protected void onDisconnect() {
        while (!isConnected()) {
            try {
                reconnect();
            } catch (Exception e) { }
        }
        super.onDisconnect();
    }

    @Override
    protected void onPrivateMessage(String sender, String login, String hostname, String message) {
        sendMessage(sender, "Please message 'manuelgu', I'm just a bot.");
        super.onPrivateMessage(sender, login, hostname, message);
    }

    @Override
    protected void onVoice(String channel, String sourceNick, String sourceLogin, String sourceHostname, String recipient) {
        if (recipient.equalsIgnoreCase(clientUsername)) {
            sendMessage(channel, "Thanks!");
        }
        super.onVoice(channel, sourceNick, sourceLogin, sourceHostname, recipient);
    }

    /**
     * Call onCommand method to handle input
     * @param command       Command to parse
     */
    private void onCommand(Command command) {
        String message = "";
        if (!command.getMessages().isEmpty()) {
            Random random = new Random();
            message = command.getMessages().get(random.nextInt(command.getMessages().size()));
        } else {
            message = command.getMessage();
        }
        sendMessage(command.getChannel(), message);
    }

    /**
     * Validates whether a message contains a valid command or not
     * @param message       Message to make checks against
     * @return              True if command is valid
     */
    private boolean validateCommand(String message) {
        message = message.toLowerCase();
        String prefixStr = message.substring(0, 1);
        String commandStr = message.substring(1);

        Command command = Command.fromCommand(prefixStr, commandStr);

        if (command == null) {
            return false;
        }
        if (message.length() < 2) {
            return false;
        }
        if (!Arrays.asList(Prefix.values()).contains(command.getPrefix())) {
            return false;
        }
        for (Command c : commandList) {
            System.out.println(c.getCommand());
            if (!c.getCommand().equalsIgnoreCase(command.getCommand())) {
                continue;
            }
            return true;
        }
        return true;
    }
}
