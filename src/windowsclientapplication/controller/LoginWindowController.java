/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windowsclientapplication.controller;
import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utilities.beans.User;
import utilities.exception.LoginNotFoundException;
import utilities.exception.ServerConnectionErrorException;
import utilities.exception.WrongPasswordException;
import utilities.interfaces.Connectable;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.Mnemonic;

/**
 * This class is a controller UI class for LogIn_Window view. Contains event
 * handlers and on window showing code.
 *
 * @author Adrian Corral
 */
public class LoginWindowController {

    /**
     * The stage is used for instance an stage attribute and can store the stage
     * from other class or send the stage to other class.
     */
    private Stage stage;

    /**
     * This is the login button on the view.
     */
    @FXML
    private Button btLogin;

    /**
     * This is the text input by the user that define the username.
     */
    @FXML
    private TextField txtLogin;

    /**
     * This is the text input by the user that define the password.
     */
    @FXML
    private PasswordField txtPass;

    /**
     * This is the link to navigate to the sign up window.
     */
    @FXML
    private Hyperlink linkClickHere;

    /**
     * This is the label thats before the username field, and shows what should write the user on the field
     */
    @FXML
    private Label lbLogin;

    /**
     * This is the label thats before the password field, and shows what should write the user on the field
     */
    @FXML
    private Label lbPass;

    /**
     * Local connectable client class that we get from the Application, when we 
     * want go to other controller we should send this.
     */
    private Connectable client;

    /**
     * Logger object used to log messages for application.
     */
    private static final Logger LOGGER = Logger.getLogger(
            "WindowsClientApplication.controller.LoginWindowController");
    
    /**
     * String that contains the word "logIn" and we send to the help controller 
     * for said what window we want
     */
    private final static String LOGIN=ResourceBundle.getBundle(
            "windowsclientapplication.PropertiesClientSide").getString("LOGIN");

    /**
     * @return Return the stage of this class
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * @param stage Sets the stage for this class
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * This method initialize the window and everything thats the stage needs.
     * This calls other method when shows the window to set attributes of the
     * window
     * @param root The parent object
     * @param client The client get it from the factory that is going to use all
     * the application
     */
    public void initStage(Parent root, Connectable client) {
        Scene scene = new Scene(root);
        //Stage Properties
        stage.setScene(scene);
        stage.setTitle("LogIn");
        stage.setResizable(false);
        stage.setOnShowing(this::handleWindowShowing);
        stage.setOnCloseRequest(this::onCloseRequest);
        stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, this::keyEventController);
        //Listeners
        txtLogin.textProperty().addListener(this::textChange);
        txtPass.textProperty().addListener(this::textChange);
        this.client = client;
        //Stage show
        stage.show();
    }

    /*MODIFICACION TOOLTIPS Y PROMPTEXT DE LOS CAMPOS DE LOGIN Y PASSWORD 14/11/2019 12:40 */
    /**
     * This is the method to control the components of this window when we shows
     * the window.
     * @param event The event is the window that is being showed.
     */
    private void handleWindowShowing(WindowEvent event) {
        btLogin.setDisable(true);
        btLogin.setMnemonicParsing(true);
        btLogin.setText("_Login");
        btLogin.setTooltip(new Tooltip("Click to complete the login"));
        lbLogin.setTooltip(new Tooltip("Username to login"));
        lbPass.setTooltip(new Tooltip("Password to login"));
        txtLogin.setPromptText("Insert the username here");
        txtLogin.setTooltip(new Tooltip("The username should have between 4 and 10 characters"));
        txtPass.setPromptText("Insert the password here");
        txtPass.setTooltip(new Tooltip("The username should have between 8 and 14 characters, including at least one number and one uppercase"));
        linkClickHere.setTooltip(new Tooltip("Click here to sign up"));
        linkClickHere.setMnemonicParsing(true);
        /*MODIFICACION AÑADIR MNEMONICO AL SIGNUP 15/11/2019 09:05*/
        KeyCombination keyCode= new KeyCodeCombination(KeyCode.S, KeyCombination.ALT_DOWN);
        Mnemonic mnemonicCode= new Mnemonic(linkClickHere, keyCode);
        getStage().getScene().addMnemonic(mnemonicCode);
        
        
    }

    /**
     * This method is used if the user try to close the application clicking in
     * the red cross(right-top in the stage) and control if the user is sure to
     * close the application.
     * @param event The event is the user trying to close the application with
     * the cross of the stage.
     */
    public void onCloseRequest(WindowEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Close confirmation");
        alert.setTitle("Exit Window");
        alert.setContentText("Are you sure that want close the application?");
        alert.initOwner(stage);
        alert.initModality(Modality.WINDOW_MODAL);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            stage.close();
            Platform.exit();

        } else {
            event.consume();
        }
    }

    /*MODIFICACION REVISAR SI EL BOTON DE LOGIN SE ACTIVA CUANDO DEBE 13/11/2019 14:40*/
    /**
     * Checks every time that the user change the TextField and if both formats
     * are correct enables the login button.
     * @param event The event when the text is changing.
     */
    private void textChange(ObservableValue observable, String oldValue, String newValue) {
        boolean passok = checkPassword(txtPass.getText().trim());
        boolean usernameOk = false;
        boolean passwordCheck = false;
        //Check if user got the correct format
        usernameOk = txtLogin.getText().trim().length() >= 4 && txtLogin.getText().trim().length() <= 10;
        //Check password got corect format
        passwordCheck = txtPass.getText().trim().length() >= 8
                && txtPass.getText().trim().length() <= 14 && passok;
        //Enable Login Button
        btLogin.setDisable(!usernameOk || !passwordCheck);
    }

    /**
     * This method checks if the password has the correct format.
     * @param password a String that contains the password written by the user.
     * @return check A boolean.
     */
    private boolean checkPassword(String password) {

        boolean capital = false;
        boolean number = false;
        boolean check = false;

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (Character.isDigit(ch)) {
                number = true;
            }
            if (Character.isUpperCase(ch)) {
                capital = true;
            }
        }

        if (capital && number) {
            check = true;
        }
        return check;
    }

    /**
     * This method sends the txtLogin and the txtPass to the factory and waits
     * if the user exists on dataBase and the password is correct to go to the
     * logout window.
     * @param event The event is the user clicking on the login button.
     * @throws LoginNotFoundException If login does not exist in the database.
     * @throws WrongPasswordException If password does not match with the user.
     */
    public void loginClick(ActionEvent event) throws LoginNotFoundException, WrongPasswordException {
        try {
            User user = new User();
            user.setLogin(txtLogin.getText().trim());
            user.setPassword(txtPass.getText().trim());
            LOGGER.info("Sending user to the login client method");
            user = client.logIn(user);

            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/windowsclientapplication/view/main_window.fxml"));
            Parent root = (Parent) loader.load();
            LogOutWindowController logOutController
                    = ((LogOutWindowController) loader.getController());
            logOutController.setStage(stage);
            LOGGER.info("Loading main window...");
            logOutController.initStage(root, client, user);
        } catch (LoginNotFoundException e) {
            LOGGER.warning("LoginWindowController: Login not found");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("LogIn Error");
            alert.setContentText("User does not exist");
            Button errorButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            errorButton.setId("loginNotFoundError");
            alert.showAndWait();
        } catch (WrongPasswordException e) {
            /*MODIFICACION MENSAJE SI LA CONTRASEÑA ES INCORRECTA 13/11/2019 14:04*/
            LOGGER.warning("LoginWindowController: Wrong password");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Password Error");
            alert.setContentText("Password is not correct");
            Button errorButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            errorButton.setId("wrongPasswordError");
            alert.showAndWait();
        } catch (ServerConnectionErrorException ex) {
            LOGGER.warning("LoginWindowController: Server connection error");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Server Error");
            alert.setContentText("Unable to connect with server");
            Button errorButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            errorButton.setId("serverConnectionError");
            alert.showAndWait();
        } catch (IOException ex) {
            LOGGER.warning("LoginWindowController: IO Exception on LoginWindowController");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Sorry, an error has ocurred");
            alert.showAndWait();
        } catch (Exception ex) {
            LOGGER.warning("LoginWindowController: Exception on LoginWindowController");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Sorry, an error has ocurred");
            alert.showAndWait();
        }
    }

    /**
     * This method opens the sign up window when the user clicks on the
     * hyperlink click here
     * @param event The event
     * @throws IOException Error when can't access to the fxml view
     */
    public void signUpClick(ActionEvent event) throws IOException {
        txtLogin.setText("");
        txtPass.setText("");
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/windowsclientapplication/view/SignUp_Window.fxml"));
        Parent root = (Parent) loader.load();
        SignUpWindowController signUpController
                = ((SignUpWindowController) loader.getController());
        signUpController.setStage(stage);
        signUpController.initStage(root, client);
    }
    
    /*MODIFICACION AÑADIR SHORTCUT F1 PARA ABRIR LA VENTANA DE AYUDA 15/11/2019 8:05*/
   /**
    * This method controls when we press one key and if is equals to someone 
    * previously defined do something
    * @param keyEvent is the event for the key that we press
    */
    public void keyEventController(KeyEvent keyEvent) {
        try {
            if (keyEvent.getCode() == KeyCode.F1) {//If user press F1 key
                FXMLLoader loader
                        = new FXMLLoader(getClass().getResource(
                                "/windowsclientapplication/view/Help.fxml"));
                Parent root = (Parent) loader.load();
                HelpController helpController
                        = ((HelpController) loader.getController());
                helpController.initAndShowStage(root, LOGIN);
            }
            
        } catch (IOException ex) {
            LOGGER.severe("Error showing the help page");
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("ERROR ON HELP WINDOW");
            alert.setContentText("There was an error loading the help window");
            alert.showAndWait();
        }

    }

}
