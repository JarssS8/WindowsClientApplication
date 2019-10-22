/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windowsclientapplication.exception;

/**
 *
 * @author adria
 */
public class WindowsProjectException extends Exception {

    /**
     * Creates a new instance of <code>WindowsProjectException</code> without
     * detail message.
     */
    public WindowsProjectException() {
    }

    /**
     * Constructs an instance of <code>WindowsProjectException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public WindowsProjectException(String msg) {
        super(msg);
    }
}
