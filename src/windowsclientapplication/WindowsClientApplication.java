/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windowsclientapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import windowsclientapplication.controller.*;

/**
 *
 * @author adria
 */
public class WindowsClientApplication extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/windowsclientapplication/view/LogIn_Window.fxml"));
        Parent root = (Parent)loader.load();
        LoginWindowController logInController= ((LoginWindowController)loader.getController());
        logInController.setStage(stage);
        logInController.initStage(root);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
