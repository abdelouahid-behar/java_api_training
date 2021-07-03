package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.concurrent.Executors;

public class Launcher {

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length != 0) {
            int port = Integer.parseInt(args[0]);
            System.out.println("listening on http://localhost:" + port + "/");
            JsonMessages server = new JsonMessages(UUID.randomUUID().toString(),"localhost:" + port, "here is the message" );
            new Launcher().start(port);
            if (args.length == 2)
                new JsonFormatage().sendPost(args[1]);
        }
    }

    public void start(int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.setExecutor(Executors.newSingleThreadExecutor());
        server.createContext("/ping", new CallPing());
        server.createContext("/api/game/start", new CallStartGame());
        server.start();
    }
}
