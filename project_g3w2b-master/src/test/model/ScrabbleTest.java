package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for all the classes in the model package. This includes the GameBoard, LetterBank, and Player classes.
public class ScrabbleTest {
    GameBoard gb;
    Player p1;
    Player p2;
    LetterBank lb;

    @BeforeEach
    void runBefore() {
        try {
            gb = new GameBoard(2);
        } catch (NumPlayerException e) {
            System.out.println("");
        }

        p1 = new Player();
        p2 = new Player();
        lb = new LetterBank();
    }

    @Test
    void testGetBoard() {
        char[][] board = gb.getBoard();
        assertEquals(15, board.length);
        assertEquals(15, board[0].length);
    }

    @Test
    void testGetNumPlayers() {
        assertEquals(2, gb.getNumPlayers());
    }

    @Test
    void testNumberOfLettersInHand() {
        assertEquals(0, p1.getNumberOfLettersInHand());
        p1.giveLetters('A');
        assertEquals(1, p1.getNumberOfLettersInHand());
        p1.giveLetters('A');
        p1.giveLetters('A');
        assertEquals(3, p1.getNumberOfLettersInHand());
    }

    @Test
    void testNumberOfLettersInBank() {
        gb.nextTurn();

        gb.clearLetters(p1);

        assertEquals(67, lb.getNumberOfBankLetters());
        lb.givePlayerLetter(p1);
        assertEquals(60, lb.getNumberOfBankLetters());
        lb.givePlayerLetter(p2);
        assertEquals(53, lb.getNumberOfBankLetters());
    }

    @Test
    void testPlaceLetter() {
        assertEquals('\u0000', gb.getBoard()[7][7]);
        gb.placeLetter('A', 7,7);
        assertEquals('A', gb.getBoard()[7][7]);
    }

    @Test
    void testSelectLetter() {
        p1.giveLetters('A');
        assertEquals('A', p1.selectLetter(0));
        assertEquals(1, p1.getLetters().size());
    }

    @Test
    void testRemoveLetter() {
        p2.giveLetters('B');
        p2.giveLetters('C');
        assertEquals('B', p2.removeLetter(0));
        assertEquals('C', p2.removeLetter(0));
    }

    @Test
    void testExceptionExpectedGreaterThan4() {
        //Constructor with just numPlayers
        try {
            GameBoard expectedExceptionGameBoard = new GameBoard(5);
            fail ("This was not expected!");
        } catch (NumPlayerException e) {
            System.out.println("Illegal number of players!");
        }

        //Constructor with numPlayers, board, letterBank
        try {
            GameBoard expectedExceptionGameBoard = new GameBoard(5, null);
            fail ("This was not expected!");
        } catch (NumPlayerException e) {
            System.out.println("Illegal number of players!");
        }
    }

    @Test
    void testExceptionExpectedLessThan2() {
        //Constructor with just numPlayers
        try {
            GameBoard expectedExceptionGameBoard = new GameBoard(0);
            fail ("This was not expected!");
        } catch (NumPlayerException e) {
            System.out.println("Illegal number of players!");
        }

        //Constructor with numPlayers, board, letterBank
        try {
            GameBoard expectedExceptionGameBoard = new GameBoard(0, null);
            fail ("This was not expected!");
        } catch (NumPlayerException e) {
            System.out.println("Illegal number of players!");
        }
    }

    @Test
    void testExceptionNotExpected() {
        try {
            GameBoard notExpectedExceptionGameBoard = new GameBoard(2);
        } catch (NumPlayerException e) {
            fail("This is a legal number of players!");
        }

        try {
            GameBoard notExpectedExceptionGameBoard = new GameBoard(2, null);
        } catch (NumPlayerException e) {
            fail("This is a legal number of players!");
        }
    }
}