package fr.lernejo.navy_battle;

import java.io.IOException;

public class Launcher {
    public static void main(String[] args) throws IOException, InterruptedException {
        int port = Integer.parseInt(args[0]);
        System.out.println("listening on http://localhost:" + port + "/");
        var server = new Server(port);
        if (args.length > 1)
            new PostReq().post(port, args[1]);
    }
}
