package chess.helper;

/**
 * This class includes constants for all the text displayed at the beginning or during the game
 */
public class UserInfoText {

    public static final String CONTINUE_NEW_OR_SAVED = "Hello player! Do you want to start a new game or continue a saved one?";
    public static final String ENTER_NEW_OR_SAVED = "Enter \"new\" to start a new one or enter \"load\" to load a saved game (without quotation marks): ";

    public static final String WRONG_EXPRESSION = "Wrong expression! Try again: ";

    public static final String LOAD_BY_MOVES_OR_NOT = "Enter G to load a saved game or enter M to load a game from moves: ";

    public static final String ENTER_FILENAME = "Enter the filename: ";
    public static final String FILE_NOT_FOUND = "File not found! Try again";

    public static final String START_GAME_INTRO = "\nHello Chess-Players! Welcome to a simple chess game.\n" +
            "It is a simple chess game with pawn promotion as the only special move, which was implemented.\n\n" +
            "When its your turn, you can save the whole game and play on later, or you can just save the moves. \n" +
            "To save the Game, type \"save\" | To save the moves, type \"saveMoves\" (without quotation marks).\n\n" +
            "Let's start! Please enter your names.";

    public static final String ENTER_NAME_BLACK = "Player 1 BLACK : ";
    public static final String ENTER_NAME_WHITE = "Player 1 WHITE : ";

    public static final String ENTER_MOVE = "Enter your move (e.g. a8 to e5): ";

    public static final String GAMES_SAVED = "The Game was successfully saved! The program is ended.";
    public static final String MOVES_SAVED = "Moves successfully saved!";

}
