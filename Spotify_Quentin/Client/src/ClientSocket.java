

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


//NOTRE CLIENT EN TANT QUE CLIENT
public class ClientSocket {
   
    private Socket clientSocket;

    private InetAddress serverAddress;
    private String serverName = /* "192.168.56.1" */ "10.0.2.5";
    private MyList mList = new MyList();
    
	private int listenerPort; //c'est le port d'�coute de mon client
   
   public ClientSocket(int port) {
	   this.listenerPort=port;  
	   exchangeSocket();
   }
   
   public void exchangeSocket() {
		try {
			serverAddress = InetAddress.getByName(serverName);
			clientSocket = new Socket(serverAddress, 4501); //pour cr�er mon socketClient je me connecte au port dispo PAS le port d'�coute
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			Thread t = new Thread(new Dialogue (clientSocket, listenerPort, mList));
			t.start();
		}
    		
    	
   
   
   
   }

