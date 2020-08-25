package ultron.commands;

import ultron.exceptions.ExceptionType;
import ultron.exceptions.UltronException;
import ultron.Parser;
import ultron.Storage;
import ultron.TaskList;
import ultron.UI;

public class DoneCommand extends Command {

    public DoneCommand(String arguments){
        super(false, arguments);
    }

    @Override
    public void execute(TaskList taskList, UI ui, Storage storage) throws UltronException {
        //Initialise index
        int index = Parser.parseInteger(this.getArguments());

        if (this.getArguments().trim().length() > 1){
            throw new UltronException("done", ExceptionType.TOO_MUCH_ARGUMENTS);
        }

        //Mark the task as done
        if (!taskList.markDone(index)) {

            //Throw an error if the method return false
            throw new UltronException("done",
                    Integer.toString(index),
                    ExceptionType.INVALID_ARGUMENT);
        }

        //Print the done message
        ui.print(String.format("Finally! Making yourself useful\n"
                + "  %s%n", taskList.get(index)));
    }
}
