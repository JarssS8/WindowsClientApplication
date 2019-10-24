/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windowsclientapplication.controller;

import java.awt.Color;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;



/**
 *
 * @author Aimar Arrizabalaga & Gaizka Andrés  
 */
public class SignUpWindowController {
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
    
     private Stage stage;
    public void setStage(Stage stage) {
        this.stage=stage;
    }
    
    public void initStage(Parent root){
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Registration");
        stage.setResizable(false);
        stage.setOnShowing(this::handleWindowShowing);
        //stage.initModality(Modality.APPLICATION_MODAL);
        btBack.setOnAction(this::handleButtonAction);
        btSignUp.setOnAction(this::handleButtonAction);
        //txtUsername.focusedProperty().addListener(this::focuschanged);
        
        stage.show();
    }
    private void handleWindowShowing(WindowEvent event){
        txtUsername.setPromptText("Introduce login");
        txtEmail.setPromptText("Introduce email");
        txtPassword.setPromptText("Introduce password");
        txtRepeatPassword.setPromptText("Repeat password");
        txtFullName.setPromptText("Introduce full name");
        btSignUp.setDisable(true);
    }
     
     
    public void handleButtonAction(ActionEvent event){
        if(event.getSource().equals(btBack)){
            lbUsernameCaution.setStyle("-fx-text-inner-color:RED;");
            txtUsername.setText("HI");
        }
        
        
        
    }
    public void focuschanged (ObservableValue observable,Boolean oldValue,
        Boolean newValue){
        
        if(newValue){
            //
        }else if(oldValue){
            if(txtUsername.getSelectedText().length()>10 || txtUsername.getSelectedText().length()<4){
                //Hacer estilo y cambiarlo al txtUsername
               
                
            }
        }
    }

   
}


