package ui;


import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.GameBoard;
import model.LetterBank;
import model.Player;
import persistence.Reader;
import persistence.Writer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.ArrayList;

// Board represents the second scene in the GUI: when the player has selected either how many players or loaded a
// previous game, and the player can now see the 15x15 game board on which they play.
public class Board extends Scene {
    private GameBoard board;
    private ArrayList<Player> listOfPlayers;
    private int tracker;
    private Parent root;
    private int width;
    private int height;
    private int numPlayers;
    private GridPane gameBoard;
    private GridPane playerLetters;
    private VBox buttons;
    private char selectedChar;
    private int selectedPos;
    private Alert alert = new Alert(Alert.AlertType.ERROR);
    private AudioInputStream sound;
    private Clip clip;
    private LetterBank lb = new LetterBank();

    public Board(Parent root, int width, int height, int numPlayers) {
        super(root, width, height);
        this.root = root;
        this.width = width;
        this.height = height;
        this.numPlayers = numPlayers;

        try {
            this.board = new GameBoard(numPlayers);
            this.sound = AudioSystem.getAudioInputStream(new File("src/Ping-sound.wav"));
            clip = AudioSystem.getClip();
            clip.open(this.sound);
        } catch (Exception e) {
            System.out.println(e);
        }
        this.listOfPlayers = new ArrayList<>();
        for (int i = 0; i < numPlayers; i++) {
            Player player = new Player();
            lb.givePlayerLetter(player);
            listOfPlayers.add(player);
        }
        tracker = 0;
        selectedChar = '\u0000';
        selectedPos = -1;
        redraw();
    }

    public Board(Parent root, int width, int height, Reader reader) {
        super(root, width, height);
        this.root = root;
        this.width = width;
        this.height = height;
        this.board = reader.getGameBoard();
        this.numPlayers = reader.getNumPlayers();
        this.lb = new LetterBank(reader.getLetterBank());
        listOfPlayers = reader.getPlayerLetters();
        tracker = 0;
        selectedChar = '\u0000';
        selectedPos = -1;
        redraw();
    }

    private void initBoard() {
        gameBoard = new GridPane();
        for (int i = 0; i < board.getBoard().length; i++) {
            gameBoard.getColumnConstraints().add(new ColumnConstraints(15));
        }
        gameBoard.setAlignment(Pos.CENTER);
        gameBoard.setGridLinesVisible(true);
        ((StackPane) getRoot()).getChildren().add(gameBoard);
    }

    private void initLetters() {
        playerLetters = new GridPane();
        for (int i = 0; i < 7; i++) {
            playerLetters.getColumnConstraints().add(new ColumnConstraints(15));
        }
        playerLetters.setAlignment(Pos.BOTTOM_CENTER);
        playerLetters.setGridLinesVisible(true);
        playerLetters.setPickOnBounds(false);
        ((StackPane) getRoot()).getChildren().add(playerLetters);
    }

    private void initButtons() {
        buttons = new VBox();
        buttons.setAlignment(Pos.BOTTOM_RIGHT);
        buttons.getChildren().add(clear());
        buttons.getChildren().add(nextPlayer());
        buttons.getChildren().add(save());
        buttons.setPickOnBounds(false);
        ((StackPane) getRoot()).getChildren().add(buttons);
    }

    private Button clear() {
        Button clear = new Button();
        clear.setText("Clear board!");
        clear.setOnMouseClicked(e -> {
            board.clearLetters(listOfPlayers.get(tracker));
            redraw();
        });
        return clear;
    }

    private Button nextPlayer() {
        Button nextPlayer = new Button();
        nextPlayer.setText("Next player!");
        nextPlayer.setOnMouseClicked(e -> {
            lb.givePlayerLetter(listOfPlayers.get(tracker));
            if (tracker == numPlayers - 1) {
                tracker = 0;
            } else {
                tracker++;
            }
            board.nextTurn();
            redraw();
        });
        return nextPlayer;
    }

    private Button save() {
        Button save = new Button();
        save.setText("Save your game!");
        save.setOnMouseClicked(e -> {
            try {
                Writer writer = new Writer(new File("scrabble.json"));
                writer.savedGameStatus(board, numPlayers, lb.getLetterBank(), listOfPlayers);
            } catch (Exception ex) {
                System.out.println();
            }
        });
        return save;
    }

    private void redraw() {
        ((StackPane) getRoot()).getChildren().clear();
        initBoard();
        initLetters();
        initButtons();
        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard()[0].length; j++) {
                gameBoard.add(createBoardLetter(listOfPlayers.get(tracker), i, j), i, j);
            }
        }
        for (int i = 0; i < listOfPlayers.get(tracker).getNumberOfLettersInHand(); i++) {
            if (i < listOfPlayers.get(tracker).getNumberOfLettersInHand()) {
                playerLetters.add(createHandLetter(listOfPlayers.get(tracker), i), i, 0);
            }
        }
    }

    private Label createBoardLetter(Player player, int i, int j) {
        char text = ' ';
        if (board.getBoard()[i][j] != '\u0000') {
            text = board.getBoard()[i][j];
        }
        Label label = new Label(Character.toString(text));
        if (text == ' ') {
            label.setOnMouseClicked(e -> {
                if (selectedChar == '\u0000') {
                    alert.setHeaderText("A character has not been selected");
                    alert.setContentText("You need to select a character to place on the board!");
                    alert.showAndWait();
                } else {
                    board.placeLetter(selectedChar, i, j);
                    player.removeLetter(selectedPos);
                    selectedChar = '\u0000';
                    selectedPos = -1;
                    redraw();
                }
            });
        }
        return label;
    }

    private Label createHandLetter(Player player, int i) {
        Label label = new Label(Character.toString(player.selectLetter(i)));
        label.setOnMouseClicked(e -> {
            selectedChar = label.getText().charAt(0);
            selectedPos = i;
            try {
                clip.setMicrosecondPosition(0);
                clip.start();
                Thread.sleep(2000);
                clip.stop();

            } catch (Exception ex) {
                System.out.println(ex);
            }
        });
        return label;
    }


}
