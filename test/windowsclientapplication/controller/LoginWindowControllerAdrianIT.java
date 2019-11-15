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
public class LoginWindowControllerAdrianIT extends ApplicationTest {
    
    /**
     * Starts the application who gonna be tested
     * @param stage Primary stage object
     * @throws Exception If there is any errors
     */
    @Override
    public void start(Stage stage) throws Exception{
        new WindowsClientApplication().start(stage);
    }
    /**
     * Tests if the view have the correct initial state 
     */
    @Test
    public void test1_InitialState(){
        verifyThat("#txtLogin",hasText(""));
        verifyThat("#txtPass",hasText(""));
        verifyThat("#btLogin", isDisabled());
        verifyThat("#linkClickHere", isEnabled());
    }
    
    /**
     * Tests the username format, checking if the Login button is disabled 
     * with all bad possible usernames and if don't have a password, the button is 
     * always disabled 
     */
    @Test
    public void test2_WrongUsernameFormat(){
        clickOn("#txtLogin");
        /*Checking without password, being the username lenght  < 4, 
        between 4 and 10(good user format) 
        and more longer than 10*/
        write("aS3");
        verifyThat("#btLogin",isDisabled());
        write("L5");
        verifyThat("#btLogin",isDisabled());
        write("aseldasdFDFsa54");
        verifyThat("#btLogin",isDisabled());
        /*Checking with password, being the username lenght more longer than 10 
        and less than 4*/
        clickOn("#txtPass");
        write("1234567A");
        verifyThat("#btLogin",isDisabled());
        doubleClickOn("#txtLogin");
        eraseText(1);
        write("aS3");
        verifyThat("#btLogin",isDisabled());
    }
    
    /**
     * Tests the password format, checking if the Login button is disabled 
     * with all bad possible passwords and if don't have an username, the button is 
     * always disabled 
     */
    @Test
    public void test3_WrongPasswordFormat(){
        clickOn("#txtPass");
        /*Checking without username, the password lenght < 8, 
        between 8 and 14 without numbers or uppercase,
        between 8 and 14 with at leats one number but without uppercase,
        between 8 and 14 with at leats one uppercase but without numbers,
        between 8 and 14 with at leats one number and one uppercase
        and finally longer than 14
        */
        write("aeeee");
        verifyThat("#btLogin",isDisabled());
        write("eee");
        verifyThat("#btLogin",isDisabled());
        write("A");
        verifyThat("#btLogin",isDisabled());
        eraseText(1);
        write("9");
        verifyThat("#btLogin",isDisabled());
        write("A");
        verifyThat("#btLogin",isDisabled());
        write("32fHGffffD");
        verifyThat("#btLogin",isDisabled());
        /*Checking with username, the password lenght is longer than 14,
        is shorter than 8,
        between 8 and 14 without numbers or uppercase,
        between 8 and 14 with at leats one number but without uppercase and
        between 8 and 14 with at leats one uppercase but without numbers
        */
        clickOn("#txtLogin");
        write("jars");
        verifyThat("#btLogin", isDisabled());
        doubleClickOn("#txtPass");
        eraseText(1);
        write("aaaeeee");
        verifyThat("#btLogin", isDisabled());
        write("a");
        verifyThat("#btLogin", isDisabled());
        write("A");
        verifyThat("#btLogin",isDisabled());
        eraseText(1);
        write("9");
        verifyThat("#btLogin",isDisabled());
        
    }
    
    /**
     * Test when the application can't connect with the server and shows an AlertDialog
     */
    //@Test
    public void test4_ConnectionError(){
        clickOn("#txtLogin");
        write("user");
        clickOn("#txtPass");
        write("BBccd1234");
        clickOn("#btLogin");
        FxAssert.verifyThat("#serverConnectionError",isEnabled());
        clickOn("#serverConnectionError");
    }
    
    /**
     * Test when you can access to the server but the username don't exist on the DataBase
     */
    
    @Test
    public void test5_LoginNotFound(){
        
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
    public void test6_PasswordNotCorrect(){
        clickOn("#txtLogin");
        write("Test");
        clickOn("#txtPass");
        write("Abcd*1234");
        clickOn("#btLogin");
        FxAssert.verifyThat("#wrongPasswordError",isEnabled());
        clickOn("#wrongPasswordError");
    }
    
    /**
     * Test if the user can access to SignUp window correctly and come back to the LoginWindow
     */
    @Test
    public void test7_SignUpWindow(){
        clickOn("#linkClickHere");
        verifyThat("#signUpWindow", isVisible());
        FxAssert.verifyThat("#CompleteAll",LabeledMatchers.hasText("Complete all the fields for a succesfull SignUp"));
        clickOn("#btBack");
        clickOn("Yes");
    
    }
    
    /**
     * Test with a correct username and password can access to the LogOut Window
     * and then return to the Login Window
     */
    @Test
    public void test8_CorrectUsernameAndPassword(){
        clickOn("#txtLogin");
        write("Test");
        clickOn("#txtPass");
        write("12345678A");
        clickOn("#btLogin");
        verifyThat("#bopMainWin", isVisible());
        clickOn("#hlLogOut");
        clickOn("#buttonYes");
    }
    
    /**
     * Test if help window shows correctly when type the F1 key
     */
    @Test
    public void test9_HelpWindow(){
        type(F1);
        verifyThat("#helpWindow", isVisible());
    }
    
}
