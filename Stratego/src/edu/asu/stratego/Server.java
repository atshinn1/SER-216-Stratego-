package edu.asu.stratego;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import edu.asu.stratego.game.ServerGameManager;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The Stratego Server creates a socket and listens for connections from every 
 * two players to form a game session. Each session is handled by a thread, 
 * ServerGameManager, that communicates with the two players and determines the 
 * status of the game.
 */
public class Server extends Application {
   //private String hostAddres;
   private Label hostIP,player1,player2,waiting;
   private String hostAddress;

    @Override
    public void start (Stage primaryStage){
        primaryStage.setTitle("Stratego Server");
        primaryStage.setHeight(300.0);
        primaryStage.setWidth(600.0);
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        
        VBox labelBox = new VBox();
        labelBox.setPadding(new Insets(10, 10, 10, 10));
        labelBox.setSpacing(15);
        
        hostIP = new Label();
        player1 = new Label();
        player2 = new Label();
        waiting = new Label();
        
        
        labelBox.getChildren().addAll(hostIP,waiting,player1,player2);
        
        
        StackPane root = new StackPane();
 
        root.getChildren().add(labelBox);
        root.setMaxHeight(300.0);
        root.setMaxWidth(500.0);      
        
        
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
        
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        ServerSocket listener = null;
        int sessionNumber     = 1;
        
        try {
            listener = new ServerSocket(4212);
            hostIP.setText("Server started @ " + hostAddress);
            waiting.setText("Waiting for incoming connections...");
            
            while (true) {
                Socket playerOne = listener.accept();
                player1.setText("Session " + sessionNumber + 
                                   ": Player 1 has joined the session");
                
                Socket playerTwo = listener.accept();
                player2.setText("Session " + sessionNumber + 
                                   ": Player 2 has joined the session");
                
                Thread session = new Thread(new ServerGameManager(
                        playerOne, playerTwo, sessionNumber++));
                session.setDaemon(true);
                session.start();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        finally { try {
            listener.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } }
        
        
    }
    
    
    
    public static void main(String[] args) throws IOException {
        Application.launch(args);
        /*String hostAddress    = InetAddress.getLocalHost().getHostAddress();
        ServerSocket listener = null;
        int sessionNumber     = 1;
        
        try {
            listener = new ServerSocket(4212);
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
                session.setDaemon(true);
                session.start();
            }
        }
        
        finally { listener.close(); }*/
    }
}