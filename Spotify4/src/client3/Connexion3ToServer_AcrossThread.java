package client3;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import client1.DialogueActionGUI;


public class Connexion3ToServer_AcrossThread {
	
	static Socket mySocket ;
	static DialogueActionGUI dialogueActionGUI;


	public static void main(String[] args) {

		InetAddress serverAddress;
        String serverName = /*IP Matthieu*/ "10.0.2.15" /*IP Quentin  "192.168.108.10" */;
        
        int port = 4500;
        
        Data_OwnList fileList = null ;

		try {
			System.out.println("Client 3");
			serverAddress = InetAddress.getByName(serverName);

			mySocket = new Socket(serverAddress, port);

			fileList = new Data_OwnList(mySocket) ;
			
			dialogueActionGUI = new DialogueActionGUI (mySocket);



		}catch (UnknownHostException e) {
			e.printStackTrace();
		}catch (IOException e) {
			System.out.println("server connection error, dying.....");
		}catch(NullPointerException e){
			System.out.println("Connection interrupted with the server. ERROR ");
		}    
		
	}



}
