package chess;

import chess.piece.Color;

import java.io.Serializable;

/**
 * Player class, whith name and colot
 */
public class Player implements Serializable {

    private String name;
    private Color color;

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

}
