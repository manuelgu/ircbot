package ircbot;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a command
 *
 */
public class Command {
    @Getter
    @Setter
    private Prefix prefix;

    @Getter
    @Setter
    private String channel;

    @Getter
    @Setter
    private String command;

    @Getter
    @Setter
    private String message;

    @Getter
    @Setter
    private List<String> messages;

    public Command(Prefix prefix, String channel, String command, List<String> messages) {
        this.prefix = prefix;
        this.channel = channel;
        this.command = command;
        this.messages = messages;
        this.message = null;
        IRCBot.commandList.add(this);
    }

    public Command(Prefix prefix, String channel, String command, String message) {
        this.prefix = prefix;
        this.channel = channel;
        this.command = command;
        this.messages = Arrays.asList();
        this.message = message;
        IRCBot.commandList.add(this);
    }

    /**
     * Get the command object from the actual command
     * @param cmd       command as string without prefix
     * @return          command object with all arguments
     */
    public static Command getCommand(String prefix, String cmd) {
        for (Command a : IRCBot.commandList) {
            if (a.getCommand().equalsIgnoreCase(cmd) && a.getPrefix().toString().equals(prefix)) {
                return a;
            }
        }
        return null;
    }

}
