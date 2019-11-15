/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windowsclientapplication.controller;

import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controller for the help window/page.
 *
 * @author Diego Urraca
 */
public class HelpController {

    /**
     * The control that shows the help page.
     */
    @FXML
    private WebView webView;

    private String windowRequest;
    
    private final static String LOGIN=ResourceBundle.getBundle(
            "windowsclientapplication.PropertiesClientSide").getString("LOGIN");
    private final static String SIGNUP=ResourceBundle.getBundle(
            "windowsclientapplication.PropertiesClientSide").getString("SIGNUP");

    /**
     * Help window initialization and showing.
     *
     * @param root
     * @param windowRequest
     */
    public void initAndShowStage(Parent root, String windowRequest) {
        this.windowRequest = windowRequest;
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Help Page");
        stage.setResizable(false);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setOnShowing(this::handleWindowShowing);
        stage.show();
    }
    /*MODIFICACION CAMBAIR RUTA EN CADA CASO  15/11/2019*/
    /**
     * Method that load and show the help page.
     *
     * @param event The event is the window that is being showed.
     */
    private void handleWindowShowing(WindowEvent event) {
        WebEngine webEngine = webView.getEngine();
        if (windowRequest.equals(SIGNUP)) {
            webEngine.load(getClass()
                    .getResource("/windowsclientapplication/view/help.html").toExternalForm());
        } else if (windowRequest.equals(LOGIN)) {
            webEngine.load(getClass()
                    .getResource("/windowsclientapplication/view/helpLogIn.html").toExternalForm());
        }
        
    }
}
