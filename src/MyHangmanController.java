import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * The controller for MyHangmanApp Model-View-Controller app
 *
 * @author Steven Sommer
 * @version 1.0.0 November 2017
 */
public class MyHangmanController {

    @FXML
    private Button startResetButton;
    @FXML
    private Label warningPromptLabel;
    @FXML
    private TextField enterWordTextField;
    @FXML
    private TextField enterGuessTextField;
    @FXML
    private ImageView hangmanImageView;
    @FXML
    private Label displayedWordLabel;

    /**
     * Displays the first incorrect letter guessed
     */
    @FXML
    private ImageView incorrect0;

    /**
     * Displays the second incorrect letter guessed
     */
    @FXML
    private ImageView incorrect1;

    /**
     * Displays the third incorrect letter guessed
     */
    @FXML
    private ImageView incorrect2;

    /**
     * Displays the fourth incorrect letter guessed
     */
    @FXML
    private ImageView incorrect3;

    /**
     * Displays the fifth incorrect letter guessed
     */
    @FXML
    private ImageView incorrect4;

    /**
     * Displays the sixth incorrect letter guessed
     */
    @FXML
    private ImageView incorrect5;

    /**
     * The Model for this Model-View-Controller application
     */
    private MyHangmanModel model = new MyHangmanModel();

    /**
     * Specifies whether the game is in progress or not
     */
    private boolean gameInProgress;

    /**
     * The filename for the initial picture to display
     */
    private String imageName = "Hangman-0.png";

    /**
     * Called when the GUI is set up to display the correct picture, specify which textfields
     * should be editable, and the text in the textfields upon launching the program
     */
    public final void initialize() {
        gameInProgress = false;
        startResetButton.setVisible(false);
        enterWordTextField.setText("");
        enterGuessTextField.setText("");
        enterWordTextField.setEditable(true);
        enterGuessTextField.setEditable(false);
        displayedWordLabel.setVisible(false);
        try {
            hangmanImageView.setImage(new Image(imageName));
        } catch (NullPointerException npe) {
            System.out.println("Images are missing from directory");
        } catch (IllegalArgumentException iae) {
            System.out.println("Image is missing, or file is unsupported");
        }
    }

    /**
     * Recieves the word from the user, verifying that only characters are entered
     *  and the length is below 15 characters before sending the correct word to the
     *  model
     * @param keyPressed    the key the user pressed. Used to verify the user entered
     *                      the word
     */
    @FXML
    private void getWord(KeyEvent keyPressed) {
        if (!gameInProgress) {
            if (keyPressed.getCode().equals(KeyCode.ENTER)) {
                if (enterWordTextField.getCharacters().length() > 15) {
                    warningPromptLabel.setText("Word is too long");
                } else {
                    if (enterWordTextField.getCharacters().toString().toLowerCase().matches("[a-z]+")) {
                        model.setCorrectString(enterWordTextField.getCharacters().toString().toLowerCase());
                        gameInProgress = true;
                        startResetButton.setVisible(true);
                        enterWordTextField.setEditable(false);
                        enterGuessTextField.setEditable(true);
                        displayedWordLabel.setVisible(true);
                        displayedWordLabel.setText(model.getDisplayedWordAsString());
                        warningPromptLabel.setText("Game Started");
                    } else {
                        warningPromptLabel.setText("Letters Only");
                    }
                }
            }
        }
    }

    /**
     * Recieves the key pressed from the user, verifies that the key was a character, and sends the character
     * to the model
     * @param keyPressed the KeyEvent from the enterGuessTextField text field
     */
    @FXML
    private void getLetterGuess(KeyEvent keyPressed) {
        if (gameInProgress) {
            try {
                if (Character.isLetter(keyPressed.getCharacter().toLowerCase().charAt(0))) {
                    switch (model.isCorrectGuess(keyPressed.getCharacter().toLowerCase().charAt(0))) {
                        case 1:
                            warningPromptLabel.setText("Correct Guess!");
                            correctGuess();
                            break;
                        case 2:
                            incorrectGuess(keyPressed.getCharacter().toLowerCase().charAt(0));
                            warningPromptLabel.setText("Incorrect Guess");
                            break;
                        case 3:
                            warningPromptLabel.setText("Previously Guessed");
                            break;
                    }
                }
            } catch (ArrayIndexOutOfBoundsException exception) {
                warningPromptLabel.setText("Only Letters Allowed");
            } catch (StringIndexOutOfBoundsException exception) {
                warningPromptLabel.setText("Only Letters Allowed");
            }
            if (model.getIncorrectGuessNum() == 6) {
                gameInProgress = false;
                warningPromptLabel.setText("Sorry You Lost!");
            } else if (!model.getDisplayedWordAsString().contains("_")) {
                warningPromptLabel.setText("You Won!");
                gameInProgress = false;
            }
            enterGuessTextField.setText("");
        }
    }

    /**
     * Updates the hidden word underneath the picture as correct letters are guessed
     */
    private void correctGuess() {
        displayedWordLabel.setText(model.getDisplayedWordAsString());
    }

    /**
     * Updates the hangman picture and the incorrect letter bank when incorrect letters
     * are guessed.
     * @param letter
     */
    private void incorrectGuess(char letter) {
        char tempImageName[] = imageName.toCharArray();
        int numToChange = Character.getNumericValue(tempImageName[8]);
        numToChange++;
        tempImageName[8] = Integer.toString(numToChange).charAt(0);
        String tempImageNameString = "";
        for (int tempImageNameCount = 0; tempImageNameCount < tempImageName.length; tempImageNameCount++) {
            tempImageNameString = tempImageNameString + tempImageName[tempImageNameCount];
        }
        imageName = tempImageNameString;
        hangmanImageView.setImage(new Image(imageName));
        ImageView[] addIncorrectLetters = {incorrect0, incorrect1, incorrect2, incorrect3, incorrect4, incorrect5};
        String imageName = "LetterImages/" + Character.toString(letter) + ".png";
        addIncorrectLetters[model.getIncorrectGuessNum() - 1].setImage(new Image(imageName));
        addIncorrectLetters[model.getIncorrectGuessNum() - 1].setVisible(true);
    }

    /**
     * Resets the text fields, buttons, hangman picture, and incorrect letter bank before starting a new game
     */
    @FXML
    private void resetGame() {
        model.resetGame();
        gameInProgress = false;
        startResetButton.setVisible(false);
        enterWordTextField.setText("");
        enterGuessTextField.setText("");
        enterWordTextField.setEditable(true);
        enterGuessTextField.setEditable(false);
        incorrect0.setVisible(false);
        incorrect1.setVisible(false);
        incorrect2.setVisible(false);
        incorrect3.setVisible(false);
        incorrect4.setVisible(false);
        incorrect5.setVisible(false);
        displayedWordLabel.setText("");
        warningPromptLabel.setText("");
        imageName = "Hangman-0.png";
        hangmanImageView.setImage(new Image(imageName));
    }


}
