import java.rmi.*;

public interface ChatMachineTemplate extends Remote {
	public String respond(String input) throws RemoteException;
}