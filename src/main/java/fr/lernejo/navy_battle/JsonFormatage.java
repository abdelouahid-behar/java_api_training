package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;

public class JsonFormatage {

    public final String id;
    public final String url;
    public final String msg;

    public JsonFormatage(String id, String url, String msg) {
        this.id = id;
        this.url = url;
        this.msg = msg;
    }

    public void start(int port) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.setExecutor(Executors.newSingleThreadExecutor());
        server.createContext("/ping", new CallPing());
        server.createContext("/api/game/start", this::callStartGame);
        server.start();
    }

    public void sendPost(String adversaryUrl) throws IOException, InterruptedException {
        java.net.http.HttpClient page = HttpClient.newHttpClient();

        HttpRequest req = HttpRequest.newBuilder()
            .uri(URI.create(adversaryUrl + "/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("{\"id\":\"" + this.id + "\", \"url\":\"" + this.url + "\", \"message\":\"" + this.msg + "\"}"))
            .build();
        var res = page.send(req, HttpResponse.BodyHandlers.ofString());
    }

    public void callStartGame(HttpExchange exchange) throws IOException {
        InputStream stream = exchange.getRequestBody();
        JSONParser parseIt = new JSONParser();
        try {
            JSONObject obj = (JSONObject) parseIt.parse(new InputStreamReader(stream, "UTF-8"));
            if (obj.get("id") == null || obj.get("url") == null || obj.get("message") == null) {
                exchange.sendResponseHeaders(400, 0);
            }
        } catch (ParseException e) {
            exchange.sendResponseHeaders(400, 0);
            e.printStackTrace();
        }

        jsonResponse(exchange);
    }

    public void jsonResponse(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Content-type", "application/json");
        String body = "{\"id\":\"" + this.id + "\", \"url\":\"" + this.url + "\", \"message\":\"" + this.msg + "\"}";
        exchange.sendResponseHeaders(202, body.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(body.getBytes());
        }
        exchange.close();
    }
}
