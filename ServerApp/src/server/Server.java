package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import LogsConstructor.LoggerWithFileHandler;


/**
 * This class allows Client to become a server and wait
 * @author Quentin Beeckmans - Mathieu Roux
 * @version 1.0
 * @since 2020-05-30
 */
public class Server {
	
	private int port;
    private LoggerWithFileHandler logsServer;

    private ServerSocket serverSocket;
	private int clientId = 0;
	public static HashMap<Integer, ArrayList<String>> serverList = new HashMap<Integer, ArrayList<String>>() ;
	
	 private final static Logger ServerLogger = Logger.getLogger("ServerLog");
	
	 /**
	  * Class Server constructor 
	  * @param int listening port
	  * @param LoggerWithFileHandler
	  */
	public Server(int port, LoggerWithFileHandler logsServer) {
		this.port=port;
	 	this.logsServer = logsServer;

	 	System.out.println("Serveur ok. En attente de connection...");
		listenSocket();

		logsServer.addHandler(Server.class.getName(), Level.WARNING, "Server turned on", "");
	}

	/**
	  * Public void listenSocket method
	  * Enable a listening socket for client
	  */
	public void listenSocket() {
		try {
			serverSocket = new ServerSocket(port);

			logsServer.addHandler(Server.class.getName(), Level.WARNING, "Listen socket created", "");
		} catch (Exception e) {
	   	 	logsServer.addHandler(Server.class.getName(), Level.SEVERE, "Listen socket crashed", e.toString());
			e.printStackTrace();
		}
		
		while(true) {
			Socket clientSocket = null;
			
			try {
				clientSocket = serverSocket.accept();
		   	 	logsServer.addHandler(Server.class.getName(), Level.WARNING, "Client listen socket turned on","");
			} catch (Exception e) {
		   	 	logsServer.addHandler(Server.class.getName(), Level.SEVERE, "Listen socket crashed after connection", e.toString());
				e.printStackTrace();
			}
			System.out.println("Connection reçue");
			Thread t = new Thread(new ClientS(clientId, clientSocket, logsServer));
			clientId++;
			t.start();
	   	 	//logsServer.addHandler(Server.class.getName(), Level.WARNING, "Client " + clientId + " connected", "");
		}	
	}
}
