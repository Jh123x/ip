import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ultron {

    //Task list to store the tasks
    private final TaskList taskList;

    //Create a UI class
    private UI ui;

    //Get the pattern for the regex for parsing the command
    private final Pattern pattern = Pattern.compile("(^\\s?\\w+\\b) ?(.*)?$");

    public Ultron() {

        //Create a new task list
        taskList = new TaskList();

        //Create new instance of UI
        ui = new UI();

    }

    private int parseInteger(final String args) throws UltronException {
        //Catch any errors in the number
        try {

            //Get the index of the items
            return Integer.parseInt(args) - 1;

        } catch (NumberFormatException e) {

            //Throw a new Ultron exception
            throw new UltronException(args, ExceptionType.INVALID_NUMBER);
        }
    }

    private boolean checkInput(final String input) throws UltronException {
        //Checks if the user wants to quit

        //Use regex to get the grp
        Matcher inputs = this.pattern.matcher(input);

        //Find the items in the group
        if (!inputs.find()) {

            //Throw an invalid command error if it is unable to find any matches
            throw new UltronException(input, ExceptionType.INVALID_COMMAND);
        }

        //Get the command
        String inputCommand = inputs.group(1);

        //Get the other arguments
        String arguments = inputs.group(2);

        //Switch case to process the commands
        switch (inputCommand) {

            //If the user keys in bye
            case "bye":
                return true;

            //If the user keys in list
            case "list":
                //Print out the list so far
                System.out.println("Heh, you cant even remember what you had");

                //Display the content
                this.taskList.displayContent();
                break;
            //Check if the user is asking for help
            case "help":
                //Print the help message
                ui.helpMessage();
                break;
            case "delete":
                //Initialise index
                int index = this.parseInteger(arguments);
                //Check if the index is out of range
                if (index >= this.taskList.size()) {

                    //Throw an Ultron exception if it is out of range
                    throw new UltronException(inputCommand,
                            Integer.toString(index + 1),
                            ExceptionType.INVALID_ARGUMENT);
                }

                //Get the task
                Task tsk = this.taskList.get(index);

                //Remove the task
                this.taskList.remove(index);

                //Print the delete message
                System.out.printf("What are you doing removing this?!?!\n  "
                                + "%s\nNow you have %d burdens%n",
                        tsk,
                        this.taskList.size());
                break;

            //Check if the user is done with any task
            case "done":
                //Initialise index
                index = this.parseInteger(arguments);

                //Mark the task as done
                if (!this.taskList.markDone(index)) {

                    //Throw an error if the method return false
                    throw new UltronException(inputCommand,
                            Integer.toString(index),
                            ExceptionType.INVALID_ARGUMENT);
                }

                //Print the done message
                System.out.printf("Finally! Making yourself useful\n"
                        + "  %s%n", this.taskList.get(index));
                break;
            //Otherwise it will be a task to be added
            default:
                //Init the enum states
                Command command;
                Task task;

                try {
                    //Get the state
                    command = Command.valueOf(inputCommand.toUpperCase());

                } catch (IllegalArgumentException e) {

                    //Throw a Duke exception
                    throw new UltronException(inputCommand,
                            ExceptionType.INVALID_COMMAND);
                }

                //Trim the args
                if (arguments.trim().length() == 0) {

                    //Throw an exception when there is nothing supplied
                    throw new UltronException(inputCommand,
                            ExceptionType.NO_ARGUMENTS_SUPPLIED);
                }

                try {
                    //Create a new task
                    task = command.createTask(arguments);

                } catch (IllegalStateException e) {

                    //Throw a Duke exception
                    throw new UltronException(inputCommand,
                            ExceptionType.INVALID_COMMAND);
                }

                //Add the task to the task list
                this.taskList.add(task);

                //Print out the message
                System.out.printf("Can't you keep track of '%s' yourself?\n"
                        + "Now you have %d burdens%n",
                        task, this.taskList.size());
        }

        //Do not quit
        return false;
    }

    public void mainLoop() {

        //Print the intro
        ui.printIntro();

        // While the user input is not bye
        do {
            // Get the next line as input
            String input = ui.getInput();

            //Print the separator
            ui.showLine();

            //If it is a terminating condition
            try {

                //If the input returns True to quitting
                if (this.checkInput(input.trim())) {

                    //Break out of the loop
                    break;
                }

            //Catch the Ultron exception
            } catch (UltronException e) {

                //Print the error message
                System.out.println(ui.wrapper(e.getMessage()));
            }

            //Print the separator
            ui.showLine();

        } while (true);

        //Print the ending before the bot exits
        ui.printEnd();
    }

    public static void main(final String[] args) {

        //Create a new duke
        Ultron duke = new Ultron();

        //Run the main loop
        duke.mainLoop();

    }
}
