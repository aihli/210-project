package persistence;

import model.GameBoard;
import model.NumPlayerException;
import model.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

// A reader that can read saved game data from a file
public class Reader {
    private FileReader fileReader;
    private GameBoard gameBoard;
    private int numPlayers;
    private ArrayList<Character> letterBank;
    private ArrayList<Player> listOfPlayers;
    private JSONParser parser;

    // EFFECTS: constructs a reader that will read the file
    public Reader(File file) throws IOException {
        fileReader = new FileReader(file);
        parser = new JSONParser();
        letterBank = new ArrayList<>();
        listOfPlayers = new ArrayList<>();
    }

    // REQUIRES: existing saved file
    // EFFECTS: method that reads everything
    public void readSavedGame() {
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(fileReader);

            numPlayers = ((Long) jsonObject.get("Players")).intValue();

            letterBankHelper(jsonObject);

            playerLetterHelper(jsonObject);

            gameBoardHelper(jsonObject);

        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    // EFFECTS: helper to read characters from LtterBank
    public void letterBankHelper(JSONObject jsonObject) {
        JSONArray lb = (JSONArray) jsonObject.get("LetterBank");
        for (String string : (Iterable<String>) lb) {
            letterBank.add(string.charAt(0));
        }
    }

    //EFFECTS: helper to read list of Player's individual list of assigned letters
    public void playerLetterHelper(JSONObject jsonObject) {
        JSONArray pl = (JSONArray) jsonObject.get("PlayerLetterList");
        for (JSONArray str : (Iterable<JSONArray>) pl) {
            Player player = new Player();
            for (String string : (Iterable<String>) str) {
                player.giveLetters(string.charAt(0));
            }
            listOfPlayers.add(player);
        }
    }

    // EFFECTS: helper to read the gameBoard status
    public void gameBoardHelper(JSONObject jsonObject) {
        char[][] board = new char[GameBoard.BOARD_DIMENSIONS][GameBoard.BOARD_DIMENSIONS];
        JSONArray boardCharacters = (JSONArray) jsonObject.get("GameBoard");
        Iterator<JSONArray> bcIterator = boardCharacters.iterator();
        int x = 0; // represents the column
        while (bcIterator.hasNext()) {
            JSONArray letters = bcIterator.next();
            int y = 0; // represents the row
            for (String string : (Iterable<String>) letters) {
                board[x][y] = string.charAt(0);
                y++;
            }
            x++;
        }
        try {
            gameBoard = new GameBoard(numPlayers, board);
        } catch (NumPlayerException e) {
            System.out.println("Illegal number of players!");
        }
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public ArrayList<Character> getLetterBank() {
        return letterBank;
    }

    public ArrayList<Player> getPlayerLetters() {
        return listOfPlayers;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }
}