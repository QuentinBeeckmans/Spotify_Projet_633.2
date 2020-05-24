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
import java.net.Socket;
import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Timer;

import server.AcceptClientD;

public class DialogueActionGUI {

	private static String [] listChoiceAction = {"get list", "CLOSE"};
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
	private Socket mySocketTemp;
	
	private File newFile;
	
	private GiveFichier sendFile;
	private ReadList readList;
	private /* ThreadToTransf */ TransfertList listThread;
	
	public DialogueActionGUI (Socket clientSocketOnServer) {
		this.clientSocketOnServer = clientSocketOnServer;
		String response = null ;
		
		onwnList = new Data_OwnList(clientSocketOnServer);
		
		while(!clientSocketOnServer.isClosed() /* || response != "CLOSE" */){

			
	        try {
				clientSocketOnServer.setKeepAlive(true);

				os = clientSocketOnServer.getOutputStream(); 
	           is = clientSocketOnServer.getInputStream();
	           writer = new PrintWriter(os,true);
		       
	           reader = new BufferedInputStream(is);
	           
				newFile = File.createTempFile("listTempReçue", ".txt");

	           
//	           writeObj = new ObjectOutputStream (os); 
//	           readObj = new ObjectInputStream(is);
	                      
	           Scanner scan = new Scanner(System.in);
	           
//	          sendList();
	          
/*	          listThread = new ThreadToTransf (sendFile = new GiveFichier(onwnList.listFichierAEchange(), clientSocketOnServer));
	          
	          listThread.start();
*/	          
	          
//	          sendFile.run();
//	          sendFile.sendFile();
//	          listThread.stop();
	          
		      Thread t = new Thread (sendFile = new GiveFichier (onwnList.listFichierAEchange(), clientSocketOnServer));
		      t.start();
		      sendFile.run();
//		      t.interrupt();
		      //serverList = listThread.getList();
		      
		      
	           
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
	           
	           System.out.println("Commade saisie : " + choice +"\t Envoyée au serveur");
	           
	           response = read();
	           System.out.println("Réponse SERVEUR av Switch : " + response);

	           
	           switch (response) {
			   
			   case "1":
//				   readList();
				   System.out.println("J'ai bien choisi get music");
				   System.out.println();
/*				   listThread = new ThreadToTransf (readList = new ReadList(serverList, clientSocketOnServer));
				   listThread.start();
				   readList.run();
*/
				   Thread t1 = new Thread (readList = new ReadList ( newFile, clientSocketOnServer));
				      t1.start();
				      
				      if (reader.read() <= 0) {
					      readList.run(); 
					      serverList = readList.readList();
				      }
				      else {
				    	  t1.wait();
//				      		t1.wait();
				      }
/*				   
	        		 System.out.println("Début de réception !!!!!!!!!");

		        	 if (readList.isRunning()) {
		        		 serverList = readList.readList();
		        		 System.out.println("Réception !!!!!!!!!");

		        	 }
		        	 else {
		        		 System.out.println("Problème de réception !!!!!!!!!");
		        	 }
		        	 
*/		        	 
		        	 for (String item : serverList) {
		        		 System.out.println("reçu par serveur " + item);
		        	 }
		        	 System.out.println();
				   System.out.println();
				   //		           response = read();
				   break;
				   
			   case "CLOSE":
//				   long startTime = System.currentTimeMillis();
//				   System.out.println(startTime);
	               Thread.sleep(5000);
	               

		           writer.close();
		           reader.close();
		           readObj.close();
		           writeObj.close();
		           clientSocketOnServer.isClosed();
		           break;
	            
	           default:
	        	   System.out.println("problème de réception serveur !");
	        	   break;
	           }
	           
	           
	        }catch (IOException e) {
		        e.printStackTrace();
		    } catch (InterruptedException e) {
				e.printStackTrace();
			}
	        finally {
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
		   
		   InputStream isRead = clientSocketOnServer.getInputStream();
           reader = new BufferedInputStream(isRead);

	      String response;
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
/*	   
	   public synchronized void readList () {
		   ArrayList<String> affichList = new ArrayList<String>();
		   Thread t = new Thread();
		   
			try {
				t.start();
				
				serverList = (ArrayList<String>) readObj.readObject();
				
				readObj.reset();
//				t.stop();
				
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   
			if (!serverList.isEmpty()) {
				String itemAffich = "";
				
				for (String item : serverList) {
//					itemAffich = item.substring(item.lastIndexOf("/"), item.indexOf(";"));
					affichList.add(item.substring(item.lastIndexOf("/"), item.indexOf(";")) );
				}
			}
			Collections.sort(affichList);
			
			System.out.println();
			System.out.println("Liste des musiques en streaming");
			System.out.println();
			
			for (String item : affichList) {
				System.out.println(item);
			}
			
	   }
	   
/*	   private synchronized void sendList (){
		   Thread t = new Thread();
			try {
				t.start();
				
				writeObj.writeObject(onwnList.listFichierAEchange());
				writeObj.flush();
				writeObj.reset();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   	 
	   }
*/
}
