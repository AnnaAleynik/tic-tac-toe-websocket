package ru.itis.aleynik.websocket.utils;

import java.util.Arrays;

public class CoordsDecoder {

    public static int[] getCoordinates(String message) {
        int[] ints = Arrays.stream(message.replaceAll("[a-zA-Z]\\s", "").split(","))
                .map(String::trim).mapToInt(Integer::parseInt).toArray();
        return ints;
    }
}
