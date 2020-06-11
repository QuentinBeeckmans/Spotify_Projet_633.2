package client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import logsConstructor.LoggerWithFileHandler;

/**
 * The Main program implements an application that start two class Client one
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
<<<<<<< HEAD
		
=======
		System.out.println(
				"Enter the server address:\nN.B. Your local address is: " + localhost.getHostAddress());
		String serverName = scan.next();
>>>>>>> c816b49c43ebdb984fcb5da055846d6890dafd00

		LoggerWithFileHandler logsServer = new LoggerWithFileHandler("ClientLog");
		
		Server server = null;
		ClientSocket myClient = null;
		boolean errorConnection = true;
		
		while(errorConnection) {
			System.out.println();
			System.out.println(
					"Enter the server address. \nN.B. Your local address is: " + localhost.getHostAddress() + ".");
			String serverName = scan.next();
			
		try {
			server = new Server(logsServer);
			logsServer.addHandler(Main.class.getName(), Level.WARNING, "Initialization of listening socket", "");

			myClient = new ClientSocket(server.getPort(), logsServer, serverName);
			logsServer.addHandler(Main.class.getName(), Level.WARNING, "Client connexion enable", "");
			
			errorConnection = false;

		} catch (Exception e) {
			System.out.println();
			System.out.println("Error in your server's address. \n"
					+ "Please, try again !");
						
			logsServer.addHandler(Main.class.getName(), Level.SEVERE,
					"Initialisation of client services (client and/or server) crashed", e.toString());			
			}
						
		}
		
		

		logsServer.closeHandler();

	}

}
