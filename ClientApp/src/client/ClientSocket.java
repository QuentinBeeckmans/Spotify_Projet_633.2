package client;

import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import LogsConstructor.LoggerWithFileHandler;

/**
 * This class implements Client Socket.
 * 
 * @author Quentin Beeckmans - Mathieu Roux
 * @version 1.0
 * @since 2020-05-30
 */
public class ClientSocket {

	private Socket clientSocket;
	private LoggerWithFileHandler logsServer;

	private InetAddress serverAddress;
	//private String serverName = "192.168.56.1";
	private String serverName;
	private MyList mList = new MyList();

	private int listenerPort;

	/**
	 * Client socket creating
	 * 
	 * @param port listener
	 */
	public ClientSocket(int port, LoggerWithFileHandler logsServer, String serverName) {
		this.listenerPort = port;
		this.logsServer = logsServer;
		this.serverName=serverName;
		exchangeSocket();
	}

	/**
	 * Public void exchangeSocket method Activate client socket
	 */
	public void exchangeSocket() {
		System.out.println(serverName);
		try {
			serverAddress = InetAddress.getByName(serverName);
			clientSocket = new Socket(serverAddress, 5000);

			logsServer.addHandler(ClientSocket.class.getName(), Level.WARNING,
					"Socket de connexion du client vers le serveur connecté", "");
		} catch (Exception e) {
			logsServer.addHandler(ClientSocket.class.getName(), Level.SEVERE,
					"Echec socket de connexion du client vers le serveur déconnecté", e.toString());
			e.printStackTrace();
		}

		Thread t = new Thread(new Dialogue(clientSocket, listenerPort, mList, logsServer));
		t.start();
		if (clientSocket.isClosed()) {
			logsServer.addHandler(ClientSocket.class.getName(), Level.WARNING,
					"Socket de connexion du client vers le serveur déconnecté", "");
		}
	}
}