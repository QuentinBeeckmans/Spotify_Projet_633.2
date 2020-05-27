

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

//NOTRE CLIENT EN TANT QUE SERVER
public class Server{
   
    private ServerSocket serverSocket = null;
    private String musiqueChoice;
	private int port;
   
   public Server() {
	   try {
		   serverSocket = new ServerSocket(0);
		} catch (Exception e) {
			// TODO: handle exception
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
    				Thread t = new Thread(new ClientServeur(clientSocket, musiqueChoice));
    				t.start();
    			}
    		}
    	}).start();
    }
   }

