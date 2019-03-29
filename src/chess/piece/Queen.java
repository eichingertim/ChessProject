package chess.piece;

import chess.Board;

public class Queen extends ChessPiece {


    public Queen(Color color) {
        super(ChessPieceType.QUEEN, color);
    }

    @Override
    public String toString() {
        if (getColor().equals(Color.BLACK)) {
            return "\u265B";
        } else {
            return "\u2655";
        }
    }

    @Override
    public boolean canMove(Board board, int row, int col) {

        int currentRow = board.getPosition(this)[0];
        int currentCol = board.getPosition(this)[1];

        Rook rook = new Rook(this.getColor());
        Bishop bishop = new Bishop(this.getColor());

        return rook.canMoveTo(board, row, col, currentRow, currentCol) || bishop.canMoveTo(board, row, col, currentRow, currentCol);
    }


}
