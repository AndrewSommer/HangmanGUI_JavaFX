import java.util.ArrayList;

/**
 * The Model for MyHangmanApp Model-View-Controller app
 *
 * @author StevenSommer
 * @version 1.0.0 November 2017
 */
public class MyHangmanModel {

    /**
     * List of the incorrect letters guessed containing no duplicates
     */
    private final ArrayList<Character> incorrectGuessed = new ArrayList<>();
    /**
     * List of the correct letters guessed containing no duplicates
     */
    private final ArrayList<Character> correctGuessed = new ArrayList<>();
    /**
     * Stores the correct word
     */
    private String correctString;
    /**
     * The characters displayed underneath the hangman picture thats updated as
     * correct letters are guessed
     */
    private char[] displayedWord;

    /**
     * Counter to keep track of incorrect guesses
     */
    private int incorrectGuesses = 0;

    /**
     * Returns the number of incorrect guesses
     * @return the number of incorrect guesses
     */
    public final int getIncorrectGuessNum() {
        return incorrectGuesses;
    }

    /**
     * Gets the word from the controller for the other user(s) to guess
     * @param correctString
     */
    public final void setCorrectString(String correctString) {
        this.correctString = correctString;
        displayedWord = new char[correctString.length()];
        for (int setter = 0; setter < correctString.length(); setter++) {
            displayedWord[setter] = '_';
        }
    }

    /**
     * Returns the updated displayed string underneath the hangman picture
     * @return
     */
    public final String getDisplayedWordAsString() {
        String tempString = "";
        for (int i = 0; i < correctString.length(); i++) {
            tempString += displayedWord[i];
        }
        return tempString;
    }

    /**
     * Clears the correct and incorrect guess lists, the correct word, the displayed word, and number of
     * incorrect guesses in order to start a new game
     */
    public final void resetGame() {
        correctGuessed.clear();
        incorrectGuessed.clear();
        correctString = null;
        displayedWord = null;
        incorrectGuesses = 0;
    }

    /**
     * Specifies whether a character guess has been previously guessed, is correct, or is incorrect
     * @param guess
     * @return 1 specifies the guess is correct, 2 specifies the guess is incorrect, and 3 specifies the
     * character has previously been guessesd
     */
    public final int isCorrectGuess(Character guess) {
        if (correctGuessed.contains(guess)) {
            char[] correctLetters = correctString.toCharArray();
            int count = 0;
            for (char i : correctLetters) {
                if (i == guess) {
                    displayedWord[count] = i;
                }
                count++;
            }
            return 3;
        }
        if (incorrectGuessed.contains(guess)) {
            return 3;
        }
        if (correctString.contains(guess.toString())) {
            correctGuessed.add(guess);
            for (int updater = 0; updater < correctString.length(); updater++) {
                if (correctString.toCharArray()[updater] == guess) {
                    displayedWord[updater] = guess;
                }
            }
            return 1;
        } else {
            incorrectGuesses++;
            incorrectGuessed.add(guess);
            return 2;
        }
    }

}
