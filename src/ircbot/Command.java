package ircbot;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

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

    public Command(Prefix prefix, String channel, String command, String message) {
        this.prefix = prefix;
        this.channel = channel;
        this.command = command;
        this.message = message;
        IRCBot.commandList.add(this);
    }

    public Command(Prefix prefix, String channel, String command, List<String> messages) {
        this.prefix = prefix;
        this.channel = channel;
        this.command = command;
        this.messages = messages;
        IRCBot.commandList.add(this);
    }

    /**
     * Get the command object from the actual command
     * <b>MIGHT CONTAIN DUPLICATE ENTRIES</b>
     * @param cmd       Command as string without prefix
     * @return          Command object with all arguments
     */
    public static Command fromCommand(String cmd) {
        for (Command a : IRCBot.commandList) {
            if (a.getCommand().equalsIgnoreCase(cmd)) {
                return a;
            }
        }
        return null;
    }

}
