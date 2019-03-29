package chess.piece;

import chess.Board;

public class Pawn extends ChessPiece {

    private static final int BLACK_PAWN_MOVEMENT = 1;
    private static final int BLACK_PAWN_START_ROW = 1;
    private static final int WHITE_PAWN_MOVEMENT = -1;
    private static final int WHITE_PAWN_START_ROW = 6;

    public Pawn(Color color) {
        super(ChessPieceType.PAWN, color);
    }

    @Override
    public String toString() {
        if (getColor().equals(Color.BLACK)) {
            return "\u265F";
        } else {
            return "\u2659";
        }

    }

    /**
     * Inside the canMove method for the Pawn is checked, whether the current pawn is black or white.
     * Then the pawn can just move 1 field forward or one field diagonal if he can kill a piece from the other player.
     * The pawn can just move 2 fields forward, if he starts at its standard position
     * @param board current board
     * @param row destination row
     * @param col destination column
     * @return returns true if he can move to the given field.
     */
    @Override
    public boolean canMove(Board board, int row, int col) {

        int currentRow = board.getPosition(this)[0];
        int currentCol = board.getPosition(this)[1];

        if (this.getColor().equals(Color.BLACK)) {
            return canMoveTo(board, currentRow, currentCol, row, col, BLACK_PAWN_MOVEMENT, BLACK_PAWN_START_ROW);
        } else {
            return canMoveTo(board, currentRow, currentCol, row, col, WHITE_PAWN_MOVEMENT, WHITE_PAWN_START_ROW);
        }
    }

    private boolean canMoveTo(Board board, int currentRow, int currentCol, int row, int col, int movementInt, int startRow) {
        if ((currentRow + movementInt == row && currentCol == col) || (currentRow + (2*movementInt) == row && currentRow == startRow && currentCol == col)) {
            return board.getBoard()[row][col] == null;
        } else if ((currentRow + movementInt == row && currentCol + movementInt == col) || (currentRow + movementInt == row && currentCol - movementInt == col)) {
            if (board.getBoard()[row][col] != null) {
                return getColor() != board.getBoard()[row][col].getColor();
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
