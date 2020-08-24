package Ultron.Commands;

import Ultron.Exceptions.ExceptionType;
import Ultron.Exceptions.UltronException;
import Ultron.Storage;
import Ultron.TaskList;
import Ultron.UI;

public class HelpCommand extends Command {

    public HelpCommand(String arguments){
        super(false, arguments);
    }

    @Override
    public void execute(TaskList taskList, UI ui, Storage storage) throws UltronException {

        if (this.getArguments().trim().length() > 0)
            throw new UltronException("list", ExceptionType.TOO_MUCH_ARGUMENTS);

        System.out.println("Heh I guess I could help an insect like you:\n"
                + "- help                      : Get help for the commands\n"
                + "- todo (name)               : Adds a todo to the list\n"
                + "- event (name) /at (date)   : Adds an event at date\n"
                + "- deadline (name) /by (date): "
                + "Adds a deadline which expires by date\n"
                + "- delete (number)           : Removes a todo from the list\n"
        );
    }
}