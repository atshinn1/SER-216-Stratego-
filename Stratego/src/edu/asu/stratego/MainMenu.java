package edu.asu.stratego;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import edu.asu.stratego.game.ClientGameManager;
import edu.asu.stratego.game.Game;
import edu.asu.stratego.game.ServerGameManager;
import edu.asu.stratego.gui.ClientStage;
import edu.asu.stratego.media.ImageConstants;
import edu.asu.stratego.media.PlayCornfield;
import edu.asu.stratego.media.PlaySound;
import edu.asu.stratego.media.Scream;


 
public class MainMenu extends Application {
	private final double BUTTON_WIDTH = 200.0;
	private final double BUTTON_HEIGHT = 50.0;
	
    public static void main(String[] args) {
        Application.launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	//new PlaySound().playCornfield();
    	
    	VBox buttonBox = new VBox();
    	buttonBox.setPadding(new Insets(130, 50, 50, 50));
        buttonBox.setSpacing(30);
        
    	primaryStage.setTitle("Stratego Main Menu");
        primaryStage.setHeight(768.0);
        primaryStage.setWidth(1024.0);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.centerOnScreen();
    	
        Button client = new Button();
        Button server = new Button();
        Button rules = new Button();
        Button exit = new Button();
        Text serverStatus = new Text("SERVER IS OFFLINE");
        serverStatus.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD, 20));
        serverStatus.setFill(Color.WHITE);
        serverStatus.setWrappingWidth(225);
                
        server.setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        client.setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        rules.setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        exit.setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        exit.setCancelButton(true);
        
        rules.setStyle("-fx-background-image: url(edu/asu/stratego/media/images/board/rulesbutton.png)");
        server.setStyle("-fx-background-image: url(edu/asu/stratego/media/images/board/serverbutton.png)");
        client.setStyle("-fx-background-image: url(edu/asu/stratego/media/images/board/clientbutton.png)");
        exit.setStyle("-fx-background-image: url(edu/asu/stratego/media/images/board/exitbutton.png)");
              
        buttonBox.getChildren().addAll(client,server,rules,exit, serverStatus);
        buttonBox.setAlignment(Pos.CENTER);        
        
        //server button listener
        server.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent event){
        		String message = "";
        		Server server;
        		
        		
        		try{
        			server = new Server();
        			Thread.sleep(1000);
        			message = server.getMessage();
        			System.out.println("Message is" + message);
        			
        		}catch(Exception e){
        			System.out.println("Server Failed.");
        			message = "Server Failed to Start for some reason.";
        			e.printStackTrace();
        		}
        		
        		finally{
        			serverStatus.setText(message);

                    
                    System.out.println("here");
        		 
        		}
        	}});
        
        //client button listener
        client.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
            	// (MODEL) Start a new game.
                new Game();
                
                // (VIEW) Display client GUI on the JavaFX Application thread.
                ClientStage client = new ClientStage();
                
                // (CONTROLLER) Control the game on a separate thread.
                Thread manager = new Thread(new ClientGameManager(client));
                manager.setDaemon(true);
                manager.start();
            }
        });

        //TODO implement how to play screen
        rules.setOnAction(new EventHandler<ActionEvent>() {
        	 
            @Override
            public void handle(ActionEvent event) {
            	// (MODEL) Start a new game.
            	StackPane root = new StackPane();
            	//root.getChildren(
                Stage rulesMenu = new Stage();
                rulesMenu.initStyle(StageStyle.DECORATED);
                rulesMenu.setTitle("Rules");
                rulesMenu.setScene(new Scene(root, 800, 600));
                root.setStyle("-fx-background-image: url(edu/asu/stratego/media/images/board/rulesBackground2.png)");
                
                
                Text rulesText = new Text("Stratego is a game in which you need to capture the flag of your opponent while defending your own flag.\nTo capture the flag you use your army of 40 pieces.\nPieces have a rank and represent individual officers and soldiers in an army. In addition to those ranked pieces you can use bombs to protect your flag.\n\nPieces move 1 square per turn, horizontally or vertically. Only the scout can move over multiple empty squares per turn. Pieces cannot jump over another piece.\n\nIf a piece is moved onto a square occupied by an opposing piece, their identities are revealed. The weaker piece is removed from the board, and the stronger\npiece is moved into the place formerly occupied by the weaker piece. If the engaging pieces are of equal rank, they are both removed. Pieces may not move onto\na square already occupied by another piece without attacking. Exception to the rule of the higher rank winning is the spy. When the spy attacks the marshal, the\nspy defeats the higher ranked marshal. However, when the marshal attacks the spy, the spy loses. Bombs lose when they are defused by a miner.\n\nThe bombs and the flag cannot be moved. A bomb defeats every piece that tries to attack it, except the miner. The flag loses from every other piece. When you\ncapture the flag of your opponent you win the game.\n\nThe Stratego board consists of 10 x 10 squares. Within the board there are two obstacles of 2 x 2 squares each. Pieces are not allowed to move there.\n\nFrom: http://www.stratego.com/play/stratego-rules/" );
                rulesText.setFont(Font.font("Tahoma", FontWeight.BOLD, 16));
                rulesText.setFill(Color.WHITE);
                rulesText.setWrappingWidth(750);

                // Create VBox container to place text into, which is then placed and aligned properly on the Scene. 
            	VBox textBox = new VBox();
            	textBox.prefWidth(200);
            	textBox.setPadding(new Insets(25, 0, 0, 25));
                textBox.getChildren().addAll(rulesText);
                root.getChildren().add(textBox);
                rulesMenu.setResizable(false);
                rulesMenu.show();
                System.out.println("here");

            }
        });
        
        
        //exit button listener
        exit.setOnAction(new EventHandler<ActionEvent>(){
        	@Override
        	public void handle(ActionEvent event){
        		primaryStage.close();
        		System.exit(0);
        	}
        });
        
        StackPane root = new StackPane();
        
        root.setStyle("-fx-background-image: url(edu/asu/stratego/media/images/board/Menu_Background.png)");
        root.getChildren().add(buttonBox);
        root.setMaxHeight(1024.0);
        root.setMaxWidth(768.0);      
        StackPane.setAlignment(buttonBox,Pos.CENTER);
        
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
        
        PlayCornfield cornfield = new PlayCornfield();
        cornfield.start();
        
    }
}