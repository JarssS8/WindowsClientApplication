/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windowsclientapplication;

import clientlogic.logic.ConnectableClientFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import utilities.interfaces.Connectable;
import windowsclientapplication.controller.LoginWindowController;

/**
 * Application class for the LogIn/LogOut project. Entry point of JavaFX
 * @author Adrian
 */
public class WindowsClientApplication extends Application {
    /**
     * Entry point of JavaFX application. Loads, set and shows the primary window,
     * then send the client from the factory and the Parent to the controller of 
     * the current window.
     * @param stage The primary window of the application
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("view/LogIn_Window.fxml"));
        Parent root = (Parent)loader.load();
        LoginWindowController logInController= ((LoginWindowController)loader.getController());
        logInController.setStage(stage);
        Connectable client = ConnectableClientFactory.getClient();
        logInController.initStage(root,client);
    }

    /**
     * Entry point for Java Application
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
