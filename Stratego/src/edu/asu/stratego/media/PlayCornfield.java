package edu.asu.stratego.media;

import java.io.File;
import java.time.Duration;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class PlayCornfield implements Runnable {
	private Media cornfield = new Media(new File("src/edu/asu/stratego/media/sound/cornfield.mp3").toURI().toString());
	private MediaPlayer cornfieldPlayer = new MediaPlayer(cornfield);
	private Thread thread = new Thread(this);
	
	@Override
	public void run() {
		while(true)
			cornfieldPlayer.play();
	}
	
	public void start(){
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		thread.start();
	}

}