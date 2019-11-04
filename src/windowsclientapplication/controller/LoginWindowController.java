/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windowsclientapplication.controller;

import clientlogic.logic.Client;
import clientlogic.logic.ConnectableClientFactory;
import java.io.IOException;
import java.util.Optional;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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
     * @return Return the stage of this class
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * @param stage Sets the stage for this class
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    private Connectable client;
    /**
     * This method initialize the window and everything thats the stage needs.
     * This calls other method when shows the window to set attributes of the
     * window
     *
     * @param root The parent object
     */
    public void initStage(Parent root, Connectable client) {
        Scene scene = new Scene(root);
        this.client=client;
        //Stage Properties
        stage.setScene(scene);
        stage.setTitle("LogIn");
        stage.setResizable(false);
        stage.setOnShowing(this::handleWindowShowing);
        stage.setOnCloseRequest(this::closeRequest);

        //Listeners
        txtUsername.textProperty().addListener(this::textChange);
        txtPassword.textProperty().addListener(this::textChange);

        //Stage show
        stage.show();
    }

    /**
     * This is the method to control the components of this window when we shows
     * the window
     *
     * @param event The event is the window that is being showed
     */
    private void handleWindowShowing(WindowEvent event) {
        btLogin.setDisable(true);

    }
    
    /**
     * This method is used if the user try to close the application clicking 
     * in the red cross(right-top in the stage) and control if the user are sure to close the application
     * @param event The event is the user trying to close the application with the cross of the stage
     */
    public void closeRequest(WindowEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Window");
        alert.setContentText("Are you sure that want close the application?");
        alert.initOwner(stage);
        alert.initModality(Modality.WINDOW_MODAL);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            stage.close();
            Platform.exit();

        } else {
            event.consume();
        }
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

            if (client.getMessage().equals(LOGIN_MESSAGE)) {//User exists on DataBase
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/windowsclientapplication/view/main_window.fxml"));
                Parent root = (Parent) loader.load();
                LogOutWindowController logOutController = ((LogOutWindowController) loader.getController());
                logOutController.setStage(stage);
                logOutController.initStage(root, user);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("LogIn Error");
                alert.setContentText("An error ocurred trying to log in, please try it again.");
                alert.initOwner(stage);
                alert.initModality(Modality.WINDOW_MODAL);
                alert.showAndWait();
                event.consume();
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
     * This method opens the sign up window when the user clicks on the
     * hyperlink click here
     *
     * @param event The event
     * @throws IOException Error when can't access to the fxml view
     */
    public void signUpClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/windowsclientapplication/view/SignUp_Window.fxml"));
        Parent root = (Parent) loader.load();
        SignUpWindowController signUpController = ((SignUpWindowController) loader.getController());
        signUpController.setStage(stage);
        signUpController.initStage(root,client);
    }
}
