/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author rahul
 */
public class ChatRoomServer extends JFrame {

    /**
     * @param args the command line arguments
     */
    private JTextField userText;
    private JTextArea chatWindow;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private ServerSocket server;
    private Socket connection;
    
    public ChatRoomServer()
    {
        super("Rahuls instant messenger");
        userText = new JTextField();
        userText.setEditable(false);
        userText.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent event)
                    {
                        sendMessage(event.getActionCommand());
                        userText.setText("");
                    }
                }
                );
        add(userText,BorderLayout.NORTH);
        chatWindow = new JTextArea();
        add(new JScrollPane(chatWindow));
        setSize(300,150);
        setVisible(true);
    }
    
    //set up and run the server
    public void startRunning()
    {
        try
        {
            server = new ServerSocket(6789,100);
            while(true)
            {
                try
                {
                    waitForConnection();
                    setupStreams();
                    whileChatting();
                    
                }catch(EOFException eofException)                    
                {
                    showMessage("\n server ended the connection");
                }
                finally
                {
                    closeConnections();
                }
            }
        }catch(IOException ioException)
        {
            ioException.printStackTrace();
        }
    }
    
    private void closeConnections()
    {
        showMessage("\nClosing Connections... \n");
        ableToType(false);
        try
        {
            output.close();
            input.close();
            connection.close();
        }catch(IOException IOexception)
        {
            IOexception.printStackTrace();
        }
    }
    //sends a message to client
    private void sendMessage(String message)
    {
        try{
            output.writeObject("SERVER - "+message);
            output.flush();
            showMessage("\nSERVER - "+message);
        }catch(IOException ioException)
        {
            chatWindow.append("\n error unable to send message");
        }
    }
    
    //waits for client to connect to the server
    private void waitForConnection() throws IOException
    {
        showMessage("waiting for someone to connect...");
        connection = server.accept();
        showMessage("now connected to "+connection.getInetAddress().getHostName());
    }
    
    //get streams to send and receive data
    private void setupStreams() throws IOException            
    {
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
        showMessage("streams are now setup");
    }
    
    //functions to be performed during chatting
    private void whileChatting() throws IOException
    {
        String message="You are now connected";
        sendMessage(message);
        ableToType(true);
        do
        {
            try
            {
                message = (String) input.readObject();
                showMessage("\n"+message);
            }catch(ClassNotFoundException classNotFoundException)
            {
                showMessage("illegal characters sent");
            }
        }while(!message.equals("CLIENT - END"));
    }
    
    //updates chatwindow
    private void showMessage(final String text)
    {
        SwingUtilities.invokeLater(
                    new Runnable()
                    {
                        public void run()
                        {
                            chatWindow.append(text);
                        }
                    }
                );
    }
    //let the user type stuffs to their box
    private void ableToType(final boolean tof)
    {
        SwingUtilities.invokeLater(
                    new Runnable()
                    {
                        public void run()
                        {
                            userText.setEditable(tof);
                        }
                    }
                );       
    }
    
   
}
