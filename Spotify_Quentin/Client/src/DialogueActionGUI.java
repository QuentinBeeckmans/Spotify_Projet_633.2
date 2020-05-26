import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.acl.LastOwnerException;
import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;

public class DialogueActionGUI {

	private boolean closeConnexion = false;
	private Socket clientSocketOnServer;
	private PrintWriter writer;
	private BufferedInputStream reader;
	
	private OutputStream os = null;
	private InputStream is = null;
	
	private ObjectOutputStream wObj;
	private ObjectInputStream rObj;
	
	private MyList onwnList;
	private ArrayList <String> serverList;
	private int portEcoute; //pour savoir le port pour délivrer la musique
	
	public DialogueActionGUI (Socket clientSocket, int port) {
		this.clientSocketOnServer = clientSocket;
		this.portEcoute=port;
		
		String response = null ;
		
		onwnList = new MyList(clientSocketOnServer);
		
		while(true){
			
	        try {
	        	 os = clientSocketOnServer.getOutputStream(); 
		           is = clientSocketOnServer.getInputStream();
		           writer = new PrintWriter(os,true);
		           //reader = new BufferedInputStream(is);
		           rObj = new ObjectInputStream(is);
		           
		           Scanner scan = new Scanner(System.in);
		 	      System.out.println("Que voulez-vous faire ?");
	 	      
	        	int choix;
			do {
				System.out.println("1 : Ajouter des musiques");
				System.out.println("2 : Ecouter des musiques");
				System.out.println("3 : Se déconnecter");
				choix = scan.nextInt();
			

				System.out.println("MON CHOIX" + choix);
	           
		         switch(choix) {
		         
		         case 1: 
		        	 System.out.println("J'ajoute des musiques");
		        	 writer.write(choix);
			         writer.flush();
		        	 onwnList.sendFileList();
		        	 break;
		        	 
		         case 2: 
		        	 System.out.println("J'écoute la musique");
			         displayMusics(); //J'affiche les musiques et en joue une
	
		        	 break;
		         case 3: 
			         System.out.println("Je sors");
			         writer.write(choix);
			         writer.flush();
			         
			         // je dois envoyer la demande de supprimer du hashtable l'id de ce client
			         Thread.sleep(5000);
			         writer.close();

		        	 break;
		         }
			}while(choix==3);
			
	           
	          /* response = read();
	           
	           switch (response) {
			   
			   case "1":
				   System.out.println("J'attends en retour la liste des musiques");
				   readList();
				   break;
				   
			   case "CLOSE":
				   System.out.println("J'ai choisi de fermer");
//				   long startTime = System.currentTimeMillis();
//				   System.out.println(startTime);
	        	   break;
	           }*/
	           
	           
	        }catch (IOException e) {
		        e.printStackTrace();
		    } catch (InterruptedException e) {
				e.printStackTrace();
			}  
		}      
		
	}
	
	
	   public ArrayList<String> readList () {
		   
			try {
				serverList = (ArrayList<String>) rObj.readObject();
				
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   
			return serverList;
	   }
	   
	   public void displayMusics() {
		   Scanner scan = new Scanner(System.in);
		   ArrayList<String> temp = readList();
		   int cpt = 0;
		   System.out.println("Choisissez une musique: ");
		   for(String lineList: temp) {
			   System.out.println(cpt + lineList);
		   }
		   int m = scan.nextInt();
		   
		   String choice = temp.get(m);
		   String [] address = choice.split(":");
		   
		   newServerConnection(address[0], address[1], address[2]);
		  
	   }

	private void newServerConnection(String Ipaddress, String port, String musiquePath) {
		try {
			Socket exchangeSocket = new Socket(Ipaddress, Integer.parseInt(port));
			System.out.println("Je suis connecté au client pour écouter");
			PrintWriter writerPath = new PrintWriter(exchangeSocket.getOutputStream());
			writerPath.write(musiquePath);
			InputStream is = new BufferedInputStream(exchangeSocket.getInputStream());
			
			//j'ajoute pour stream
			SimpleAudioPlayer player = new SimpleAudioPlayer(is);
			player.play();
			Thread.sleep(player.clip.getMicrosecondLength());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	   

}
