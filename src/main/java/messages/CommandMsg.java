package messages;
import java.io.Serializable;
/**
 * Message witch include command and arguments
 */
public class CommandMsg implements Serializable {
    private String commandName;
    private String commandStringArgument;
    private Serializable commandObjectArgument;
    private User user;

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCommandObjectArgument(Serializable commandObjectArgument) {
        this.commandObjectArgument = commandObjectArgument;
    }

    public void setCommandStringArgument(String commandStringArgument) {
        this.commandStringArgument = commandStringArgument;
    }

    public CommandMsg(String commandNm, String commandSA, Serializable commandOA,User user) {
        commandName = commandNm;
        commandStringArgument = commandSA;
        commandObjectArgument = commandOA;
        this.user=user;
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
