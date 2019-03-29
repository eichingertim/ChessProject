package chess;

import chess.piece.*;

import java.io.Serializable;


public class Board implements Serializable {

    private ChessPiece[][] board;
    public static final char LETTERS[] = {'a','b','c','d','e','f','g','h'};
    public static final char NUMS[] = {'8','7','6','5','4','3','2','1'};

    private boolean gameOver = false;

    /**
     * creates a new Board
     */
    public Board() {
        board = new ChessPiece[8][8];
        createStandardChessBoard();
    }

    private void createStandardChessBoard() {
        createBlackPieces();
        createWhitePieces();
    }

    /**
     * Creates all black pieces
     */
    private void createBlackPieces() {
        board[0][0] = new Rook(Color.BLACK);
        board[0][1] = new Knight(Color.BLACK);
        board[0][2] = new Bishop(Color.BLACK);
        board[0][3] = new Queen(Color.BLACK);
        board[0][4] = new King(Color.BLACK);
        board[0][5] = new Bishop(Color.BLACK);
        board[0][6] = new Knight(Color.BLACK);
        board[0][7] = new Rook(Color.BLACK);

        for(int i = 0; i < 8; i++){
            board[1][i] = new Pawn(Color.BLACK);
        }
    }

    /**
     * creates all white pieces
     */
    private void createWhitePieces() {
        board[7][0] = new Rook(Color.WHITE);
        board[7][1] = new Knight(Color.WHITE);
        board[7][2] = new Bishop(Color.WHITE);
        board[7][3] = new Queen(Color.WHITE);
        board[7][4] = new King(Color.WHITE);
        board[7][5] = new Bishop(Color.WHITE);
        board[7][6] = new Knight(Color.WHITE);
        board[7][7] = new Rook(Color.WHITE);

        for(int i = 0; i < 8; i++){
            board[6][i] = new Pawn(Color.WHITE);
        }
    }

    public ChessPiece[][] getBoard() {
        return board;
    }

    /**
     *
     * @param piece The piece of which we want to know the position
     * @return returns an array with the values [rowPos, colPos]
     */
    public int[] getPosition(ChessPiece piece) {
        int[] position = new int[2];
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {

                if (board[row][col] == null) {
                    continue;
                } else if (board[row][col] == piece) {
                    position[0] = row;
                    position[1] = col;
                    return position;
                }
            }
        }
        return position;
    }

    /**
     * With this method, one piece can be moved to another field
     * @param rowCur current Row
     * @param colCur current Column
     * @param rowDes destination row
     * @param colDes destination column
     */
    public void move(int rowCur, int colCur, int rowDes, int colDes) {
        board[rowDes][colDes] = board[rowCur][colCur];
        board[rowCur][colCur] = null;
    }

    /**
     * The toString method creates a String of the board
     * @return it returns the full board
     */
    public String toString() {

        //Space
        StringBuilder endString = new StringBuilder("\n   ");

        //First Line: All letters are set with space between them
        for(char i: LETTERS) {
            endString.append("  \u202F\u202F").append(i).append("\u202F");
        }

        //Space
        endString.append("\n   ");

        //A parting-line is set between letters and the actual field
        for (char i : LETTERS) {
            endString.append("-----");
        }

        //Space
        endString.append("\n");

        //The board gets created with a number at the start of each line
        for(int i = 0; i < NUMS.length; i++) {

            //Numbers
            endString.append(" ").append(NUMS[i]).append(" | ");

            //Chessboard
            for(ChessPiece j: board[i]){
                if (j != null) {
                    endString.append(j.toString()).append(" | ");
                } else {
                    endString.append("\u26DA" + " | ");
                }
            }

            //Space
            endString.append("\n   ");

            //Parting-line between one line and another
            for(int j = 0; j < 8; j++){
                endString.append("-----");
            }

            //Space
            endString.append("\n");
        }
        return endString.toString();

    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
}
