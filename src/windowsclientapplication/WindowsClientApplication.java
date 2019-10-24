/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windowsclientapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import windowsclientapplication.controller.*;

/**
 *
 * @author adria
 */
public class WindowsClientApplication extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/SignUp_Window.fxml"));
        Parent root =(Parent)loader.load();
        
        SignUpWindowController controller =((SignUpWindowController)loader.getController());
        
        controller.setStage(stage);
        controller.initStage(root);
        /*
         Parent root = FXMLLoader.load(getClass().getResource("view/SignUp_Window.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        */
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
