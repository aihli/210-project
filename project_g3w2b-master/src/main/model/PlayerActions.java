package model;

import java.util.ArrayList;

// PlayerActions class refers to what actions the player can make when it's their turn.
// Players can place a letter, clear the letters they made on their turn, and then pass their turn on.
public class PlayerActions {
    ArrayList<Integer> justPlacedX;
    ArrayList<Integer> justPlacedY;

    public PlayerActions() {
        this.justPlacedX = new ArrayList<>();
        this.justPlacedY = new ArrayList<>();
    }

    //EFFECTS: creates list of the positions of letters placed
    public void nextTurn() {
        this.justPlacedX = new ArrayList<>();
        this.justPlacedY = new ArrayList<>();
    }

    // REQUIRES: xposition and yposition must be within board dimensions
    // EFFECTS: places selected letter onto the playing board
    // MODIFIES: board
    public void placeLetter(char letter, int xposition, int yposition, char[][] board) {
        board[xposition][yposition] = letter;
        this.justPlacedX.add(xposition);
        this.justPlacedY.add(yposition);
    }

    // REQUIRES: letters to have been placed (otherwise no function)
    // EFFECTS: removes letters placed on board from current turn
    // MODIFIES: board
    public void clearLetters(Player player, char[][] board) {
        for (int i = 0; i < justPlacedX.size(); i++) {
            player.giveLetters(board[justPlacedX.get(i)][justPlacedY.get(i)]);
            board[justPlacedX.get(i)][justPlacedY.get(i)] = '\u0000';
        }
        this.justPlacedX = new ArrayList<>();
        this.justPlacedY = new ArrayList<>();
    }
}
