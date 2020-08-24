package Ultron;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

import Ultron.Tasks.Task;
import Ultron.Exceptions.UltronException;
import Ultron.Exceptions.ExceptionType;
import Ultron.Commands.TaskCommand;

/**
 * The main Storage class for Ultron
 */
public class Storage {

    private final File f;

    /**
     * The Storage class
     * This writes the data to a file and retrieves it when needed
     * @param path Path to the storage file
     */
    public Storage(String path){
        //Create a new file object
        f = new File(path);

    }

    /**
     * Encodes a Task to a string
     * @param task A Task to be encoded
     * @return String The encoded String of the task
     */
    private String encode(Task task){
        /*
          @param task Task to be encoded to string
         * @return String containing the command
         */
        return String.format("%s~%s", task.getType(), task.getCommand());
    }

    /**
     * Decodes a string to a Task
     * @param string String to be decoded to a Task
     * @return task A task based on the string
     * @throws UltronException If the command or line is invalid
     **/
    private Task decode(String string) throws UltronException{


        //Split the string according to the ,
        String[] data = string.split("~");
        TaskCommand taskCommand;

        try{
            //Get the command based on the first entry
            taskCommand = TaskCommand.valueOf(data[0].toLowerCase());
        } catch (IllegalArgumentException e){
            throw new UltronException(data[0], ExceptionType.INVALID_COMMAND);
        }

        //Return the task based on the data
        return taskCommand.commandParser.apply(data[1]);

    }

    /**
     * Fetches all of the data in the storage file to an arraylist of task
     * @return taskArrayList An Arraylist containing the tasks stored
     * @throws UltronException If there is an error decoding the file or if there is an IO error
     */
    public ArrayList<Task> load() throws UltronException{

        ArrayList<Task> taskArrayList = new ArrayList<>();

        try{
            Scanner scanner = new Scanner(f);
            while (scanner.hasNext()){
                String nextLine = scanner.nextLine();
                taskArrayList.add(decode(nextLine));
            }
            scanner.close();
            return taskArrayList;
        } catch (FileNotFoundException e){
            return taskArrayList;
        }

    }

    public void writeAll(ArrayList<Task> taskArrayList) throws UltronException{

        //Check if the directory exist
        if(!f.exists()){
            try{
                Files.createDirectory(Path.of(f.getParent()));
            }catch (IOException e){
                throw new UltronException(f.getPath(), ExceptionType.DIRECTORY_NOT_CREATED);
            }
        }

        try {
            FileWriter fw = new FileWriter(f.getPath());
            for(Task task: taskArrayList){
                    fw.write(encode(task)+"\n");
            }
            fw.close();
        } catch (IOException e) {
            throw new UltronException(f.getPath(), ExceptionType.IO_EXCEPTION);
        }
    }

}