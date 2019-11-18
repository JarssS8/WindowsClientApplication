/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windowsclientapplication.controller;

import clientlogic.logic.ConnectableClientFactory;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utilities.beans.User;
import utilities.exception.ServerConnectionErrorException;
import utilities.interfaces.Connectable;

/**
 * This class is a controller UI class for main_window view. Contains event
 * handlers and on window showing code.
 *
 * @author Diego Urraca
 */
public class LogOutWindowController {
    
    /**
     * Declaration of the logger
     */
    private static final Logger LOGGER = Logger
            .getLogger("windowsclientapplication.LogOutWindowController");
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
    /**
     * Close application menu bar button
     */
    @FXML
    private MenuItem mbClose;
    /**
     * Help menu bar button
     */
    @FXML
    private MenuItem mbAbout;
    /**
     * LogOut htperlink
     */
    @FXML
    private Hyperlink hlLogOut;
    /**
     * Label of full name of the user
     */
    @FXML
    private Label lblUser;
    /**
     * Label of last connection date of the user
     */
    @FXML
    private Label lblLastConn;
    /**
     * Label of email of the user
     */
    @FXML
    private Label lblEmail;
    /**
     * Label of last password change date of the user 
     */
    @FXML
    private Label lblLastPass;
    /**
     * Label of simple name in status barof the user
     */
    @FXML
    private Label lblStatusUser;
    /**
     * Label of last connection date in the status bar of the user
     */
    @FXML
    private Label lblStatusLastConn;
    
    /**
     * The stage of this window
     */
    private Stage stage;
    /**
     * Client with ip and port
     */
    private Connectable client = ConnectableClientFactory.getClient(IP, PORT);
    /**
     * Object user to fillwith the data of the user that's loging in
     */
    private User user = null;
    
    /**
     * Method that set the stage for this window
     * @param stage from Log in window
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Method to initialize the window
     *
     * @param root the loader for the scene
     * @param user The user wich is loged in
     */
    public void initStage(Parent root, User user) {
        try {
            this.user = user;
            Scene scene = new Scene(root);
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("Welcome");
            stage.setResizable(false);
            stage.setOnShowing(this::onWindowShowing);
            stage.setOnCloseRequest(this::handleCrossCloseAction);
            mbClose.setOnAction(this::handleCloseAction);
            mbClose.setAccelerator(new KeyCodeCombination(KeyCode.C,KeyCombination.CONTROL_DOWN)); //Modificacion DIN 14/11/2019
            mbAbout.setOnAction(this::handleAboutAction);
            mbAbout.setAccelerator(new KeyCodeCombination(KeyCode.F1)); //Modificacion DIN 14/11/2019
            hlLogOut.setBorder(Border.EMPTY); //Modificacion DIN 14/11/2019
            hlLogOut.setOnAction(this::handleLogOutAction);
            hlLogOut.setOnKeyTyped(this::handleLogOutKeyAction);
            stage.show(); //Modificacion DIN 14/11/2019
        } catch (Exception e) {
            LOGGER.severe("Can not initialize the main window");
        }
    }

    
    
    
    /**
     * Method that handle the close option of the menu bar or pressing 
     * Ctrl + C and calls to the Log Out method.
     * 
     * @param event The event is the user clicking the Close Option on the
     * menu bar or pressing Ctrl + C
     */
    public void handleCloseAction(ActionEvent event) { //Modificacion DIN 14/11/2019
        close();
    }
    
    /**
     * Method that handle the close option when user closes the application by
     * clicking the cross of the window and calls close method.
     * 
     * @param event The event is the user clicking the cross of the window
     */
    public void handleCrossCloseAction(WindowEvent event) { //Modificacion DIN 14/11/2019
        close();
    }
    
    /**
     * Method that handle the logout option by shortcut and calls
     * to the Log Out method.
     *
     * @param event The event is the user pressing Ctrl + L
     */
    public void handleLogOutKeyAction(KeyEvent event) { //Modificacion DIN 14/11/2019
        logOut();
    }
    
    /**
     * Method that handle the logout hyperlink of the status bar and calls
     * to the Log Out method.
     *
     * @param event The event is the user clicking the Log Out Hyperlink
     */
    public void handleLogOutAction(ActionEvent event) { //Modificacion DIN 14/11/2019
        logOut();
    }

    /**
     * Method that handle the close alert
     */
    public void close(){ //Modificacion DIN 14/11/2019
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Close confirmation");
        alert.setHeaderText("You are about to close the application.");
        alert.setContentText("Are you sure?");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        Button buttonYes = (Button) alert.getDialogPane().lookupButton(ButtonType.YES);
        buttonYes.setId("buttonYes");
        Button buttonNo = (Button) alert.getDialogPane().lookupButton(ButtonType.NO);
        buttonNo.setId("buttonNo");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            try {
                client.logOut(user);
                LOGGER.info("User loggin date updated sucesfully");
            } catch (ServerConnectionErrorException e) {
                LOGGER.warning("Couldn't connect to server. LogOut date not updated.");
            }finally{
                Platform.exit();
            }
        } else {
            alert.close();
        }
    }
    
    /**
     * Method that handle the Log Out alert
     */
    public void logOut(){ //Modificacion DIN 14/11/2019
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("LogOut confirmation");
        alert.setHeaderText("You are about to Log Out.");
        alert.setContentText("Are you sure?");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        Button buttonYes = (Button) alert.getDialogPane().lookupButton(ButtonType.YES);
        buttonYes.setId("buttonYes");
        Button buttonNo = (Button) alert.getDialogPane().lookupButton(ButtonType.NO);
        buttonNo.setId("buttonNo");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            try {
                client.logOut(user);
                LOGGER.info("User login date updated sucesfully");
            } catch (ServerConnectionErrorException e) {
                LOGGER.warning("Couldn't connect to server. LogOut date not updated.");
            }finally{
                stage.close();
            }
        } else {
            alert.close();
        }
    }

    /**
     * Method that handle the about/help option of the menu
     *
     * @param event The event is the user trying to open the help window or 
     * pressing F1
     *
     */
    public void handleAboutAction(ActionEvent event) {
        try {
            FXMLLoader loader
                    = new FXMLLoader(getClass().getResource("/windowsclientapplication/view/Help.fxml"));
            Parent root = (Parent) loader.load();
            HelpController helpController
                    = ((HelpController) loader.getController());
            helpController.initAndShowStage(root);
        } catch (Exception ex) {
            LOGGER.severe("Error showing the help page");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Alert");
            alert.setHeaderText("ERROR");
            alert.setContentText("There was an error loading the help page");
            alert.showAndWait();
        }
    }

    /**
     * Method that loads the texts and prepare the objects of the window
     *
     * @param event The event is the window that is being showed.
     */
    public void onWindowShowing(WindowEvent event) {
        LOGGER.info("Starting loading the labels");
        String auxName=user.getFullName(); //Modificacion DIN 14/11/2019
        int nameSpace = auxName.indexOf(" "); //Modificacion DIN 14/11/2019
        lblUser.setText(user.getFullName());
        lblLastConn.setText(user.getLastAccess().toString());
        lblEmail.setText(user.getEmail());
        lblLastPass.setText(user.getLastPasswordChange().toString());
        if(nameSpace != -1 && nameSpace != 0) //Modificacion DIN 14/11/2019
            auxName = auxName.substring(0, nameSpace);
        lblStatusUser.setText(auxName); //Modificacion DIN 14/11/2019
        lblStatusLastConn.setText(user.getLastAccess().toString());
    }

}
