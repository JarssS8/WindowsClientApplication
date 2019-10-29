/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windowsclientapplication.controller;

import java.awt.Color;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utilities.beans.User;
import utilities.exception.DBException;
import utilities.exception.LogicException;
import utilities.exception.LoginAlreadyTakenException;
import utilities.interfaces.Connectable;
import utilities.util.Util;


/**
 *
 * @author Aimar Arrizabalaga & Gaizka Andrés  
 */
public class SignUpWindowController {
    private static final Logger LOGGER=Logger
        .getLogger("WindowsClientApplication.controller.SignUpWindowController");
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
    
     private Stage stage;
    public void setStage(Stage stage) {
        this.stage=stage;
    }
    Connectable client;
    public void initStage(Parent root, Connectable client){
        Scene scene = new Scene(root);
        this.client=client;
        stage.setScene(scene);
        stage.setTitle("Registration");
        stage.setResizable(false);
        stage.setOnShowing(this::handleWindowShowing);
        //stage.initModality(Modality.APPLICATION_MODAL);
        btBack.setOnAction(this::handleButtonAction);
        btSignUp.setOnAction(this::handleButtonAction);
        //txtUsername.focusedProperty().addListener(this::focuschanged);
        txtUsername.textProperty().addListener(this::handleTextChange);
        txtPassword.textProperty().addListener(this::handleTextChange);
        txtRepeatPassword.textProperty().addListener(this::handleTextChange);
        txtEmail.textProperty().addListener(this::handleTextChange);
        stage.setOnCloseRequest(this::handleCloseAction);
        stage.show();
    }
    /**
     * A method that prepare the window events
     * @param event 
     */
    private void handleWindowShowing(WindowEvent event){
        LOGGER.info("Setting the window...");
        txtUsername.setPromptText("Introduce login");
        txtEmail.setPromptText("Introduce email");
        txtPassword.setPromptText("Introduce password");
        txtRepeatPassword.setPromptText("Repeat password");
        txtFullName.setPromptText("Introduce full name");
        btSignUp.setDisable(true);
    }
    private void handleCloseAction(WindowEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Close confirmation");
        alert.setHeaderText("You pressed the 'Close' button.");
        alert.setContentText("Are you sure?");
        alert.getButtonTypes().setAll(ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.YES){
            stage.close();
        }
        else{
            alert.close();
        }
    }
     /**
      * A method that registres the button actions
      * @param event 
      */
    public void handleButtonAction(ActionEvent event){
        
        if(event.getSource().equals(btBack)){
           LOGGER.info("Closing the window");
           Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Close confirmation");
            alert.setHeaderText("You pressed the 'Close' button.");
            alert.setContentText("Are you sure?");
            alert.getButtonTypes().setAll(ButtonType.YES,ButtonType.NO);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.YES){
                stage.close();
            }else{
                alert.close();
            }
        }if(event.getSource().equals(btSignUp)){
            try{ 
                LOGGER.info("Creating new user...");
                User user= new User();
                user.setLogin(txtUsername.getText().trim().substring(0,9));
                user.setPassword(txtPassword.getText().trim().substring(0, 13));
                user.setEmail(txtEmail.getText().trim().substring(0, 69));
                user.setFullName(txtFullName.getText().trim().substring(0, 44));
           
                LOGGER.info("Sending the user...");
                try {
                    client.signUp(user);
                } catch (DBException ex) {
                    Logger.getLogger(SignUpWindowController.class.getName())
                        .log(Level.SEVERE, null, ex);
                }
                LOGGER.info("User send");
            } catch (LoginAlreadyTakenException ex) {
                Logger.getLogger(SignUpWindowController.class.getName())
            .log(Level.SEVERE, null, ex);
            } catch (LogicException ex) {
                Logger.getLogger(SignUpWindowController.class.getName())
            .log(Level.SEVERE, null, ex);
            }
        }
        
        
        
    }
    /**
     * A method that validates the pattern of the email
     * 
     * @param email A string with the email
     * @return check A boolean that return the checking of the email
     */
    private boolean checkEmail(String email){
        boolean check = false;
        LOGGER.info("Checking the email...");
        check = Util.validarEmail(email);
        LOGGER.info("Email checked");        
        return check;
    }
    /**
     * A method that validates if both password fields are the same
     * 
     * @param password the password the client set
     * @param passwordRepeat the repetition of the password
     * @return checkRepeat A boolean that return the check of 
     * the passwords is ok
     */
    
    private boolean checkPassRepeat(String password,String passwordRepeat){
        boolean checkRepeat=false;
        LOGGER.info("Checking if the passwords are the same ");
        if(password.equalsIgnoreCase(passwordRepeat)){
            checkRepeat=true;
        }
        LOGGER.info("Passwords checked");
        return checkRepeat;
    }
    /**
     * A method that checks if the password has an uppercase and a number
     * 
     * @param password the password the client set
     * @return check A boolean that return if the validation is ok
     */
    private boolean checkPassword(String password){
        
        boolean capital = false;
        boolean number = false;
        boolean check = false;
           
        
        LOGGER.info("Checking the password...");
        for(int i=0;i<password.length();i++){
            char ch = password.charAt(i);
            if(Character.isDigit(ch)){
                    number=true;
            }
            if(Character.isUpperCase(ch)){
                capital=true;
            }
        }
        LOGGER.info("Password checked");
        if(capital && number){
            check=true;
        }
        return check;
    }
    /**
     * A method that validate the text change of the fields
     * 
     * @param observable
     * @param oldValue
     * @param newValue 
    */
    private void handleTextChange (ObservableValue observable,String oldValue,
        String newValue){
        
        LOGGER.info("Calling the methods that validate the text fields");
        boolean passCheck = checkPassword(txtPassword.getText().trim());
        boolean passCheckRepeat = checkPassRepeat(txtPassword.getText().trim()
            ,txtRepeatPassword.getText().trim());
        boolean emailCheck = checkEmail(txtEmail.getText().trim());
        
        boolean username = false;
        boolean passwordlength = false;
        boolean passwordCheck = false;
        boolean passwordRepeat = false;
        boolean email = false;
        
        if(txtUsername.getText().trim().length()>3 && 
            txtUsername.getText().trim().length()<11){
            
            username= true;
            lbUsernameCaution.setTextFill(Paint.valueOf("BLACK"));
    
        }else{
            btSignUp.setDisable(true);
            lbUsernameCaution.setTextFill(Paint.valueOf("RED")); 
            
        }
        
        if(txtPassword.getText().trim().length()>7 && 
            txtPassword.getText().trim().length()<15){
            
            passwordlength=true;
            lbPasswordCaution1.setTextFill(Paint.valueOf("BLACK"));
                
        }else{
            
            btSignUp.setDisable(true);
            lbPasswordCaution1.setTextFill(Paint.valueOf("RED"));
            lbPasswordCaution3.setTextFill(Paint.valueOf("RED"));
                
        }
        
        if(passCheck){
            
            passwordCheck=true;
            lbPasswordCaution2A.setTextFill(Paint.valueOf("BLACK"));    
            lbPasswordCaution2B.setTextFill(Paint.valueOf("BLACK")); 
                    
        }else{
            
            btSignUp.setDisable(true);
            lbPasswordCaution2A.setTextFill(Paint.valueOf("RED"));
            lbPasswordCaution2B.setTextFill(Paint.valueOf("RED"));
            lbPasswordCaution3.setTextFill(Paint.valueOf("RED"));
                    
        } 
        
        if(passCheckRepeat){
            
            passwordRepeat=true;
            lbPasswordCaution3.setTextFill(Paint.valueOf("BLACK"));
                    
        }else{
            
            btSignUp.setDisable(true);
            lbPasswordCaution3.setTextFill(Paint.valueOf("RED"));
                        
        }  
        
        if(emailCheck){
            
            emailCheck=true;
            lbEmailCaution.setTextFill(Paint.valueOf("BLACK"));
                    
        }else{
            
            btSignUp.setDisable(true);
            lbEmailCaution.setTextFill(Paint.valueOf("RED"));
                    
        }
        if(username && passwordlength && passwordRepeat && passwordCheck 
            && emailCheck){
            
            btSignUp.setDisable(false);

        }
        
       
    }
   
}


