/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windowsclientapplication.controller;

import clientlogic.logic.ConnectableClientFactory;
import java.io.IOException;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventType;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.JOptionPane;
import utilities.beans.Message;
import static utilities.beans.Message.LOGIN_MESSAGE;
import utilities.beans.User;
import utilities.exception.DBException;
import utilities.exception.LogicException;
import utilities.exception.LoginNotFoundException;
import utilities.exception.WrongPasswordException;
import utilities.interfaces.Connectable;
import windowsclientapplication.exception.WindowsProjectException;

/**
 *
 * @author adria
 */
public class LoginWindowController {

    /**
     * The stage is used for instance an stage attribute and can store the stage
     * from other class or send the stage to other class
     */
    private Stage stage;

    /**
     * This is the login button on the view
     */
    @FXML
    private Button btLogin;

    /**
     * This is the text input by the user that define the username
     */
    @FXML
    private TextField txtUsername;

    /**
     * This is the text input by the user that define the password
     */
    @FXML
    private PasswordField txtPassword;

    /**
     * This is the hyperlink for go to the sign up window
     */
    @FXML
    private Hyperlink linkClickHere;

    /**
     * @return the stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * @param stage the stage to set
     */
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

        //Stage Properties
        stage.setScene(scene);
        stage.setTitle("LogIn");
        stage.setResizable(false);
        stage.setOnShowing(this::handleWindowShowing);
        //Listeners
        txtUsername.textProperty().addListener(this::textChange);
        txtPassword.textProperty().addListener(this::textChange);
        //Stage show
        stage.show();
    }

    /**
     *
     * @param event
     */
    private void handleWindowShowing(WindowEvent event) {
        btLogin.setDisable(true);

    }

    /**
     * Checks every time that the user change the TextField and if both formats
     * are correct set the login button enable
     *
     * @param event The event when the text is changing
     */
    private void textChange(ObservableValue observable, String oldValue, String newValue) {
        //Check if user got the correct format
        if (txtUsername.getText().trim().length() >= 4 && txtUsername.getText().trim().length() <= 10) {
            //Check if password got the correct format
            if (txtPassword.getText().trim().length() >= 8 && txtPassword.getText().trim().length() <= 14) {
                //Enables LogIn button
                btLogin.setDisable(false);
            } else {
                btLogin.setDisable(true);
            }
        } else {
            btLogin.setDisable(true);
        }
    }

    /**
     * This method send the txtUsername and the txtPassword to the factory and
     * waits if the user exits on dataBase and the password is correct for go to
     * the logout window
     *
     * @param event The event is the user clicking on the login button
     * @throws utilities.exception.LoginNotFoundException
     * @throws utilities.exception.DBException
     * @throws utilities.exception.WrongPasswordException
     * @throws utilities.exception.LogicException
     * @throws windowsclientapplication.exception.WindowsProjectException
     */
    public void loginClick(ActionEvent event) throws LoginNotFoundException, DBException, WrongPasswordException, LogicException, WindowsProjectException {
        try {
            User user = new User();
            user.setLogin(txtUsername.getText().trim());
            user.setPassword(txtPassword.getText().trim());
            Connectable client = ConnectableClientFactory.getClient();
            user = client.logIn(user);

            if (client.getMessage().equals(LOGIN_MESSAGE)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main_window.fxml"));
                Parent root = (Parent) loader.load();
                LogOutWindowController logOutController = ((LogOutWindowController) loader.getController());
                logOutController.setStage(stage);
                logOutController.initStage(root, user);
            } else {
                throw new LoginNotFoundException("Login not correct");
            }
        } catch (LoginNotFoundException e) {
            throw new LoginNotFoundException(e.getMessage());

        } catch (WrongPasswordException e) {
            throw new WrongPasswordException(e.getMessage());

        } catch (LogicException e) {
            throw new LogicException(e.getMessage());

        } catch (DBException e) {
            throw new DBException(e.getMessage());
        } catch (IOException e) {
            throw new WindowsProjectException(e.getMessage());
        }
    }
    
    /**
     * This method opens the sign up window when the user clicks on the hyperlink click here
     * @param event The event 
     * @throws IOException 
     */
    public void signUpClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("windowsclientapplication/view/main_window.fxml"));
        Parent root = (Parent) loader.load();
        SignUpWindowController signUpController = ((SignUpWindowController) loader.getController());
        signUpController.setStage(stage);
        signUpController.initStage(root);
    }

}
