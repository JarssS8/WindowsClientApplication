/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windowsclientapplication.controller;

import javafx.stage.Stage;
import org.junit.After;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import windowsclientapplication.WindowsClientApplication;

/**
 *
 * @author Gaizka Andres
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginWindowControllerIT extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new WindowsClientApplication().start(stage);
    }

    /**
     * Clean all Textfields after one test
     */
    @After
    public void cleanText() {

        doubleClickOn("#txtLogin");
        eraseText(1);
        doubleClickOn("#txtPass");
        eraseText(1);
    }

    /**
     * Test if the login minimum is correct
     */
    @Test
    public void testA_LoginIncorrectMin() {
        clickOn("#txtLogin");
        write("a");
        clickOn("#txtPass");
        write("12345678A");
        FxAssert.verifyThat("#btLogin", isDisabled());
    }

    /**
     * Test if the login maximum is correct
     */
    @Test
    public void testB_LoginIncorrectMax() {
        clickOn("#txtLogin");
        write("aaaaaaaaaaaaaaaaaaaaaa");
        clickOn("#txtPass");
        write("12345678A");
        FxAssert.verifyThat("#btLogin", isDisabled());
    }

    /**
     * Test if the password length is correct
     */
    @Test
    public void testC_PasswordIncorrectLengthMin() {
        clickOn("#txtLogin");
        write("Aimar");
        clickOn("#txtPass");
        write("1");
        FxAssert.verifyThat("#btLogin", isDisabled());
    }

    /**
     * Test if the password minimum is correct
     */
    public void testD_PasswordIncorrectLengthMax() {
        clickOn("#txtLogin");
        write("Aimar");
        clickOn("#txtPass");
        write("11111111111");
        FxAssert.verifyThat("#btLogin", isDisabled());
    }

    /**
     * Test if the password maximum is correct
     */
    @Test
    public void testE_PasswordIncorrectUppercase() {
        clickOn("#txtLogin");
        write("Aimar");
        clickOn("#txtPass");
        write("12345678a");
        FxAssert.verifyThat("#btLogin", isDisabled());
    }

    /**
     * Test if login is correct
     */
    @Test
    public void testF_LogInCorrect() {
        clickOn("#txtLogin");
        write("Aimar");
        clickOn("#txtPass");
        write("12345678A");
        FxAssert.verifyThat("#btLogin", isEnabled());
    }

    /**
     * Test if go correctly to signup window
     */
    @Test
    public void testG_SignUp() {
        clickOn("#linkClickHere");
        FxAssert.verifyThat("#btBack", isVisible());
    }
}
