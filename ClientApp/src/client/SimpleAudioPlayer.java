package client;

import javax.sound.sampled.*;
import logsConstructor.LoggerWithFileHandler;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

/**
 * This class initialize AudioPlayer
 * 
 * @author Quentin Beeckmans - Matthieu Roux
 * @version 1.0
 * @since 2020-05-30
 */
public class SimpleAudioPlayer {

	private LoggerWithFileHandler logsServer;

	// to store current position
	Long currentFrame;
	Clip clip;

	// current status of clip
	String status;

	AudioInputStream audioInputStream;
	static String filePath;

	/**
	 * Constructor to initialize streams and clip
	 * 
	 * @param is         the InputStream create
	 * @param logsServer logger
	 * @throws UnsupportedAudioFileException if audio file type is not supported
	 * @throws IOException	if stream cannot be read.
	 * @throws LineUnavailableException if audioStream is unavailable 
	 */
	public SimpleAudioPlayer(InputStream is, LoggerWithFileHandler logsServer)
			throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		audioInputStream = AudioSystem.getAudioInputStream(is);
		this.logsServer = logsServer;

		clip = AudioSystem.getClip();
		clip.open(audioInputStream);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	/**
	 * This method start clip audio
	 */
	public void play() {
		clip.start();
		status = "play";
		logsServer.addHandler(SimpleAudioPlayer.class.getName(), Level.INFO, "Streaming go on", "");
	}

	/**
	 * This stop music
	 */
	public void pause() {
		if (status.equals("paused")) {
			System.out.println("audio is already paused");
			return;
		}
		this.currentFrame = this.clip.getMicrosecondPosition();
		clip.stop();
		status = "paused";
	}

	/**
	 * This close clip audio
	 */
	public void close() {
		clip.close();
	}
}
