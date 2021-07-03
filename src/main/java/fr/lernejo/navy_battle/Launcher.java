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
            JsonFormatage server = new JsonFormatage(UUID.randomUUID().toString(),"localhost:" + port, "here is the message" );
            server.start(port);
            if (args.length == 2)
                server.sendPost(args[1]);
        }
    }

}
