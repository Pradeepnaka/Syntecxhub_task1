package yntecx;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PortScanner {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Host (e.g., localhost or 127.0.0.1): ");
        String host = scanner.nextLine();

        System.out.print("Enter Start Port: ");
        int startPort = scanner.nextInt();

        System.out.print("Enter End Port: ");
        int endPort = scanner.nextInt();

        System.out.println("\nScanning " + host + " from port " + startPort + " to " + endPort + "...\n");

        ExecutorService executor = Executors.newFixedThreadPool(20);

        for (int port = startPort; port <= endPort; port++) {
            final int currentPort = port;
            executor.execute(() -> {
                scanPort(host, currentPort);
            });
        }

        executor.shutdown();
        scanner.close();
    }

    private static void scanPort(String host, int port) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(host, port), 1000);
            socket.close();

            String result = "Port " + port + " is OPEN\n";
            System.out.print(result);
            logResult(result);

        } catch (IOException e) {
            // Closed port
        }
    }

    private synchronized static void logResult(String message) {
        try (FileWriter fw = new FileWriter("log.txt", true)) {
            fw.write(message);
        } catch (IOException e) {
            System.out.println("Error writing to log file: " + e.getMessage());
        }
    }
}
