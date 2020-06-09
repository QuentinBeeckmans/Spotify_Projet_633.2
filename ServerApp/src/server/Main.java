package server;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is a simulation of Server Application Streaming Audio which send musics
 * lists to clients
 * 
 * @author Quentin Beeckmans - Matthieu Roux
 * @version 1.0
 * @since 2020-05-30
 *
 */
public class Main {
	public final static Logger ServerLogger = Logger.getLogger("ServerLog");

	public static void main(String[] args) {

		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy_MM");
		String date = ft.format(dNow);

		try {
			FileHandler fh = new FileHandler("./Server" + date + ".log", true);
			CustomFormatter SktFormatter = new CustomFormatter();
			fh.setFormatter(SktFormatter);
			ServerLogger.addHandler(fh);

			Main.ServerLogger.setLevel(Level.INFO);
			Main.ServerLogger.info("*********** program starts ***********");

			new Server(5000);

		} catch (IllegalArgumentException | SecurityException | IOException e) {
			ServerLogger.setLevel(Level.SEVERE);
			ServerLogger.severe("Initialisation of Server services crashed: " + e.toString());
			e.printStackTrace();
		}

		ServerLogger.setLevel(Level.INFO);
		ServerLogger.info("*********** program close ***********");
	}
}
