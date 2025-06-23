import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.scene.control.ListView;

public class Server{

	int count = 1;	
	ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	TheServer server;
	private Consumer<Serializable> callback;
	int port;
	
	BaccaratInfo info = new BaccaratInfo();
	
	Server(int port, Consumer<Serializable> call){
	
		callback = call;
		this.port = port;
		server = new TheServer();
		server.start();
		
	}
	
	
	public class TheServer extends Thread{
		
		public void run() {
		
			try(ServerSocket mysocket = new ServerSocket(Server.this.port);){
			
		    while(true) {
		
				ClientThread c = new ClientThread(mysocket.accept(), count);
				callback.accept("client has connected to server: " + "client #" + count);
				clients.add(c);
				c.start();
				
				count++;
				
			    }
			}//end of try
				catch(Exception e) {
					callback.accept("Server socket did not launch");
				}
			}//end of while
		}
	

		class ClientThread extends Thread{
			
		
			Socket connection;
			int count;
			ObjectInputStream in;
			ObjectOutputStream out;
			
			ClientThread(Socket s, int count){
				this.connection = s;
				this.count = count;	
			}
			
			public void updateClients(BaccaratInfo data) {
					try {
					 out.writeObject(data);
					}
					catch(Exception e) {}
				
			}
			
			public void run(){
					
				try {
					in = new ObjectInputStream(connection.getInputStream());
					out = new ObjectOutputStream(connection.getOutputStream());
					
					connection.setTcpNoDelay(true);	
				}
				catch(Exception e) {
					System.out.println("Streams not open");
				}
				
				 while(true) {
					    try {
					    	BaccaratInfo data = (BaccaratInfo) in.readObject();
					    	info = data;
					    	String pOpion;
					    	
					    	if(info.Pquit) {
					    		callback.accept("client: " + count + " left the sever!");
					    		clients.remove(this);
					    		break;
					    		
					    	}
					    	
					    	if(data.PlayerBetOpion == 1) {
					    		pOpion = "Player";
					    	} else if(data.PlayerBetOpion == 2) {
					    		pOpion = "Banker";
					    	} else {
					    		pOpion = "Draw";
					    	}
					    	callback.accept("client#: " + count + " has bet: " + info.PlayerBetAmount + " on the " + pOpion);

					    	info.outcome =" You have bet: " + info.PlayerBetAmount + " on the " + pOpion + "\n";
					    	BaccaratGame game = new BaccaratGame();
					    	game.startGame();
					    	game.getBet(data.PlayerBetAmount);
					    	
					    	info.Cards = new int[6];
					    	info.suite = new String[6];
					    	
					    	info.outcome += "Player Total: "+ game.PlayerPoints() + "  Banker Total: " + game.BankerPoints()
    						+ "\n" + game.evaluteOption() + " wins";
					    	info.playerTotalAmount =  game.evaluateWinnings();
					    	
					    	callback.accept("client#: " + count + " Player Total: "+ game.PlayerPoints() + "  Banker Total: " + game.BankerPoints()
    						+ "\n" + game.evaluteOption() + " wins");
					    	
					    	if(pOpion == game.evaluteOption()) {
					    		info.outcome += "\nCongrades, you bet " + game.evaluteOption() + "! You win!\n";
					    		callback.accept("client#: " + count + " Congrades, you bet " + game.evaluteOption() + "! You win!\n");
					    		
					    		info.isWin = true;
					    	} else {
					    		info.outcome += "\nSorry, you bet " + pOpion + "! You lost your bet!\n";
					    		callback.accept("client#: " + count + " Sorry, you bet " + pOpion + "! You lost your bet!\n");
					    		info.isWin = false;
					    	}
					    	
					    	
					    	for(int i = 0; i < game.playerHand.size(); i++) {
					    		info.Cards[i] = game.playerHand.get(i).value;
					    		info.suite[i] = game.playerHand.get(i).suite;
					    	}
					    	if(game.playerHand.size() == 2) {
					    		info.Cards[2] = 0;
					    		info.suite[2] = "";
					    	}
					    	for(int i = 0; i < game.BankerHand.size(); i++) {
					    		info.Cards[i + 3] = game.BankerHand.get(i).value;
					    		info.suite[i + 3] = game.BankerHand.get(i).suite;
					    	}
					    	if(game.BankerHand.size() == 2) {
					    		info.Cards[5] = 0;
					    		info.suite[5] = "";
					    	}
					    	
					    	updateClients(info);
					    }
					    catch(Exception e) {
					    	callback.accept("OOOOPPs...Something wrong with the socket from client: " + count + "....closing down!");
					    	clients.remove(this);
					    	break;
					    }
					}
				}//end of run
			
			
		}//end of client thread
}


	
	

	
