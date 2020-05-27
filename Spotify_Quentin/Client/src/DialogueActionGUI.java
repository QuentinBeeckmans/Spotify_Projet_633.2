import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

	private Socket clientSocketOnServer;
	
	private BufferedReader reader;
	private PrintWriter send;
	
	private MyList onwnList;
	private ArrayList <String> serverList;
	private int portListening; //pour savoir le port pour d�livrer la musique
	private Scanner scan = new Scanner(System.in);
	
	public DialogueActionGUI (Socket clientSocket, int port) {
		this.clientSocketOnServer = clientSocket;
		this.portListening=port;
		
		String response = null ;
		
		onwnList = new MyList(clientSocketOnServer, portListening);
		
		while(true){
			
	        try {
	        
	        	reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				send = new PrintWriter(clientSocket.getOutputStream());

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
			        	 onwnList.sendFileList(this);
			        	 
			        	 break;
			        	 
			         case 2: 
			        	 System.out.println("J'affiche les musiques du serveur: ");
			        	 reading();
			        	 displayMusics(); //J'affiche les musiques et en joue une
			        	 break;
			        	 
			         case 3: 
				         System.out.println("Je sors");
		
				         onwnList.removeFileList(this);
				         // je dois envoyer la demande de supprimer du hashtable l'id de ce client
				         Thread.sleep(5000);
				         send.close();
				         reader.close();
				         clientSocket.close();
			        	 break;
			         }
				}while(choix!=3);
   
	        }catch (IOException e) {
		        e.printStackTrace();
		    } catch (InterruptedException e) {
				e.printStackTrace();
			}  
		}      
		
	}
	
	public void toSend(String message) {
		send.println(message);
		send.flush();
	}

	public void reading() {
		while(true) {
			try {
				String line = readLine();
				addLineToClient(line); 
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
	
	 synchronized public void addLineToClient(String ligne) throws ClassNotFoundException, IOException {
		String [] temp = ligne.split("|");

		switch(temp[0]+temp[1]+temp[2]) {
		case "add":
			System.out.println("demande d'ajout d'un client");
			System.out.println(ligne);
			serverList.add(ligne);
			break;
		case "rem":
			System.out.println("demande de retirer d'un client");
			serverList.remove(ligne);
			break;
		
		default:
			System.out.println("Wrong message");
		}
		
	}

	public void displayMusics(){
		   int cpt = 0;
		   System.out.println("Choisissez une musique: ");
		   for(String lineList: serverList) {
			   System.out.println(cpt + " : "  + lineList);
		   }
		   int m=scan.nextInt();
		   
		   String choice = serverList.get(m);
		   String [] address = choice.split("|");
		   
		   
		   System.out.println(address[0] + "-> "+ address[1] + "-> "+ address[2]);
		   //newServerConnection(address[0], address[1], address[2]);
		  
	}

	

	private void newServerConnection(String Ipaddress, String port, String musiquePath) {
		try {
			Socket exchangeSocket = new Socket(Ipaddress, Integer.parseInt(port));
			System.out.println("Je suis connect� au client pour �couter");
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