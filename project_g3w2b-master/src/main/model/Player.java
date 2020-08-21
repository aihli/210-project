package model;

import java.util.ArrayList;

// Player represents each player in the game.
public class Player {
    private ArrayList<Character> myLetters;

    public Player() {
        myLetters = new ArrayList<>();
    }

    // REQUIRES: letter to be valid char from letterBank
    // EFFECTS: adds a letter to Player's myLetters
    // MODIFIES: this
    public void giveLetters(char letter) {
        myLetters.add(letter);
    }

    public ArrayList<Character> getLetters() {
        return myLetters;
    }

    // EFFECTS: counts the number of letters in the ArrayList
    public int numOfLetters() {
        return myLetters.size();
    }

    // REQUIRES: letterPosition to be within the bounds of ArrayList
    // EFFECTS: returns the character at the letterPosition
    public char selectLetter(int letterPosition) {
        return myLetters.get(letterPosition);
    }

    // REQUIRES: the letter position to be within the range
    // EFFECTS: removes the letter from the Player's possession and returns the letter
    // MODIFIES: this
    public char removeLetter(int letterPosition) {
        return myLetters.remove(letterPosition);
    }

    public int getNumberOfLettersInHand() {
        return myLetters.size();
    }
}