package server;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Exchanger;
/*
•	The client will be able to connect to the server through socket connections 
•	The client should be able to give its list of file to the server 
•	The client should be able to give its IP address 
•	The client should be able to get a list of clients with their available audio files 
•	The client should be able to ask for another client IP address 
•	The client should be able to connect to another client and ask to stream one file 
•	The client should be able to accept a network connection from another client and stream the selected file
•	The client should be able to play the audio stream  

 */
public class AcceptClientD implements Runnable {
	
	private Socket clientSocketOnServer;
	private int clientNumber;
	Exchanger exchange = new Exchanger ();
	private ListFileExchanger listExchange ;
	private PrintWriter writer = null;
	private BufferedInputStream reader = null;
	private String response = null;

	//Constructor
	public AcceptClientD (Socket clientSocketOnServer, int clientNo)
	{
		this.clientSocketOnServer = clientSocketOnServer;
		this.clientNumber = clientNo;
		

	}
	//overwrite the thread run()
	public void run() {

		System.out.println("Client Nr "+clientNumber+ " is connected");
		System.out.println("Socket is available for connection"+ clientSocketOnServer);
		
	      boolean closeConnexion = false;
	      //tant que la connexion est active, on traite les demandes
	      while(!clientSocketOnServer.isClosed()){
	         
	         try {
	            
	             writer = new PrintWriter(clientSocketOnServer.getOutputStream(),true);
	             reader = new BufferedInputStream(clientSocketOnServer.getInputStream());        	 
	             
	        	 listExchange = new ListFileExchanger(exchange);
	            
	            //On attend la demande du client            
	            response = read();
	        	 	
	            System.out.println("Réponse reçue du Serveur : " + response);
	        	 	            
	            //On affiche quelques infos, pour le dÃ©buggage
/*	            String debug = "";
	            debug = "Thread : " + Thread.currentThread().getName() + ". ";
	            debug += "Demande de l'adresse : " + remote.getAddress().getHostAddress() +".";
	            debug += " Sur le port : " + remote.getPort() + ".\n";
	            debug += "\t -> Commande reÃ§ue : " + response + "\n";
	            System.err.println("\n" + debug);
*/	            
	            //On traite la demande du client en fonction de la commande envoyÃ©e
	            String toSend = "path music";
	            
	            /* response.toUpperCase() */
	            
	            switch( response){
	               case "1" :
	                  toSend = "path music";
	                  response=null;
	                  listExchange = new ListFileExchanger(exchange);
	                  break;
	               case "2" :
	                  toSend = "get list";
	                  response=null;
	                  listExchange = new ListFileExchanger(exchange);
	                  break;
	               case "3" :
	                  toSend = "change password";
	                  response=null;
	                  listExchange = new ListFileExchanger(exchange);
	                  break;
	               case "4" : // "CLOSE" 
	                  toSend = "CLOSE"; 
	                  System.out.println("La connexion va Ãªtre arrÃªtÃ©");
	                  closeConnexion = true;
	                  break;
	              /* 
	               default : 
	                  toSend = "Commande inconnu !";                     
	                  break;
	               */
	            }
	            
//	            System.out.println(toSend);
	            
	            //On envoie la rÃ©ponse au client
	            writer.write(toSend);
	            //Il FAUT IMPERATIVEMENT UTILISER flush()
	            //Sinon les donnÃ©es ne seront pas transmises au client
	            //et il attendra indÃ©finiment
	            writer.flush();
	            
	            if(closeConnexion){
	               System.err.println("COMMANDE CLOSE DETECTEE ! ");
//	               writer = null;
//	               reader = null;
	               clientSocketOnServer.close();
	               break;
	            }
	         }catch (IOException e) {
		            e.printStackTrace();
		         }  
/*	         catch(SocketException e){
	            System.err.println("LA CONNEXION A ETE INTERROMPUE ! ");
	            break;
	         }        
*/	      }
		
	}
	
	//La mÃ©thode pour lire les rÃ©ponses
	   private String read() throws IOException{      
		  String response;
	      int stream;
	      int cpt = 0;
	      
	      byte[] b = new byte[4096];
//	      while ( (stream = reader.read(b)) >=0 ) {
//	    	  response += new String(b, 0, stream);
//	      }
//	   
	      stream = reader.read(b);
	      response = new String(b, 0, stream); 
	      
	      // remise à zéro du buffer (sécurité peut-être pas obligatoire)
	      b = new byte [8] ;
	      
	      return response;
	   }
	   

	public Socket getSocketClient() {

		return clientSocketOnServer;		
	}

}
