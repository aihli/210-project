package ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import persistence.Reader;

import java.io.File;

// App is the opening screen. Run this class to play the game!
public class App extends Application {

    private Board board;
    private Text intro = new Text(10, 10, "Enter the number of players: ");
    private TextField textField = new TextField();
    private Alert alert = new Alert(Alert.AlertType.ERROR);
    private FileChooser fc = new FileChooser();
    private Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setTitle("Welcome to Scrabble!");
        stage.setScene(new Scene(root(),500, 500));
        stage.show();
    }

    private Button startButton() {
        Button button = new Button();
        button.setText("Click to Start");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    int numPlayers = Integer.parseInt(textField.getText().toString());
                    board = new Board(new StackPane(), 500, 500, numPlayers);
                    stage.setScene(board);
                } catch (Exception e) {
                    System.out.println(e);
                    alert.setHeaderText("Invalid input");
                    alert.setContentText("Must be an integer value between 2 and 4.");
                    alert.showAndWait();
                }
            }
        });
        return button;
    }

    private Button chooseSavedFile() {
        Button button = new Button();
        button.setText("Load saved game");
        button.setOnMouseClicked(e -> {
            File file = fc.showOpenDialog(stage);
            if (file != null) {
                try {
                    Reader reader = new Reader(file);
                    reader.readSavedGame();
                    board = new Board(new StackPane(), 500, 500, reader);
                    stage.setScene(board);
                } catch (Exception ex) {
                    System.out.println(ex);
                    alert.setHeaderText("Invalid file");
                    alert.setContentText("Must choose a saved game file.");
                    alert.showAndWait();
                }
            }
        });
        return button;
    }

    private Parent root() {
        VBox root = new VBox();
        root.getChildren().add(intro);
        root.getChildren().add(textField);
        root.getChildren().add(startButton());
        root.getChildren().add(chooseSavedFile());
        return root;
    }

}
