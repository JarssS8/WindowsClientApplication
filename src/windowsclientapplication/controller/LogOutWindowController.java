/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windowsclientapplication.controller;

import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utillities.beans.User;

/**
 *
 * @author Diego
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
    
    
    
    
    
   private void handleButtonAction(ActionEvent event) {
        label.setText(greeting);
        
        //TODO set te text to the labels
        
    }
    
    /**
     * 
     * @param e 
     */
    private void onWindowShowing(WindowEvent e){
        label.setText(greeting);
    }

    /**
     * 
     * @param stage 
     */
    void setStage(Stage stage) {
        this.stage=stage;
    }
    
}
