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
import client2.GiveFichier;
import client2.ReadList;
/*
 import client1.ThreadToTransf;
import client1.TransfertList;
*/

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
	private ArrayList<String> clientList = null;;
	
//	private TransfertList listThread;
//	private ThreadToTransf listThreadReceptList;
	private GiveFichier sendFile;
	private ReadList readList;
	
	private Connexion_read connexionRead;

	
	private File fileTemp;
	private File totalListFile;
	
	private InetAddress serverAdress;
	private int port;
	private Socket echangeSocketTemp;

	//Constructor
	public AcceptClientD (Socket clientSocketOnServer, int clientNo, InetAddress serverAddress, int port)
	{
		this.clientSocketOnServer = clientSocketOnServer;
		this.clientNumber = clientNo;
		this.serverList = serverList;
		this.serverAdress = serverAddress;
		this.port = port;
		

	}
	//overwrite the thread run()
	public void run() {
		
		System.out.println("IP CLIENT connecté" +clientSocketOnServer.getInetAddress());
		System.out.println("Client Nr "+clientNumber+ " is connected");
		System.out.println("Socket is available for connection"+ clientSocketOnServer);
		   	 	
	    boolean closeConnexion = false;
	    
	    boolean serverActivity = true;
	    
	      //tant que la connexion est active, on traite les demandes
	    while(!clientSocketOnServer.isClosed() ){
	    	
	         try {
//	        	 serverActivity = serverAdress.isReachable(5000);

//	 	    	clientSocketOnServer.setKeepAlive(true);
	        	 
//	        	 Socket echangeSwitchWrite = new Socket(serverAdress, 4510);
	 	           
/*	 	           ServerSocket servSockTemp = new ServerSocket(4502);
	 	          Socket echangeSwitchRead = servSockTemp.accept();
*/
//				os = echangeSwitchWrite.getOutputStream(); 
//	           is = echangeSwitchRead.getInputStream();
	        	 
		         os = clientSocketOnServer.getOutputStream(); 
//		         is = clientSocketOnServer.getInputStream()
		        		 ;
		         writer = new PrintWriter(os,true);

//		         reader = new BufferedInputStream(is);

//		         readObj = new ObjectInputStream(is);
//		         writeObj = new ObjectOutputStream (os);
		         
		           
	        	 int cpt = 0;
	        	 

	        	 ServerSocket servSock = new ServerSocket(4505);
	           echangeSocketTemp = servSock.accept();
	        	 Thread t = new Thread (  readList = new ReadList (echangeSocketTemp)  )  ;
						      t.start();
						      readList.run(); 
				    	   clientList = readList.readList();

			    	  while (clientList.isEmpty() || clientList.contains("EMPTY LIST")) {
					      clientList = readList.readList();
					      
			    	  }			    	  
			    		  
				      fileTemp = readList.getTempListFile();
	        	 
        		 System.out.println("Début de réception !!!!!!!!!");

        	 
	        	 for (String item : clientList) {
	        		 System.out.println("reçu par Client " + item);
	        	 }
	        	 

	            //On attend la demande du client            
	            response = read();
	        	 	
		           System.out.println("Réponse du client av Switch : " + response);

		           //On traite la demande du client en fonction de la commande envoyée
	            String toSend = "path music";
	            
	            
	            switch( response){
	               case "1" :
	                  toSend = "1";
	                  response="1";
	                  System.out.println("SWITCH COTE SERVEUR : " + response);
	                  writer.write(response);							
	                  writer.flush();
	                  
	                  totalListFile = fileTemp;
	                  
			           Socket socketTemp = new Socket(serverAdress, 45000);
//			           echangeSocketTemp.close();
//			           echangeSocketTemp = new Socket(serverAdress, 45000);

	                  Thread t1 = new Thread(sendFile = new GiveFichier (totalListFile, socketTemp));
	                  t1.start();
	                  t1.wait(5000);
	                  sendFile.run();
	                  sendFile.sendFile();
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
	         finally {
		        	System.out.println("LA connexion est coupé suite à un arrêt de la connexion du Serveur");

	               try {
					clientSocketOnServer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	 	    }
			
			    }
	    
	    
		
	}
	
	//La méthode pour lire les réponses
	   private String read() throws IOException{      
		  String response;
	      int stream;
	      int cpt = 0;
	      
	      is = clientSocketOnServer.getInputStream();
         reader = new BufferedInputStream(is);

	      byte[] b = new byte[4096];
//	      while ( (stream = reader.read(b)) >=0 ) {
//	    	  response += new String(b, 0, stream);
//	      }
//	   
	      stream = reader.read(b);
	      response = new String(b, 0, stream); 
	      
	      // remise � z�ro du buffer (s�curit� peut-�tre pas obligatoire)
	      b = new byte [0] ;
	      
	      return response;
	   }
	   

	public Socket getSocketClient() {

		return clientSocketOnServer;		
	}
	
	
	public ArrayList <String> getGlobalList () {
		
		return serverList;
	}
	
	   public synchronized void readList () {
		   
		   
		   try {
			  
			clientList = (ArrayList<String>) readObj.readObject();
			
			readObj.wait();

			serverList.addAll(clientList);
			
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
/*		   for(String item : serverList) {
			   System.out.println(item);
		   }
		   
		   System.out.println();
		   
		   for(String item : clientList) {
			   System.out.println(item);
		   }
*/		   
	   }
	   
	   private synchronized void sendList (){
		   
		   Thread t = new Thread();
		   ArrayList<String> lisToSend;
		   
		   lisToSend = serverList;
		   
		   // Permet de ne pas envoyer au client ses propres musiques
/*		   
		   for(String item : lisToSend) {
			   
			   if(item.contains((CharSequence) clientSocketOnServer.getInetAddress())) {
				   lisToSend.remove(item);
				}			   
		   }
*/		   
		   try {
//			   t.start();
			   
			   writeObj.writeObject(lisToSend);

			   writeObj.flush();
			   
				writeObj.wait();

		} catch (IOException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
		} catch (InterruptedException e) {
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
