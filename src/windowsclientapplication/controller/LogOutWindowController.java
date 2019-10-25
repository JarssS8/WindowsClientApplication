/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windowsclientapplication.controller;

import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utillities.beans.User;

/**
 *
 * @author Diego Urraca
 */
public class LogOutWindowController {
    //TODO Logger need to be erased at the end!!
    private static final Logger LOGGER = Logger
            .getLogger("windowsclientapplication.LogOutWindowController");
    
    @FXML
    private Menu menOpt;
    @FXML
    private Menu menHelp;
    @FXML
    private MenuItem menClose;
    @FXML
    private MenuItem menAbout;
    
    private User usr;
    private Stage stage;
    
    /**
     * Method to initialize the window
     * @param root 
     */
    public void initStage (Parent root){
        try{
            Scene scene = new Scene(root);
            stage = new Stage();
            //Window set to modal window
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle("Welcome");
            stage.setResizable(false);
            stage.setOnShowing(this::onWindowShowing);
            stage.showAndWait();
        }
        catch(Exception e){
            //TODO have to code the exception for this initStage-Catch
        }
    }
    
    
    
    /**
     * Method that handle the buttons press actions
     * @param event 
     */
   private void handleButtonAction(ActionEvent event) {
        //label.setText(greeting);
        
        //TODO set the text to the labels
        
    }
    
    /**
     * Method that loads the texts and prepare the objects of the window
     * @param e 
     */
    private void onWindowShowing(WindowEvent e){
        LOGGER.info("Starting onWindoShowing");
    }

    /**
     * 
     * @param stage 
     */
    void setStage(Stage stage) {
        this.stage=stage;
    }
    
}
