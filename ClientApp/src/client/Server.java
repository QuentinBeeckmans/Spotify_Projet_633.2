package client;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;

import LogsConstructor.LoggerWithFileHandler;

/**
 * This class allows Client to become a server and wait a connection from others
 * clients
 * 
 * @author Quentin Beeckmans - Matthieu Roux
 * @version 1.0
 * @since 2020-05-30
 */
public class Server {

	private LoggerWithFileHandler logsServer;
	private ServerSocket serverSocket = null;
	private int port;

	/**
	 * Class contructor
	 * 
	 * @param logsServer
	 */
	public Server(LoggerWithFileHandler logsServer) {
		try {
			serverSocket = new ServerSocket(0);
			logsServer.addHandler(Server.class.getName(), Level.WARNING, "Server socket turned on", "");
		} catch (Exception e) {
			logsServer.addHandler(Server.class.getName(), Level.SEVERE, "Server socket crashed", e.toString());
			e.printStackTrace();
		}

		port = serverSocket.getLocalPort();
		listen();
	}

	/**
	 * This method returns port listening
	 */
	public int getPort() {
		return port;
	}

	/**
	 * This runnable method create a listener socket and run a new Client when it
	 * connects
	 */
	private void listen() {
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					Socket clientSocket = null;

					try {
						clientSocket = serverSocket.accept();
						System.out.println("Another client's stream request ...");

					} catch (Exception e) {
						logsServer.addHandler(Server.class.getName(), Level.SEVERE,
								"Listen socket crashed after connection", e.toString());
						e.printStackTrace();
					}

					// ceci est mon client 2 qui se connecte au client 1
					Thread t = new Thread(new ClientServer(clientSocket, logsServer));
					t.start();

				}
			}
		}).start();
	}
}
