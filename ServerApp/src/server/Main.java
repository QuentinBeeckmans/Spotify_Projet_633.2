package server;

import java.util.logging.Level;

import LogsConstructor.LoggerWithFileHandler;

/**
 * Main to run the server
 * 
 * @author Administrator
 */

//Cr�er en static LoggerWithFileHandler logsServer = new LoggerWithFileHandler("ServerLogs"); g�rer function pour cr�er tous les mois un
public class Main {

	public static void main(String[] args) {

		LoggerWithFileHandler logsServer = new LoggerWithFileHandler("ServerLogs");

		try {
			Server server = new Server(5000, logsServer);
		} catch (IllegalArgumentException e) {
			logsServer.addHandler(Main.class.getName(), Level.SEVERE, "Initialisation of Server services crashed",
					e.toString());
		}
	}
}
