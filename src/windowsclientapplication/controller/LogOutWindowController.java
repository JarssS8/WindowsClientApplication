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
    @FXML
    private MenuItem mbClose;
    @FXML
    private MenuItem mbAbout;
    @FXML
    private Hyperlink hlLogOut;
    @FXML
    private Label lblUser;
    @FXML
    private Label lblLastConn;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblLastPass;
    @FXML
    private Label lblStatusUser;
    @FXML
    private Label lblStatusLastConn;

    private Stage stage;
    private Connectable client = ConnectableClientFactory.getClient(IP, PORT);
    private User user = null;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Method to initialize the window
     *
     * @param root
     * @param user
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
            stage.setOnCloseRequest(this::handleCrossAction);
            mbClose.setOnAction(this::handleCloseAction);
            mbAbout.setOnAction(this::handleAboutAction);
            hlLogOut.setOnAction(this::handleCloseAction);
            stage.show();
        } catch (Exception e) {
            LOGGER.severe("Can not initialize the main window");
        }
    }

    /**
     * Method that handle the close option of the menu bar and logout hyperlink
     * of the status bar / confirmation close alert
     *
     * @param event The event is the user trying to close the application
     */
    public void handleCloseAction(ActionEvent event) {
        boolean is = false;
        String[] mssg = new String[3];
        if (event.getSource().equals(mbClose)) {//Menu close
            mssg[0] = "Close confirmation";
            mssg[1] = "You pressed the 'Close' button.";
            mssg[2] = "Are you sure?";
        } else {//LogOut
            is = true;
            mssg[0] = "LogOut confirmation";
            mssg[1] = "You pressed the 'LogOut' button.";
            mssg[2] = "Are you sure?";
        }
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(mssg[0]);
        alert.setHeaderText(mssg[1]);
        alert.setContentText(mssg[2]);
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        Button buttonYes = (Button) alert.getDialogPane().lookupButton(ButtonType.NO);
        buttonYes.setId("buttonYes");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            try {
                client.logOut(user);
                LOGGER.info("User login date updated sucesfully");
                if (is) {
                    stage.close();
                } else {
                    Platform.exit();
                }
            } catch (ServerConnectionErrorException e) {
                LOGGER.warning("Couldn't connect to server. LogOut date not updated.");
                stage.close();
            }
        } else {
            event.consume();
        }
    }

    /**
     * Method that handle the cross button of the window to close the
     * application confirmation
     *
     * @param event The event is the user trying to close the application with
     * the cross of the stage.
     */
    public void handleCrossAction(WindowEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Close confirmation");
        alert.setHeaderText("You pressed the Cross(Close) button.");
        alert.setContentText("Are you sure?");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            try {
                client.logOut(user);
                LOGGER.info("User loggin date updated sucesfully");
                Platform.exit();
            } catch (ServerConnectionErrorException e) {
                LOGGER.warning("Couldn't connect to server. LogOut date not updated.");
                Platform.exit();
            }
        } else {
            event.consume();
        }
    }

    /**
     * Method that handle the about/help option of the menu bar
     *
     * @param event The event is the user trying to open the help window
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
        lblUser.setText(user.getFullName());
        lblLastConn.setText(user.getLastAccess().toString());
        lblEmail.setText(user.getEmail());
        lblLastPass.setText(user.getLastPasswordChange().toString());
        lblStatusUser.setText(user.getLogin());
        lblStatusLastConn.setText(user.getLastAccess().toString());
    }

}
