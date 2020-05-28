import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;

public class ClientS implements Runnable {
	
	private Socket clientSocket;
	private String clientId;
	
	private ArrayList<String> clientList = new ArrayList<String>() ;
	private HashMap<String, ArrayList<String>> serverList = new HashMap<String, ArrayList<String>>() ;
	
	private ObjectOutputStream send;
	private ObjectInputStream reader;
	
	
	private String address; //ip
	private int port;
	
	private String reponse;
	
	public ClientS(int clientId, Socket clientSocket) {
		this.clientSocket=clientSocket;
		this.clientId=Integer.toString(clientId);
	}

	@Override
	public void run() {
		try {
			System.out.println("Client n° " + clientId + " IP" + clientSocket.getInetAddress());
			
			reader = new ObjectInputStream(clientSocket.getInputStream());
			send = new ObjectOutputStream(clientSocket.getOutputStream());

			readList();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	public void readList () {
		   
		try {
			clientList = (ArrayList<String>) reader.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	
		addToServer(clientId, clientList);
	}
		   
	
	 synchronized private void addToServer(String index,ArrayList<String> list) {
		serverList.put(index, list);
		   
		shareGlobalList(index);
	}

	 private void shareGlobalList(String index) {
		Set<String> clients = serverList.keySet(); //on récupère les key de chaque champs du Hashmap
		ArrayList<String> lisToSend;
		
		for(String key: clients){
		System.out.println(key + " - " + index);
			if(key.equals(index)) {
				System.out.println("J'ai trouvé un client");
				lisToSend = serverList.get(key);
				sendObject(lisToSend);
				System.out.println("Ca se passe ici");
				for(String item:lisToSend) {
					System.out.println(item);
				}
			}
		}
	}
	
	private void sendObject(ArrayList<String> list) {
		try {
			send.writeObject(list);
			send.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}





