package client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import LogsConstructor.LoggerWithFileHandler;

/**
 * The Main program implements an application that start two class Client One
 * server Client and one simply Client with Client port listener from Client
 * server.
 * 
 * @author Quentin Beeckmans - Matthieu Roux
 * @version 1.0
 * @since 2020-05-30
 */
public class Main {

	public static void main(String[] args) {
		
		InetAddress localhost = null;
		try {
			localhost = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} 
 
		Scanner scan = new Scanner(System.in);
		System.out.println("Entry your ip( Your local address is: " +  localhost.getHostAddress()+").");
		String serverName = scan.next();
		
		LoggerWithFileHandler logsServer = new LoggerWithFileHandler("ClientLogs");
		
		try {
			Server server = new Server(logsServer);
			logsServer.addHandler(Main.class.getName(), Level.WARNING, "Initialization of listening socket", "");

			ClientSocket myClient = new ClientSocket(server.getPort(), logsServer, serverName);
			logsServer.addHandler(Main.class.getName(), Level.WARNING, "Client connexion enable", "");
			
			
		} catch (IllegalArgumentException e) {

			logsServer.addHandler(Main.class.getName(), Level.SEVERE,
					"Initialisation of client services (client and/or server) crashed", e.toString());
		}
		
		logsServer.closeHandler();
				
	}

}
