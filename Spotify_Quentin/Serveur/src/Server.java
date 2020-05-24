import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;

public class Server {
	private InetAddress localAddress = null;
	private int port;
	private ServerSocket serverSocket;
	private String interfaceName = "eth4";
	private int clientId = 0;
	
	private ArrayList <String> clientList = new ArrayList<String>();
	
	//static ArrayList <String> globalList = new ArrayList<String>();
	
	public Server(int port) {
		this.port=port;
		System.out.println("Serveur ok. En attente de connection...");
	}

	public void listenSocket() {
		try {
			NetworkInterface ni = NetworkInterface.getByName(interfaceName);
	        Enumeration<InetAddress> inetAddresses =  ni.getInetAddresses();
			while(inetAddresses.hasMoreElements()) {
	            InetAddress ia = inetAddresses.nextElement();
	            
	            if(!ia.isLinkLocalAddress()) {
	               if(!ia.isLoopbackAddress()) {
	            	   System.out.println(ni.getName() + "->IP: " + ia.getHostAddress());
	            	   localAddress = ia;
	               }
	            }   
            }
			serverSocket = new ServerSocket(port, 10, localAddress);
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
			Thread t = new Thread(new Client(clientSocket,clientId, clientList));
			clientId++;
			t.start();
		}
		
	}
	
	
	
}
