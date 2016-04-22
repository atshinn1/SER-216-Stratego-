package edu.asu.stratego.media;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Scream implements Runnable{
	private Thread thread = new Thread(this);
	Media wilhelm = new Media(new File("src/edu/asu/stratego/media/sound/Wilhelm_Scream.mp3").toURI().toString());
	MediaPlayer scream = new MediaPlayer(wilhelm);
	boolean playing;
	@Override
	public void run() {
		playing = true;
		scream.play();
		playing = false;
	}
	
	public void start(){
		thread.start();
	}

	public boolean isPlaying(){
		return playing;
	}
}
