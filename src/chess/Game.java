/**
 * The Game program implements a simple version of chess.
 * It implements only the basic moves and pawn promotion and does not support special moves like "Rochade".
 * Neither it implements checkmate. So the King has to be killed to end the game.
 *
 * @auther Tim Eichinger
 * @version 2.0
 */
package chess;

import chess.piece.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static chess.helper.UserInfoText.*;

public class Game implements Serializable {

    private Player playerBlack, playerWhite;
    private Board board;

    private ArrayList<String> moves;

    private String moveStr = "";
    private boolean pieceThrown = false;
    private boolean wasLastMoveByBlack = false;
    private boolean isSave = false;
    private String winningPlayer = "";

    private static final String NEW = "new";
    private static final String LOAD = "load";

    private static final String GAME = "G";
    private static final String MOVES = "M";

    private static final String SAVE = "save";
    private static final String SAVE_MOVES = "saveMoves";

    public static void main(String[] args) {
        startingMethod();
    }

    /**
     * This method processes the user input for starting or loading a game.
     */
    private static void startingMethod() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n" + CONTINUE_NEW_OR_SAVED);
        System.out.print(ENTER_NEW_OR_SAVED);

        String input = scanner.nextLine();

        while (!(input.equals(NEW) || input.equals(LOAD))) {
            System.out.print(WRONG_EXPRESSION);
            input = scanner.nextLine();
        }

        if (input.equals(NEW)) {
            new Game();
        } else {
            handleLoadAction(scanner);
        }

    }

    /**
     * This method handles the user input for loading a saved game or a game by moves.
     * @param scanner
     */
    private static void handleLoadAction(Scanner scanner) {
        System.out.print(LOAD_BY_MOVES_OR_NOT);
        String input = scanner.nextLine();
        while (!(input.equals(GAME) || input.equals(MOVES))) {
            System.out.print(WRONG_EXPRESSION);
            input = scanner.nextLine();
        }
        if (input.equals(GAME)) {
            tryLoadGame(false, scanner);
        } else {
            tryLoadGame(true, scanner);
        }
    }

    private static void tryLoadGame(boolean byMoves, Scanner scanner) {
        try {
            System.out.print(ENTER_FILENAME);
            String filename = scanner.nextLine();
            if (byMoves) {
                Game.loadGameByMoves(filename);
            } else {
                Game.loadGame(filename);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(FILE_NOT_FOUND);
            handleLoadAction(scanner);
        }
    }

    /**
     * A constructor to load a game that was previously serialized and saved.
     * @param moves includes the saved moves which are made so far.
     * @param board includes the saved board.
     * @param playerBlack includes the saved playerBlack.
     * @param playerWhite includes the saved playerWhite.
     * @param wasLastMoveByBlack includes which player made the last move before the game was saved.
     */
    public Game(ArrayList<String> moves, Board board, Player playerBlack, Player playerWhite, boolean wasLastMoveByBlack) {
        this.wasLastMoveByBlack = wasLastMoveByBlack;
        this.moves = moves;
        this.board = board;
        this.playerWhite = playerWhite;
        this.playerBlack = playerBlack;
        Scanner scanner = new Scanner(System.in);
        startLoadedGame(scanner);
        startGameRounds(scanner);
        endGame(scanner);
    }

    /**
     * Constructor for a Game which is loaded from moves which are taken from an array-list or a file!
     * @param moves includes the saved moves which are made so far
     * @param board includes a board where the saved moves are already made!
     */
    public Game(ArrayList<String> moves, Board board) {
        this.moves = moves;
        this.board = board;
        Scanner scanner = new Scanner(System.in);
        startGame(scanner, true);
        startGameRounds(scanner);
        endGame(scanner);

    }

    /**
     * Constructor to start a new Game
     */
    public Game() {
        moves = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        startGame(scanner, false);
        startGameRounds(scanner);
        endGame(scanner);

    }

    /**
     * Starting method for a new Game
     * @param scanner includes the current used scanner.
     */
    private void startGame(Scanner scanner, boolean isLoad) {
        System.out.println(START_GAME_INTRO);

        System.out.print(ENTER_NAME_BLACK);
        playerBlack = new Player(scanner.nextLine(), Color.BLACK);
        System.out.print(ENTER_NAME_WHITE);
        playerWhite = new Player(scanner.nextLine(), Color.WHITE);
        if (isLoad) {
            System.out.println("\n Let's continue the game: " + playerBlack.getName() + "(BLACK)" + " vs " + playerWhite.getName() + "(WHITE)");
        } else {
            System.out.println("\n Let's start the game: " + playerBlack.getName() + "(BLACK)" + " vs " + playerWhite.getName() + "(WHITE)");
            board = new Board();
        }
        System.out.println(board.toString());

    }

    /**
     * Start method of a game whose score was saved and now loaded again
     * @param scanner includes the current used scanner.
     */
    private void startLoadedGame(Scanner scanner) {
        System.out.println("\n Let's continue the game: " + playerBlack.getName() + "(BLACK)" + " vs " + playerWhite.getName() + "(WHITE)");
        System.out.println(board.toString());
    }

    /**
     * Method which includes the handling of the game (-rounds).
     * @param scanner includes the current used scanner.
     */
    private void startGameRounds(Scanner scanner) {
        while (!board.isGameOver()) {
            if (wasLastMoveByBlack) {
                movePlayer(scanner, playerWhite);
                if (board.isGameOver()) {
                    break;
                }
                movePlayer(scanner, playerBlack);
            } else {
                movePlayer(scanner, playerBlack);
                if (board.isGameOver()) {
                    break;
                }
                movePlayer(scanner, playerWhite);
            }
        }
    }

    /**
     * method which is called, when the game ends.
     * @param scanner includes the current used scanner.
     */
    private void endGame(Scanner scanner) {
        scanner.close();
        System.out.println("Game Over! " + winningPlayer + " has won the game! Congratulations!");
    }

    /**
     * Handles the move of a Player
     * @param scanner includes the current used scanner.
     * @param player current Player
     */
    private void movePlayer(Scanner scanner, Player player) {
        System.out.println("It's your turn " + player.getName() + "!");
        System.out.print(ENTER_MOVE);
        String playerMove = scanner.nextLine();
        handleSaves(player, playerMove, scanner);
        while (!isValidMove(getStartPos(playerMove, false), getEndPos(playerMove, false), board, player)) {
            if (isSave) {
                System.out.print(ENTER_MOVE);
                handleSaves(player, playerMove, scanner);
                isSave = false;
            } else {
                System.out.println(WRONG_EXPRESSION);
                System.out.print(ENTER_MOVE);
            }

            playerMove = scanner.nextLine();
        }
        handleMovement(playerMove, player, scanner);
        System.out.println(moves);
        System.out.println(board.toString());
    }

    /**
     * The method handles the movement of the player entered move.
     * There is checked, whether a pawn promotion is valid.
     */
    private void handleMovement(String playerMove, Player player, Scanner scanner) {
        if (isPawnPromotionValid(playerMove)) {
            board.move(getStartPos(playerMove, false)[0], getStartPos(playerMove, false)[1], getEndPos(playerMove, false)[0], getEndPos(playerMove, false)[1]);
            checkPawnPromotion(player, getEndPos(playerMove, false)[0], getEndPos(playerMove, false)[1], scanner, playerMove);
        } else {
            addMoveToArrayList(playerMove, null);
            board.move(getStartPos(playerMove, false)[0], getStartPos(playerMove, false)[1], getEndPos(playerMove, false)[0], getEndPos(playerMove, false)[1]);
        }
    }

    /**
     * This method handles the user input for saving the game or moves.
     */
    private void handleSaves(Player player, String playerMove, Scanner scanner) {
        if (playerMove.equals(SAVE)) {
            wasLastMoveByBlack = player.getColor() == Color.WHITE;
            this.saveGame(playerBlack.getName() + playerWhite.getName());
            System.out.println(GAMES_SAVED);
            scanner.close();
            System.exit(0);
        } else if (playerMove.equals(SAVE_MOVES)) {
            this.saveMoves(playerBlack.getName() + "vs" + playerWhite.getName());
            System.out.println(MOVES_SAVED);
            isSave = true;
        }
    }

    /**
     * checks if a pawn promotion is valid, as the position is correct.
     */
    private boolean isPawnPromotionValid(String playerMove) {
        if (board.getBoard()[getStartPos(playerMove, false)[0]][getStartPos(playerMove, false)[1]].getClass().equals(Pawn.class)) {
            return getEndPos(playerMove, false)[0] == 7 || getEndPos(playerMove, false)[0] == 0;
        } else {
            return false;
        }
    }

    /**
     * checks if the pawn promotion is valid for the specific color.
     */
    private void checkPawnPromotion(Player player, int destRow, int destCol, Scanner scanner, String playerMove) {
        if (player.getColor() == Color.BLACK) {
            if (destRow == 7) {
                promotePawn(player, scanner, destRow, destCol, playerMove);
            }
        } else {
            if (destRow == 0) {
                promotePawn(player, scanner, destRow, destCol, playerMove);
            }
        }

    }

    /**
     * this method executes the pawn promotion
     */
    private void promotePawn(Player player, Scanner scanner, int destRow, int destCol, String playerMove) {
        String[] validInputs = {"bishop", "queen", "rook", "knight"};
        System.out.println(player.getName() + ", you can change your pawn!");
        System.out.print("Enter the type of a ChessPiece you want your pawn to be transformed to (Queen, Knight, Rook or Bishop): ");
        String pieceTrans = scanner.nextLine().toLowerCase();
        while (!Arrays.asList(validInputs).contains(pieceTrans)) {
            System.out.print("Wrong input! Try again: ");
            pieceTrans = scanner.nextLine().toLowerCase();
        }
        switch (pieceTrans) {
            case "queen":
                board.getBoard()[destRow][destCol] = new Queen(player.getColor());
                addMoveToArrayList(playerMove, new Queen(Color.WHITE));
                break;
            case "knight":
                board.getBoard()[destRow][destCol] = new Knight(player.getColor());
                addMoveToArrayList(playerMove, new Knight(Color.WHITE));
                break;
            case "bishop":
                board.getBoard()[destRow][destCol] = new Bishop(player.getColor());
                addMoveToArrayList(playerMove, new Bishop(Color.WHITE));
                break;
            case "rook":
                board.getBoard()[destRow][destCol] = new Rook(player.getColor());
                addMoveToArrayList(playerMove, new Rook(Color.WHITE));
                break;
        }
    }

    /**
     * Adds one move to the ArrayList "moves"
     * @param playerMove includes the String of the user entered move
     */
    private void addMoveToArrayList(String playerMove, ChessPiece pawnChangePiece) {
        if (pawnChangePiece != null) {
            moveStr += new Pawn(Color.WHITE).toString();
        } else {
            moveStr += whiteUniCodeChessPiece(playerMove);
        }

        if (pieceThrown) {
            moveStr += playerMove.split(" ")[0] + "x" + playerMove.split(" ")[2];
        } else {
            moveStr += playerMove.split(" ")[0] + "-" + playerMove.split(" ")[2];
            pieceThrown = false;
        }
        if (pawnChangePiece != null) {
            moveStr += pawnChangePiece.toString();
        }
        moves.add(moveStr);
        moveStr = "";
    }

    /**
     * converts a chesspiece into the white representation of it
     * @param playerMove includes the string of the user entered move
     * @return the white representation of a chesspiece
     */
    private String whiteUniCodeChessPiece(String playerMove) {
        int currentRow = getStartPos(playerMove, false)[0];
        int currentCol = getStartPos(playerMove, false)[1];

        ChessPieceType chessPiece = board.getBoard()[currentRow][currentCol].getChessPieceType();
        String unicode = "";
        switch (chessPiece) {
            case KING:
                unicode = "\u2654";
                break;
            case QUEEN:
                unicode = "\u2655";
                break;
            case ROOK:
                unicode = "\u2656";
                break;
            case BISHOP:
                unicode = "\u2657";
                break;
            case KNIGHT:
                unicode = "\u2658";
                break;
            case PAWN:
                unicode = "\u2659";
                break;
        }

        return unicode;

    }

    /**
     * Checks if the user entered move is a valid move
     * @param startPos includes the player entered start-position
     * @param endPos includes the player entered destination
     * @param board includes the current board object
     * @param player includs the current player, who can move.
     * @return if it's a valid move it returns true
     */
    private boolean isValidMove(int[] startPos, int[] endPos, Board board, Player player) {
        if (startPos[0] != -1 && startPos[1] != -1 && endPos[0] != -1 && endPos[1] != -1) {

            ChessPiece chessPieceStart = board.getBoard()[startPos[0]][startPos[1]];
            if (chessPieceStart == null) {
                return false;
            }

            if (chessPieceStart.getColor() == player.getColor()) {
                if (chessPieceStart.getPossibleDestinations(board)[endPos[0]][endPos[1]]) {
                    if (board.getBoard()[endPos[0]][endPos[1]] != null) {
                        ChessPiece chessPieceEnd = board.getBoard()[endPos[0]][endPos[1]];
                        pieceThrown = true;
                        if (chessPieceEnd.getChessPieceType() == ChessPieceType.KING) {
                            board.setGameOver(true);
                            winningPlayer = player.getName();
                        }
                    }
                    return true;
                } else return false;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * filters the start-position out of the user entered string and sets the position in a integer-array.
     * if the array includes -1, the position was invalid
     * @param move includes the user entered move
     * @param isLoad represents if the moves are loaded from an arrayList or a file.
     * @return Integer array which includes 2 values representing row and column of start
     */
    private static int[] getStartPos(String move, boolean isLoad) {
        int[] startPos = {-1, -1};

        if (!isLoad) {
            setPosNormalGame(move, startPos, 0);
        } else {
            setPosLoadingGame(move, startPos);
        }

        return startPos;
    }

    /**
     * filters the destination-position out of the user entered string and sets the position in a integer-array.
     * if the array includes -1, the position was invalid
     * @param move includes the user entered move
     * @param isLoad represents if the moves are loaded from an arrayList or a file.
     * @return Integer array which includes 2 values representing row and column of destination
     */
    private static int[] getEndPos(String move, boolean isLoad) {
        int[] endPos = {-1, -1};

        if (!isLoad) {
            setPosNormalGame(move, endPos, 2);
        } else {
            setPosLoadingGame(move, endPos);
        }

        return endPos;
    }

    /**
     * Helping method for getStartPos and getEndPos. This method si called when the moves are from a loaded game.
     * @param move This variable includes either the start position or destination position of the user input.
     * @param pos This method represents the IntegerArray which is used in getEndPos and getStartPos
     */
    private static void setPosLoadingGame(String move, int[] pos) {
        char col = move.charAt(0);
        char row = move.charAt(1);

        for (int i = 0; i < Board.LETTERS.length; i++) {
            if (Board.LETTERS[i] == col) {
                pos[1] = i;
            }
        }

        for (int i = 7; i >= 0; i--) {
            if (Board.NUMS[i] == row) {
                pos[0] = i;
            }
        }
    }

    /**
     * Helping method for getStartPos and getEndPos. This method si called when the moves are from a new game.
     * @param move This variable includes either the start position or destination position of the user input.
     * @param pos This method represents the IntegerArray which is used in getEndPos and getStartPos
     * @param startOrEnd This variable includes, whether the method is used for start-position or end-position
     */
    private static void setPosNormalGame(String move, int[] pos, int startOrEnd) {
        if (move.split(" ").length == 3) {
            String moveEnd = move.split(" ")[startOrEnd];
            if (moveEnd.length() == 2) {
                char col = moveEnd.charAt(0);
                char row = moveEnd.charAt(1);

                for (int i = 0; i < Board.LETTERS.length; i++) {
                    if (Board.LETTERS[i] == col) {
                        pos[1] = i;
                    }
                }

                for (int i = 7; i >= 0; i--) {
                    if (Board.NUMS[i] == row) {
                        pos[0] = i;
                    }
                }
            }
        }
    }

    /**
     * Saves all moves into a txt-file
     * @param fileName represents the name of the file
     */
    public void saveMoves(String fileName) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            for (int i = 0; i < getMoves().size(); i++) {
                bw.write(getMoves().get(i) + "\n");
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Serializes the game and score and saves it into a file
     * @param fileName represents the name of the file
     */
    public void saveGame(String fileName) {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(
                    new FileOutputStream(fileName + ".ser"));
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oos != null)
                try {
                    oos.close();
                } catch (IOException ignored) {
                }
        }
    }

    /**
     * loads a game by an array-list of moves
     * @param moves array-list witch moves that were made
     * @return new Game where the moves of the array-list are already made.
     */
    public static Game loadGameByMoves(ArrayList<String> moves) {
        Board board = new Board();
        executeMoves(board, moves);
        return new Game(moves, board);
    }

    /**
     * loads a game by a file of moves
     * @param fileName represents the name of a file
     * @return new Game where the moves of the file are already made.
     */
    public static Game loadGameByMoves(String fileName) {
        Board board = new Board();

        ArrayList<String> moves = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String oneLine;
            while ((oneLine = br.readLine()) != null) {
                moves.add(oneLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        executeMoves(board, moves);
        return new Game(moves, board);
    }

    /**
     * Helping-method for loading a Game by moves.
     * This method executes all move which are saved in the array-list "moves"
     * @param board includes the current board representation
     * @param moves includes the saved moves
     */
    private static void executeMoves(Board board, ArrayList<String> moves) {
        for (String move : moves) {
            int currentRow = getStartPos(move.substring(1, 3), true)[0];
            int currentCol = getStartPos(move.substring(1, 3), true)[1];
            int destRow = getEndPos(move.substring(4, 6), true)[0];
            int destCol = getEndPos(move.substring(4, 6), true)[1];

            if (move.length() == 7) {
                board.move(currentRow, currentCol, destRow, destCol);
                switch (move.substring(6)) {
                    case "\u2655": //Queen
                        board.getBoard()[destRow][destCol] = new Queen(board.getBoard()[destRow][destCol].getColor());
                        break;
                    case "\u2657": //Bishop
                        board.getBoard()[destRow][destCol] = new Bishop(board.getBoard()[destRow][destCol].getColor());
                        break;
                    case "\u2656": //Rook
                        board.getBoard()[destRow][destCol] = new Rook(board.getBoard()[destRow][destCol].getColor());
                        break;
                    case "\u2658": //Knight
                        board.getBoard()[destRow][destCol] = new Knight(board.getBoard()[destRow][destCol].getColor());
                        break;
                }

            } else {
                board.move(currentRow, currentCol, destRow, destCol);
            }


        }
    }

    /**
     * Deserialize and loads a game from a previously serialized and saved game.
     * @param fileName represents the filename of a file
     * @return new Game which represents the score of saved game
     */
    public static Game loadGame(String fileName) {

        ObjectInputStream inputStream = null;
        try {
            inputStream = new ObjectInputStream(new FileInputStream(fileName + ".ser"));
            Object obj = inputStream.readObject();
            if (obj instanceof Game) {
                Game game = (Game) obj;
                return new Game(game.getMoves(), game.getBoard(), game.getPlayerBlack(), game.getPlayerWhite(), game.isWasLastMoveByBlack());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null)
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
        }
        return null;
    }


    public Board getBoard() {
        return board;
    }

    public ArrayList<String> getMoves() {
        return moves;
    }

    public Player getPlayerBlack() {
        return playerBlack;
    }

    public Player getPlayerWhite() {
        return playerWhite;
    }

    public boolean isWasLastMoveByBlack() {
        return wasLastMoveByBlack;
    }
}
