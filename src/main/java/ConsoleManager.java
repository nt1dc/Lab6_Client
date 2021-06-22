import java.util.Scanner;

/**
 * Operate console print or parse from int
 */
public class ConsoleManager {
    private Scanner scanner;
    private String[] command;


    /**
     * Constructor, just get scanner to use
     * @param sc Scanner
     */
    public ConsoleManager(Scanner sc){
        scanner = sc;
    }

    /**
     * Static method to print from any part of program
     * @param msg Message
     */
    static public void print(String msg)
    {
        System.out.println(msg);
    }

    /**
     * Static method to print error notification
     * @param msg Message
     */
    static public void printErr(String msg)
    {
        System.out.println("err: " + msg);
    }

    /**
     * Method that ask group from user
     * @return Group
     */
    public StudyGroup askGroup(){

            GroupBuilder newGroup = new GroupBuilder(scanner);
            newGroup.setFields();


        return newGroup.studyGropCreator();
    }


    public void waitCommand(){
        command = new String[]{"", ""};
        while (command[0].equals("")){
            String line = scanner.nextLine();
            if (!line.trim().equals("")){
                command = (line .trim()+ " ").split(" ", 2);
            }
        }
    }

    public String getCommand(){
        return command[0].trim();
    }

    public String getArg(){
        return command[1].trim();
    }
}
