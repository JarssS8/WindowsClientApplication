/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windowsclientapplication.controller;

import org.junit.Test;
import static org.junit.Assert.*;
import org.testfx.framework.junit.ApplicationTest;




/**
 *
 * @author Gaizka Andres
 */
public class SignUpWindowControllerIT extends ApplicationTest{
    
    public SignUpWindowControllerIT() {
        
    }

    @Test
    public void testValidUsername() {
        clickOn("#txtUsername");
        write("A");
        clickOn("#txtPassword");
        write("12345678A");
        clickOn("#txtRepeatPassword");
        write("12345678A");
        clickOn("#txtEmail");
        write("email.example@extemsion.cope");
            
    }
    
}
