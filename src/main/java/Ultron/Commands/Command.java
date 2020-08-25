package ultron.commands;

import ultron.Storage;
import ultron.UI;
import ultron.TaskList;
import ultron.exceptions.UltronException;

public abstract class Command {

    private final boolean isExit;
    private final String arguments;

    public Command(boolean isExit, String arguments){
        this.isExit = isExit;
        this.arguments = arguments;
    }

    public abstract void execute(TaskList taskList, UI ui, Storage storage) throws UltronException;

    public boolean isExit(){
        return isExit;
    }

    protected String getArguments(){
        return this.arguments;
    }

}
