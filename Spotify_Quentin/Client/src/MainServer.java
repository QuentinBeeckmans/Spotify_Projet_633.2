

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

//NOTRE CLIENT EN TANT QUE CLIENT
public class MainServer{
   
    private Socket clientSocket;
    private String musiqueChoice;
    
    private InetAddress serverAddress;
    private String serverName = "192.168.56.1";
    
    private DialogueActionGUI dialogueActionGUI;
    
	private int port;
   
   public MainServer(int port) {
	   this.port=port;  
   }
   
   public void exchangeSocket() {
		new Thread(new Runnable() {
    		public void run() {
    			while(true) {
    				try {
						serverAddress = InetAddress.getByName(serverName);

						clientSocket = new Socket(serverAddress, port);
					} catch (UnknownHostException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

    				dialogueActionGUI = new DialogueActionGUI (clientSocket);
    			}
    		}
    	}).start();
   }
   
   
   }

