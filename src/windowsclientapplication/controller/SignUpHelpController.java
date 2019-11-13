/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windowsclientapplication.controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Gaizka Andrews
 */
public class SignUpHelpController {
    
    @FXML
    private WebView webViewHelpSignUp;
    private Stage stage;
    
    void setStage(Stage stage) {
        this.stage=stage;
    }
    
    public void initStage(Parent root){
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Sign Up help");
        stage.setResizable(false);
        stage.setOnShowing(this::handleWindowShowing);
        stage.show();
        
    }
    private void handleWindowShowing(WindowEvent Event){
        WebEngine wEngine = webViewHelpSignUp.getEngine();
        wEngine.load(getClass().
                getResource("/windowsclientapplication/view/helpSignUp.html")
                    .toExternalForm());
    }

    

   
}
