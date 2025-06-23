import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;



public class Client extends Thread{

	
	Socket socketClient;
	String IPaddress;
	int Port;
	BaccaratInfo info = new BaccaratInfo();
	ObjectOutputStream out;
	ObjectInputStream in;
	String message;
	
	private Consumer<Serializable> callback;
	
	Client(String IP, int port,Consumer<Serializable> call){
		IPaddress = IP;
		Port = port;
		callback = call;
	}
	
	public void run() {
		//"127.0.0.1"
		try {
		socketClient= new Socket(IPaddress,Port);
	    out = new ObjectOutputStream(socketClient.getOutputStream());
	    in = new ObjectInputStream(socketClient.getInputStream());
	    socketClient.setTcpNoDelay(true);
		}
		catch(Exception e) {}
		
		while(true) {
			 
			try {
			info = (BaccaratInfo) in.readObject();
			
			callback.accept(info);
			}
			catch(Exception e) {}
		}
	
    }
	
	public void setMessage() {
		
		this.message = info.outcome;
	}
	
	public void startgame() {
		send(info);
	}
	
	public void BetOption(int option) {
		info.PlayerBetOpion = option;
//		send(info);
		
	}
	
	public void BetAmount(int amount) {
		info.PlayerBetAmount = amount;
//		send(info);
		
	}
	public void QuitGame(boolean t) {
		info.Pquit = true;
		send(info);
	}
	
	public void send(BaccaratInfo data) {
		
		try {
			out.writeObject(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
