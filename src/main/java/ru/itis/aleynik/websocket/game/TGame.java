package ru.itis.aleynik.websocket.game;

import java.util.Arrays;

public class TGame {

    private char[][] fieldPrint;
    private Byte[][] fieldByte;
    private boolean count;
    // true - gamer (x)
    // false - server (o)

    private static final byte GAMER_POINT = 40;
    private static final byte SERVER_POINT = 10;
    private static final byte GAMER_WIN_POINT = 120;
    private static final byte SERVER_WIN_POINT = 30;
    private static final byte FIELD_LENGTH = 3;


    public TGame() {
        this.fieldPrint = getField();
        this.fieldByte = new Byte[][]{{0,0,0}, {0,0,0}, {0,0,0}};
        count = true;
    }

    public boolean play(Coordinates c) {
        return play(c.getX(), c.getY());
    }

    public boolean play(int[] coords) throws IllegalArgumentException {
        return play(coords[0], coords[1]);
    }

    public boolean play(int x, int y) throws IllegalArgumentException {

        byte point;
        char symbol;
        byte sum;

        if (fieldByte[x][y] != 0 ) throw new IllegalArgumentException();

        if (count) {
            point = GAMER_POINT;
            symbol = 'x';
            sum = GAMER_WIN_POINT;
//            fieldPrint[x+1][y+1] = 'x';
//            fieldByte[x][y] = GAMER_POINT;
//            print(fieldByte);
//            count = !count;
//            return winGamer();
        } else {
            point = SERVER_POINT;
            symbol = 'o';
            sum = SERVER_WIN_POINT;
        }


        fieldPrint[x+1][y+1] = symbol;
        fieldByte[x][y] = point;

        print(fieldByte);
        count = !count;
//        return winServer();
        return win(sum);
    }

    public String getFieldToPrint() {
        StringBuilder sb = new StringBuilder();

        for (char[] a: fieldPrint) {
            for (char c : a) {
                sb.append(c).append(" ");
            }
            sb.append("<br>").append("\n");
        }

        return sb.toString();
    }

    public String[] getFreeCell() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fieldByte.length; i++) {
            for (int j = 0; j < fieldByte.length; j++) {
                if (fieldByte[i][j] == 0) sb.append(i).append(", ").append(j).append(';');
            }
        }

        return sb.toString().split(";");
    }

    private boolean winGamer() {
        return win(GAMER_WIN_POINT);
    }

    private boolean winServer() {
        return win(SERVER_WIN_POINT);
    }

    private boolean win(int sum) {
        for (int i = 0; i < FIELD_LENGTH; i++) {
            if (fieldByte[i][0] + fieldByte[i][1] + fieldByte[i][2] == sum) return true;
        }

        for (int i = 0; i < fieldByte.length; i++) {
            if (fieldByte[0][i] + fieldByte[1][i] + fieldByte[2][i] == sum) return true;
        }

        if (fieldByte[0][0] + fieldByte[1][1] + fieldByte[2][2] == sum) return true;

        return fieldByte[FIELD_LENGTH - 1][0] + fieldByte[1][1] + fieldByte[0][2] == sum;
    }

    private void print(Byte[][] arr) {
        for (Byte[] a: arr) {
            System.out.println(Arrays.toString(a));
        }
        System.out.println();
    }


    private char[][] getField() {

        return new char[][]{
                {'-', '0', '1', '2'},
                {'0', '-', '-', '-'},
                {'1', '-', '-', '-'},
                {'2', '-', '-', '-'},
        };
    }
}
