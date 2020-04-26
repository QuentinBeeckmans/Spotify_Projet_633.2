package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

public class AcceptClientD implements Runnable {
	
	private Socket clientSocketOnServer;
	private int clientNumber;

	//Constructor
	public AcceptClientD (Socket clientSocketOnServer, int clientNo)
	{
		this.clientSocketOnServer = clientSocketOnServer;
		this.clientNumber = clientNo;

	}
	//overwrite the thread run()
	public void run() {

		System.out.println("Client Nr "+clientNumber+ " is connected");
		System.out.println("Socket is available for connection"+ clientSocketOnServer);
		
	}
	
	
	public ArrayList <String> transfertList () {
		ArrayList <String> list = new ArrayList<String> ();
		
		try {
			Thread.sleep(3000);
			//////////////////////////////////////////Transfert ArrayList (Objet)  ///////////////////////						
			System.out.println("debut transfert");
			
			InputStream is = clientSocketOnServer.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(clientSocketOnServer.getInputStream());
 
			ArrayList <String> al = (ArrayList ) ois.readObject() ;
			
//			is.close();
//			ois.close();
			//sockEchange.close();
			//sockEcoute.close();
			
			int cpt = 0;
			for (String item : al) {
			cpt++;
			System.out.println(cpt + " : " + item);
			}
			
			list = al;
			
			System.out.println("fin transfert");
			
/////////////////////////////////////////////////////////////////

//			clientSocketOnServer.close();	
//			Thread.sleep(3000);

		
	} catch (IOException e) {
		e.printStackTrace();
/*		} catch (InterruptedException e) {
		e.printStackTrace();
*/		} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InterruptedException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
		
		return list;

	}
	
	public Socket getSocketClient() {

		return clientSocketOnServer;		
	}

}
