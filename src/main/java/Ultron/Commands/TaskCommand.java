package ultron.commands;

import ultron.tasks.Deadline;
import ultron.tasks.Event;
import ultron.tasks.Task;
import ultron.tasks.Todo;

import java.util.function.Function;

public enum TaskCommand {

    /**
     * Enum for the TODO task.
     */
    todo(Todo::parseCommand),

    /**
     * Enum for the Event class.
     */
    event(Event::parseCommand),

    /**
     * Enum for the deadline class.
     */
    deadline(Deadline::parseCommand);

    /**
     * To store the parseCommand method of the different classes.
     */
    private final Function<String, Task> commandParser;


    /**
     * Constructor for TaskCommand.
     * @param commandParser A function containing the parseCommand method for the classes
     */
    TaskCommand(final Function<String, Task> commandParser) {
        this.commandParser = commandParser;
    }

    //Get the task given description

    /**
     * Create a task based on the description.
     * @param description
     * @return Task with the description
     */
    public Task createTask(final String description) {
        return commandParser.apply(description);
    }

    public Function<String, Task> getCommandParser() {
        return commandParser;
    }


}
