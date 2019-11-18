/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windowsclientapplication.controller;

import javafx.stage.Stage;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.testfx.framework.junit.ApplicationTest;
import windowsclientapplication.WindowsClientApplication;
import org.testfx.api.FxAssert;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

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

    /**
     * Clean all Textfields after one test
     */
    @Ignore
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

    /**
     * Test if the login is correct
     */
    @Ignore
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

    @Ignore
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

    /**
     * Test if the password length is correct
     *
     */
    @Ignore
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

    /**
     * Test if the password field and repeatpassword are the same
     */
    @Ignore
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

    /**
     * Test if the email have the correct pattern
     */
    @Ignore
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

    /**
     * Test if fullname have the correct length
     */
    @Ignore
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

    /**
     * Test if the signup is correctly done
     */
    @Test
    public void testG_SignUpOk() {
        clickOn("#linkClickHere");
        clickOn("#txtUsername");
        write("TestLogin");
        clickOn("#txtPassword");
        write("12345678A");
        clickOn("#txtRepeatPassword");
        write("12345678A");
        clickOn("#txtEmail");
        write("email@ext.cope");
        clickOn("#txtFullName");
        write("Test Login");
        clickOn("#btSignUp");
        FxAssert.verifyThat("Registration completed.", isVisible());
        clickOn("#okbutton");
        FxAssert.verifyThat("#logInPane", isVisible());
    }

    /**
     * Test if the login already taken exception workd correctly
     */
    @Test
    public void testH_SignUpLogintaken() {
        clickOn("#linkClickHere");
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
        FxAssert.verifyThat("#loginTakenButton", isEnabled());
        clickOn("#loginTakenButton");
    }

}
