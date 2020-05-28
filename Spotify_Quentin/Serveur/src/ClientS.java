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
	private int clientId;
	
	private ArrayList<String> clientList = new ArrayList<String>() ;
	
	
	private ObjectOutputStream send;
	private ObjectInputStream reader;
	
	public ClientS(int clientId, Socket clientSocket) {
		this.clientSocket=clientSocket;
		this.clientId=clientId;

	}

	@Override
	public void run() {
		try {
			System.out.println("Client n° " + clientId + " IP" + clientSocket.getInetAddress());

			send = new ObjectOutputStream(clientSocket.getOutputStream());

			readList();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	synchronized public void readList () {
		
		try {
			reader = new ObjectInputStream(clientSocket.getInputStream());
			ArrayList<String> arrayList = (ArrayList<String>) reader.readObject();
			clientList = arrayList;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		for(String item:clientList) {
			System.out.println(item);
		}
		addToServer(clientId, clientList);
	}
		   
	
	 synchronized public void addToServer(int index,ArrayList<String> list) {
		 System.out.println("AVANT PUT " + Server.serverList.keySet());
		Server.serverList.put(index, list);
		System.out.println("APRES PUT " + Server.serverList.keySet());
		   System.out.println("Mon index client passé " + index);
		   
		shareGlobalList(index);
	}

	 private void shareGlobalList(int index) {
		Set<Integer> clients = Server.serverList.keySet(); //on récupère les key de chaque champs du Hashmap
		ArrayList<String> lisToSend;
		
		
		for(Integer key: clients){
		System.out.println(key + " - " + index);
			if(key!=index) {
				System.out.println("J'ai trouvé un client");
				lisToSend = Server.serverList.get(key);
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





