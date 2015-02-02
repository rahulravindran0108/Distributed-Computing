import java.rmi.*;  
import java.util.Scanner;

public class MyChatClient{  

	public static void main(String args[]){  
		Scanner s = new Scanner(System.in);

    	try{  
    		ChatMachineTemplate stub=(ChatMachineTemplate)Naming.lookup("rmi://localhost:5000/chat");  
    		System.out.print("Enter Message to be sent to server:");
    		String input = s.nextLine();
    		System.out.println("Server:"+stub.respond(input));
    	}catch(Exception e){}  
    }  
}  