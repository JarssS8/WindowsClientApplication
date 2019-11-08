/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windowsclientapplication.controller;

import clientlogic.logic.ConnectableClientFactory;

import java.sql.Timestamp;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
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
 * This class is a controller UI class for SignUp_Window view.
 * Contains event handlers and on window showing code.
 * @author Aimar Arrizabalaga & Gaizka Andrés
 */
public class SignUpWindowController {

    private static final Logger LOGGER = Logger
            .getLogger("WindowsClientApplication.controller.SignUpWindowController");
    /**
     * Declaration of the port for the connection
     */
    private static final int PORT = Integer.parseInt(ResourceBundle.getBundle(
            "windowsclientapplication.PropertiesClientSide").getString("PORT"));

    /**
     * Declaration of the IP for the connection
     */
    private static final String IP = ResourceBundle.getBundle(
            "windowsclientapplication.PropertiesClientSide").getString("IP");
    @FXML
    private Button btBack;
    @FXML
    private Button btSignUp;
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
    private Label lbPasswordCaution2B;
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

    public void setStage(Stage stage) {
        this.stage = stage;
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
        stage.setOnCloseRequest(this::handleCloseAction);
        btSignUp.setDisable(false);
        stage.show();
    }

    /**
     * This is the method to control the components of this window when we shows
     * the window.
     * @param event The event is the window that is being showed.
     */
    private void handleWindowShowing(WindowEvent event) {
        LOGGER.info("Setting the window...");
        txtUsername.setPromptText("Introduce login");
        txtEmail.setPromptText("Introduce email");
        txtPassword.setPromptText("Introduce password");
        txtRepeatPassword.setPromptText("Repeat password");
        txtFullName.setPromptText("Introduce full name");
        btSignUp.setTooltip(new Tooltip("Click to complete the registration"));
        btBack.setTooltip(new Tooltip("Return to LogIn"));
        lbUsername.setTooltip(new Tooltip("Username to login"));
        lbPassword.setTooltip(new Tooltip("Password to login"));
        lbRepeatPassword.setTooltip(new Tooltip("Repeat the password"));
        lbEmail.setTooltip(new Tooltip("Email to send information"));
        lbFullName.setTooltip(new Tooltip("Your Full Name"));
        btSignUp.setMnemonicParsing(true);
        btBack.setMnemonicParsing(true);
        btSignUp.setText("_Sign Up");
        btBack.setText("_Back");

    }
    /**
     * This method is used if the user try to close the application clicking in
     * the red cross(right-top in the stage) and control if the user is sure to
     * close the application.
     *
     * @param event The event is the user trying to close the application with
     * the cross of the stage.
     **/
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
     * A method that registres the button actions
     *
     * @param eventThe event is the user clicking on the buttons.
     */
    public void handleButtonAction(ActionEvent event) {

        if (event.getSource().equals(btBack)) {
            LOGGER.info("Closing the window");
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Close confirmation");
            alert.setHeaderText("Registration will be cancelled.\n"
                    + "All the data will be erased.");
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
                    Connectable client = ConnectableClientFactory.getClient(IP,PORT);
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

        if (txtUsername.getText().trim().length() > 3
                && txtUsername.getText().trim().length() < 11) {

            username = true;
            lbUsernameCaution.setTextFill(Paint.valueOf("BLACK"));

        } else {

            lbUsernameCaution.setTextFill(Paint.valueOf("RED"));

        }

        if (txtPassword.getText().trim().length() > 7
                && txtPassword.getText().trim().length() < 15) {

            passwordlength = true;
            lbPasswordCaution1.setTextFill(Paint.valueOf("BLACK"));

        } else {

            lbPasswordCaution1.setTextFill(Paint.valueOf("RED"));

        }

        if (passCheck) {

            passwordCheck = true;
            lbPasswordCaution2A.setTextFill(Paint.valueOf("BLACK"));
            lbPasswordCaution2B.setTextFill(Paint.valueOf("BLACK"));

        } else {

            lbPasswordCaution2A.setTextFill(Paint.valueOf("RED"));
            lbPasswordCaution2B.setTextFill(Paint.valueOf("RED"));

        }

        if (passCheckRepeat) {

            passwordRepeat = true;
            lbPasswordCaution3.setTextFill(Paint.valueOf("BLACK"));
        } else {

            lbPasswordCaution3.setTextFill(Paint.valueOf("RED"));

        }
        if (emailCheck) {

            email = true;
            lbEmailCaution.setTextFill(Paint.valueOf("BLACK"));

        } else {

            lbEmailCaution.setTextFill(Paint.valueOf("RED"));

        }

        if (!txtFullName.getText().trim().isEmpty() && txtFullName.getText().trim().length() < 44) {

            fullname = true;
            lbFullNameCaution.setTextFill(Paint.valueOf("BLACK"));

        } else {

            lbFullNameCaution.setTextFill(Paint.valueOf("RED"));

        }

        if (username && passwordlength && passwordRepeat && passwordCheck
                && email && fullname) {

            ok = true;

        }

        return ok;
    }

}
