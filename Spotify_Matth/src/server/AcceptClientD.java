package server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
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
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Exchanger;

import client2.Connexion_Give;
import client2.Connexion_read;
import client2.TransmitList;
import client2.TransmitSwitch;
import client2.ReadList;
import client2.ReadSwitch;
/*
 import client1.ThreadToTransf;
import client1.TransfertList;
*/
import client2.SocketRead;
import client2.SocketTransmit;

/*
�	The client will be able to connect to the server through socket connections 
�	The client should be able to give its list of file to the server 
�	The client should be able to give its IP address 
�	The client should be able to get a list of clients with their available audio files 
�	The client should be able to ask for another client IP address 
�	The client should be able to connect to another client and ask to stream one file 
�	The client should be able to accept a network connection from another client and stream the selected file
�	The client should be able to play the audio stream  

 */
public class AcceptClientD implements Runnable {

	private static int portList = 4505;
	private static int portList2 = 4506;
	private static int portSwitch1 = 4510;
	private static int portSwitch2 = 4511;
	private static int portStream = 4550;
	private Socket clientSocketOnServer;
	private int clientNumber;
	private String response = null;
	private String choice = null;
	private ArrayList<String> serverList;
	private ArrayList<String> clientList = null;;
	private Socket socketTransmitSwitch;
	private Socket socketReadSwitch;
	private Socket socketTransmitList;
	private Socket socketReadList;
	private TransmitList sendFile;
	private ReadList readList;
	private ReadSwitch readSwitch;
	private TransmitSwitch transmitSwitch;
	private Connexion_read connexionRead;
	private File fileTemp;
	private File totalListFile;
	private InetAddress clientInetAddresss;
	private int port;

	public AcceptClientD(Socket clientSocketOnServer, int clientNo, int port) {
		this.clientSocketOnServer = clientSocketOnServer;
		this.clientNumber = clientNo;
		this.serverList = serverList;
		this.clientInetAddresss = clientSocketOnServer.getInetAddress();
		this.port = port;

	}

	public void run() {

		System.out.println("IP CLIENT connecté" + clientSocketOnServer.getInetAddress());
		System.out.println("Client Nr " + clientNumber + " is connected");
		System.out.println("Socket is available for connection" + clientSocketOnServer);

/*		boolean closeConnexion = false;

		// tant que la connexion est active, on traite les demandes
		while (!clientSocketOnServer.isClosed()) {

			try {

				SocketRead socketRead = new SocketRead(portList);
				socketReadList = socketRead.getSocket();

				while (!socketReadList.isClosed()) {

					Thread t = new Thread(readList = new ReadList(socketReadList));
					t.start();
					readList.run();
					clientList = readList.readList();

					while (clientList.isEmpty() && clientList.contains("EMPTY LIST")) {
						clientList = readList.readList();

					}

					readList.close();

				}
				fileTemp = readList.getTempListFile();

				/**********
				 * A retirer !! Juste pour nous pour savoir qu'on peut connaître et accéder au
				 * fichier temporaire
				 ***************/
//				System.out.println(fileTemp.getAbsolutePath());

				/***************************************************************************************************************/

/*				for (String item : clientList) {
					System.out.println("reçu par Client " + item);
				}

				SocketRead socketReadSwicth = new SocketRead(portSwitch1);
				socketReadSwitch = socketReadSwicth.getSocket();

//				while (!socketReadSwitch.isClosed()) {
					Thread tReadSwitch = new Thread(readSwitch = new ReadSwitch(socketReadSwitch));
					tReadSwitch.start();
					readSwitch.run();

					response = "";

					while (response == "") {

						response = readSwitch.readSwitch();

					}

//					readSwitch.close();

//				}

				// On traite la demande du client en fonction de la commande envoyée
				String toSend = "path music";

				switch (response) {
				case "1":
					response = "1";

					Socket socketTransmitSwitch = new SocketTransmit(clientInetAddresss, portSwitch2).getSocket();

//					while (!socketTransmitSwitch.isClosed()) {
						Thread tTransmitSwitch = new Thread(
								transmitSwitch = new TransmitSwitch(response, socketTransmitSwitch));
						tTransmitSwitch.start();

						transmitSwitch.run();

						transmitSwitch.sendSwitch();
*/
/*						if (!socketTransmitSwitch.getReuseAddress()) {

							transmitSwitch.close();

						}
*/
//					}

/*					totalListFile = fileTemp;

					Socket socketTransmitList2 = new SocketTransmit(clientInetAddresss, portList2).getSocket();

					while (!socketTransmitList2.isClosed()) {
						Thread t1 = new Thread(sendFile = new TransmitList(totalListFile, socketTransmitList2));
						t1.start();

						sendFile.run();

						sendFile.sendFile();

						if (!socketTransmitList2.getReuseAddress()) {
							t1.sleep(2000);
							sendFile.close();
						}

					}

					break;

				case "2": // "CLOSE"
					toSend = "CLOSE";
					System.out.println("La connexion va être arrêté");

					SocketRead socketReadSwicth2 = new SocketRead(portSwitch2);
					Socket socketReadSwitch = socketReadSwicth2.getSocket();

//					while (!socketReadSwitch.isClosed()) {
						Thread tReadSwitch2 = new Thread(readSwitch = new ReadSwitch(socketReadSwitch));
						tReadSwitch2.start();
						readSwitch.run();

//						response = readSwitch.readSwitch();

						response = "111";

						System.out.println("J'ai bien " + response);

						while (response == "111") {

							System.out.println(socketReadSwitch.isClosed());

							response = readSwitch.readSwitch();

						}

//						readSwitch.close();

//					}

					closeConnexion = true;

//					socketReadSwitch.close();

					break;

				}

				if (closeConnexion) {
					System.err.println("COMMANDE CLOSE DETECTEE ! ");
					closeConnexion = true;

					removeClientList();

					// PERMET UN TEMPS DE LATENCE POUR ÊTRE SÛR QUE LA TRANSMISSION SOIT FAITE
					Thread.sleep(5000);

					clientSocketOnServer.close();
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				System.out.println("LA connexion est coupé suite à un arrêt de la connexion du Serveur");

				try {
					clientSocketOnServer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
*/
	}

	public ArrayList<String> getGlobalList() {

		return serverList;
	}

	private void removeClientList() {

		for (String item : serverList) {
			if (item.contains((CharSequence) clientSocketOnServer.getInetAddress())) {
				serverList.remove(item);
			}
		}

	}

}
