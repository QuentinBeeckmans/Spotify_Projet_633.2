package client3;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import server.ListFileExchanger;

public class DialogueActionGUI {

	private static String [] listChoiceAction = {"path music" , "get list", "change password", "CLOSE"};
	private boolean closeConnexion = false;
	private Socket clientSocketOnServer;
	private PrintWriter writer = null;
	private BufferedInputStream reader = null;
	
	public DialogueActionGUI (Socket clientSocketOnServer) {
		this.clientSocketOnServer = clientSocketOnServer;
		String response = "TRUE" ;
		
		while(!clientSocketOnServer.isClosed() || response != "CLOSE"){

	        try {
	                       
	           writer = new PrintWriter(clientSocketOnServer.getOutputStream(),true);
	           reader = new BufferedInputStream(clientSocketOnServer.getInputStream());        	 
	       	 	            
	           Scanner scan = new Scanner(System.in);
	 	      
	 	      System.out.println("QUe voulez-vous faire ?");
	 	      
	 	      for (int i = 0; i < listChoiceAction.length; i++) {
				System.out.println((i+1) + " : " + listChoiceAction[i]);
			}
	 	      
	           //On traite la demande du client en fonction de la commande envoyée
	 //          String command = getCommand();
	 	      int choix = scan.nextInt();
	
	           String choice = Integer.toString(choix);
	          
	           System.out.println("MON CHOIIX" + choice);
	           
	           //On envoie la réponse au serveur
	           writer.write(choice);
	
	           writer.flush();
	           
	           System.out.println("Commade saisie : " + choice +"\t Envoyé au serveur");
	           
	           response = read();
	           
	           System.out.println("Réponse reçu : " + response);
	           
	        }catch (IOException e) {
		        e.printStackTrace();
		    }  
		}      
		
	}
	
	//La méthode pour lire les réponses
	   private String read() throws IOException{      
	      
	      String response = "";
	      int stream;
	      
	      byte[] b = new byte[4096];
/*	      while ( (stream = reader.read(b)) >=0 ) {
		      
		      response = new String(b, 0, stream);
		      }
*/		      
	      stream = reader.read(b);
	      response = new String(b, 0, stream); 
		      
		      // remise à zèro du buffer (sécurité peut-être pas obligatoire)
		      b = new byte [8] ;
//	      stream = reader.read(b);
//	      response = new String(b, 0, stream); 
	      
	      return response;

	   }
}
