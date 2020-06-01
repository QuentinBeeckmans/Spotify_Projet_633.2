package client;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;

import LogsConstructor.LoggerWithFileHandler;


/**
 * This class initialize 
* @author Quentin Beeckmans - Mathieu Roux
* @version 1.0
* @since 2020-05-30
*/

public class Dialogue implements Runnable {

	private Socket clientSocketOnServer;
    private LoggerWithFileHandler logsServer;
	
	private ObjectOutputStream send;
	private ObjectInputStream reader;
	
	public MyList mList;
	
	public ArrayList<String> serverList = new ArrayList<String>() ;
	
	private int portListening; //pour savoir le port pour d�livrer la musique
	private Scanner scan = new Scanner(System.in);
	
	/**
	 * This constructor implements an interface to ask some questions at the client
	 * @param clientSocket
	 * @param port
	 * @param mList
	 */
	public Dialogue (Socket clientSocket, int port, MyList mList, LoggerWithFileHandler logsClient) {
		this.clientSocketOnServer = clientSocket;
		this.portListening=port;
		this.mList=mList;
		this.logsServer = logsClient;

   	 	logsServer.addHandler(Dialogue.class.getName(), Level.INFO, "Nouveau client: IP: "  + clientSocketOnServer.getInetAddress(), "port d'écoute " + String.valueOf(port));
	}

	public String getPort() {
		return Integer.toString(portListening);
		
	}
	
	public String getIp() {
		return clientSocketOnServer.getInetAddress().toString();
		
	}
	
	/**
	 * This method allows to choice to send music repertory and listen a music fron an other client
	 */
	@Override
	public void run() {
		int choix;
    	
		do {
			System.out.println("Que voulez-vous faire ?");
			System.out.println("1 : Ajouter des musiques");
			System.out.println("2 : Ecouter des musiques");
			System.out.println("3 : Se d�connecter");
			
			choix = scan.nextInt();
			
	         
			switch(choix) {
	         
	         case 1: 
	        	 System.out.println("J'ajoute des musiques au serveur");
	        	 mList.sendFileList(this);
	        	 logsServer.addHandler(Dialogue.class.getName(), Level.INFO, "Client choosed : add music on server", "");

//	        	 readList();
	        	 break;
	        	 
	         case 2: 
	        	 System.out.println("J'affiche les musiques du serveur: ");
	        	 
	        	 readList();
	        	 displayMusics(serverList);
	        	 logsServer.addHandler(Dialogue.class.getName(), Level.INFO, "Client choosed : display available music", "");
	        	 break;
	        	 
	         case 3: 
		         System.out.println("Je sors");
		         try {
					Thread.sleep(1000);
					send.close();
			         reader.close();
			         clientSocketOnServer.close();

			         logsServer.addHandler(Dialogue.class.getName(), Level.WARNING, "Client disconnected","");
				} catch (InterruptedException | IOException e) {
					logsServer.addHandler(Dialogue.class.getName(), Level.SEVERE, "Client connexion interrupted with server", e.toString());
					e.printStackTrace();
				}
		         
	        	 break;
	         }
		}while(choix!=3);
	}

		     
	        
	/**
	 * This method allows to get an arrayList from socket inputstream
	 */
	synchronized public void readList () {
		
		try {
			reader = new ObjectInputStream(clientSocketOnServer.getInputStream());
			ArrayList<String> arrayList = (ArrayList<String>) reader.readObject();
			serverList = arrayList;
		} catch (ClassNotFoundException | IOException e) {
			logsServer.addHandler(ClientSocket.class.getName(), Level.SEVERE, "Problem with list of music reception", e.toString());
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * This method allows to send an arrayList from socket outpustream
	 */
	public void sendObject(ArrayList<String> list) {
		try {
			send = new ObjectOutputStream(clientSocketOnServer.getOutputStream());
			send.writeObject(list);
			send.flush();
		} catch (IOException e) {
			logsServer.addHandler(Dialogue.class.getName(), Level.FINE,  "sendObject crash",  e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	/**
	 * This method allows to display an arrayList to make a choice of music
	 */
	public void displayMusics(ArrayList<String> serverList2){
		   int cpt = 0;
		   String [] temp;

		   try {
	
			   System.out.println("Choisissez une musique: ");
			   
			   for(String lineList: serverList2) {
				   System.out.println(cpt + " : "  + lineList.substring(lineList.lastIndexOf("\\") + 1));
				   cpt++;
			   }
			   int m=scan.nextInt();
			   
				logsServer.addHandler(Dialogue.class.getName(), Level.INFO,"Music choice: " + String.valueOf(m), "");
			   
			   String choice = serverList2.get(m);
			   String [] address = choice.split(";");
			   String str = address[1];
			   String strNew = str.replace("/", "");
			   
	
			   newServerConnection(strNew, address[0], address[2]); 
			} catch (IllegalThreadStateException | ArrayIndexOutOfBoundsException | NullPointerException e) {
				logsServer.addHandler(Dialogue.class.getName(), Level.SEVERE, "Display list crashed", e.toString());
			}
	}

	
	/**
	 * This method allows to do a newer connection Socket with the client who have the music choice and get stream audio after
	 */
	private void newServerConnection(String Ipaddress, String port, String musiquePath) {
		try {
			Socket exchangeSocket = new Socket(Ipaddress, Integer.parseInt(port));
			System.out.println("Je suis connect� au client pour �couter");
			
			logsServer.addHandler(Dialogue.class.getName(), Level.INFO, "Client connected","");
			
			PrintWriter writerPath = new PrintWriter(exchangeSocket.getOutputStream());
			writerPath.println(musiquePath);
			writerPath.flush();
			
			InputStream is = new BufferedInputStream(exchangeSocket.getInputStream());
			
			SimpleAudioPlayer player = new SimpleAudioPlayer(is, logsServer);
			player.play();
			logsServer.addHandler(Dialogue.class.getName(), Level.INFO, "Début du streaming", musiquePath);
			Thread.sleep(player.clip.getMicrosecondLength());
		} catch (Exception e) {
			logsServer.addHandler(Dialogue.class.getName(), Level.SEVERE, "New Server Connexion crash", e.getMessage());
			e.printStackTrace();
		}
	}
}