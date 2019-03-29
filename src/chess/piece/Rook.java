package chess.piece;

import chess.Board;

public class Rook extends ChessPiece {

    public Rook(Color color) {
        super(ChessPieceType.ROOK, color);
    }

    @Override
    public String toString() {
        if (getColor().equals(Color.BLACK)) {
            return "\u265C";
        } else {
            return "\u2656";
        }
    }


    @Override
    public boolean canMove(Board board, int row, int col) {

        int currentRow = board.getPosition(this)[0];
        int currentCol = board.getPosition(this)[1];

        return canMoveTo(board, row, col, currentRow, currentCol);
    }

    /**
     * The canMoveTo method is divided into four big if-else conditions. Each section of them represents a move direction.
     * @param board current Board
     * @param row destination row
     * @param col destination colzmn
     * @param currentRow start row
     * @param currentCol start column
     * @return whether the rook can move to row/col or not.
     */
    public boolean canMoveTo(Board board, int row, int col, int currentRow, int currentCol) {

        /**
         * 1. Condition: col > currentCol and row == currentRow ==> Rook move-direction is "right"
         * 2. Condition: col < currentCol and row == currentRow ==> Rook move-direction is "left"
         * 3. Condition: row < currentRow and col == currentCol ==> Rook move-direction is "top"
         * 4. Condition: row > currentRow and col == currentCol ==> Rook move-direction is "bottom"
         */
        if (col > currentCol && row == currentRow) {

            /**
             * The for-loop goes from the currentCol+1 to the col destination. Inside the loop is checked,
             * whether there is a piece on the line to the destination or not, so the rook can move to the destination or not.
             */
            for (int i = currentCol + 1; i <= col; i++) {
                if (board.getBoard()[row][i] != null) {
                    if (board.getBoard()[row][i].getColor() != this.getColor()) {
                        if (i == col) {
                            try {
                                if (board.getBoard()[row][col - 1] != null) {
                                    return board.getBoard()[row][col - 1].getColor() != board.getBoard()[row][col].getColor();
                                } else {
                                    return true;
                                }
                            } catch (Exception e) {
                                return true;
                            }
                        } else {
                            return false;
                        }
                    } else return false;
                } else if ((board.getBoard()[currentRow][i] == null) && i < col) {

                } else {
                    return true;
                }
            }
            return false;
        } else if (col < currentCol && row == currentRow) { // Rook Movement: Left

            /**
             * The for-loop goes from the currentCol-1 to the col destination. Inside the loop is checked,
             * whether there is a piece on the line to the destination or not, so the rook can move to the destination or not.
             */
            for (int i = currentCol - 1; i >= col; i--) {
                if (board.getBoard()[row][i] != null) {
                    if (board.getBoard()[row][i].getColor() != this.getColor()) {
                        if (i == col) {
                            try {
                                if (board.getBoard()[row][col + 1] != null) {
                                    return board.getBoard()[row][col + 1].getColor() != board.getBoard()[row][col].getColor();
                                } else {
                                    return true;
                                }
                            } catch (Exception e) {
                                return true;
                            }
                        } else {
                            return false;
                        }
                    } else return false;
                } else if ((board.getBoard()[currentRow][i] == null) && i > col) {

                } else {
                    return true;
                }
            }
            return false;
        } else if (row < currentRow && col == currentCol) {

            /**
             * The for-loop goes from the currentRow-1 to the row destination. Inside the loop is checked,
             * whether there is a piece on the line to the destination or not, so the rook can move to the destination or not.
             */
            for (int i = currentRow - 1; i >= row; i--) {
                if (board.getBoard()[i][currentCol] != null) {
                    if (board.getBoard()[i][currentCol].getColor() != this.getColor()) {
                        if (i == row) {
                            try {
                                if (board.getBoard()[row + 1][currentCol] != null) {
                                    return board.getBoard()[row + 1][currentCol].getColor() != board.getBoard()[row][currentCol].getColor();
                                } else {
                                    return true;
                                }
                            } catch (Exception e) {
                                return true;
                            }
                        } else {
                            return false;
                        }
                    } else return false;
                } else if ((board.getBoard()[i][currentCol] == null) && i > row) {

                } else {
                    return true;
                }
            }
            return false;
        } else if (row > currentRow && col == currentCol) {

            /**
             * The for-loop goes from the currentRow+1 to the row destination. Inside the loop is checked,
             * whether there is a piece on the line to the destination or not, so the rook can move to the destination or not.
             */
            for (int i = currentRow + 1; i <= row; i++) {
                if (board.getBoard()[i][currentCol] != null) {
                    if (board.getBoard()[i][currentCol].getColor() != this.getColor()) {
                        if (i == row) {
                            try {
                                if (board.getBoard()[row - 1][currentCol] != null) {
                                    return board.getBoard()[row - 1][currentCol].getColor() != board.getBoard()[row][currentCol].getColor();
                                } else {
                                    return true;
                                }
                            } catch (Exception e) {
                                return true;
                            }
                        } else {
                            return false;
                        }
                    } else return false;
                } else if ((board.getBoard()[i][currentCol] == null) && i < row) {

                } else {
                    return true;
                }
            }
            return false;
        }
        return false;
    }
}
