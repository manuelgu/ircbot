package ircbot;

import org.jibble.pircbot.PircBot;
import java.util.ArrayList;
import java.util.List;

public class IRCBot extends PircBot {
    private static IRCBot instance = new IRCBot();
    public static List<Command> commandList = new ArrayList<>();

    private static final String CHANNEL = "#manuelgu";

    public static void main(String[] args) throws Exception {
        // Enable verbose mode
        instance.setVerbose(true);

        // Register commands
        new Command(Prefix.QUESTION_MARK, CHANNEL, "test", "Mommy, it works!");

        // Connect and authorize
        instance.setName("mServant");
        instance.connect("irc.esper.net", 6667);
        instance.identify(API.getAuthPass());

        // Join channel
        instance.joinChannel(CHANNEL);
    }

    @Override
    protected void onMessage(String channel, String sender, String login, String hostname, String message) {
        message = message.toLowerCase();

        String command = message.substring(1);

        onCommand(Command.fromCommand(command));



//        // Voice-Only
//        if (message.startsWith("!ping")) {
//            sendMessage(channel, "Pong!");
//        }
//
//        // Everybody
//        if (message.startsWith("!webchat")) {
//            sendMessage(channel,
//                    "http://webchat.esper.net/?nick=&channels=manuelgu");
//        }
//
//        if (message.startsWith("!lmgtfy")) {
//            if (message.length() > 8) {
//                String query = message.substring(8);
//                query = query.replaceAll(" ", "+");
//                String link = "http://lmgtfy.com/?q=" + query;
//
//                sendMessage(channel, link);
//            } else {
//                sendMessage(channel, "!lmgtfy <query>");
//            }
//        }

        super.onMessage(channel, sender, login, hostname, message);
    }

    @Override
    protected void onDisconnect() {
        while (!isConnected()) {
            try {
                reconnect();
            } catch (Exception e) {
            }
        }

        super.onDisconnect();
    }

    /**
     * Call onCommand method to handle input
     * @param command       Command to parse
     */
    public void onCommand(Command command) {
        sendMessage(command.getChannel(), command.getMessage());
    }
}
