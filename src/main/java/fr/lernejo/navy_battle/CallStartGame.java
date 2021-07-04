package fr.lernejo.navy_battle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class CallStartGame implements HttpHandler {

    private final StringBuilder URL = new StringBuilder();
    CallStartGame(int port) {
        this.URL.append("http://localhost:").append(port);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equals("POST")) {
            errorMessage(exchange);
        }
        else {
            FormatJson reqBody = parser(exchange);
            if (reqBody.url.equals("\"\"") || reqBody.id.equals("\"\"") || reqBody.message.equals("\"\"") || reqBody == null) {
                resultMessage(exchange, "not goof format", 400);
            }
            else {
                resultMessage(exchange, "{\n\t\"id\":\"" + UUID.randomUUID() + "\",\n\t\"url\":\"" + this.URL + "\",\n\t\"message\":\"May the best code win\"\n}", 202);
            }
            var party = new NewGame(reqBody, reqBody);
            try {
                party.startIt();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    private void resultMessage(HttpExchange exchange, String message, int codeReq) throws IOException {
        exchange.sendResponseHeaders(codeReq, message.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(message.getBytes());
        }
    }

    private void errorMessage(HttpExchange exchange) throws IOException {
        String body = "Not Found !";
        exchange.sendResponseHeaders(404, body.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(body.getBytes());
        }
    }

    private String streamToString(InputStream str) throws IOException {
        StringBuilder stream = new StringBuilder();
        int i;
        while ((i = str.read()) > 0) {
            stream.append((char) i);
        }
        return stream.toString();
    }

    private FormatJson parser(HttpExchange exchange) throws IOException {
        ObjectMapper obj = new ObjectMapper();
        FormatJson reqBody = null;
        String streamString = streamToString(exchange.getRequestBody());
        if (streamString.isBlank()) {
            return null;
        }
        else {
            try {
                reqBody = obj.readValue(streamString, FormatJson.class);
            } catch (IllegalArgumentException e) {
                exchange.sendResponseHeaders(400, "Not Found !".length());
                throw new IllegalArgumentException();
            }
        }
        return reqBody;
    }
}
