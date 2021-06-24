package fr.lernejo.navy_battle;

import java.net.InetSocketAddress;
import java.io.IOException;

import com.sun.net.httpserver.HttpServer;

public class Launcher {

    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        System.out.println("listening on http://localhost:" + port + "/");

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/ping", new CallHandler());

        server.start();
    }

}
