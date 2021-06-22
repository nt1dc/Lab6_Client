import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * Main class for client
 * @author Konanykhina Antonina
 */
public class Main {
    public static void main(String[] args) {

        try {
            System.out.println(InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        Scanner scanner;
        try {
            System.setOut(new PrintStream(System.out, true, "windows-1251"));
            scanner = new Scanner(System.in);
        } catch (UnsupportedEncodingException e) {
            ConsoleManager.printErr("Code error");
            scanner = new Scanner(System.in);
        }

        int recconectAtmpts = 20;
        int timeout = 10;
        String host = "192.168.0.102";
        int port = 1821;
        try {
//            host = args[0].trim();
//            port = Integer.parseInt(args[1].trim());
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
