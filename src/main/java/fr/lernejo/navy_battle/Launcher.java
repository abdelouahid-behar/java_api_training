package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import java.net.InetSocketAddress;
import java.io.OutputStream;
import java.io.IOException;

public class Launcher {

    public static void main(String[] args) {
        try {
            if (args.length != 1) {
                System.err.println("Usage: Launcher [port]");
                System.exit(-1);
            }

            int port = Integer.parseInt(args[0]);
            System.out.println("listening on http://localhost:" + port + "/");
            NewServer(port);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void NewServer(int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/ping", Launcher::PingRoute);
        server.start();
    }

    private static void PingRoute(HttpExchange exchange) throws IOException {
        String body = "Hello";
        exchange.sendResponseHeaders(200, body.length());
        try (OutputStream os = exchange.getResponseBody()) { // (1)
            os.write(body.getBytes());
        }
    }
}
