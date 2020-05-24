package client2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Timer;

import server.AcceptClientD;

public class DialogueActionGUI {

	private static String[] listChoiceAction = { "get list", "CLOSE" };
	private boolean closeConnexion = false;
	private Socket socketOnServer;
	private PrintWriter writer = null;
	private BufferedInputStream reader = null;
	private OutputStream os = null;
	private InputStream is = null;
	private ObjectOutputStream writeObj = null;
	private ObjectInputStream readObj = null;
	private Data_OwnList onwnList;
	private ArrayList<String> serverList;
	private Socket mySocketTemp;
	private Socket echangeSocketTemp;

	private InetAddress serverAdress;
	private int port;

	private Connexion_read connexionRead;

	private File newFile;

	private GiveFichier sendFile;
	private ReadList readList;
//	private /* ThreadToTransf */ TransfertList listThread;

	public DialogueActionGUI(Socket socket, InetAddress serverAdress, int port) {

//		connexion = new Connexion1ToServer_AcrossThread();

		/*
		 * this.socketOnServer = connexion.getSocket(); String response = null ;
		 * 
		 * onwnList = new Data_OwnList(socketOnServer);
		 */

		this.serverAdress = serverAdress;
		this.port = port;

		while (true) {

			this.socketOnServer = socket;
			String response = null;

			onwnList = new Data_OwnList(socketOnServer);

			try {

				while (!socketOnServer.isClosed() /* || response != "CLOSE" */) {

//	        	SocketOnServer.setKeepAlive(true);

					
			//		 Socket echangeSwitchWrite = new Socket(serverAdress, 4515);
/*					  
					 ServerSocket servSockTemp = new ServerSocket(4501); 
					 Socket echangeSwitchRead = servSockTemp.accept();
*/
//					 os = echangeSwitchWrite.getOutputStream(); 
//					 is = echangeSwitchRead.getInputStream();
					 
					os = socketOnServer.getOutputStream();
//					is = socketOnServer.getInputStream();

					writer = new PrintWriter(os, true);

//					reader = new BufferedInputStream(is);

					newFile = File.createTempFile("listTempReçue", ".txt");

					Scanner scan = new Scanner(System.in);

					echangeSocketTemp = new Socket(serverAdress, 4505);
					Thread t = new Thread(
							sendFile = new GiveFichier(onwnList.listFichierAEchange(), echangeSocketTemp));
					t.start();
					sendFile.run();

					sendFile.sendFile();

					System.out.println("QUe voulez-vous faire ?");

					for (int i = 0; i < listChoiceAction.length; i++) {
						System.out.println((i + 1) + " : " + listChoiceAction[i]);
					}

					// On traite la demande du client en fonction de la commande envoyée
					// String command = getCommand();
					int choix = scan.nextInt();

					String choice = Integer.toString(choix);

					System.out.println("MON CHOIX" + choice);

					// On envoie la réponse au serveur
					writer.write(choice);

					writer.flush();

					System.out.println("Commade saisie : " + choice + "\t Envoyée au serveur");

					response = read();
					System.out.println("Réponse SERVEUR av Switch : " + response);

					switch (response) {

					case "1":
						System.out.println("J'ai bien choisi get music");
						System.out.println();

						ServerSocket servSock = new ServerSocket(45000);
						echangeSocketTemp.close();
	//					Socket socketTemp = servSock.accept();
						echangeSocketTemp = servSock.accept();
						
						Thread t1 = new Thread(readList = new ReadList(echangeSocketTemp));
						t1.start();
						readList.run();
						serverList = readList.readList();

						for (String item : serverList) {
							System.out.println("reçu par serveur " + item);
						}
						System.out.println();
						System.out.println();

						break;

					case "CLOSE":
						Thread.sleep(5000);

						writer.close();
						reader.close();
						readObj.close();
						writeObj.close();
						socketOnServer.isClosed();
						break;

					default:
						System.out.println("problème de réception serveur !");
						break;
					}

				}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {

				System.out.println("LA connexion est coupé suite à un arrêt de la connexion du CLIENT");
				try {
					socketOnServer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	// La méthode pour lire les réponses
	private String read() throws IOException {

		InputStream isRead = socketOnServer.getInputStream();
		reader = new BufferedInputStream(isRead);

		String response;
		int stream;

		byte[] b = new byte[4096];

		stream = reader.read(b);
		response = new String(b, 0, stream);

		// remise à zèro du buffer (sécurité peut-être pas obligatoire)
		b = new byte[0];

		return response;
	}
	/*
	 * public synchronized void readList () { ArrayList<String> affichList = new
	 * ArrayList<String>(); Thread t = new Thread();
	 * 
	 * try { t.start();
	 * 
	 * serverList = (ArrayList<String>) readObj.readObject();
	 * 
	 * readObj.reset(); // t.stop();
	 * 
	 * } catch (ClassNotFoundException | IOException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); }
	 * 
	 * if (!serverList.isEmpty()) { String itemAffich = "";
	 * 
	 * for (String item : serverList) { // itemAffich =
	 * item.substring(item.lastIndexOf("/"), item.indexOf(";"));
	 * affichList.add(item.substring(item.lastIndexOf("/"), item.indexOf(";")) ); }
	 * } Collections.sort(affichList);
	 * 
	 * System.out.println(); System.out.println("Liste des musiques en streaming");
	 * System.out.println();
	 * 
	 * for (String item : affichList) { System.out.println(item); }
	 * 
	 * }
	 * 
	 * /* private synchronized void sendList (){ Thread t = new Thread(); try {
	 * t.start();
	 * 
	 * writeObj.writeObject(onwnList.listFichierAEchange()); writeObj.flush();
	 * writeObj.reset();
	 * 
	 * } catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); }
	 * 
	 * }
	 */
}
