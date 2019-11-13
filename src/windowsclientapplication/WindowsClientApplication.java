/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windowsclientapplication;

import clientlogic.logic.Client;
import clientlogic.logic.ConnectableClientFactory;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import utilities.interfaces.Connectable;
import windowsclientapplication.controller.*;

/**
 * Application class for the WindowsClientApplication project. 
 * Launches the the JavaFX Application.
 * @author adria
 */
public class WindowsClientApplication extends Application {
    
    /**
     * Declaration of the port for the connection
     */
    private static final int PORT = Integer.parseInt(ResourceBundle.getBundle(
            "windowsclientapplication.PropertiesClientSide").getString("PORT"));

    /**
     * Declaration of the IP for the connection
     */
    private static final String IP = ResourceBundle.getBundle(
            "windowsclientapplication.PropertiesClientSide").getString("IP");
    
    private static final Logger LOGGER = Logger
            .getLogger("windowsclientapplication.WindowsClientApplication");
    /**
     * Starts the JavaFx application. Loads, sets and shows the the fxml view.
     * @param stage The main window of the application.
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {
        Connectable client = ConnectableClientFactory.getClient(IP,PORT);
        LOGGER.info("Loading LogIn window...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/windowsclientapplication/view/LogIn_Window.fxml"));
        Parent root =(Parent)loader.load();
        LoginWindowController controller = loader.getController();

        controller.setStage(stage);
        controller.setClient((Client) client);
        controller.initStage(root);
    }

    /**
     * This class launches the Java application.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}