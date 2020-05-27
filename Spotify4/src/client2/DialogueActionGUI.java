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
//	InputStream isRead = null;
	
	private ObjectOutputStream writeObj = null;
	private ObjectInputStream readObj = null;
	private Data_OwnList onwnList;
	private ArrayList<String> serverList;
	
//	private Socket socketRead;
	private Socket socketTransmitSwitch;
	private Socket socketReadSwitch;
	private Socket socketTransmitList;
	private Socket socketReadList;
	
	private Socket socketTransmitStream;
	private Socket socketReadStream;

	private InetAddress serverAdress;
	private int port;


	private Connexion_read connexionRead;

	private File newFile;

	private TransmitList sendFile;
	private ReadList readList;
	
	private ReadSwitch readSwitch;
	private TransmitSwitch transmitSwitch;
//	private /* ThreadToTransf */ TransfertList listThread;

	public DialogueActionGUI(Socket socket, InetAddress serverAdress, int port) {

//		connexion = new Connexion1ToServer_AcrossThread();

		/*
		 * this.socketOnServer = connexion.getSocket(); String response = null ;
		 * 
		 * onwnList = new Data_OwnList(socketOnServer);
		 */

		this.serverAdress = serverAdress;
//		this.port = port;

//		while (true) {

			this.socketOnServer = socket;
			String response = null;

			onwnList = new Data_OwnList(socketOnServer);

			while (!socketOnServer.isClosed() /* || response != "CLOSE" */) {

			
			try {


//	        	SocketOnServer.setKeepAlive(true);

					
			//		 Socket echangeSwitchWrite = new Socket(serverAdress, 4515);
/*					  
					 ServerSocket servSockTemp = new ServerSocket(4501); 
					 Socket echangeSwitchRead = servSockTemp.accept();
*/
//					 os = echangeSwitchWrite.getOutputStream(); 
//					 is = echangeSwitchRead.getInputStream();
					

//					reader = new BufferedInputStream(is);

					newFile = File.createTempFile("listTempReçue", ".txt");

					Scanner scan = new Scanner(System.in);

/**************************************  Transmission de la liste >> faire méthode à mettre ici 
 * 								et à lancer dans le main au démarrage de la connexion de l'utilisteur **********************************/
					socketTransmitList = new SocketTransmit (socketOnServer.getInetAddress(), portList).getSocket();
					
					while (!socketTransmitList.isClosed()) {

					Thread t = new Thread(sendFile = new TransmitList(onwnList.listFichierAEchange(), socketTransmitList));
					t.start();
//					t.sleep(2000);
					
					sendFile.run();
					
					sendFile.sendFile();

//					t.sleep(5000);
					
					if (!socketTransmitList.getReuseAddress()) {
						
						sendFile.getOutPutStreamBuffer().close();
			    		
						sendFile.getInPutStreamBuffer ().close();
						socketTransmitList.close();
					}
			System.out.println("TransmitList du Client à sa connexion ; socket close ? " + socketTransmitList.isClosed());
					}
/*****************************   FIN de la méthode à créer *****************************************/
					
					while (true) {

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
					
//					writeSwitch(choice);
					
					socketTransmitSwitch = new SocketTransmit (serverAdress, portSwitch1).getSocket();

			           while (!socketTransmitSwitch.isClosed()) {
		                  Thread tTransmitSwitch = new Thread(transmitSwitch = new TransmitSwitch (choice, socketTransmitSwitch));

//		                  tTransmitSwitch.sleep(5000);
		                  
		                  tTransmitSwitch.start();

		                  transmitSwitch.run();
		                  
		                  transmitSwitch.sendSwitch();
					
		                  
		                  
							if (!socketTransmitSwitch.getReuseAddress()) {
//		                  if (socketTransmitSwitch.isClosed()) {
								
//				                  tTransmitSwitch.sleep(5000);
								
								transmitSwitch.getOutPutStreamBuffer().close();					    		
								transmitSwitch.getWriter().close();
//								socketTransmitSwitch.close();
								System.out.println(socketTransmitSwitch.isClosed());
							}
		                  
			           }
			           
			          
					
/*					if (socketTransmitSwitch.isClosed()) {
						
						os.close();
						
						System.out.println("socketTransmitSwitch : " + socketTransmitSwitch.isClosed());


//						Thread.currentThread().sleep(5000);
						
						cpt++;

					}
*/										
					System.out.println("Commade saisie : " + choice + "\t Envoyée au serveur");

//					response = readSwitch();
//					reader.close();

					SocketRead socketReadSwicth2 =  new SocketRead(portSwitch2);
					Socket socketReadSwitch = socketReadSwicth2.getSocket();
					

					while (!socketReadSwitch.isClosed()) {
						Thread tReadSwitch = new Thread (  readSwitch = new ReadSwitch (socketReadSwitch)  )  ;
						tReadSwitch.start();
						readSwitch.run();

//					response = readSwitch.readSwitch();


						response = "111";

						System.out.println("J'ai bien " + response);

					while (     response =="111") {

						System.out.println(socketReadSwitch.isClosed());

						response = readSwitch.readSwitch();
						
						System.out.println("Réponse SERVEUR av Switch : " + response);
				      
			    	  }	
					
						readSwitch.getReader().close();				    		
						readSwitch.getInPutStreamBuffer ().close();
//			    	  socketReadSwitch.close();
//			    	  socketRead.getServerSocket().close();
					}
					
					
					System.out.println("Réponse SERVEUR av Switch : " + response);

//					Thread.currentThread().sleep(5000);

					switch (response) {

					case "1":
//						System.out.println("J'ai bien choisi get music");
						System.out.println();


	//					ServerSocket servSock = new ServerSocket(portReadTransfList);
	//					Socket socketTemp = servSock.accept();
	//					socketRead = servSock.accept();
						
						SocketRead socketRead =  new SocketRead(portList2);
						socketReadList = socketRead.getSocket();
						

						while (!socketReadList.isClosed()) {
						Thread t1 = new Thread(readList = new ReadList(socketReadList));
						t1.start();
						readList.run();
//						serverList = readList.readList();
						
						serverList = new ArrayList<String>();


						while (serverList.isEmpty()) {

							System.out.println("J'ai bien choisi get music");

							serverList = readList.readList();
						      
				    	  }			    	  
				    		
				    	  readList.getOutPutStreamBuffer().close();				    		
				    	  readList.getInPutStreamBuffer ().close();
//				    	  socketReadList.close();
//				    	  socketRead.getServerSocket().close();
						}
						
						for (String item : serverList) {
							System.out.println("reçu du serveur " + item);
						}
						System.out.println();
						System.out.println();
						
						socketTransmitSwitch.close();
						socketReadList.close();
						socketReadSwitch.close();
						
						break;

					case "CLOSE":
						
	//					response = readSwitch();

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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

/*	private synchronized void writeSwitch (String choice) throws IOException {
		
		socketTransmitSwitch = new SocketTransmit(serverAdress, portSwitch).getSocket();
			
				os = socketTransmitSwitch.getOutputStream();	
				writer = new PrintWriter(os, true);
				
				writer.write(choice);
		
				writer.flush();
				
				writer.close();
			
	}
	
	// La méthode pour lire les réponses
/*	private synchronized String readSwitch() throws IOException {
		
		System.out.println("Est-ce que readSwitch se lance ???????????????????");
		
		
		
//		System.out.println("test socketReadSwitch : " + socketReadSwitch.getPort());
		try {
			Thread.currentThread().sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		socketReadSwitch.close();
		socketReadSwitch = new SocketRead(portSwitch).getSocket();
		
		try {
			Thread.currentThread().sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String response = null;
		int stream;

		byte[] b = new byte[0];

		b = new byte[4096];
		
		while (response == null) {
		 is = socketReadSwitch.getInputStream();
		reader = new BufferedInputStream(is);

		stream = reader.read(b);
		response = new String(b, 0, stream);

		// remise à zèro du buffer (sécurité peut-être pas obligatoire)
		b = new byte[0];
				
		}
		
		is.close();
    	reader.close();
    	socketReadSwitch.close();
    	
		return response;
	}
	*/
	
	
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
