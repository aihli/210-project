package persistence;

// reference: https://mkyong.com/java/json-simple-example-read-and-write-json/

import model.GameBoard;
import model.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.util.ArrayList;

// A writer that can write game data to a file
public class Writer {
    private FileWriter fileWriter;
    private JSONObject obj;

    // EFFECTS: constructs a writer that will write data to file
    public Writer(File file) throws IOException {
        if (file == null) {
            throw new IOException();
        }
        fileWriter = new FileWriter(file);
        obj = new JSONObject();
    }

    // REQUIRES: the game to have begun
    // MODIFIES: this
    // EFFECTS: saves the game status as a JSON object
    public void savedGameStatus(GameBoard gameBoard,
                                int numPlayers,
                                ArrayList<Character> letterBank,
                                ArrayList<Player> listOfPlayers) {

        obj.put("Players", numPlayers);

        letterBankHelper(letterBank);

        listPeopleHelper(listOfPlayers);

        listGameBoardHelper(gameBoard);

        try {
            writeSavedGame(obj);
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    // MODIFIES: obj
    // EFFECTS: serves as a helper function to add (stringed) letters to listLetterBank
    public void letterBankHelper(ArrayList<Character> letterBank) {
        JSONArray listLetterBank = new JSONArray();
        for (char letter: letterBank) {
            listLetterBank.add(String.valueOf(letter));
        }
        obj.put("LetterBank", listLetterBank);
    }

    // MODIFIES: obj
    // EFFECTS: serves as a helper function to add strings to listLetter to listPeople
    public void listPeopleHelper(ArrayList<Player> people) {
        JSONArray listPeople = new JSONArray();
        for (Player player: people) {
            JSONArray listLetter = new JSONArray();
            for (char c: player.getLetters()) {
                listLetter.add(String.valueOf(c));
            }
            listPeople.add(listLetter);
        }
        obj.put("PlayerLetterList", listPeople);
    }

    // MODIFIES: obj
    // EFFECTS: serves as a helper function to add gameBoard information as list
    public void listGameBoardHelper(GameBoard gameBoard) {
        JSONArray listGameBoard = new JSONArray();
        for (int i = 0; i < GameBoard.BOARD_DIMENSIONS; i++) {
            JSONArray listPositions = new JSONArray();
            for (int j = 0; j < GameBoard.BOARD_DIMENSIONS; j++) {
                listPositions.add(String.valueOf(gameBoard.getBoard()[i][j]));
            }
            listGameBoard.add(listPositions);
        }
        obj.put("GameBoard", listGameBoard);
    }

    // REQUIRES: JSON Object to have stored information
    // MODIFIES: file
    // EFFECTS: writes the saved game into a file
    public void writeSavedGame(JSONObject obj) throws IOException {
        fileWriter.write(obj.toJSONString());
        fileWriter.flush();
    }
}