package chess.piece;

import chess.Board;

public class Knight extends ChessPiece {

    public Knight(Color color) {
        super(ChessPieceType.KNIGHT, color);
    }

    @Override
    public String toString() {
        if (getColor().equals(Color.BLACK)) {
            return "\u265E";
        } else {
            return "\u2658";
        }
    }

    @Override
    public boolean canMove(Board board, int row, int col) {

        int currentRow = board.getPosition(this)[0];
        int currentCol = board.getPosition(this)[1];

        if (isValidKnightMovementWay(currentRow, currentCol, row, col)) {

            if (board.getBoard()[row][col] != null) {
                return getColor() != board.getBoard()[row][col].getColor();
            } else {
                return true;
            }

        } else {
            return false;
        }
    }

    private boolean isValidKnightMovementWay(int currentRow, int currentCol, int row, int col) {
        return ((Math.abs(currentRow - row) == 2 && Math.abs(currentCol - col) == 1) || (Math.abs(currentRow - row) == 1 && Math.abs(currentCol - col) == 2));
    }

}
