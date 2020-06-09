package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

/**
 * This class allows Client to become a server and wait
 * 
 * @author Quentin Beeckmans - Matthieu Roux
 * @version 1.0
 * @since 2020-05-30
 */
public class Server {

	private int port;

	private ServerSocket serverSocket;
	private int clientId = 0;
	public static HashMap<Integer, ArrayList<String>> serverList = new HashMap<Integer, ArrayList<String>>();

	/**
	 * Class Server constructor specifying number of port
	 * 
	 * @param port the listening port
	 */
	public Server(int port) {
		this.port = port;

		System.out.println("Server enable. \nWaiting connexion...");

		listenSocket();
	}

	/**
	 * Public void listenSocket method Enable a listening socket for client
	 */
	public void listenSocket() {

		try {

			serverSocket = new ServerSocket(port);

			Main.ServerLogger.setLevel(Level.WARNING);
			Main.ServerLogger.warning("Listen socket created");

		} catch (Exception e) {
			Main.ServerLogger.setLevel(Level.SEVERE);
			Main.ServerLogger.severe("Listen socket crashed: " + e.toString());
			e.printStackTrace();
		}

		while (true) {
			Socket clientSocket = null;

			try {
				clientSocket = serverSocket.accept();

				Main.ServerLogger.setLevel(Level.WARNING);
				Main.ServerLogger.warning("Client listen socket turned on.");

			} catch (Exception e) {
				Main.ServerLogger.setLevel(Level.SEVERE);
				Main.ServerLogger.severe("Listen socket crashed after connection: " + e.toString());
				e.printStackTrace();
			}
			System.out.println("Received connection.");
			Thread t = new Thread(new ClientS(clientId, clientSocket));
			clientId++;
			t.start();
		}
	}
}
