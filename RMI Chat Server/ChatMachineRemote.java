import java.rmi.*;  
import java.rmi.server.*;  
import java.util.Scanner;

public class ChatMachineRemote extends UnicastRemoteObject implements ChatMachineTemplate {  

    ChatMachineRemote()throws RemoteException{  
    	super();  
	}  
	
	public String respond(String input) {
		Scanner s = new Scanner(System.in);
		System.out.println("You have an incoming Message From The Client");
		System.out.println("Client:"+input);
		System.out.print("Enter Your Response:");
		String output = s.nextLine();
		return output;
	}
}  