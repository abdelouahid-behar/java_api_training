package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Server {

    final HttpServer server;
    Server(int port) throws IOException {

        this.server = HttpServer.create(new InetSocketAddress(port), 0);
        this.server.setExecutor(Executors.newSingleThreadExecutor());

        this.server.createContext("/ping", new CallPing());
        this.server.createContext("/api/game/start", new CallStartGame(port));

        this.server.start();
    }

}
