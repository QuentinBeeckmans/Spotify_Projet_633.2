package client2;

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
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;

public class DialogueActionGUI {

	private static String [] listChoiceAction = {"path music" , "get list", "change password", "CLOSE"};
	private boolean closeConnexion = false;
	private Socket clientSocketOnServer;
	private PrintWriter writer = null;
	private BufferedInputStream reader = null;
	private OutputStream os = null;
	private InputStream is = null;
	private ObjectOutputStream writeObj = null;
	private ObjectInputStream readObj = null;
	private Data_OwnList onwnList;
	private ArrayList <String> serverList;
	
	public DialogueActionGUI (Socket clientSocketOnServer) {
		this.clientSocketOnServer = clientSocketOnServer;
		String response = null ;
		
		while(!clientSocketOnServer.isClosed() /* || response != "CLOSE" */){

	        try {
	           os = clientSocketOnServer.getOutputStream(); 
	           is = clientSocketOnServer.getInputStream();
	           writer = new PrintWriter(os,true);
	           reader = new BufferedInputStream(is);
	           readObj = new ObjectInputStream(is);
	           writeObj = new ObjectOutputStream (os);            
	           Scanner scan = new Scanner(System.in);
	 	      
	           sendList();
	           
	 	      System.out.println("QUe voulez-vous faire ?");
	 	      
	 	      for (int i = 0; i < listChoiceAction.length; i++) {
				System.out.println((i+1) + " : " + listChoiceAction[i]);
			}
	 	      
	           //On traite la demande du client en fonction de la commande envoyée
	 //          String command = getCommand();
	 	      int choix = scan.nextInt();
	
	           String choice = Integer.toString(choix);
	          
	           System.out.println("MON CHOIX" + choice);
	           
	           //On envoie la réponse au serveur
	           writer.write(choice);
	
	           writer.flush();
	           
	           System.out.println("Commade saisie : " + choice +"\t Envoy�e au serveur");
	           
	           response = read();
	           System.out.println("Réponse SERVEUR av Switch : " + response);

	           
	           switch (response) {
			   
			   case "0":
				   // lire la chanson
//		           response = read();
				   break;
				   
			   case "1":
				   writer.write("musicPath"); 
				   System.out.println("J'ai bien choisi path music");
				   System.out.println();
				   System.out.println();
//		           response = read();
				   break;
				   
			   case "2":
				   readList();
				   for (String item : serverList) {
					   System.out.println(item.substring(0,item.indexOf("/")) + item.substring(item.lastIndexOf("/"),item.indexOf(";")));
				   }
				   System.out.println("J'ai bien choisi get music");
				   System.out.println();
				   System.out.println();
				   //		           response = read();
				   break;
			    
			   case "3":
				   System.out.println("J'ai bien choisi change pwd");
				   System.out.println();
				   System.out.println();
				   //		           response = read();
				   break;
				   
			   case "CLOSE":
//				   long startTime = System.currentTimeMillis();
//				   System.out.println(startTime);
//				   Thread.w
				   break;
	            
	           default:
	        	   break;
	           }
	           
	           
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
	   
	   public void readList () {
		   
		   try {
			serverList = (ArrayList<String>) readObj.readObject();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
	   }
	   
	   private void sendList (){
		   
		   
		   try {

			writeObj.writeObject(onwnList.listFichierAEchange());

			writeObj.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
		   
	   }
}
