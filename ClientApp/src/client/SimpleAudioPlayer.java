package client;

import javax.sound.sampled.*;

import LogsConstructor.LoggerWithFileHandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

/**
 * This class initialize AudioPlayer
 * @author Quentin Beeckmans - Mathieu Roux
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
     * @param is
     * @param logsServer
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    public SimpleAudioPlayer(InputStream is, LoggerWithFileHandler logsServer)
            throws UnsupportedAudioFileException,
            IOException, LineUnavailableException
    {
    	
		this.logsServer = logsServer;

        // create AudioInputStream object
        audioInputStream = AudioSystem.getAudioInputStream(is);
        //AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());

        // create clip reference
        clip = AudioSystem.getClip();

        // open audioInputStream to the clip
        clip.open(audioInputStream);

        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void play()
    {
        clip.start();

        status = "play";

    	logsServer.addHandler(SimpleAudioPlayer.class.getName(), Level.INFO, "Streaming go on", "");
    }

    // Method to pause the audio
    public void pause()
    {
        if (status.equals("paused"))
        {
            System.out.println("audio is already paused");
            return;
        }
        this.currentFrame = this.clip.getMicrosecondPosition();
        clip.stop();
        status = "paused";
    }

    // Method to reset audio stream
    public void resetAudioStream() throws UnsupportedAudioFileException, IOException,
            LineUnavailableException
    {
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        
    	logsServer.addHandler(SimpleAudioPlayer.class.getName(), Level.INFO, "Stream reset", "");
    }
}
