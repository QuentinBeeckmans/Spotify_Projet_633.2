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
import client2.TransmitList;
import client2.TransmitSwitch;
import client2.ReadList;
import client2.ReadSwitch;
/*
 import client1.ThreadToTransf;
import client1.TransfertList;
*/
import client2.SocketRead;
import client2.SocketTransmit;

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
	
	private static int portList = 4505;
	private static int portList2 = 4506;
	private static int portSwitch1 = 4510;
	private static int portSwitch2 = 4511;
	private static int portStream = 4550;
	
	private Socket clientSocketOnServer;
	private int clientNumber;
	
	//private PrintWriter writer = null;
	private BufferedInputStream reader = null;
	//private OutputStream os = null;
	// private InputStream is = null;
	private ObjectOutputStream writeObj = null;
	private ObjectInputStream readObj = null;
//	private BufferedReader bufRead = null;
	
	private String response = null;
	private String choice = null;
	
	private ArrayList <String> serverList;
	private ArrayList<String> clientList = null;;
	
	private Socket socketTransmitSwitch;
	private Socket socketReadSwitch;
	private Socket socketTransmitList;
	private Socket socketReadList;
	
//	private TransfertList listThread;
//	private ThreadToTransf listThreadReceptList;
	private TransmitList sendFile;
	private ReadList readList;
	
	private ReadSwitch readSwitch;
	private TransmitSwitch transmitSwitch;
	
	private Connexion_read connexionRead;

	
	private File fileTemp;
	private File totalListFile;
	
//	private InetAddress serverAdress;
//	private InetAddress servInetAddressRead;
	private InetAddress clientInetAddresss;
	
	private int port;
//	private Socket socketRead;
//	private Socket socketGive;

	//Constructor
	public AcceptClientD (Socket clientSocketOnServer, int clientNo, int port)
	{
		this.clientSocketOnServer = clientSocketOnServer;
		this.clientNumber = clientNo;
		this.serverList = serverList;
		this.clientInetAddresss = clientSocketOnServer.getInetAddress();
		this.port = port;
		

	}
	//overwrite the thread run()
	public void run() {
		
		System.out.println("IP CLIENT connecté" +clientSocketOnServer.getInetAddress());
		System.out.println("Client Nr "+clientNumber+ " is connected");
		System.out.println("Socket is available for connection"+ clientSocketOnServer);
		   	 	
	    boolean closeConnexion = false;
	    
//	    boolean serverActivity = true;
	    
	      //tant que la connexion est active, on traite les demandes
	    while(!clientSocketOnServer.isClosed() ){
	    	
	         try {

		        SocketRead socketRead =  new SocketRead(portList);
				socketReadList = socketRead.getSocket();
				
	           while (!socketReadList.isClosed()) {

	        	   Thread t = new Thread (  readList = new ReadList (socketReadList)  )  ;
	        	   t.start();
	        	   readList.run();
//	        	   t.sleep(5000);
				   clientList = readList.readList();

		    	  while (clientList.isEmpty() && clientList.contains("EMPTY LIST")) {
				      clientList = readList.readList();
				      
		    	  }			    	  
		    		
		    	  readList.close();
		    	  
		  //  	  getOutPutStreamBuffer().close();
		  //  	  readList.getInPutStreamBuffer ().close();
//		    	  socketReadList.close();
//		    	  socketRead.getServerSocket().close();
	  
	           }
			      fileTemp = readList.getTempListFile();
	        	 
			      System.out.println(fileTemp.getAbsolutePath());
	           
        	 
	        	 for (String item : clientList) {
	        		 System.out.println("reçu par Client " + item);
	        	 }
	        	 

	            //On attend la demande du client 
	        	 
//	            Thread tSwitch = new thread();
//	            tSwitch.start();
	            


	//	            response = readSwitch();
		            
		            SocketRead socketReadSwicth =  new SocketRead(portSwitch1);
					socketReadSwitch = socketReadSwicth.getSocket();
					
					System.out.println("J'ai bien choisi get music");

					while (!socketReadSwitch.isClosed()) {
						Thread tReadSwitch = new Thread (  readSwitch = new ReadSwitch (socketReadSwitch)  )  ;
						tReadSwitch.start();
						readSwitch.run();
			        	   
					response = "";
					
//					response = readSwitch.readSwitch();

					
					while (response =="") {
						
						System.out.println("Est-ce que je passe ici ++++++++++++++++");

						response = readSwitch.readSwitch();
						
						System.out.println("Est-ce que response ici ++++++++++++++++" + response);

					      
			    	  }	
					
						readSwitch.getReader().close();				    		
						readSwitch.getInPutStreamBuffer ().close();
//			    	  socketReadSwitch.close();
//			    	  socketRead.getServerSocket().close();
					}

		           System.out.println("Réponse du client av Switch : " + response);
		           
		           

		           //On traite la demande du client en fonction de la commande envoyée
	            String toSend = "path music";
	            
	            
	            switch( response){
	               case "1" :
	                  toSend = "1";
	                  response="1";
	                  System.out.println("SWITCH COTE SERVEUR : " + response);

/*	                  int choix = scan.nextInt();

						String choice = Integer.toString(choix);

						System.out.println("MON CHOIX" + choice);
*/
						// On envoie la réponse au serveur
/*						writeSwitch(response);

						if (socketTransmitSwitch.isClosed()) {
//							os.close();
							System.out.println("Commade saisie : " + response + "\t Envoyée au Client");
							
						}
*/						
//	                  clientInetAddresss = clientSocketOnServer.getInetAddress();
	                  
						Socket socketTransmitSwitch = new SocketTransmit (clientInetAddresss, portSwitch2).getSocket();

				           while (!socketTransmitSwitch.isClosed()) {
			                  Thread tTransmitSwitch = new Thread(transmitSwitch = new TransmitSwitch (response, socketTransmitSwitch));
			                  tTransmitSwitch.start();
//			                  t1.sleep(5000);

			                  transmitSwitch.run();
		                  
			                  transmitSwitch.sendSwitch();
			                  
//			                  System.out.println("COTE SERVEUR transmitSwitch ");

					           if (!socketTransmitSwitch.getReuseAddress()) {
	//				           if (socketTransmitSwitch.isClosed()) {
									
									transmitSwitch.getOutPutStreamBuffer().close();					    		
									transmitSwitch.getWriter().close();
//									socketTransmitSwitch.close();

//									System.out.println(socketTransmitSwitch.isClosed());
									
								}       
  

				           }
				           


	                  
	                  totalListFile = fileTemp;
	                  
//			           Socket socketTemp = new Socket(serverAdress, 45000);
//			           echangeSocketTemp.close();


//			           socketGive = new Socket(clientInetAddresss, 4505);

	                  Socket socketTransmitList2 = new SocketTransmit (clientInetAddresss, portList2).getSocket();

			           while (!socketTransmitList2.isClosed()) {
		                  Thread t1 = new Thread(sendFile = new TransmitList (totalListFile, socketTransmitList2));
		                  t1.start();

		                  sendFile.run();
		                  
		                  sendFile.sendFile();
					
		                  if (!socketTransmitList2.getReuseAddress()) {
//					           if (socketTransmitList.isClosed()) {
									System.out.println(socketTransmitList2.isClosed());
									t1.sleep(2000);
									sendFile.close();
					//				getOutPutStreamBuffer().close();					    		
					//				sendFile.getInPutStreamBuffer ().close();
//									socketTransmitList.close();
								}  
		                  
			           }
			           
			           
			           
	                  break;
	                  
	               case "2" : // "CLOSE" 
	                  toSend = "CLOSE";
	                  System.out.println("La connexion va être arrêté");
	                  
						SocketRead socketReadSwicth2 =  new SocketRead(portSwitch2);
						Socket socketReadSwitch = socketReadSwicth2.getSocket();					

						while (!socketReadSwitch.isClosed()) {
							Thread tReadSwitch = new Thread (  readSwitch = new ReadSwitch (socketReadSwitch)  )  ;
							tReadSwitch.start();
							readSwitch.run();

//						response = readSwitch.readSwitch();


							response = "111";

							System.out.println("J'ai bien " + response);

						while (     response =="111") {

							System.out.println(socketReadSwitch.isClosed());

							response = readSwitch.readSwitch();
							
							System.out.println("Réponse SERVEUR av Switch : " + response);
					      
				    	  }	
						
							readSwitch.getReader().close();				    		
							readSwitch.getInPutStreamBuffer ().close();
//				    	  socketReadSwitch.close();
//				    	  socketRead.getServerSocket().close();
						}
	                  	                  
	                  closeConnexion = true;
	                  
	                  socketReadSwitch.close();

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
//	            writer.flush();
	            
	            if(closeConnexion){
	               System.err.println("COMMANDE CLOSE DETECTEE ! ");
	                  closeConnexion = true;
	                  
	                  removeClientList();
	                  
	                  
	                  Thread.sleep(5000);

//	                  writer.close();
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
	

/*	private synchronized void writeSwitch (String choice) throws IOException {
		
		socketTransmitSwitch = new SocketTransmit(clientInetAddresss, portSwitch).getSocket();
		
//		while (!socketTransmitSwitch.isClosed()) {
			
			OutputStream osWrite = socketTransmitSwitch.getOutputStream();
	
			PrintWriter writerSwitch = new PrintWriter(osWrite, true);
			
			writerSwitch.write(choice);
	
			writerSwitch.flush();

			writerSwitch.close();	
			osWrite.close();
//		}
	}
	
	// La méthode pour lire les réponses
/*	private synchronized String readSwitch() throws IOException {
		
		InputStream is = null;
		BufferedInputStream reader = null;
		socketReadSwitch = new SocketRead(portSwitch).getSocket();
		response = null;
		int stream;

		byte[] b = new byte[0];

		b = new byte[4096];
		
		while (response == null) {
		 is = socketReadSwitch.getInputStream();
		reader = new BufferedInputStream(is);

		stream = reader.read(b);
		response = new String(b, 0, stream);

		// remise à zèro du buffer (sécurité peut-être pas obligatoire)
				
		}
		
		is.close();
    	reader.close();
    	socketReadSwitch.close();
    	
		System.out.println("socketReadSwitch : " + socketReadSwitch.isClosed());

    	
		return response;
	}
*/
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
