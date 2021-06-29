package messages;

import java.io.Serializable;

/**
 * Message witch include command and arguments
 */
public class CommandMsg implements Serializable {
    private String commandName;
    private String commandStringArgument;
    private Serializable commandObjectArgument;

    public void setCommandObjectArgument(Serializable commandObjectArgument) {
        this.commandObjectArgument = commandObjectArgument;
    }

    public void setCommandStringArgument(String commandStringArgument) {
        this.commandStringArgument = commandStringArgument;
    }

    public CommandMsg(String commandNm, String commandSA, Serializable commandOA) {
        commandName = commandNm;
        commandStringArgument = commandSA;
        commandObjectArgument = commandOA;
    }

    /**
     * @return Command name.
     */
    public String getCommandName() {
        return commandName;
    }

    /**
     * @return Command string argument.
     */
    public String getCommandStringArgument() {
        return commandStringArgument;
    }

    /**
     * @return Command object argument.
     */
    public Object getCommandObjectArgument() {
        return commandObjectArgument;
    }
}
