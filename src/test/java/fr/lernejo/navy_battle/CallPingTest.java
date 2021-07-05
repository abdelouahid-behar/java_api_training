package fr.lernejo.navy_battle;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.URI;
import java.net.http.HttpResponse;

public class CallPingTest {

    @Test
    void testOfPing() throws IOException, InterruptedException {
        var server = new Server(9876);
        HttpClient web = HttpClient.newHttpClient();

        HttpRequest req = HttpRequest.newBuilder().uri(URI.create("http://localhost:9876/ping")).GET().build();

        HttpResponse<String> response = web.send(req, HttpResponse.BodyHandlers.ofString());
        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals("OK", response.body());
    }
}
