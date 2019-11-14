/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windowsclientapplication.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utilities.beans.User;
import utilities.exception.LoginAlreadyTakenException;
import utilities.exception.ServerConnectionErrorException;
import utilities.interfaces.Connectable;
import utilities.util.Util;

/**
 * This class is a controller UI class for SignUp_Window view. Contains event
 * handlers and on window showing code.
 *
 * @author Aimar Arrizabalaga, Gaizka Andrés
 */
public class SignUpWindowController {

    private static final Logger LOGGER = Logger
            .getLogger("WindowsClientApplication.controller.SignUpWindowController");

    @FXML
    private Button btBack;
    @FXML
    private Button btSignUp;
    @FXML
    private Button btHelpMe;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtRepeatPassword;
    @FXML
    private TextField txtFullName;
    @FXML
    private Label lbUsernameCaution;
    @FXML
    private Label lbEmailCaution;
    @FXML
    private Label lbPasswordCaution1;
    @FXML
    private Label lbPasswordCaution2A;
    @FXML
    private Label lbPasswordCaution3;
    @FXML
    private Label lbFullNameCaution;
    @FXML
    private Label lbUsername;
    @FXML
    private Label lbPassword;
    @FXML
    private Label lbEmail;
    @FXML
    private Label lbRepeatPassword;
    @FXML
    private Label lbFullName;

    private Stage stage;

    private Connectable client;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /* MODIFICACION DIN 13/11/2019 */
    public void setClient(Connectable client) {
        this.client = client;
    }

    /**
     * This method initialize the window and everything thats the stage needs.
     * This calls other method when shows the window to set attributes of the
     * window
     *
     * @param root The parent object
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Registration");
        stage.setResizable(false);
        stage.setOnShowing(this::handleWindowShowing);
        stage.initModality(Modality.APPLICATION_MODAL);
        btBack.setOnAction(this::handleButtonAction);
        btSignUp.setOnAction(this::handleButtonAction);
        btHelpMe.setOnAction(this::handleHelpMeButtonAction);
        stage.setOnCloseRequest(this::handleCloseAction);
        btSignUp.setDisable(false);
        /* MODIFICACION DIN 14/11/2019
        I've tried many different options to trigger the button by pressing F1
        key with lambda expressions but I didn't understand them. I've decided
        to do it with method reference.*/
        stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyEventAction);
        stage.show();
    }

    /**
     * This is the method to control the components of this window when we shows
     * the window.
     *
     * @param event The event is the window that is being showed.
     */
    private void handleWindowShowing(WindowEvent event) {
        LOGGER.info("Setting the window...");
        
        //Prompt Text
        txtUsername.setPromptText("Introduce login");
        txtEmail.setPromptText("Introduce email");
        txtPassword.setPromptText("Introduce password");
        txtRepeatPassword.setPromptText("Repeat password");
        txtFullName.setPromptText("Introduce full name");
        
        //Tooltips
        btSignUp.setTooltip(new Tooltip("Click to complete the registration"));
        btBack.setTooltip(new Tooltip("Return to LogIn"));
        /* MODIFICACION DIN 13/11/2019 */
        btHelpMe.setTooltip(new Tooltip("Open help window"));
        lbUsername.setTooltip(new Tooltip("Username to login"));
        lbPassword.setTooltip(new Tooltip("Password to login"));
        lbRepeatPassword.setTooltip(new Tooltip("Repeat the password"));
        lbEmail.setTooltip(new Tooltip("Email to send information"));
        lbFullName.setTooltip(new Tooltip("Your Full Name"));
        
        //Mnemonic
        btSignUp.setMnemonicParsing(true);
        btBack.setMnemonicParsing(true);
        /* MODIFICACION DIN 13/11/2019 */
        btHelpMe.setMnemonicParsing(true);
        btSignUp.setText("_Sign Up");
        btBack.setText("_Back");
        /* MODIFICACION DIN 13/11/2019 */
        btHelpMe.setText("_HelpMe");

    }

    /**
     * This method is used if the user try to close the application clicking in
     * the red cross(right-top in the stage) and control if the user is sure to
     * close the application.
     *
     * @param event The event is the user trying to close the application with
     * the cross of the stage.
     *
     */
    private void handleCloseAction(WindowEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Close confirmation");
        alert.setHeaderText("You pressed the 'Close'. \n"
                + "All the data will be erased..");
        alert.setContentText("Are you sure?");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            stage.close();
        } else {
            event.consume();
        }
    }

    
    /**
     * This method receives and checks a Key Event and shows the help window if 
     * the event is a F1 key pressing.
     * @param event A KeyEvent event.
     */
    /* MODIFICACION DIN 14/11/2019 */
    public void handleKeyEventAction(KeyEvent event) {
        try {
            //Check if the Key pressed is F1.
            if (event.getCode() == KeyCode.F1) {
                //Load Help window.
                LOGGER.info("Loading SignUpHelp window...");
                FXMLLoader loader
                        = new FXMLLoader(getClass().getResource(
                                "/windowsclientapplication/view/Help.fxml"));
                Parent root = (Parent) loader.load();
                HelpController helpController
                        = ((HelpController) loader.getController());
                helpController.initAndShowStage(root);
            }
        } catch (Exception ex) {
            LOGGER.severe("SignUpWindowController: Error showing the help window");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("ERROR");
            alert.setContentText("There was an error loading the help window");
            alert.showAndWait();
        }
    }

    /**
     * This method handles the HelpMe button. Loads, initializes and shows the
     * SignUpHelp window.
     * @param event An ActionEvent event. 
     */
    /* MODIFICACION DIN 14/11/2019 */
    public void handleHelpMeButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader
                    = new FXMLLoader(getClass().getResource(
                            "/windowsclientapplication/view/Help.fxml"));
            Parent root = (Parent) loader.load();
            HelpController helpController
                    = ((HelpController) loader.getController());
            helpController.initAndShowStage(root);
        } catch (Exception ex) {
            LOGGER.severe("Error showing the help window");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("ERROR");
            alert.setContentText("There was an error loading the help window");
            alert.showAndWait();
        }
    }

    /**
     * A method that registres the button actions
     *
     * @param event An ActionEvent event.
     */
    public void handleButtonAction(ActionEvent event) {

        if (event.getSource().equals(btBack)) {
            LOGGER.info("Closing the window");
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Close confirmation");
            alert.setHeaderText("Sign up cancelled.");
            alert.setContentText("Are you sure?");
            alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.YES) {
                stage.close();
            } else {
                alert.close();
            }
        }
        if (event.getSource().equals(btSignUp)) {
            try {
                if (checkValidation()) {
                    LOGGER.info("Creating new user...");
                    User user = new User();
                    user.setLogin(txtUsername.getText().trim());
                    user.setPassword(txtPassword.getText().trim());
                    user.setEmail(txtEmail.getText().trim());
                    user.setFullName(txtFullName.getText().trim());
                    user.setLastAccess(Timestamp.valueOf(LocalDateTime.now()));
                    user.setLastPasswordChange(Timestamp.valueOf(LocalDateTime.now()));
                    user.setPrivilege(1);
                    user.setStatus(1);
                    LOGGER.info("Sending the user...");
                    client.signUp(user);

                    //An alert to let the user know the signing up's been correct.
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("User Sent");
                    alert.setHeaderText("Registration completed.");
                    Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                    okButton.setId("okbutton");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {

                        stage.close();

                    }
                    LOGGER.info("User sent correctly");
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("Sing up failed");
                    alert.setContentText("Check the validation tips below");
                    Button errorButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                    errorButton.setId("errorbutton");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.YES) {
                        alert.close();

                    }
                    LOGGER.info("Error sending user");
                }
            } catch (LoginAlreadyTakenException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("SignUp Error");
                alert.setContentText("An error ocurred trying to sign up, "
                        + "login arleady taken.");
                Button LoginTakenButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                LoginTakenButton.setId("loginTakenButton");
                alert.showAndWait();
            } catch (ServerConnectionErrorException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("LogIn Error");
                alert.setContentText("An error ocurred trying to sign up, "
                        + "can not connect with the server.");
                alert.showAndWait();
            }

        }
    }

    /**
     * A method that validates the pattern of the email
     *
     * @param email A string with the email
     * @return check A boolean that return the checking of the email
     */
    private boolean checkEmail(String email) {
        boolean check = false;
        check = Util.validarEmail(email);
        return check;
    }

    /**
     * A method that validates if both password fields are the same
     *
     * @param password the password the client set
     * @param passwordRepeat the repetition of the password
     * @return checkRepeat A boolean that return the check of the passwords is
     * ok
     */
    private boolean checkPassRepeat(String password, String passwordRepeat) {
        boolean checkRepeat = false;

        if (password.equalsIgnoreCase(passwordRepeat)) {
            checkRepeat = true;
        }

        return checkRepeat;
    }

    /**
     * A method that checks if the password has an uppercase and a number
     *
     * @param password the password the client set
     * @return check A boolean that return if the validation is ok
     */
    private boolean checkPassword(String password) {

        boolean capital = false;
        boolean number = false;
        boolean check = false;

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (Character.isDigit(ch)) {
                number = true;
            }
            if (Character.isUpperCase(ch)) {
                capital = true;
            }
        }

        if (capital && number) {
            check = true;
        }
        return check;
    }

    /**
     * A method that validate the text change of the fields
     *
     */
    private boolean checkValidation() {
        boolean passCheck = checkPassword(txtPassword.getText().trim());
        boolean passCheckRepeat = checkPassRepeat(txtPassword.getText()
                .trim(), txtRepeatPassword.getText().trim());
        boolean emailCheck = checkEmail(txtEmail.getText().trim());

        boolean username = false;
        boolean passwordlength = false;
        boolean passwordCheck = false;
        boolean passwordRepeat = false;
        boolean email = false;
        boolean fullname = false;
        boolean ok = false;
        boolean focused = false;

        //Check username
        if (txtUsername.getText().trim().length() > 3
                && txtUsername.getText().trim().length() < 11) {

            username = true;
            lbUsernameCaution.setTextFill(Paint.valueOf("BLACK"));

        } else {
            /* MODIFICACION DIN 14/11/2019 */
            if (!focused) {
                txtUsername.requestFocus();
                focused = true;
            }
            lbUsernameCaution.setTextFill(Paint.valueOf("RED"));

        }//End check username

        //EmailCheck
        if (emailCheck) {

            email = true;
            lbEmailCaution.setTextFill(Paint.valueOf("BLACK"));

        } else {
            /* MODIFICACION DIN 14/11/2019 */
            if (!focused) {
                txtEmail.requestFocus();
                focused = true;
            }
            lbEmailCaution.setTextFill(Paint.valueOf("RED"));

        }//End EmailCheck 

        //Check FullName
        if (!txtFullName.getText().trim().isEmpty() && txtFullName.getText().trim().length() < 44) {

            fullname = true;
            lbFullNameCaution.setTextFill(Paint.valueOf("BLACK"));

        } else {
            /* MODIFICACION DIN 14/11/2019 */
            if (!focused) {
                txtFullName.requestFocus();
                focused = true;
            }
            lbFullNameCaution.setTextFill(Paint.valueOf("RED"));

        }//End Check FullName

        //Check password
        if (txtPassword.getText().trim().length() > 7
                && txtPassword.getText().trim().length() < 15) {

            passwordlength = true;
            lbPasswordCaution1.setTextFill(Paint.valueOf("BLACK"));

        } else {
            /* MODIFICACION DIN 14/11/2019 */
            if (!focused) {
                txtPassword.requestFocus();
                focused = true;
            }
            lbPasswordCaution1.setTextFill(Paint.valueOf("RED"));

        }// End check password

        
        //PassCheck
        if (passCheck) {

            passwordCheck = true;
            lbPasswordCaution2A.setTextFill(Paint.valueOf("BLACK"));

        } else {
            /* MODIFICACION DIN 14/11/2019 */
            if (!focused) {
                txtPassword.requestFocus();
                focused=true;
            }
            lbPasswordCaution2A.setTextFill(Paint.valueOf("RED"));

        }//End PassCheck

        //PassCheckRepeat
        if (passCheckRepeat) {

            passwordRepeat = true;
            lbPasswordCaution3.setTextFill(Paint.valueOf("BLACK"));
        } else {
            /* MODIFICACION DIN 14/11/2019 */
            if (!focused) {
                txtRepeatPassword.requestFocus();
                focused = true;
            }
            lbPasswordCaution3.setTextFill(Paint.valueOf("RED"));

        }//End PassCheckRepeat       

        //Check all
        if (username && passwordlength && passwordRepeat && passwordCheck
                && email && fullname) {

            ok = true;

        }//End chack all

        return ok;
    }

}
