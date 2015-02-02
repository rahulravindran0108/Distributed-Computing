/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.swing.JFrame;
/**
 *
 * @author rahul
 */
public class ClientTest {
    public static void main(String [] args)
    {
        Client example;
        example = new Client("127.0.0.1");
        example.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        example.startRunning();
        
    }
}
