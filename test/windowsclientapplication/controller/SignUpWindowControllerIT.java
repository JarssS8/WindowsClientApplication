/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windowsclientapplication.controller;

import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.testfx.framework.junit.ApplicationTest;
import windowsclientapplication.WindowsClientApplication;
import org.testfx.api.FxAssert;
import org.testfx.matcher.base.NodeMatchers;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;

/**
 *
 * @author Gaizka Andres
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignUpWindowControllerIT extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new WindowsClientApplication().start(stage);
    }

    @After
    public void cleanText() {
        
        doubleClickOn("#txtUsername");
        eraseText(1);
        doubleClickOn("#txtPassword");
        eraseText(1);
        doubleClickOn("#txtRepeatPassword");
        eraseText(1);
        doubleClickOn("#txtEmail");
        eraseText(1);
        clickOn("#txtEmail");
        eraseText(10);
        clickOn("#txtFullName");
        eraseText(10);

    }
    
    @Test
    public void testA_ErrorUsername() {
        clickOn("#linkClickHere");
        clickOn("#txtUsername");
        write("A");
        clickOn("#txtPassword");
        write("12345678A");
        clickOn("#txtRepeatPassword");
        write("12345678A");
        clickOn("#txtEmail");
        write("email@ext.cope");
        clickOn("#txtFullName");
        write("Aimar Null");
        clickOn("#btSignUp");
        FxAssert.verifyThat("#errorbutton", isEnabled());
        clickOn("#errorbutton");

    }

    @Test
    public void testB_ErrorPasswordUpperCase() {
        
        clickOn("#txtUsername");
        write("Aimar");
        clickOn("#txtPassword");
        write("12345678");
        clickOn("#txtRepeatPassword");
        write("12345678");
        clickOn("#txtEmail");
        write("email@ext.cope");
        clickOn("#txtFullName");
        write("Aimar Null");
        clickOn("#btSignUp");
        FxAssert.verifyThat("#errorbutton", isEnabled());
        clickOn("#errorbutton");
    }

    @Test
    public void testC_ErrorPasswordLength() {
        
        clickOn("#txtUsername");
        write("Aimar");
        clickOn("#txtPassword");
        write("1");
        clickOn("#txtRepeatPassword");
        write("1");
        clickOn("#txtEmail");
        write("email@ext.cope");
        clickOn("#txtFullName");
        write("Aimar Null");
        clickOn("#btSignUp");
        FxAssert.verifyThat("#errorbutton", isEnabled());
        clickOn("#errorbutton");
    }

    @Test
    public void testD_ErrorPasswordRepeat() {
      
        clickOn("#txtUsername");
        write("Aimar");
        clickOn("#txtPassword");
        write("12345678");
        clickOn("#txtRepeatPassword");
        write("1");
        clickOn("#txtEmail");
        write("email@ext.cope");
        clickOn("#txtFullName");
        write("Aimar Null");
        clickOn("#btSignUp");
        FxAssert.verifyThat("#errorbutton", isEnabled());
        clickOn("#errorbutton");
    }

    @Test
    public void testE_ErrorEmail() {
        clickOn("#txtUsername");
        write("Aimar");
        clickOn("#txtPassword");
        write("12345678A");
        clickOn("#txtRepeatPassword");
        write("12345678A");
        clickOn("#txtEmail");
        write("email@ext.");
        clickOn("#txtFullName");
        write("Aimar Null");
        clickOn("#btSignUp");
        FxAssert.verifyThat("#errorbutton", isEnabled());
        clickOn("#errorbutton");
    }

    @Test
    public void testF_ErrorFullName() {
        clickOn("#txtUsername");
        write("Aimar");
        clickOn("#txtPassword");
        write("12345678A");
        clickOn("#txtRepeatPassword");
        write("12345678A");
        clickOn("#txtEmail");
        write("email@ext.cope");
        clickOn("#btSignUp");
        FxAssert.verifyThat("#errorbutton", isEnabled());
        clickOn("#errorbutton");
    }
    
    @Test
    public void testG_SignUpOk() {
        clickOn("#txtUsername");
        write("Aimar");
        clickOn("#txtPassword");
        write("12345678A");
        clickOn("#txtRepeatPassword");
        write("12345678A");
        clickOn("#txtEmail");
        write("email@ext.cope");
        clickOn("#txtFullName");
        write("Aimar Null");
        clickOn("#btSignUp");
        FxAssert.verifyThat("#okbutton", isEnabled());
        clickOn("#okbutton");
    }
}
