import java.rmi.*;  
import java.rmi.registry.*;  

public class MyChatServer{  

    public static void main(String args[]){  
        try{  
            ChatMachineTemplate stub=new ChatMachineRemote();  
            Naming.rebind("rmi://localhost:5000/chat",stub);  
        }catch(Exception e){
            System.out.println(e);
        }  
    }  
}  