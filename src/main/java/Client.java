

import com.google.common.hash.Hashing;
import exeptions.ConnectionBrokenException;
import messages.AnswerMsg;
import messages.CommandMsg;
import messages.Status;
import messages.User;
import org.junit.Test;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


/**
 * Class working with connection
 */
public class Client {
    private String serverHost;
    private int serverPort;
    private int connectionAttempts;
    private int connectionTimeout;
    private boolean authenticated = false;
    public int attempts = 0;
    private ObjectOutputStream serverWriter;
    private ObjectInputStream serverReader;
    private SocketChannel socket;
    private ConsoleManager consoleManager;
    private ByteBuffer buffer = ByteBuffer.allocate(102400);
    private User user = new User();
    private AnswerMsg answ;

    public Client() {
    }

    public Client(String host, int port, int attempts, int timeout, ConsoleManager cons) {
        serverHost = host;
        serverPort = port;
        connectionAttempts = attempts;
        connectionTimeout = timeout;
        consoleManager = cons;
    }

    /**
     * Connect client to server
     *
     * @return is closed successfully or not
     */
    private boolean connectToServer() {
        try {
            if (attempts > 0)
                System.out.println("Попытка переподключиться");
            attempts++;
            socket = SocketChannel.open();
            socket.connect(new InetSocketAddress(serverHost, serverPort));
//            System.out.println("Получаю разрешение на чтение и запись");
//            serverWriter = new ObjectOutputStream(socket.socket().getOutputStream());
//            System.out.println("Получено разрешение на запись");
//            serverReader = new ObjectInputStream(socket.socket().getInputStream());
//            System.out.println("Получено разрешение на чтение");
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

    public void registration() throws ConnectionBrokenException, IOException, ClassNotFoundException {
        System.out.println("Starting Registration");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter ur username");
        user.setUserName(scanner.nextLine());
        System.out.println("Enter ur password");
        user.setPassword(Hashing.sha256().hashString(scanner.nextLine(), StandardCharsets.UTF_8).toString());
        writeMessage(new CommandMsg("registration", null, null, user));
        readMessage();
        if (answ.getStatus().equals(Status.ERROR)) {
            registration();
        } else {
            System.out.println("Регистрация прошла успешно");
            authentication();
        }
    }

    private void authentication() throws ConnectionBrokenException, IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter ur username");
        String line = scanner.nextLine();
        user.setUserName(line);
        System.out.println("Enter ur password");
        user.setPassword(Hashing.sha256().hashString(scanner.nextLine(), StandardCharsets.UTF_8).toString());

        writeMessage(new CommandMsg("authentication", "", null, user));
        readMessage();

        try {
            if (answ.getMessage().equals("Хорошая работа ОЛЕГ")) {
                authenticated = true;
                System.out.println("Authentication complete");
            } else {
                System.out.println("Wrong login or password");
                System.out.println("Введите \"Я обещаю зачесть данную лабу Антонову Дмитрию\", чтобы пройти регистрацию");
                if (scanner.nextLine().equals("Я обещаю зачесть данную лабу Антонову Дмитрию")) {
                    registration();
                } else {
                    System.out.println("Ну тогда пробуй еще раз");
                    authentication();
                }
            }
        } catch (ConnectionBrokenException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    /**
     * Method write message to server in CommandMsg format
     *
     * @param msg message
     * @throws ConnectionBrokenException If connection was broken
     */
    private void writeMessage(CommandMsg msg) throws ConnectionBrokenException {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(msg);
            oos.flush();
            byte[] data = bos.toByteArray();
            ByteBuffer buffer = ByteBuffer.wrap(data);
            System.out.println(buffer.array().length);
            socket.write(buffer);
        } catch (IOException exception) {
            ConsoleManager.printErr("Разрыв соеденения");
            throw new ConnectionBrokenException();
        }
    }

    /**
     * Read message from server in AnswerMsg format
     *
     * @return Message
     * @throws ConnectionBrokenException If connection was broken
     */
    private AnswerMsg readMessage() throws ConnectionBrokenException, IOException {
        AnswerMsg retMsg = new AnswerMsg();
        ByteBuffer readBuffer = ByteBuffer.allocate(102400);
        try {
            System.out.println("жду ответа");
            int num = socket.read(readBuffer);
            if (num > 0) {
                System.out.println("Начинаю чтение объекта");
                // Processing incoming data...
                ByteArrayInputStream inputStream = new ByteArrayInputStream(readBuffer.array());//массив байтов
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                answ = (AnswerMsg) objectInputStream.readObject();//считываем объект
                System.out.println("Объект получен");
                System.out.println(answ.getMessage());
                readBuffer.clear();
            }
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
    private void closeConnection() {
        try {
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
    public void run() throws IOException, ClassNotFoundException, ConnectionBrokenException {
        boolean wasSend = false;
        boolean work = true;
        System.out.println("Подключаюсь к серверу");
        while (!connectToServer()) {
            if (attempts > connectionAttempts) {
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
        StudyGroup studyGroup = null;
        //print("Подключился, работаю");

        while (work) {
            try {


                while (!authenticated) {
                    authentication();
                }
            } catch (ConnectionBrokenException e) {
                System.out.println("Попытка переподключиться");
                while (!connectToServer()) {
                    if (attempts > connectionAttempts) {
                        ConsoleManager.printErr("Превышено количество попыток подключиться");
                        return;
                    }
                    try {
                        Thread.sleep(connectionTimeout);
                    } catch (InterruptedException exception) {
                        ConsoleManager.printErr("Произошла ошибка при попытке ожидания подключения");
                    }
                }

            }
            consoleManager.waitCommand();
            if (consoleManager.getCommand().equals("exit")){
                socket.close();
                System.exit(0);
            }
            if (consoleManager.getCommand().equals("add") | consoleManager.getCommand().equals("update")
                    | consoleManager.getCommand().equals("add_if_min")) {
                GroupBuilder groupBuilder = new GroupBuilder(new Scanner(System.in));
                groupBuilder.setFields();
                studyGroup = groupBuilder.studyGropCreator();
            }
            CommandMsg send = new CommandMsg(consoleManager.getCommand(), consoleManager.getArg(), studyGroup, user);
            try {

                writeMessage(send);
                wasSend = true;
                System.out.println("я чё-то отправил");
                answ = readMessage();
            } catch (ConnectionBrokenException exception1) {
                System.out.println("Попытка переподключиться");
                while (!connectToServer()) {
                    if (attempts > connectionAttempts) {
                        ConsoleManager.printErr("Превышено количество попыток подключиться");
                        return;
                    }
                    try {
                        Thread.sleep(connectionTimeout);
                    } catch (InterruptedException exception) {
                        ConsoleManager.printErr("Произошла ошибка при попытке ожидания подключения");
                    }
                }
                if (wasSend) {
                    System.out.println("Сервер запомнил данные о команде");
                } else {
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
