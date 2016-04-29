package edu.asu.stratego;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import edu.asu.stratego.game.ServerGameManager;

/**
 * The Stratego Server creates a socket and listens for connections from every 
 * two players to form a game session. Each session is handled by a thread, 
 * ServerGameManager, that communicates with the two players and determines the 
 * status of the game.
 */
public class Server extends Thread {
	
	private Thread serverThread;
	private String message = "";
	
	public Server(){
		
		serverThread = new Thread(this, "serverThread");
		serverThread.start();
		
	}

    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void run(){
    		
    	try {
			runServer(); // Call server as separate method
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    // Made separate from run() since when overriding run(), adding throws IOException changes signature.
    public void runServer() throws IOException{
    	
        String hostAddress    = InetAddress.getLocalHost().getHostAddress();
        ServerSocket listener = null;;
        int sessionNumber     = 1;
        
        try {
            listener = new ServerSocket(4212);
            String serverMessage =  "Server started @ "; 
            serverMessage.concat(hostAddress);
            this.setMessage(serverMessage+hostAddress);

            System.out.println("Server started @ " + hostAddress);
            System.out.println("Waiting for incoming connections...\n");
            
            while (true) {
                Socket playerOne = listener.accept();
                System.out.println("Session " + sessionNumber + 
                                   ": Player 1 has joined the session");
                
                Socket playerTwo = listener.accept();
                System.out.println("Session " + sessionNumber + 
                                   ": Player 2 has joined the session");
                
                Thread session = new Thread(new ServerGameManager(
                        playerOne, playerTwo, sessionNumber++));
                System.out.println("Here");
                session.setDaemon(true);
                session.start();
            }
        }catch(Exception e){
        	this.setMessage("FAILED TO START SERVER");
        }
        
        finally { listener.close(); }
    	
    }

}