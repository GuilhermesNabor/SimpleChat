package client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    private static String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Essa máquina é o servidor? (S/N)");
        String servidor = scanner.nextLine();

        if (servidor.equalsIgnoreCase("S")) {
            SERVER_ADDRESS = "localhost";
        } else {
            System.out.println("Qual é o endereço IP da máquina servidor? ");
            SERVER_ADDRESS = scanner.nextLine();
        }

        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            Thread readerThread = new Thread(() -> {
                String message;
                try {
                    while ((message = in.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            readerThread.start();

            while (scanner.hasNextLine()) {
                String message = scanner.nextLine();
                out.println(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
