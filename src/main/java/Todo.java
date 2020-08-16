public class Todo extends Task{

    //Call the constructor
    public Todo(String description){

        //Call the superclass constructor
        super(description);

    }

    public static Todo parseCommand(String args){
        return new Todo(args);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
