package ru.itis.aleynik.websocket;

import com.google.gson.Gson;
import ru.itis.aleynik.websocket.game.TGame;
import ru.itis.aleynik.websocket.server.Message;

public class Test {
    public static void main(String[] args) {
        TGame t = new TGame();

        t.play(0, 0);
        t.play(0, 1);
        t.play(1, 1);
        t.play(1, 2);
        System.out.println(t.play(2, 2));

        Gson gson = new Gson();

        String jsonString = "{\"type\":\"play\",\"coordinates\":{\"x\":\"0\",\"y\":\"0\"}}";
        String withoutCoords = "{\"type\":\"play\"}";


        Message message = gson.fromJson(withoutCoords, Message.class);
        System.out.println(message);

    }
}
