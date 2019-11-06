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
            stage.setOnCloseRequest(this::handleCrossAction);
            mbClose.setOnAction(this::handleCloseAction);
            mbAbout.setOnAction(this::handleAboutAction);
            hlLogOut.setOnAction(this::handleCloseAction);
            stage.showAndWait();
        }
        catch(Exception e){
            LOGGER.severe("Can not initialize the main window");
        }
    }
    
    
    
    /**
     * Method that handle the close option of the menu bar and logout
     * hyperlink of the status bar / confirmation
     * close alert
     * @param event 
     */
   public void handleCloseAction(ActionEvent event) {
       boolean is = false;
       String[] mssg = new String[3];
       if(event.getSource().equals(mbClose)){//Menu close
           mssg[1] = "Close confirmation";
           mssg[2] = "You pressed the 'Close' button.";
           mssg[3] = "Are you sure?";
       }else{//LogOut
           is = true;
           mssg[1] = "LogOut confirmation";
           mssg[2] = "You pressed the 'LogOut' button.";
           mssg[3] = "Are you sure?";
       }
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(mssg[1]);
        alert.setHeaderText(mssg[2]);
        alert.setContentText(mssg[3]);
        alert.getButtonTypes().setAll(ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.YES){
            try {
                client.logOut(user);
                LOGGER.info("User loggin date updated sucesfully");
                if(is)
                    stage.close();
                else
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
    * Method that handle the cross button of the window to close the application
    * confirmation
    * @param event 
    */
   public void handleCrossAction(WindowEvent event){
       Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Close confirmation");
        alert.setHeaderText("You pressed the Cross(Close) button.");
        alert.setContentText("Are you sure?");
        alert.getButtonTypes().setAll(ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.YES){
            try {
                client.logOut(user);
                LOGGER.info("User loggin date updated sucesfully");
                Platform.exit();
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
