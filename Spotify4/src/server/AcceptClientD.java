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
//	 	    	clientSocketOnServer.setKeepAlive(true);
	        	 
		         os = clientSocketOnServer.getOutputStream(); 
		         is = clientSocketOnServer.getInputStream();
		         writer = new PrintWriter(os,true);

		         reader = new BufferedInputStream(is);

//		         readObj = new ObjectInputStream(is);
//		         writeObj = new ObjectOutputStream (os);
		         
		           
	        	 int cpt = 0;
	        	 
//	        	 Thread.sleep(20000);
	        	 

	        	 //	        	 readList ();

/*	        	 listThreadReceptList = new ThreadToTransf (readList = new ReadList(clientList, clientSocketOnServer));
	        	 listThreadReceptList.start();
	        	 readList.run();
*/	        	 
	        	 
//				       while (clientList.isEmpty() && clientList == null) 
 /*				    	   connexionRead = new Connexion_read();
							 Thread t = new Thread (readList = connexionRead.reception());
*/				        	 Thread t = new Thread (  readList = new ReadList (clientSocketOnServer)  )  ;
						      t.start();
			//			      t.sleep(3000);
//						      if (reader.read() <= 0) {
						      readList.run(); 
						      System.out.println(" Il doit y avoir une liste !!!!!!!!!!!!!!");
				    	   clientList = readList.readList();
						      System.out.println(" On cherche une liste !!!!!!!!!!!!!!");
//				       }
				      System.out.println(" Il doit y avoir une liste !!!!!!!!!!!!!!");

				      fileTemp = readList.getTempListFile();

/*			      }
			      else {
			    	  t.wait(); 
//			      		t1.wait();
			      }
/*	        	 Thread t = new Thread (readList = new ReadList (reader));
			      t.start();
//			      t.wait(5000);

			      readList.run();
//			      t.interrupt();

			      clientList = readList.readList();
*/
			      if(clientList.isEmpty() || clientList.contains("EMPTY LIST")) {
			    	  while (clientList.isEmpty() || clientList.contains("EMPTY LIST")) {
//			    		  t.sleep(10000);
//					      clientList = listThread.getList();
					      clientList = readList.readList();
					      
System.out.println(" Il doit y avoir une liste !!!!!!!!!!!!!!");

			    	  }			    	  
			    		  
			      }
			      else {
		    		  
		    		  System.out.println(" Il doit y avoir une liste !!!!!!!!!!!!!!");
		    	  }
	        	 
        		 System.out.println("Début de réception !!!!!!!!!");

/*	        	 if (readList.isRunning()) {
	        		 clientList = readList.readList();
	        		 System.out.println("Réception !!!!!!!!!");

	        	 }
	        	 else {
	        		 System.out.println("Problème de réception !!!!!!!!!");
	        	 }
	        	 
*/	        	 
	        	 for (String item : clientList) {
	        		 System.out.println("reçu par Client " + item);
	        	 }
	        	 
//	        	 listThreadReceptList.interrupt();
/*	        	 
	        	 for (String item : listEchang) {
	        		 cpt++;
	        		 System.out.println(cpt + " : " + item);
	        	 }
*/	        	 
//                 listEchang = listExchange.listReceived();

	            //On attend la demande du client            
	            response = read();
	        	 	
		           System.out.println("Réponse du client av Switch : " + response);
  	 	            
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
	                  toSend = "1";
	                  response="1";
	                  System.out.println("SWITCH COTE SERVEUR : " + response);
	                  writer.write(response);							

	                  //	                  sendList ();
/*
	                  listThreadTransfList = new ThreadToTransf (sendFile = new GiveFichier(clientList, clientSocketOnServer));
	    	          
	                  listThreadTransfList.start();
	    	          
	    	          sendFile.run();
*/    //	    	          listThreadTransfList.interrupt();
	    	          
	                  
	                  totalListFile = fileTemp;
	                  Connexion_Give connexionGive = new Connexion_Give(totalListFile);
	     		     Thread t1 = new Thread (sendFile = connexionGive.transmisson());
//	                  Thread t1 = new Thread(sendFile = new GiveFichier (totalListFile));
	                  t1.start();
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
		   
		   Thread t = new Thread();
		   
		   try {
			t.start();
			  
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
