/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windowsclientapplication.controller;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
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
     * This method is going to be executed after every test in this class.
     * It's for clean the fields for the next text
     */
    @After
    public void eraseText(){
        doubleClickOn("#txtLogin");
        eraseText(1);
        doubleClickOn("#txtPass");
        eraseText(1);
    } 
    
    /**
     * Tests if the view have the correct initial state 
     */
    @Test
    public void test1_InitialState(){
        //Falta window name
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
        eraseText(20);
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
        eraseText(20);
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
    public void test4_ConnectionError(){
        Assert.assertThat(targetWindow("Server Error").lookup(".label").queryLabeled()).hasText("Unable to connect with server");
    }
    /*
    public void test4_UsernameAndPasswordGoodFormat(){
        clickOn("#txtLogin");
        write("j2NM45");
        clickOn("#txtPass");
        write("j2NM45edbn");
        verifyThat("#btLogin", isEnabled());
    }
    */
}
