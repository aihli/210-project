package ui;

import model.GameBoard;
import model.LetterBank;
import model.NumPlayerException;
import model.Player;
import persistence.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Main class creates the console line app
public class Main {
    static Scanner playerInput;

    public static void main(String[] args) {
        System.out.println("Welcome to Scrabble!\n"
                + "How many players would like to play?");
        playerInput = new Scanner(System.in);
        int input = playerInput.nextInt();
        GameBoard board = null;

        try {
            board = new GameBoard(input);
        } catch (NumPlayerException e) {
            System.out.println("This should never happen because this class is archaic!");
        }

        ArrayList<Player> listOfPlayer = new ArrayList<Player>();

        //

        LetterBank letterBank = new LetterBank();

        viewBoard(board);
        giveLetters(letterBank, listOfPlayer);

        while (true) {
            for (int i = 0; i < listOfPlayer.size(); i++) {
                viewBoard(board);
                viewLetters(listOfPlayer.get(i));
                placeLetter(listOfPlayer.get(i), board);
            }
        }

    }

    // EFFECTS: helper for adding players to the console
    private static void addPlayerToConstructor(ArrayList<Player> listOfPlayer, int input) {
        for (int i = 0; i < input; i++) {
            Player player = new Player();
            listOfPlayer.add(player);
        }
    }

    // EFFECTS: displays a GameBoard
    private static void viewBoard(GameBoard board) {
        char[][] visualBoard = board.getBoard();

        for (int i = 0; i < GameBoard.BOARD_DIMENSIONS; i++) {
            for (int j = 0; j < GameBoard.BOARD_DIMENSIONS; j++) {
                if (visualBoard[i][j] == '\u0000') {
                    System.out.print('*');
                } else {
                    System.out.print(visualBoard[i][j]);
                }
            }
            System.out.println();
        }
    }

    // EFFECTS: assigns random letters to players from letterBank
    // MODIFIES: player in playerList, letterBank
    private static void giveLetters(LetterBank letterBank, ArrayList<Player> playerList) {
        for (int i = 0; i < playerList.size(); i++) {
            letterBank.givePlayerLetter(playerList.get(i));
        }
    }

    // EFFECTS: shows the randomly assigned letters
    private static void viewLetters(Player player) {
        for (int i = 0; i < player.getNumberOfLettersInHand(); i++) {
            System.out.print(player.getLetters().get(i));
            System.out.print(' ');
        }
    }

    // EFFECTS: removes a letter from player's assigned letters and places on board
    // MODIFIES: board, player
    private static void placeLetter(Player player, GameBoard board) {
        System.out.println("Which letter would you like to place on the board?");
        int var = playerInput.nextInt();
        System.out.println("Where would you like to place it?");
        int x = playerInput.nextInt();
        int y = playerInput.nextInt();
        char selectedLetter = player.removeLetter(var);
        board.placeLetter(selectedLetter, x, y);

        System.out.println("Would you like to continue placing letters down?");
        String input = playerInput.next();
        if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("ye")
                || input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("ay")
                || input.equalsIgnoreCase("ayy") || input.equalsIgnoreCase("ya")
                || input.equalsIgnoreCase("yadude") || input.equalsIgnoreCase("yessir")
                || input.equalsIgnoreCase("ye") || input.equalsIgnoreCase("yee")
                || input.equalsIgnoreCase("yeet")) {
            placeLetter(player, board);
        }
    }
}
