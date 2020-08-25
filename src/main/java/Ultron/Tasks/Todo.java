package ultron.tasks;

public class Todo extends Task {

    //Call the constructor
    public Todo(final String description) {

        //Call the superclass constructor
        super(description);

    }

    public static Todo parseCommand(final String args) {
        return new Todo(args);
    }

    @Override
    public String getType() {
        return "TODO";
    }

    @Override
    public String getCommand() {
        return String.format("%s", getMessage());
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
