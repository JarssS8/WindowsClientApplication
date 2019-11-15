/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windowsclientapplication.controller;
import static javafx.scene.input.KeyCode.F1;
import javafx.stage.Stage;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.testfx.api.FxAssert;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import org.testfx.matcher.control.LabeledMatchers;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import windowsclientapplication.WindowsClientApplication;

/*MODIFICACION HACER NUEVA CLASE DE TEST 13/11/2019 14:50*/
/**
 * Testing class for Login view and controller using TextFX framework
 * @author Adrian
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginWindowControllerAdrianWithConnectionIT extends ApplicationTest {
    
    /**
     * Starts the application who gonna be tested
     * @param stage Primary stage object
     * @throws Exception If there is any errors
     */
    @Override
    public void start(Stage stage) throws Exception{
        new WindowsClientApplication().start(stage);
    }
    @Test
    public void test1_LoginNotFound(){
        
        clickOn("#txtLogin");
        write("Test2");
        clickOn("#txtPass");
        write("12345678A");
        clickOn("#btLogin");
        FxAssert.verifyThat("#loginNotFoundError",isEnabled());
        clickOn("#loginNotFoundError");
    }
    
    /**
     * Test when you can access to the server and the username is correct but the password don't match
     */
    @Test
    public void test2_PasswordNotCorrect(){
        clickOn("#txtLogin");
        write("Test");
        clickOn("#txtPass");
        write("Abcd*1234");
        clickOn("#btLogin");
        FxAssert.verifyThat("#wrongPasswordError",isEnabled());
        clickOn("#wrongPasswordError");
    }
    
    
    
    /**
     * Test with a correct username and password can access to the LogOut Window
     * and then return to the Login Window
     */
    @Test
    public void test3_CorrectUsernameAndPassword(){
        clickOn("#txtLogin");
        write("Test");
        clickOn("#txtPass");
        write("12345678A");
        clickOn("#btLogin");
        verifyThat("#bopMainWin", isVisible());
        clickOn("#hlLogOut");
        clickOn("#buttonYes");
    } 
}
