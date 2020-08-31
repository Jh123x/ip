package ultron;

import java.util.ArrayList;

import javafx.application.Platform;
import ultron.commands.Command;
import ultron.exceptions.UltronException;
import ultron.ui.UI;

public final class Ultron {
    /** Store the UI class. */
    private final UI ui;
    /** Store the Storage. */
    private final Storage storage;
    /** Task list to store the tasks. */
    private TaskList taskList;

    /**
     * The Ultron class.
     */
    public Ultron() {
        String path = "data/data.txt";
        storage = new Storage(path);

        try {

            //Fetch all from storage
            this.taskList = new TaskList(storage.load());

        } catch (UltronException e) {

            //Initialise the blank arraylist
            this.taskList = new TaskList(new ArrayList<>());

        }
        //Create new instance of UI
        ui = new UI();

    }

    /**
     * GetResponse of Ultron based on input.
     *
     * @param input input from the user.
     * @return String message by Ultron for the user.
     */
    public String getResponse(String input) {

        //Check if it is the command to show the intro message
        if (input == "showIntro") {
            ui.setIntro();
            return ui.getMessage();
        } else {
            try {
                //Get the command to execute
                Command c = Parser.parseCommand(input);

                //Execute the command
                c.execute(taskList, ui, storage);

                //Check if it is a quitting command
                if (c.isExit()) {

                    //Close the application
                    Platform.exit();
                }

            } catch (UltronException e) {
                //Return the error message
                ui.setMessage(e.getMessage());
            }
        }

        //Return the message stored in the UI
        return ui.getMessage();
    }

}
