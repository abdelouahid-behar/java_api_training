package fr.lernejo.navy_battle;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CallStartGame implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
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

        new JsonFormatage().jsonResponse(exchange);
    }
}

