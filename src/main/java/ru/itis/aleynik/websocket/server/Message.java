package ru.itis.aleynik.websocket.server;

import com.google.gson.Gson;
import ru.itis.aleynik.websocket.game.Coordinates;
import ru.itis.aleynik.websocket.utils.JsonCoder;

public class Message {
    public String type;
    public String value;
    public Coordinates coordinates;

    public static Message fromJson(String message) {

        return JsonCoder.fromJson(message);
    }

    public Message(String type) {
        this.type = type;
    }

    public Message(String type, Coordinates coordinates) {
        this.type = type;
        this.coordinates = coordinates;
    }

    public String getJsonMessage() {
        return JsonCoder.toJson(this);
    }

    public String getType() {
        return type;
    }

    public Message setType(String type) {
        this.type = type;
        return this;
    }

    public String getValue() {
        return value;
    }

    public Message setValue(String value) {
        this.value = value;
        return this;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Message setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type='" + type + '\'' +
                ", coordinates=" + coordinates +
                '}';
    }
}
