package ultron.commands;

import ultron.Storage;
import ultron.TaskList;
import ultron.exceptions.ExceptionType;
import ultron.exceptions.UltronException;
import ultron.tasks.Task;
import ultron.ui.UI;

public final class TaskAllocator extends Command {

    /**
     * Store TaskCommand corresponding to the command.
     */
    private final TaskCommand taskCommand;

    /**
     * Allocates the correct task.
     *
     * @param command   Command given.
     * @param arguments Arguments given.
     * @throws UltronException if the command is invalid.
     */
    public TaskAllocator(final String command,
                         final String arguments) throws UltronException {

        //Call the superclass
        super(false, arguments);

        try {
            //Get the state
            this.taskCommand = TaskCommand.valueOf(command.toLowerCase());

        } catch (IllegalArgumentException e) {

            //Throw a Duke exception
            throw new UltronException(command,
                ExceptionType.INVALID_COMMAND);
        }
    }

    /**
     * Add the correct Task to the tasklist.
     *
     * @param taskList List of tasks.
     * @param ui       UI for Ultron.
     * @param storage  Storage for Ultron.
     * @throws UltronException if there are no arguments or.
     *                         if there was an error creating the task.
     */
    @Override
    public void execute(final TaskList taskList,
                        final UI ui,
                        final Storage storage) throws UltronException {

        //Init the enum states
        Task task;
        String command = taskCommand.name();

        //Trim the args
        if (getArgument().trim().length() == 0) {

            //Throw an exception when there is nothing supplied
            throw new UltronException(command,
                    ExceptionType.NO_ARGUMENTS_SUPPLIED);
        }

        try {
            //Create a new task
            task = taskCommand.createTask(getArgument());

        } catch (IllegalStateException e) {

            //Throw a Duke exception
            throw new UltronException(command,
                    ExceptionType.INVALID_COMMAND);
        }

        //Add the task to the task list
        taskList.add(task);

        //Print out the message
        ui.setMessage(String.format("Can't you keep track of '%s' yourself?\n"
                + "Now you have %d burdens%n",
                task, taskList.size()));
    }
}
