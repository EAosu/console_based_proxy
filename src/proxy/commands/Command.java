package proxy.commands;

/**
 The Command interface represents a command to be executed.
 */

public interface Command {
    /**
     * The name of the file to be used by the command.
     */
    String fileName = "blocked.txt";

    /**
     * Executes the command.
     */
    void execute();
}