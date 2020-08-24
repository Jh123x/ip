public class ListCommand extends Command {

    public ListCommand(String arguments){
        super(false, arguments);
    }

    @Override
    public void execute(TaskList taskList, UI ui, Storage storage) throws UltronException{

        //Check if there are arguments
        if (this.getArguments().trim().length() > 0){

            throw new UltronException("list", ExceptionType.TOO_MUCH_ARGUMENTS);

        }

        //If the list is empty
        if(taskList.size() == 0){

            //When there is no task
            System.out.println("You have no business with me");

        } else {

            //Print the starting list
            System.out.println("Heh, you cant even remember what you had");

            //Iterate through the task and print it
            for(int i = 0; i< taskList.size(); ++i){

                //Print out each item on the list
                System.out.printf("%d.%s\n", i+1, taskList.get(i));

            }

        }

    }

}
