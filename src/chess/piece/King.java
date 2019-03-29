package chess.piece;

import chess.Board;

public class King extends ChessPiece {

    public King(Color color) {
        super(ChessPieceType.KING, color);
    }

    @Override
    public String toString() {
        if (getColor().equals(Color.BLACK)) {
            return "\u265A";
        } else {
            return "\u2654";
        }
    }

    /**
     * The King can move one field in all directions.
     * @param board current Board
     * @param row destination row
     * @param col destination col
     * @return returns, whether the king can move or not
     */
    @Override
    public boolean canMove(Board board, int row, int col) {

        int currentRow = board.getPosition(this)[0];
        int currentCol = board.getPosition(this)[1];

        if ((Math.abs(row - currentRow)) <= 1 || (Math.abs(col - currentCol) <= 1)) {
            if (board.getBoard()[row][col] != null) {
                return board.getBoard()[row][col].getColor() != getColor();
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
}
