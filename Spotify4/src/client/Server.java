package client;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;

import LogsConstructor.LoggerWithFileHandler;


/**
 * This class allows Client to become a server and wait 
 * @author Quentin Beeckmans - Mathieu Roux
 * @version 1.0
 * @since 2020-05-30
 */
public class Server{
   
	private LoggerWithFileHandler logsServer;
    private ServerSocket serverSocket = null;
	private int port;
   
/**
 * Class contructor
 * @param logsServer
 * @version 1.0
 * @since 2020-05-30
 */
   public Server(LoggerWithFileHandler logsServer) {
	   try {
		   serverSocket = new ServerSocket(0);
	   	 	logsServer.addHandler(Server.class.getName(), Level.WARNING, "Server socket turned on","");
	} catch (Exception e) {
   	 	logsServer.addHandler(Server.class.getName(), Level.SEVERE, "Server socket crashed",e.toString());
			e.printStackTrace();
		}
	   
	   port = serverSocket.getLocalPort();
	   listen();
   }
   
   /**
	  * Public void getPort method
	  * Recover listening port
	  * @author Quentin Beeckmans - Mathieu Roux
	  * @version 1.0
	  * @since 2020-05-30
	  */
   public int getPort() {return port;}
    
   /**
	  * Public void listen method
	  * Enable a listening socket for client
	  * @author Quentin Beeckmans - Mathieu Roux
	  * @version 1.0
	  * @since 2020-05-30
	  */
   private void listen() {
    	new Thread(new Runnable() {
    		public void run() {
    			while(true) {
    				Socket clientSocket = null;
    				
    				try {
						clientSocket = serverSocket.accept();
						System.out.println("connection request received");
				   	 	logsServer.addHandler(Server.class.getName(), Level.WARNING, "Client listen socket turned on","");
					} catch (Exception e) {
				   	 	logsServer.addHandler(Server.class.getName(), Level.SEVERE, "Listen socket crashed after connection", e.toString());
						e.printStackTrace();
					}
    				
    				//ceci est mon client 2 qui se connecte au client 1
    				Thread t = new Thread(new ClientServeur(clientSocket, logsServer));
    				t.start();
			   	 	logsServer.addHandler(Server.class.getName(), Level.WARNING, "Client thread of listen socket going on","");
    			}
    		}
    	}).start();
    }
   }

