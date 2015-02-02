/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.swing.JFrame;
/**
 *
 * @author rahul
 */
public class ServerTest {
    
    public static void main(String [] args)
    {
        ChatRoomServer example = new ChatRoomServer();
        example.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        example.startRunning();
        
    }
    
}
