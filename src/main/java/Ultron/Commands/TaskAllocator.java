package ultron.commands;

import ultron.Storage;
import ultron.TaskList;
import ultron.UI;
import ultron.exceptions.ExceptionType;
import ultron.exceptions.UltronException;
import ultron.tasks.Task;

public final class TaskAllocator extends Command {

    private final TaskCommand taskCommand;

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

    @Override
    public void execute(final TaskList taskList,
                        final UI ui,
                        final Storage storage) throws UltronException {

        //Init the enum states
        Task task;
        String command = taskCommand.name();

        //Trim the args
        if (getArguments().trim().length() == 0) {

            //Throw an exception when there is nothing supplied
            throw new UltronException(command,
                    ExceptionType.NO_ARGUMENTS_SUPPLIED);
        }

        try {
            //Create a new task
            task = taskCommand.createTask(getArguments());

        } catch (IllegalStateException e) {

            //Throw a Duke exception
            throw new UltronException(command,
                    ExceptionType.INVALID_COMMAND);
        }

        //Add the task to the task list
        taskList.add(task);

        //Print out the message
        ui.print(String.format("Can't you keep track of '%s' yourself?\n"
                        + "Now you have %d burdens%n",
                task, taskList.size()));
    }
}
