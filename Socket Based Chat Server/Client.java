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
public class Client extends JFrame{

    /**
     * @param args the command line arguments
     */
    private JTextField userText;
    private JTextArea chatWindow;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String message="";
    private String serverIP;
    private Socket connection;
    
    public Client(String host)
    {
        super("client");
        serverIP = host;
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
        add(new JScrollPane(chatWindow),BorderLayout.CENTER);
        setSize(300,150);
        setVisible(true);
    }
    
    //connect to server
    public void startRunning()
    {
        try
        {
            connectToServer();
            setupStreams();
            whileChatting();
        }catch(EOFException eofException){
            showMessage("client terminated the connection");
        }catch(IOException ioException)
        {
            ioException.printStackTrace();
        }finally{
            closeConnections();
        }
    }
    
    //connection to server method
    private void connectToServer() throws IOException
    {
         showMessage("attempting connection..\n");
         connection = new Socket(InetAddress.getByName(serverIP),6789);
         showMessage("connected to "+connection.getInetAddress().getHostName());
         
    }
    
    //setup streams to send and receive the messages
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
        }while(!message.equals("SERVER - END"));
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
   
    //SENDING MESSAGE TO SERVER
    private void sendMessage(String message)
    {
        try{
            output.writeObject("CLIENT - "+message);
            output.flush();
            showMessage("\nCLIENT - "+message);
        }catch(IOException ioException)
        {
            chatWindow.append("\n error unable to send message");
        }
    }
    
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
