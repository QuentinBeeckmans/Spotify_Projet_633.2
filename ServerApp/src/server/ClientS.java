package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;

/**
 * This Runnable Class implements ClientS Create client on server Share server
 * list with client Receive client list and add it into server list
 * 
 * @author Quentin Beeckmans - Matthieu Roux
 * @version 1.0
 * @since 2020-05-30
 */
public class ClientS implements Runnable {

	private Socket clientSocket;
	private int clientId;

	private ArrayList<String> clientList = new ArrayList<String>();

	private ObjectOutputStream send;
	private ObjectInputStream reader;

	/**
	 * That create an object Client server side
	 * 
	 * @param clientId     to increment the id of Client connected
	 * @param clientSocket the socket from client
	 */
	public ClientS(int clientId, Socket clientSocket) {
		this.clientSocket = clientSocket;
		this.clientId = clientId;
	}

	/**
	 * This runnable method invoke readList method to add at Hashmap a global list
	 */
	@Override
	public void run() {
		try {
			System.out.println("Client n\u00B0 " + clientId + " IP" + clientSocket.getInetAddress().toString());

			Main.ServerLogger.setLevel(Level.INFO);
			Main.ServerLogger.info("Client n° " + clientId + " IP" + clientSocket.getInetAddress().toString());

			send = new ObjectOutputStream(clientSocket.getOutputStream());

			readList();

			Main.ServerLogger.setLevel(Level.INFO);
			Main.ServerLogger.info("Communication with client : OK. Lists exchanged");

		} catch (Exception e) {
			Main.ServerLogger.setLevel(Level.SEVERE);
			Main.ServerLogger.severe("Communication with client : KO: " + e.toString());
			e.printStackTrace();
		}

	}

	/**
	 * This method allows to get an arrayList from socket InputStream and add it
	 */
	synchronized public void readList() {

		try {

			reader = new ObjectInputStream(clientSocket.getInputStream());
			@SuppressWarnings("unchecked")
			ArrayList<String> arrayList = (ArrayList<String>) reader.readObject();
			clientList = arrayList;

		} catch (ClassNotFoundException | IOException e) {
			Main.ServerLogger.setLevel(Level.SEVERE);
			Main.ServerLogger.severe("Impossible to read list" + e.toString());
			e.printStackTrace();
		}

		System.out.println("List from Client:");
		for (String item : clientList) {
			System.out.println(item);
		}

		addToServer(clientId, clientList);
	}

	/**
	 * Synchronized method add client list into server list
	 * 
	 * @param index is key of hashmap
	 * @param list  the arrayList added to global list server
	 */
	synchronized public void addToServer(int index, ArrayList<String> list) {
		Server.serverList.put(index, list);

		Main.ServerLogger.setLevel(Level.INFO);
		Main.ServerLogger.info("Music list added on server music list.");

		shareGlobalList(index);
	}

	/**
	 * This method share clients lists audio at everyone except the sender
	 * 
	 * @param index key
	 */
	private void shareGlobalList(int index) {
		Set<Integer> clients = Server.serverList.keySet(); // To get all key existing in hashmap
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

		Main.ServerLogger.setLevel(Level.INFO);
		Main.ServerLogger.info("Music list shared with client.");
	}

	/**
	 * Private void method shareGlobalList Share client list on server list with
	 * client applicant
	 * 
	 * @param list the ArrayList to send
	 */
	private void sendObject(ArrayList<String> list) {
		try {
			send.writeObject(list);
			send.flush();
		} catch (IOException e) {
			Main.ServerLogger.setLevel(Level.SEVERE);
			Main.ServerLogger.severe("Music list sharing crashed: " + e.toString());
			e.printStackTrace();
		}
	}
}
