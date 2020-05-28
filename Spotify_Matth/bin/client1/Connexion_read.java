package client2;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class Connexion_read {
	
	private Socket mySocket ;
//	static DialogueActionGUI dialogueActionGUI;
	private ReadList readList;


	public Connexion_read () {

		InetAddress serverAddress;
        String serverName = /*IP Matthieu */ "10.0.2.15" /*IP Quentin "192.168.56.1" */ ;
        
        int port = 4500;
        
 //       Data_OwnList fileList = null ;

		try {
			
//			while (true) {
				

			System.out.println("Client 3");
			serverAddress = InetAddress.getByName(serverName);

			mySocket = new Socket(serverAddress, port);
			
	           System.out.println(" Pendant READ connexion");
			
//			fileList = new Data_OwnList(mySocket) ;
			
			System.out.println("IP SERVEUR connecté" + mySocket.getInetAddress());
		
//			dialogueActionGUI = new DialogueActionGUI (mySocket);
			
			readList = new ReadList(mySocket);
			
//			InputStream is = mySocket.getInputStream();
//			ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
 
//			ArrayList <String> al = (ArrayList ) ois.readObject() ;

//			}
			
		}catch (UnknownHostException e) {
			e.printStackTrace();
		}catch (IOException e) {
			System.out.println("server connection error, dying.....");
		}catch(NullPointerException e){
			System.out.println("Connection interrupted with the server. ERROR ");
		}    
		
	}

	public ReadList reception () {
		
//		readList = new ReadList(socket);
		
		return readList;
	}

}