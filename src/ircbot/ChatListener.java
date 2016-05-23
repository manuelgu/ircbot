package ircbot;

import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ConnectEvent;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.hooks.events.VoiceEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ChatListener extends ListenerAdapter {
    Logger privLog;
    FileHandler fh;

    public ChatListener() {
        try {
            privLog = Logger.getLogger("PrivateMSG");
            fh = new FileHandler("privatemessages.log");
            privLog.addHandler(fh);

            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(MessageEvent event) throws Exception {
        // Command
        if (validateCommand(event.getMessage())) {
            String prefix = event.getMessage().substring(0, 1);
            String command = event.getMessage().substring(1);
            event.respondChannel(onCommand(Command.getCommand(prefix, command)));
        }
    }

    @Override
    public void onVoice(VoiceEvent event) throws Exception {
        if (event.getRecipient() == null) {
            return;
        }
        if (event.getRecipient().getNick().equalsIgnoreCase(IRCBot.getClientUsername())) {
            event.respond("Thanks!");
        }
    }

    @Override
    public void onPrivateMessage(PrivateMessageEvent event) throws Exception {
        if (event.getUser() == null) {
            return;
        }
        User user = event.getUser();
        if (IRCBot.ops.contains(user.getNick())) {
            String message = event.getMessage();
            if (message.startsWith("!join")) {
                String channel = message.substring(6);
                event.getBot().sendIRC().joinChannel(channel);
            }
            if (message.startsWith("!part")) {
                String channel = message.substring(6);
                event.getBot().sendRaw().rawLine("PART " + channel);
            }
            if (message.startsWith("!quit")) {
                event.getBot().sendIRC().quitServer();
            }

        } else {
            event.respondPrivateMessage("Please message 'manuelgu', I'm just a bot.");
            privLog.info("[" + event.getUser().getNick() + "] " + event.getMessage());
        }
    }

    @Override
    public void onJoin(JoinEvent event) throws Exception {
        if (event.getUser() == null) {
            return;
        }
        if (!event.getUser().getNick().equalsIgnoreCase(event.getBot().getNick())) {
            event.respond("Hi! Please be patient, it might be that nobody is around at the moment.");
        }
    }

    @Override
    public void onConnect(ConnectEvent event) throws Exception {
        for (String channel : IRCBot.autoJoinChannels) {
            if (channel == null) {
                return;
            }
            event.getBot().sendIRC().joinChannel("#" + channel);
        }
    }

    /**
     * Call onCommand method to handle input
     *
     * @param command command to parse
     * @return respond message
     */
    private String onCommand(Command command) {
        String message;
        // Multiple answers available?
        if (!command.getMessages().isEmpty()) {
            // Pick random
            Random random = new Random();
            message = command.getMessages().get(random.nextInt(command.getMessages().size()));
        } else {
            message = command.getMessage();
        }
        return message;
    }

    /**
     * Validates whether a message contains a valid command or not
     *
     * @param message message to make checks against
     * @return true if command is valid
     */
    private boolean validateCommand(String message) {
        message = message.toLowerCase();
        String prefixStr = message.substring(0, 1);
        String commandStr = message.substring(1);

        Command command = Command.getCommand(prefixStr, commandStr);

        if (command == null) {
            return false;
        }
        // Message at least prefix + one character
        if (message.length() < 2) {
            return false;
        }
        // Prefix is valid
        if (!Arrays.asList(Prefix.values()).contains(command.getPrefix())) {
            return false;
        }
        for (Command c : IRCBot.commandList) {
            if (!command.getCommand().equalsIgnoreCase(c.getCommand())) {
                continue;
            }
            return true;
        }
        return true;
    }
}
