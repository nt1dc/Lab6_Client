import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * Main class for client
 */
public class Main {
    private static String host;

    public static void main(String[] args) {
        System.out.println(args[1]);


        try {
            System.out.println(InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        Scanner scanner;
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
            scanner = new Scanner(System.in);
        } catch (UnsupportedEncodingException e) {
            ConsoleManager.printErr("Code error");
            scanner = new Scanner(System.in);
        }

        int recconectAtmpts = 20;
        int timeout = 10000;

        int port;
        try {
            host = args[0].trim();
            port = Integer.parseInt(args[1].trim());
            ConsoleManager.print("Получены хост: " + host + " и порт: " + port);
        }catch (NumberFormatException exception){
            ConsoleManager.printErr("Порт должен быть числом");
            return;
        }catch (ArrayIndexOutOfBoundsException exception){
            ConsoleManager.printErr("Не достаточно аргументов");
            return;
        }
        ConsoleManager consoleManager = new ConsoleManager(scanner);
        Client client = new Client(host, port, recconectAtmpts, timeout, consoleManager);
        client.run();
    }
}
