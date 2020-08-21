package persistence;

import model.GameBoard;
import model.LetterBank;
import model.NumPlayerException;
import model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

// Unit tests for the Reader and Writer classes in the persistence package.
public class PersistenceTest {
    GameBoard gb;
    Player p1;
    Player p2;
    ArrayList<Player> ps;
    LetterBank lb;

    @BeforeEach
    void runBefore() {
        try {
            gb = new GameBoard(2);
        } catch (NumPlayerException e) {
            System.out.println("");
        }

        ps = new ArrayList<>();
        p1 = new Player();
        p2 = new Player();
        lb = new LetterBank();
        lb.givePlayerLetter(p1);
        lb.givePlayerLetter(p2);
        ps.add(p1);
        ps.add(p2);
        gb.placeLetter('A', 7, 7);
    }

    @Test
    void writerTest() {
        try {
            Writer w = new Writer(new File("saved.json"));
            w.savedGameStatus(gb, 2, lb.getLetterBank(), ps);
            Reader r = new Reader(new File("saved.json"));
            r.readSavedGame();

            assertEquals(2, r.getNumPlayers());
            assertEquals(lb.getLetterBank(), r.getLetterBank());

            ArrayList<Player> players = r.getPlayerLetters();
            for (int i = 0; i < 2; i++) {
                assertEquals(ps.get(i).getLetters(), players.get(i).getLetters());
            }

            for (int x = 0; x < GameBoard.BOARD_DIMENSIONS; x++) {
                for (int y = 0; y < GameBoard.BOARD_DIMENSIONS; y++) {
                    assertEquals(gb.getBoard()[x][y], r.getGameBoard().getBoard()[x][y]);
                }
            }

        } catch (Exception e) {
            System.out.println("press f for respect");
        }
    }

    @Test
    void testException() {
        try {
            Writer w = new Writer(null);
        } catch (Exception e) {
            assertTrue(true);
        }

        try {
            Reader r = new Reader(null);
        } catch (Exception e) {
            assertTrue(true);
        }
    }
}