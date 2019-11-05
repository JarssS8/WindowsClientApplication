/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windowsclientapplication.controller;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utilities.beans.User;
import utilities.exception.DBException;
import utilities.exception.LogicException;
import utilities.interfaces.Connectable;

/**
 *
 * @author Diego Urraca
 */
public class LogOutWindowController {
    private static final Logger LOGGER = Logger
            .getLogger("windowsclientapplication.LogOutWindowController");
    
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
    private Connectable client=null;
    private User user=null;
    
    /**
     * Method to initialize the window
     * @param root 
     * @param client 
     * @param user 
     */
    public void initStage (Parent root,Connectable client,User user){
        try{
            this.client = client;
            this.user = user;
            Scene scene = new Scene(root);
            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("Welcome");
            stage.setResizable(false);
            stage.setOnShowing(this::onWindowShowing);
            stage.setOnCloseRequest(this::handleCloseAction);
            mbClose.setOnAction(this::handleCloseAction);
            mbAbout.setOnAction(this::handleAboutAction);
            hlLogOut.setOnAction(this::handleLogOutAction);
            stage.showAndWait();
        }
        catch(Exception e){
            LOGGER.severe("Can not initialize the main window");
        }
    }
    
    
    
    /**
     * Method that handle the close option of the menu bar / confirmation
     * close alert
     * @param event 
     */
   public void handleCloseAction(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Close confirmation");
        alert.setHeaderText("You pressed the 'Close' button.");
        alert.setContentText("Are you sure?");
        alert.getButtonTypes().setAll(ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.YES){
            try {
                client.logOut(user);
                LOGGER.info("User loggin date updated sucesfully");
                Platform.exit();
            } catch (LogicException ex) {
                LOGGER.severe("Problems updating the Log In date on close");
            } catch (DBException ex) {
                LOGGER.severe("Problems connecting with server.");
            }
        }
        else
            alert.close();
    }
   
   /**
    * Method that handle the log out option of the status bar / confirmation
    * log out alert
    * @param event 
    */
   public void handleLogOutAction(ActionEvent event){
       Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("LogOut confirmation");
        alert.setHeaderText("You pressed the 'LogOut' button.");
        alert.setContentText("Are you sure?");
        alert.getButtonTypes().setAll(ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.YES){
            try {
                client.logOut(user);
                LOGGER.info("User loggin date updated sucesfully");
                stage.close();
            } catch (LogicException ex) {
                LOGGER.severe("Problems updating the Log In date on logout");
            }catch (DBException ex) {
                LOGGER.severe("Problems connecting with server.");
            }
        }
        else
            alert.close();
   }
   
   /**
    * Method that handle the about/help option of the menu bar
    * @param event 
    */
   public void handleAboutAction(ActionEvent event) {
        try{
            Runtime rTime = Runtime.getRuntime();
            String url = "..\\help\\help.html";
            String browser = "C:/Program Files/Internet Explorer/iexplore.exe";
            Process pc = rTime.exec(browser+url);
            pc.wait();
        }catch(IOException e){
            LOGGER.severe("Input / Output error");
        }catch(InterruptedException e){
            LOGGER.severe("Runtime interrupted");
        }catch(Exception e){
            LOGGER.severe("Error trying to load the help HTML");
        }
       
   }
    
    /**
     * Method that loads the texts and prepare the objects of the window
     * @param event
     */
    public void onWindowShowing(WindowEvent event){
        LOGGER.info("Starting loading the labels");
        lblUser.setText(user.getFullName());
        lblLastConn.setText(user.getLastAccess().toString());   
        lblEmail.setText(user.getEmail());
        lblLastPass.setText(user.getLastPasswordChange().toString());
        lblStatusUser.setText(user.getLogin());
        lblStatusLastConn.setText(user.getLastAccess().toString());           
    }

    /**
     * Method that sets the stage
     * @param stage 
     */
    public void setStage(Stage stage) {
        this.stage=stage;
    }
    
}
