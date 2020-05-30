import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * This class allows Client to become a server and wait 
 * @author Quentin Beeckmans - Mathieu Roux
 * @version 1.0
 * @since 2020-05-30
 */
public class Server{
   
    private ServerSocket serverSocket = null;
	private int port;
   
   public Server() {
	   try {
		   serverSocket = new ServerSocket(0);
		} catch (Exception e) {
			ClientLogger.severe("Problème lors de la connection au serveur " + e.toString());
			e.printStackTrace();
		}
	   
	   port = serverSocket.getLocalPort();
	   listen();
   }
   
   public int getPort() {return port;}
    
   private void listen() {
    	new Thread(new Runnable() {
    		public void run() {
    			while(true) {
    				Socket clientSocket = null;
    				
    				try {
						clientSocket = serverSocket.accept();
						System.out.println("connection request received");
					} catch (Exception e) {
						e.printStackTrace();
					}
    				
    				//ceci est mon client 2 qui se connecte au client 1
    				Thread t = new Thread(new ClientServeur(clientSocket));
    				t.start();
    			}
    		}
    	}).start();
    }
   }

