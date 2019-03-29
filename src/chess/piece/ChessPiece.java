package chess.piece;

import chess.Board;

import java.io.Serializable;

public abstract class ChessPiece implements Serializable {

    private Color color;
    private ChessPieceType chessPieceType;

    public ChessPiece(ChessPieceType type, Color color) {
        chessPieceType = type;
        this.color = color;
    }

    public abstract String toString();

    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        ChessPiece chessPiece = (ChessPiece) obj;
        return chessPiece == this;
    }

    public Color getColor() {
        return color;
    }

    public ChessPieceType getChessPieceType() {
        return chessPieceType;
    }

    public boolean[][] getPossibleDestinations(Board board) {

        boolean[][] helperBool = new boolean[8][8];

        for (int row = 0; row < helperBool.length; row++) {
            for (int col = 0; col < helperBool[row].length; col++) {
                helperBool[row][col] = canMove(board, row, col);
            }
        }

        return helperBool;

    }

    public abstract boolean canMove(Board board, int row, int col);

}
