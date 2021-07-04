package fr.lernejo.navy_battle;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class NewGame {
    public final FormatJson AdversaryJsonProp;
    public final FormatJson MyJsonProp;

    NewGame(FormatJson reqBody, FormatJson MyreqBody) {
        this.AdversaryJsonProp = reqBody;
        this.MyJsonProp = MyreqBody;
    }

    public void startIt() throws IOException, InterruptedException {
        HttpClient web = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(AdversaryJsonProp.url.substring(1, AdversaryJsonProp.url.length() - 1) + "/api/game/fire?cell=A1"))
            .setHeader("Accept", "application/json").setHeader("Content-Type", "application/json").GET().build();
        HttpResponse<String> response = web.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}
