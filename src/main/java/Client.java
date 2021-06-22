

import exeptions.ConnectionBrokenException;
import messages.AnswerMsg;
import messages.CommandMsg;
import messages.Status;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Scanner;


/**
 * Class working with connection
 */
public class Client {
    private String serverHost;
    private int serverPort;
    private int connectionAttempts;
    private int connectionTimeout;

    public int attempts = 0;

    private ObjectOutputStream serverWriter;

    private ObjectInputStream serverReader;

    private Socket socket;

    private ConsoleManager consoleManager;

    public Client(String host, int port, int attempts, int timeout, ConsoleManager cons){
        serverHost = host;
        serverPort = port;
        connectionAttempts = attempts;
        connectionTimeout = timeout;
        consoleManager = cons;
    }

    /**
     * Connect client to server
     * @return is closed successfully or not
     */
    private boolean connectToServer(){
        try {
            if (attempts > 0)
                System.out.println("Попытка переподключиться");
            attempts++;
            socket = new Socket(serverHost, serverPort);
            System.out.println("Получаю разрешение на чтение и запись");
            serverWriter = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Получено разрешение на запись");
            serverReader = new ObjectInputStream(socket.getInputStream());
            System.out.println("Получено разрешение на чтение");
        } catch (UnknownHostException e) {
            ConsoleManager.printErr("Неизвестный хост: " + serverHost + "\n");
            return false;
        } catch (IOException exception) {
            ConsoleManager.printErr("Ошибка открытия порта " + serverPort + "\n");
            return false;
        }
        System.out.println("Порт успешно открыт.");
        return true;
    }

    /**
     * Method write message to server in CommandMsg format
     * @param msg message
     * @throws ConnectionBrokenException If connection was broken
     */
    private void writeMessage(CommandMsg msg) throws ConnectionBrokenException {
        try{
            serverWriter.writeObject(msg);

        } catch (IOException exception) {
            exception.printStackTrace();
            ConsoleManager.printErr("Разрыв соеденения");
            throw new ConnectionBrokenException();
        }
    }

    /**
     * Read message from server in AnswerMsg format
     * @return Message
     * @throws ConnectionBrokenException If connection was broken
     */
    private AnswerMsg readMessage() throws ConnectionBrokenException{
        AnswerMsg retMsg = null;
        try {
            retMsg =  (AnswerMsg) serverReader.readObject();
        } catch (IOException exception) {
            ConsoleManager.printErr("Разрыв соеденения");
            throw new ConnectionBrokenException();
        } catch (ClassNotFoundException exception) {
            ConsoleManager.printErr("Пришедшие данные не класс");
        }
        return retMsg;
    }

    /**
     * Close connection when everything end.
     */
    private void closeConnection(){
        try{
            socket.close();
            serverReader.close();
            serverWriter.close();
            System.out.println("Соеденение успешно закрыто");
        } catch (IOException exception) {
            ConsoleManager.printErr("Ошибка закрытия файлов");
        }
    }

    /**
     * Main function witch get command and send.
     */
    public void run(){
        boolean work = true;
        System.out.println("Подключаюсь к серверу");
        while (!connectToServer()) {
            if(attempts > connectionAttempts){
                ConsoleManager.printErr("Превышено количество попыток подключиться");
                return;
            }
            try {
                Thread.sleep(connectionTimeout);
            } catch (InterruptedException e) {
                ConsoleManager.printErr("Произошла ошибка при попытке ожидания подключения!");
                System.out.println("Повторное подключение будет произведено немедленно.");
            }

        }
        //print("Подключился, работаю");
        while (work){
            //print("Жду команду");
            consoleManager.waitCommand();
            StudyGroup studyGroup = null;
            if (consoleManager.getCommand().equals("add") | consoleManager.getCommand().equals("update")
            | consoleManager.getCommand().equals("add_if_max")){
                GroupBuilder groupBuilder = new GroupBuilder(new Scanner(System.in));
                groupBuilder.setFields();
                studyGroup = groupBuilder.studyGropCreator();
            }
//            CommandMsg send = new CommandMsg(consoleManager.getCommand(), consoleManager.getArg(),new StudyGroup("1",1,1,1,FormOfEducation.EVENING_CLASSES,Semester.THIRD,"das",new Date(11),1,Long.parseLong("1"),"1"));

            CommandMsg send = new CommandMsg(consoleManager.getCommand(), consoleManager.getArg(),studyGroup);
            AnswerMsg answ = null;
            boolean wasSend = false;
            try{

                writeMessage(send);
                wasSend = true;
                answ = readMessage();
            } catch (ConnectionBrokenException e) {
                System.out.println("Попытка переподключиться");
                while (!connectToServer()){
                    if(attempts > connectionAttempts){
                        ConsoleManager.printErr("Превышено количество попыток подключиться");
                        return;
                    }
                    try {
                        Thread.sleep(connectionTimeout);
                    } catch (InterruptedException exception) {
                        ConsoleManager.printErr("Произошла ошибка при попытке ожидания подключения");
                    }
                }
                if (wasSend){
                    System.out.println("Сервер запомнил данные о команде");
                }else {
                    System.out.println("Сервер не запомнил данные о команде");
                }

            }
            if (answ != null) {
                if (answ.getStatus() == Status.ERROR) {
                    System.out.println("При выполнении программы произошла ошибка");
                    System.out.println(answ.getMessage().trim());
                } else {
                    System.out.println(answ.getMessage().trim());
                }
                if (answ.getStatus() == Status.EXIT) {
                    work = false;
                }
            }
        }
        closeConnection();
    }

}
