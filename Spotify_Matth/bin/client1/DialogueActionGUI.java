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
	private static int portList = 4505;
	private static int portList2 = 4506;
	private static int portSwitch1 = 4510;
	private static int portSwitch2 = 4511;
	private static int portStream = 4550;

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
	private Socket socketTransmitSwitch;
	private Socket socketReadSwitch;
	private Socket socketTransmitList;
	private Socket socketReadList;
	private Socket socketTransmitStream;
	private Socket socketReadStream;
	private InetAddress serverAdress;
	private File newFile;
	private TransmitList sendFile;
	private ReadList readList;
	private ReadSwitch readSwitch;
	private TransmitSwitch transmitSwitch;

	public DialogueActionGUI(Socket socket, InetAddress serverAdress, int port) {

		this.serverAdress = serverAdress;

		this.socketOnServer = socket;
		String response = null;

		onwnList = new Data_OwnList(socketOnServer);

		while (!socketOnServer.isClosed()) {

			try {

				newFile = File.createTempFile("listTempReçue", ".txt");

				Scanner scan = new Scanner(System.in);

				/**************************************
				 * Transmission de la liste >> faire méthode à mettre ici et à lancer dans le
				 * main au démarrage de la connexion de l'utilisteur
				 **********************************/
				socketTransmitList = new SocketTransmit(socketOnServer.getInetAddress(), portList).getSocket();

				while (!socketTransmitList.isClosed()) {

					Thread t = new Thread(
							sendFile = new TransmitList(onwnList.listFichierAEchange(), socketTransmitList));
					t.start();

					sendFile.run();

					sendFile.sendFile();

					if (!socketTransmitList.getReuseAddress()) {

						sendFile.close();
					}
				}
				/*****************************
				 * FIN de la méthode à créer
				 *****************************************/

				while (true) {

					System.out.println("QUe voulez-vous faire ?");

					for (int i = 0; i < listChoiceAction.length; i++) {
						System.out.println((i + 1) + " : " + listChoiceAction[i]);
					}

					// On traite la demande du client en fonction de la commande envoyée
					int choix = scan.nextInt();

					String choice = Integer.toString(choix);

					socketTransmitSwitch = new SocketTransmit(serverAdress, portSwitch1).getSocket();

//					while (!socketTransmitSwitch.isClosed()) {
						Thread tTransmitSwitch = new Thread(
								transmitSwitch = new TransmitSwitch(choice, socketTransmitSwitch));

						tTransmitSwitch.start();

						transmitSwitch.run();

						transmitSwitch.sendSwitch();

/*						if (!socketTransmitSwitch.getReuseAddress()) {
							transmitSwitch.close();
						}
*/
//					}

					SocketRead socketReadSwicth2 = new SocketRead(portSwitch2);
					Socket socketReadSwitch = socketReadSwicth2.getSocket();

//					while (!socketReadSwitch.isClosed()) {
						Thread tReadSwitch = new Thread(readSwitch = new ReadSwitch(socketReadSwitch));
						tReadSwitch.start();
						readSwitch.run();

						response = "111";

						System.out.println("J'ai bien " + response);

						while (response == "111") {
							response = readSwitch.readSwitch();

						}

//						readSwitch.close();

//					}

					switch (response) {

					case "1":
						SocketRead socketRead = new SocketRead(portList2);
						socketReadList = socketRead.getSocket();

						while (!socketReadList.isClosed()) {
							Thread t1 = new Thread(readList = new ReadList(socketReadList));
							t1.start();
							readList.run();

							serverList = new ArrayList<String>();

							while (serverList.isEmpty()) {

								serverList = readList.readList();

							}

							readList.close();

						}

						for (String item : serverList) {
							System.out.println("reçu du serveur " + item);
						}
						System.out.println();
						System.out.println();
						
						ArrayList<String> searchList = onwnList.formatList(serverList);
						onwnList.printList(searchList);
						
						
//						socketTransmitSwitch.close();
						socketReadList.close();
//						socketReadSwitch.close();
						break;

					case "CLOSE":

						Thread.sleep(5000);

						socketOnServer.isClosed();
						break;

					default:
						System.out.println("problème de réception serveur !");
						break;
					}

				}
			}

//			} 
			catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {

				System.out.println("LA connexion est coupé suite à un arrêt de la connexion du CLIENT");
				try {
					socketOnServer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
