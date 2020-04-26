package client1;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;

public class ConnexionToServer_AcrossThread {
	
	static Socket mySocket ;

	public static void main(String[] args) {

		ServerSocket sockEcoute ;
		InetAddress serverAddress;
        String serverName =  "10.0.2.15" /* "192.168.108.10" */;
        
        int port = 4500;
        int buffer = 10;
        
        Data_OwnList fileList = null ;
//		Enumeration<NetworkInterface> allNi ;

		try {
			System.out.println("Etape 1");
			serverAddress = InetAddress.getByName(serverName);

			System.out.println("Etape 2");
			
			
			System.out.println("IP : " + serverAddress.getHostAddress());

//			Socket mySocket = new Socket(serverAddress, port);
			
			System.out.println("Etape 3");

//			Socket mySocket = sockEcoute.accept();
			
			System.out.println("Etape 4");

			mySocket = new Socket(serverAddress, port);

			fileList = new Data_OwnList(mySocket) ;
			fileList.sendFileList ();
			
			System.out.println("Etape 5");

			//wait a bit before exit
//			Thread.sleep(5000);
			
			receiveGlobalList ();
						
//			System.out.println("\nTerminate client program...");
//			mySocket.close();
//			sockEcoute.close();

		}catch (UnknownHostException e) {
			e.printStackTrace();
		}catch (IOException e) {
			System.out.println("server connection error, dying.....");
		}catch(NullPointerException e){
			System.out.println("Connection interrupted with the server. ERROR ");
		}
/*	    catch (InterruptedException e) {
			System.out.println("interrupted exception");		
	    }
*/	    
		
	}
	
	public static void receiveGlobalList () {
		int cpt =0;
		try {
			//////////////////////////////////////////Transfert ArrayList (Objet)  ///////////////////////						
			System.out.println("debut transfert globalList");
			
			InputStream is = mySocket.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(mySocket.getInputStream());

			ArrayList <String> al = (ArrayList) ois.readObject() ;
			
//			is.close();
//			ois.close();
			
			System.out.println("Vous avez le choix entre : ");
			
			for (String item : al) {
				cpt++;
				System.out.println(cpt + ": " + item);
			}
			
			
			System.out.println("fin transfert globalList");

/////////////////////////////////////////////////////////////////

//			mySocket.close();	

		
	} catch (IOException e) {
		e.printStackTrace();
/*		} catch (InterruptedException e) {
		e.printStackTrace();
*/	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}
		
	}

}
