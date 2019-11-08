/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windowsclientapplication.controller;

//imports
import javafx.stage.Stage;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
import windowsclientapplication.WindowsClientApplication;

/**
 *
 * Method that test the LogOut/Main window and help page.
 *
 * @author Diego Urraca
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LogOutWindowControllerIT extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new WindowsClientApplication().start(stage);
    }
    /**
     * Test the login
     */
    @Test
    public void testA_Enter() {
        clickOn("#txtLogin");
        write("Test");
        clickOn("#txtPass");
        write("12345678A");
        clickOn("#btLogin");
    }
    
    /**
     * Test that load the data in help window
     */
    @Test
    public void testB_LoadData() {
        FxAssert.verifyThat("#lblUser", hasText("Test User"));
        FxAssert.verifyThat("#lblEmail", hasText("test@gmail.com"));
        FxAssert.verifyThat("#lblStatusUser", hasText("Test"));
        
    }
    
    /**
     * Test that logOut hyperLink, menu button and an alert appears
     */
    @Test
    public void testC_LogOut() {
        clickOn("#hlLogOut");
        FxAssert.verifyThat("#buttonYes", isVisible());
        clickOn("#buttonYes");
    }
    
    /**
     * Test that logOut menu button and an alert appears
     */
    @Test
    public void testD_Close() {
        clickOn("#menOpt");
        clickOn("#mbClose");
        FxAssert.verifyThat("#buttonYes", isVisible());
        clickOn("#buttonYes");
    }
    
    /**
     * Test that Help window appears
     */
    @Test
    public void testE_Help() {
        clickOn("#menHel");
        clickOn("#mbAbout");
        FxAssert.verifyThat("#webView", isVisible());

    }
}
