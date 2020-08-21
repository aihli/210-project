package model;

// Exception class for the number of players exceeding 4 or less than 2
public class NumPlayerException extends Exception {

    public NumPlayerException(String str) {
        super(str);
    }
}
