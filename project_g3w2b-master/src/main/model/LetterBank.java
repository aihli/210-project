package model;

import java.util.ArrayList;
import java.util.Random;

// The LetterBank class contains the information pertaining to how many, and what, letters exist in each unique game of
// scrabble, as well as allocating letters.
public class LetterBank {
    private ArrayList<Character> letterBank;

    public LetterBank(ArrayList<Character> letterBank) {
        this.letterBank = letterBank;
    }

    public LetterBank() {
        letterBank = new ArrayList<>();
        // A-9, B-2, C-2, D-4, E-12, F-2, G-3, H-2,
        // I-9, J-1, K-1, L-4, M-2, N-6, O-8, P-2,
        // Q-1, R-6, S-4, T-6, U-4, V-2, W-2, X-1,
        // Y-2, Z-1 and Blanks-2.
        addMultipleLetters(9, 'A');
        addMultipleLetters(2, 'B');
        addMultipleLetters(2, 'C');
        addMultipleLetters(4, 'D');
        addMultipleLetters(12, 'F');
        addMultipleLetters(3, 'G');
        addMultipleLetters(2, 'H');
        addMultipleLetters(9, 'I');
        letterBank.add('J');
        letterBank.add('K');
        addMultipleLetters(4, 'L');
        addMultipleLetters(2, 'M');
        addMultipleLetters(6, 'N');
        addMultipleLetters(2, 'V'
        );
        addMultipleLetters(2, 'W');
        letterBank.add('X');
        addMultipleLetters(2, 'Y');
        letterBank.add('Z');
        addMultipleLetters(2, ' ');
    }

    // EFFECTS: creates an abstract for loop that allows easily adding # of letters
    private void addMultipleLetters(Integer number, Character character) {
        for (int i = 0; i < number; i++) {
            letterBank.add(character);
        }
    }

    // MODIFIES: playerLetterBank ArrayList because letter will be removed
    // EFFECTS: returns random letter to player
    private char returnRandomCharacter() {
        Random rand = new Random();
        return letterBank.remove(rand.nextInt(letterBank.size()));
    }

    // MODIFIES: this, and letters possessed by player
    // EFFECTS: gives a random letter from the LetterBank to the Player
    public void givePlayerLetter(Player player) {
        int num = 7 - player.numOfLetters();
        for (int i = 0; i < num; i++) {
            player.giveLetters(returnRandomCharacter());
        }
    }

    public ArrayList<Character> getLetterBank() {
        return letterBank;
    }

    public int getNumberOfBankLetters() {
        return letterBank.size();
    }
}


