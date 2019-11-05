/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windowsclientapplication.controller;

import clientlogic.logic.ConnectableClientFactory;
import java.awt.Color;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @FXML
    private Label lbFullNameCaution;
    
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
        txtUsername.focusedProperty().addListener(this::focusChanged);
        txtPassword.focusedProperty().addListener(this::focusChanged);
        txtRepeatPassword.focusedProperty().addListener(this::focusChanged);
        txtEmail.focusedProperty().addListener(this::focusChanged);
        stage.setOnCloseRequest(this::handleCloseAction);
        btSignUp.setDisable(true);
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
                Connectable client = ConnectableClientFactory.getClient();
                LOGGER.info("Creating new user...");
                User user= new User();
                user.setLogin(txtUsername.getText().trim());
                user.setPassword(txtPassword.getText().trim());
                user.setEmail(txtEmail.getText().trim());
                user.setFullName(txtFullName.getText().trim());
                user.setLastAccess(Timestamp.valueOf(LocalDateTime.now()));
                user.setLastPasswordChange(Timestamp.valueOf(LocalDateTime.now()));
                user.setPrivilege(1);
                user.setStatus(1);
                LOGGER.info("Sending the user...");
                
                client.signUp(user);
                
                LOGGER.info("User sent");
                
                
            } catch (LoginAlreadyTakenException ex) {
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("SignUp Error");
               alert.setContentText("Username already exist");
               alert.showAndWait();
            } catch (LogicException ex) {
                
            } catch (DBException ex) {
                
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
        check = Util.validarEmail(email);
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
       
        if(password.equalsIgnoreCase(passwordRepeat)){
            checkRepeat=true;
        }
        
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
           
     
        for(int i=0;i<password.length();i++){
            char ch = password.charAt(i);
            if(Character.isDigit(ch)){
                    number=true;
            }
            if(Character.isUpperCase(ch)){
                capital=true;
            }
        }
       
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
    private void focusChanged (ObservableValue observable,Boolean oldValue,
        Boolean newValue){
            boolean passCheck = checkPassword(txtPassword.getText().trim());
            boolean passCheckRepeat = checkPassRepeat(txtPassword.getText()
                .trim(),txtRepeatPassword.getText().trim());
            boolean emailCheck = checkEmail(txtEmail.getText().trim());
        
            boolean username = false;
            boolean passwordlength = false;
            boolean passwordCheck = false;
            boolean passwordRepeat = false;
            boolean email = false;
            boolean fullname = false;
        if(newValue){
                   
            
        }else if(oldValue){
            
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

                email=true;
                lbEmailCaution.setTextFill(Paint.valueOf("BLACK"));

            }else{

                btSignUp.setDisable(true);
                lbEmailCaution.setTextFill(Paint.valueOf("RED"));

            }
            
            if(txtFullName.getText().trim().length()<44 
                || txtFullName.getText().isEmpty()){
                fullname = true;
                lbFullNameCaution.setTextFill(Paint.valueOf("BLACK"));
            }else{
                btSignUp.setDisable(true);
                lbFullNameCaution.setTextFill(Paint.valueOf("RED"));
            }
            
            
            if(username && passwordlength && passwordRepeat && passwordCheck 
                && email && fullname){

                btSignUp.setDisable(false);

            }
        }
        
        
       
    }
   
}


