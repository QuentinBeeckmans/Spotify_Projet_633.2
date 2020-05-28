import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

public class Server {
	
	private int port;
	private ServerSocket serverSocket;
	private int clientId = 0;
	public static HashMap<Integer, ArrayList<String>> serverList = new HashMap<Integer, ArrayList<String>>() ;
	
	public Server(int port) {
		this.port=port;
		System.out.println("Serveur ok. En attente de connection...");
		listenSocket();
	}

	public void listenSocket() {
		try {
			serverSocket = new ServerSocket(port);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		while(true) {
			Socket clientSocket = null;
			
			try {
				clientSocket = serverSocket.accept();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Connection reçue");
			Thread t = new Thread(new ClientS(clientId, clientSocket));
			clientId++;
			t.start();
		}	
	}
	

}
