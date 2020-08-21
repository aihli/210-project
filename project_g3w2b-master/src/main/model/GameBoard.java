package model;

// GameBoard represents the implementation of the game: the specifications for the board. This includes the number of
// players, as well as the dimensions of the board, and how the board is receptive to letter placement.
public class GameBoard {
    char[][] board; // two dimensional array
    int numPlayers;
    public static final int BOARD_DIMENSIONS = 15; // not that this will change...
    PlayerActions actions;

    public GameBoard(int numPlayers) throws NumPlayerException {
        if (numPlayers > 4 || numPlayers < 2) {
            throw new NumPlayerException("Illegal number of players");
        }

        this.numPlayers = numPlayers;
        this.board = new char[BOARD_DIMENSIONS][BOARD_DIMENSIONS];
        this.actions = new PlayerActions();
    }

    public GameBoard(int numPlayers, char[][] board) throws NumPlayerException {
        if (numPlayers > 4 || numPlayers < 2) {
            throw new NumPlayerException("Illegal number of players");
        }

        this.numPlayers = numPlayers;
        this.board = board;
        this.actions = new PlayerActions();
    }

    //EFFECTS: next turn for the game
    public void nextTurn() {
        actions.nextTurn();
    }

    // see: PlayerActions
    // REQUIRES: xposition and yposition must be within board dimensions
    // EFFECTS: places selected letter onto the playing board
    // MODIFIES: board
    public void placeLetter(char letter, int xposition, int yposition) {
        actions.placeLetter(letter, xposition, yposition, board);
    }

    //see: PlayerActions
    // REQUIRES: letters to have been placed (otherwise no function)
    // EFFECTS: removes letters placed on board from current turn
    // MODIFIES: board
    public void clearLetters(Player player) {
        actions.clearLetters(player, board);
    }

    public char[][] getBoard() {
        return board;
    }

    public int getNumPlayers() {
        return numPlayers;
    }
}