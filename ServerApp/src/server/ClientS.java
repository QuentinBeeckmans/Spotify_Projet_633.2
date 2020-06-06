package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;

import LogsConstructor.LoggerWithFileHandler;

/**
 * This Runnable Class implements ClientS Create client on server Share server
 * list with client Receive client list and add it into server list
 * 
 * @author Quentin Beeckmans - Mathieu Roux
 * @version 1.0
 * @since 2020-05-30
 */
public class ClientS implements Runnable {

	private Socket clientSocket;
	private int clientId;
	private LoggerWithFileHandler logsServer;

	private ArrayList<String> clientList = new ArrayList<String>();

	private ObjectOutputStream send;
	private ObjectInputStream reader;

	/**
	 * Class contructor
	 * 
	 * @param clientId     to increment the id of Client connected
	 * @param clientSocket
	 * @param logsServer
	 */
	public ClientS(int clientId, Socket clientSocket, LoggerWithFileHandler logsServer) {
		this.clientSocket = clientSocket;
		this.clientId = clientId;
		this.logsServer = logsServer;
		logsServer.addHandler(Server.class.getName(), Level.WARNING, "Client " + clientId + " connected", "");

	}

	/**
	 * This runnable method invoque readList method to add at Hasmap a global list
	 */
	@Override
	public void run() {
		try {
			System.out.println("Client n° " + clientId + " IP" + clientSocket.getInetAddress());

			send = new ObjectOutputStream(clientSocket.getOutputStream());

			readList();
			logsServer.addHandler(ClientS.class.getName(), Level.INFO, "Communication with client : OK",
					"Lists exchanged");

		} catch (Exception e) {
			logsServer.addHandler(ClientS.class.getName(), Level.SEVERE, "Communication with client : KO",
					e.toString());
			e.printStackTrace();
		}

	}

	/**
	 * This method allows to get an arrayList from socket InputStream and add it
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	synchronized public void readList() {

		try {
			reader = new ObjectInputStream(clientSocket.getInputStream());
			@SuppressWarnings("unchecked")
			ArrayList<String> arrayList = (ArrayList<String>) reader.readObject();
			clientList = arrayList;
		} catch (ClassNotFoundException | IOException e) {
			logsServer.addHandler(ClientS.class.getName(), Level.SEVERE, "Impossible to read list", e.toString());
			e.printStackTrace();
		}
		for (String item : clientList) {
			System.out.println(item);
		}
		addToServer(clientId, clientList);
	}

	/**
	 * Synchronized method add client list into server list
	 * 
	 * @param index             is key of hashmap
	 * @param ArrayList<String> list
	 */
	synchronized public void addToServer(int index, ArrayList<String> list) {
		Server.serverList.put(index, list);
		logsServer.addHandler(ClientS.class.getName(), Level.INFO, "Music list added on server music list", "");

		shareGlobalList(index);
	}

	/**
	 * This method share clients lists audio at everyone except the sender
	 * 
	 * @param index key
	 */
	private void shareGlobalList(int index) {
		Set<Integer> clients = Server.serverList.keySet(); // To get all key existing in hasmap
		int cpt = clients.size();
		ArrayList<String> lisToSend = new ArrayList<String>();

		for (Integer key : clients) {

			if (key != index) {

				lisToSend.addAll(Server.serverList.get(key));
			} else {
				if (cpt == 1) {
					lisToSend = null;
				}
			}
		}
		sendObject(lisToSend);

		logsServer.addHandler(ClientS.class.getName(), Level.INFO, "Music list shared with client", "");
	}

	/**
	 * Private void method shareGlobalList Share client list on server list with
	 * client applicant
	 * 
	 * @param ArrayList<String> list
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
