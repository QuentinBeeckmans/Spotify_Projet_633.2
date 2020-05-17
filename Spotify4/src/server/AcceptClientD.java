package server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Exchanger;

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
	
	private Socket clientSocketOnServer;
	private int clientNumber;
	private PrintWriter writer = null;
	private BufferedInputStream reader = null;
	private OutputStream os = null;
	private InputStream is = null;
	private ObjectOutputStream writeObj = null;
	private ObjectInputStream readObj = null;
//	private BufferedReader bufRead = null;
	private String response = null;
	private ArrayList <String> serverList;
	private ArrayList<String> clientList ;
	private Thread ExchangeListThread;

	//Constructor
	public AcceptClientD (Socket clientSocketOnServer, int clientNo, ArrayList <String> serverList)
	{
		this.clientSocketOnServer = clientSocketOnServer;
		this.clientNumber = clientNo;
		this.serverList = serverList;
		

	}
	//overwrite the thread run()
	public void run() {
		
		System.out.println("IP CLIENT connecté" +clientSocketOnServer.getInetAddress());
		System.out.println("Client Nr "+clientNumber+ " is connected");
		System.out.println("Socket is available for connection"+ clientSocketOnServer);
		   	 	
	    boolean closeConnexion = false;
	      //tant que la connexion est active, on traite les demandes
	    while(!clientSocketOnServer.isClosed()){
	         
	         try {
	            
		         os = clientSocketOnServer.getOutputStream(); 
		         is = clientSocketOnServer.getInputStream();
		         writer = new PrintWriter(os,true);
		         reader = new BufferedInputStream(is);
		         readObj = new ObjectInputStream(is);
		         writeObj = new ObjectOutputStream (os); 
		           
	        	 int cpt = 0;
	        	 
//	        	 Thread.sleep(20000);
	        	 
	        	 readList ();
/*	        	 
	        	 for (String item : listEchang) {
	        		 cpt++;
	        		 System.out.println(cpt + " : " + item);
	        	 }
*/	        	 
//                 listEchang = listExchange.listReceived();

	            //On attend la demande du client            
	            response = read();
	        	 	
	            System.out.println("R�ponse re�ue du Serveur : " + response);
	        	 	            
	            //On affiche quelques infos, pour le débuggage
/*	            String debug = "";
	            debug = "Thread : " + Thread.currentThread().getName() + ". ";
	            debug += "Demande de l'adresse : " + remote.getAddress().getHostAddress() +".";
	            debug += " Sur le port : " + remote.getPort() + ".\n";
	            debug += "\t -> Commande reçue : " + response + "\n";
	            System.err.println("\n" + debug);
*/	            
	            //On traite la demande du client en fonction de la commande envoyée
	            String toSend = "path music";
	            
	            /* response.toUpperCase() */
	            
	            switch( response){
	               case "1" :
	                  toSend = "2" /* "get list" */;
	                  response="2";
	  	            writer.write(toSend);
	  	            	sendList ();
	                  break;
	                  
	               case "2" : // "CLOSE" 
	                  toSend = "CLOSE"; 
	                  System.out.println("La connexion va être arrêté");
	                  closeConnexion = true;
	                  break;
	              /* 
	               default : 
	                  toSend = "Commande inconnu !";                     
	                  break;
	               */
	            }
	            
//	            System.out.println(toSend);
	            
	            //On envoie la réponse au client
/*	            writer.write(toSend);							*/
	            //Il FAUT IMPERATIVEMENT UTILISER flush()
	            //Sinon les données ne seront pas transmises au client
	            //et il attendra indéfiniment
	            writer.flush();
	            
	            if(closeConnexion){
	               System.err.println("COMMANDE CLOSE DETECTEE ! ");
	                  closeConnexion = true;
	                  
	                  removeClientList();
	                  
	                  
	                  Thread.sleep(5000);

	                  writer.close();
	                  reader.close();
	                  writeObj.close();
	                  readObj.close();	               

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
	         */
 catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	  
			
			    }
		
	}
	
	//La méthode pour lire les réponses
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
	      
	      // remise � z�ro du buffer (s�curit� peut-�tre pas obligatoire)
	      b = new byte [8] ;
	      
	      return response;
	   }
	   

	public Socket getSocketClient() {

		return clientSocketOnServer;		
	}
	
	
	public ArrayList <String> getGlobalList () {
		
		return serverList;
	}
	
	   public void readList () {
		   
		   try {
			clientList = (ArrayList<String>) readObj.readObject();
			
			serverList.addAll(clientList);
			
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
		   for(String item : serverList) {
			   System.out.println(item);
		   }
		   
		   System.out.println();
		   
		   for(String item : clientList) {
			   System.out.println(item);
		   }
		   
	   }
	   
	   private void sendList (){
		   
		   ArrayList<String> lisToSend;
		   
		   lisToSend = serverList;
		   
		   for(String item : lisToSend) {
			   
			   if(item.contains((CharSequence) clientSocketOnServer.getInetAddress())) {
				   lisToSend.remove(item);
				}			   
		   }
		   
		   try {

			writeObj.writeObject(lisToSend);

			writeObj.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
		   
	   }
	   
	   private void removeClientList() {
		   
		   for (String item : serverList) {
				if(item.contains((CharSequence) clientSocketOnServer.getInetAddress())) {
					serverList.remove(item);
				}
			}
		   
	   }

}
