package ru.itis.aleynik.websocket.server;

import org.glassfish.tyrus.server.Server;
import ru.itis.aleynik.websocket.game.Coordinates;
import ru.itis.aleynik.websocket.game.TGame;
import ru.itis.aleynik.websocket.utils.CoordsDecoder;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Scanner;

@ServerEndpoint(value = "/game")
public class GameServer {

    private TGame game;
    private boolean end;
    private Server server;

    public GameServer() {
        server = new Server("127.0.0.1", 8080, "", null, this.getClass());

        try {
            server.start();
            new Scanner(System.in).nextLine();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        } finally {
            server.stop();
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("A Client " + session.getId() + " disconnected with reason " + closeReason.getReasonPhrase() + ", code " + closeReason.getCloseCode().getCode() + ".");
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        System.out.println("Got new connection: " + session.getId());
        startGame(session);
    }

    @OnMessage
    public String onMessage(String message) {
            Message m = Message.fromJson(message);
            switch (m.getType()) {
                case "play" :
                    return playGame(m.getCoordinates());
                case "restart" :
                    return startGame();
                default:
                    throw new IllegalArgumentException();
            }
    }

    private String playGame(Coordinates coordinates) {
//        end = game.play(CoordsDecoder.getCoordinates(message));
        try {
        end = game.play(coordinates);

        System.out.println(game.getFieldToPrint());

        StringBuilder sb = new StringBuilder();
        sb.append("You:<br>");
        sb.append(game.getFieldToPrint());

        if (end) {
            sb.append("<br> You win! Wanna try again?");
            return (new Message("win").setValue(sb.toString())).getJsonMessage();
        }

        sb.append("<br>me:<br>");
        end = game.play(getCoords(game.getFreeCell()));
        sb.append(game.getFieldToPrint());
        System.out.println(game.getFieldToPrint());
        if (end) {
            sb.append("<br> I win! Wanna try again?");
            return (new Message("win").setValue(sb.toString())).getJsonMessage();
        }

        return new Message("play").setValue(sb.toString()).getJsonMessage();
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException ex) {
            System.out.println(ex.getMessage());
            return (new Message("play").setValue("try again, you can use only 0, 1, 2!")).getJsonMessage();
        } catch (IllegalArgumentException e) {
            return (new Message("play").setValue("Hey, it's not free!")).getJsonMessage();

        }
    }

    private String startGame() {
        this.game = new TGame();
        end = false;
        System.out.println("from start " + game.getFieldToPrint());
        StringBuilder sb = new StringBuilder();
        sb.append("Hi! You will play with me! You - x, let's go! <br>");
        sb.append(game.getFieldToPrint());
        return new Message("play").setValue(sb.toString()).getJsonMessage();
    }

    private void startGame(Session session) {
        try {
            session.getBasicRemote().sendText(startGame());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private int[] getCoords(String[] freeCell) {
        int a = (int) ( Math.random() * freeCell.length );
        System.out.println("server: " + freeCell[a]);
        return CoordsDecoder.getCoordinates(freeCell[a]);
    }
}
