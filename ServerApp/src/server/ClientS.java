package server;

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
import java.util.logging.Level;

import LogsConstructor.LoggerWithFileHandler;

/**
 * This Runnable Class implements ClientS
 * Create client on server
 * Share server list with client
 * Receive client list and add it into server list
 * @author Quentin Beeckmans - Mathieu Roux
 * @version 1.0
 * @since 2020-05-30
 */
public class ClientS implements Runnable {
	
	private Socket clientSocket;
	private int clientId;
    private LoggerWithFileHandler logsServer;

	private ArrayList<String> clientList = new ArrayList<String>() ;
		
	private ObjectOutputStream send;
	private ObjectInputStream reader;
	
	/**
	 * Class contructor
	 * @param clientId
	 * @param clientSocket
	 * @param logsServer
	 * @author Quentin Beeckmans - Mathieu Roux
	 * @version 1.0
	 * @since 2020-05-30
	 */
	public ClientS(int clientId, Socket clientSocket, LoggerWithFileHandler logsServer) {
		this.clientSocket=clientSocket;
		this.clientId=clientId;
	 	this.logsServer = logsServer;

	}

	@Override
	public void run() {
		try {
			System.out.println("Client n° " + clientId + " IP" + clientSocket.getInetAddress());

			send = new ObjectOutputStream(clientSocket.getOutputStream());

			readList();
       	 	logsServer.addHandler(ClientS.class.getName(), Level.INFO, "Communication with client : OK","Lists exchanged");

		} catch (Exception e) {
       	 	logsServer.addHandler(ClientS.class.getName(), Level.SEVERE, "Communication with client : KO",e.toString());
			e.printStackTrace();
		}

	}
	
	/**
	 * Synchronized public void method readList
	 * Receive client list
	 * @author Quentin Beeckmans - Mathieu Roux
	 * @version 1.0
	 * @since 2020-05-30
	 */
	synchronized public void readList () {
		
		try {
			reader = new ObjectInputStream(clientSocket.getInputStream());
			ArrayList<String> arrayList = (ArrayList<String>) reader.readObject();
			clientList = arrayList;
		} catch (ClassNotFoundException | IOException e) {
       	 	logsServer.addHandler(ClientS.class.getName(), Level.SEVERE, "Impossible to read list",e.toString());
			e.printStackTrace();
		}
		for(String item:clientList) {
			System.out.println(item);
		}
		addToServer(clientId, clientList);
	}
		   
	/**
	 * Synchronized public void method addToServer
	 * Add client list into server list
	 * @param index in HasTable
	 * @param ArrayList<String> list 
	 * @author Quentin Beeckmans - Mathieu Roux
	 * @version 1.0
	 * @since 2020-05-30
	 */
	 synchronized public void addToServer(int index,ArrayList<String> list) {
		System.out.println("AVANT PUT " + Server.serverList.keySet());
		Server.serverList.put(index, list);
		System.out.println("APRES PUT " + Server.serverList.keySet());
		System.out.println("Mon index client passé " + index);

		logsServer.addHandler(ClientS.class.getName(), Level.INFO, "Music list added on server music list","");
		   
		shareGlobalList(index);
	}

	 /**
	  * Private void shareGlobalList method of server
	  * Search client list on server list for client applicant
	  * @param index in HasTable
	  * @author Quentin Beeckmans - Mathieu Roux
	  * @version 1.0
	  * @since 2020-05-30
	  */
	 private void shareGlobalList(int index) {
		Set<Integer> clients = Server.serverList.keySet(); //on rï¿½cupï¿½re les key de chaque champs du Hashmap
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

		logsServer.addHandler(ClientS.class.getName(), Level.INFO, "Music list shared with client","");
	}
	
	 /**
	  * Private void method shareGlobalList
	  * Share client list on server list with client applicant
	  * @param ArrayList<String> list 
	  * @author Quentin Beeckmans - Mathieu Roux
	  * @version 1.0
	  * @since 2020-05-30
	  */
	 private void sendObject(ArrayList<String> list) {
		try {
			send.writeObject(list);
			send.flush();
		} catch (IOException e) {
	   	 	logsServer.addHandler(ClientS.class.getName(), Level.SEVERE, "Music list sharing crashed", e.toString());
			e.printStackTrace();
		}
	}
}





