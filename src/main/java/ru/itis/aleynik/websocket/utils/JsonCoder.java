package ru.itis.aleynik.websocket.utils;

import com.google.gson.Gson;
import ru.itis.aleynik.websocket.server.Message;

public class JsonCoder {

    public static Message fromJson(String message) {
        Gson gson = new Gson();
        return gson.fromJson(message, Message.class);
    }

    public static String toJson(Message message) {
        Gson gson = new Gson();
        return gson.toJson(message);
    }
}
