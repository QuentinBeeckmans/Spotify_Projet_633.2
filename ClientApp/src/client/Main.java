package client;

import java.util.logging.Level;

import LogsConstructor.LoggerWithFileHandler;


/**
 * The Main program implements an application that start two class Client
 * one simply Client with Client server port and one server Client.
 * 
 * @author Quentin Beeckmans - Mathieu Roux
 * @version 1.0
 * @since 2020-05-30
 */
public class Main {

	public static void main(String[] args) {

		LoggerWithFileHandler logsServer = new LoggerWithFileHandler("logsServer");

		try {
			Server server = new Server(logsServer);
			logsServer.addHandler(Main.class.getName(), Level.WARNING, "Initialization of listening socket", "");
		
			ClientSocket myClient = new ClientSocket(server.getPort(), logsServer);
			logsServer.addHandler(Main.class.getName(), Level.WARNING, "Client connexion enable", "");
		}
		catch (IllegalArgumentException e) {
		
			logsServer.addHandler(Main.class.getName(), Level.SEVERE, "Initialisation of client services (client and/or server) crashed", e.toString());
		}
	}

}
