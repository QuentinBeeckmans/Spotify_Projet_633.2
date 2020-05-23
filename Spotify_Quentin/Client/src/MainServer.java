
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MainServer {
	private int port;
	private ServerSocket serverSocket;
	private int clientId = 0;
	
	private ArrayList <String> clientList = new ArrayList<String>();
	
	//static ArrayList <String> globalList = new ArrayList<String>();
	
	public MainServer(int port) {
		this.port=port;
		System.out.println("Serveur ok. En attente de connection...");
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
			System.out.println("Connection re�ue");
			Thread t = new Thread(new Client(clientSocket, /*dossier musique*/));
			t.start();
		}
		
	}
	
	
	
}
