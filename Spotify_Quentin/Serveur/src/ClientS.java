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
import java.util.Hashtable;
import java.util.Set;

public class ClientS implements Runnable {
	private Socket clientSocket;
	private int clientId;
	
	private Hashtable <String, ArrayList> globalList = new Hashtable<String, ArrayList>();
	
	private ArrayList<String> clientList = new ArrayList<String>() ;
	private ArrayList<String> lisToSend;
	
	private BufferedReader reader;
	private PrintWriter send;
	
	
	private String address; //ip
	private int port;
	
	private String reponse;
	
	public ClientS(int clientId, Socket clientSocket, ArrayList<String> clientList) {
		this.clientSocket=clientSocket;
		this.clientId=clientId;
		this.clientList=clientList;
	}

	@Override
	public void run() {
		try {
			System.out.println("Client n° " + clientId + " IP" + clientSocket.getInetAddress());
			
			reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			send = new PrintWriter(clientSocket.getOutputStream());

			reading();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void reading() {
		while(true) {
			try {
				String line = readLine();
				addFilesClientToServer(line); 
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	


	public void addFilesClientToServer(String line) throws ClassNotFoundException, IOException {
			String [] temp = line.split("|");
			
			switch(temp[0]+temp[1]+temp[2]) {
			case "add":
				System.out.println("demande d'ajout d'un client de sa liste");
				 
				addingLinetoList(this, line);
				break;
			case "rem":
				System.out.println("demande de retirer d'un client de sa liste pour déconnection");
				remove(Integer.toString(clientId));
				break;
			
			default:
				System.out.println("Wrong message");
			}
			
	}
	private void addingLinetoList(ClientS clientS, String line2) {
		synchronized (clientS) {
			clientList.add(line2);
		}
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				put(clientS,Integer.toString(clientId), clientList); 
				
			}
		}).start();
		
		
	}

	public void put(ClientS clientS, String id, ArrayList<String> clientList2) {
		synchronized (clientS) {
			globalList.put(id, clientList2);
		}

		sendClientListtoOther(clientS);
		
	}
	
	synchronized public void sendClientListtoOther (ClientS c){
		Set<String> clients = globalList.keySet();

		for(String key: clients){
		System.out.println(key  +  Integer.toString(c.getClientId()));
			if(key != (Integer.toString(c.getClientId()))) {
				lisToSend = globalList.get(key);
				
			}
        }
		for(String temp:lisToSend) {
			//toSend(temp);
			System.out.println("ici probleme" + temp);
		}
		
		
		//serverLogger.info("Global list have been sending to client");
   
	}
	/**
	 * To remove all arrayList from globalList with the key parameter clientId
	 * @param key
	 */
	 public void remove(String key) {
		 globalList.remove(key);
	 }
	 
	public void toSend(String message) {
		send.println(message);
		send.flush();
	}
	 
	 private String readLine() throws IOException, ClassNotFoundException{
		 String line = null;
			while(true) {
				line = reader.readLine();
				
				if(line != null) {
					break;
				}
			}
			
			return line;
	}
	 
	public int getClientId() {
		return clientId;
	}
	
	public String getIpAddress() {
		return address;
	}
}





