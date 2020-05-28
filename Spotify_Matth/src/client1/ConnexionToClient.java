package client2;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnexionToClient {
	
	private Socket mySocket ;
	private DialogueActionGUI dialogueActionGUI;
	private TransmitList giveFichier;
	private ReadList readList;
	private int port;

	
	public ConnexionToClient(int port) {

		InetAddress serverAddress;
        String serverName = /*IP Matthieu */ "10.0.2.15" /*IP Quentin "192.168.56.1" */ ;
        
        this.port = port;
        
        Data_OwnList fileList = null ;
        
 //       while (true) {

			try {
				
				
//				while ( /* mySocket.isClosed() */ true) {
					System.out.println("Client 2111");
					serverAddress = InetAddress.getByName(serverName);
		
					mySocket = new Socket(serverAddress, port);
				
					while (mySocket.isConnected()) {
					
						dialogueActionGUI = new DialogueActionGUI (mySocket, serverAddress, port);
					
//						fileList = new Data_OwnList(mySocket) ;
						
						
						
						System.out.println("IP SERVEUR connecté" + mySocket.getInetAddress());
				
					}
				
	//			InputStream is = mySocket.getInputStream();
	//			ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());
	 
	//			ArrayList <String> al = (ArrayList ) ois.readObject() ;
	

//				}
				
			}catch (UnknownHostException e) {
				e.printStackTrace();
			}catch (IOException e) {
				System.out.println("server connection error, dying.....");
			}catch(NullPointerException e){
				System.out.println("Connection interrupted with the server. ERROR ");
			}    
			
//		}

	}
	

	public Socket getSocket () {
		return mySocket;
	}

	

}
