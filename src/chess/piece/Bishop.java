package chess.piece;

import chess.Board;

public class Bishop extends ChessPiece {

    public Bishop(Color color) {
        super(ChessPieceType.BISHOP, color);
    }

    @Override
    public String toString() {
        if (getColor().equals(Color.BLACK)) {
            return "\u265D";
        } else {
            return "\u2657";
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
     * @param col destination column
     * @param currentRow start row
     * @param currentCol start column
     * @return whether the rook can move to row/col or not.
     */
    public boolean canMoveTo(Board board, int row, int col, int currentRow, int currentCol) {

        /**
         * 1. Condition: col < currentCol and row < currentRow ==> Bishop move-direction is "top-left"
         * 2. Condition: col < currentCol and row > currentRow ==> Bishop move-direction is "bottom-left"
         * 3. Condition: row > currentRow and col > currentCol ==> Bishop move-direction is "bottom-right"
         * 4. Condition: row < currentRow and col > currentCol ==> Bishop move-direction is "top-right"
         */

        if (col < currentCol && row < currentRow) { // Bishop Movement: Top - Left
            if (isDiagonal(row, col, currentRow, currentCol)) {

                /**
                 * The for-loop goes from the currentCol-1 and currentRow-1 to the col and row destination. Inside the loop is checked,
                 * whether there is a piece on the line to the destination or not, so the rook can move to the destination or not.
                 */
                for (int i = currentCol - 1, j = currentRow - 1; i >= col && j >= row; i--, j--) {
                    if (board.getBoard()[j][i] != null) {
                        if (board.getBoard()[j][i].getColor() != this.getColor()) {
                            if (i == col && j == row) {
                                try {
                                    if (board.getBoard()[row + 1][col + 1] != null) {
                                        return board.getBoard()[row + 1][col + 1].getColor() != board.getBoard()[row][col].getColor();
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
                    } else if ((board.getBoard()[j][i] == null) && (i > col || j > row  )) {

                    } else {
                        return true;
                    }
                }
            } else {
                return false;
            }
        } else if (col < currentCol && row > currentRow) { // Bishop Movement: Bottom - Left
            if (isDiagonal(row, col, currentRow, currentCol)) {

                /**
                 * The for-loop goes from the currentCol-1 and currentRow+1 to the col and row destination. Inside the loop is checked,
                 * whether there is a piece on the line to the destination or not, so the rook can move to the destination or not.
                 */
                for (int i = currentCol - 1, j = currentRow + 1; i >= col && j <= row; i--, j++) {
                    if (board.getBoard()[j][i] != null) {
                        if (board.getBoard()[j][i].getColor() != this.getColor()) {
                            if (i == col && j == row) {
                                try {
                                    if (board.getBoard()[row - 1][col + 1] != null) {
                                        return board.getBoard()[row - 1][col + 1].getColor() != board.getBoard()[row][col].getColor();
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
                    } else if ((board.getBoard()[j][i] == null) && (i > col || j < row  )) {

                    } else {
                        return true;
                    }
                }
            } else {
                return false;
            }
        } else if (col > currentCol && row > currentRow) { // Bishop Movement: Bottom - Right
            if (isDiagonal(row, col, currentRow, currentCol)) {

                /**
                 * The for-loop goes from the currentCol+1 and currentRow+1 to the col and row destination. Inside the loop is checked,
                 * whether there is a piece on the line to the destination or not, so the rook can move to the destination or not.
                 */
                for (int i = currentCol + 1, j = currentRow + 1; i <= col && j <= row; i++, j++) {
                    if (board.getBoard()[j][i] != null) {
                        if (board.getBoard()[j][i].getColor() != this.getColor()) {
                            if (i == col && j == row) {
                                try {
                                    if (board.getBoard()[row - 1][col - 1] != null) {
                                        return board.getBoard()[row - 1][col - 1].getColor() != board.getBoard()[row][col].getColor();
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
                    } else if ((board.getBoard()[j][i] == null) && (i < col || j < row  )) {

                    } else {
                        return true;
                    }
                }
            } else {
                return false;
            }
        } else if (col > currentCol && row < currentRow) { // Bishop Movement: Top - Right
            if (isDiagonal(row, col, currentRow, currentCol)) {
                /**
                 * The for-loop goes from the currentCol+1 and currentRow-1 to the col and row destination. Inside the loop is checked,
                 * whether there is a piece on the line to the destination or not, so the rook can move to the destination or not.
                 */
                for (int i = currentCol + 1, j = currentRow - 1; i <= col && j >= row; i++, j--) {
                    if (board.getBoard()[j][i] != null) {
                        if (board.getBoard()[j][i].getColor() != this.getColor()) {
                            if (i == col && j == row) {
                                try {
                                    if (board.getBoard()[row + 1][col - 1] != null) {
                                        return board.getBoard()[row + 1][col - 1].getColor() != board.getBoard()[row][col].getColor();
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
                    } else if ((board.getBoard()[j][i] == null) && (i < col || j > row  )) {

                    } else {
                        return true;
                    }
                }
            } else {
                return false;
            }
        } else {
            return false;
        }

        return false;
    }

    /**
     * This method checks entered way is diagonal
     * @param row destination row
     * @param col destination column
     * @param currentRow start row
     * @param currentCol start column
     * @return if its a diagonal path from start to destination field
     */
    private boolean isDiagonal(int row, int col, int currentRow, int currentCol) {
        return ((Math.abs(currentRow - row) == Math.abs(currentCol - col)));
    }

}
