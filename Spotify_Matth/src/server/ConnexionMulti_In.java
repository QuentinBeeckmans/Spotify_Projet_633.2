package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.*;

public class ConnexionMulti_In {

	static ArrayList <String> globalList = new ArrayList<String>();
	static Socket clientSocket = null ;
	static InetAddress localAddress = null;
	static ServerSocket mySkServer;
	static String interfaceName = "eth0";

	public static void main(String[] args) {

		int ClientNo = 1;
		
		try {
			NetworkInterface ni = NetworkInterface.getByName(interfaceName);
	        Enumeration<InetAddress> inetAddresses =  ni.getInetAddresses();
			while(inetAddresses.hasMoreElements()) {
	            InetAddress ia = inetAddresses.nextElement();
	            
	            if(!ia.isLinkLocalAddress()) {
	               if(!ia.isLoopbackAddress()) {
	            	   System.out.println(ni.getName() + "->IP: " + ia.getHostAddress());
	            	   localAddress = ia;
	               }
	            }   
            }
			
			//Warning : the backlog value (2nd parameter is handled by the implementation
			mySkServer = new ServerSocket(4500,10,localAddress);
			System.out.println("Default Timeout :" + mySkServer.getSoTimeout());
			System.out.println("Used IpAddress :" + mySkServer.getInetAddress());
			System.out.println("Listening to Port :" + mySkServer.getLocalPort());
			
			//wait for a client connection
			while(true)
			{
				AcceptClientD client ;
				clientSocket = mySkServer.accept();
				System.out.println("connection request received");
				Thread t = new Thread(client = new AcceptClientD(clientSocket,ClientNo));
				ClientNo++;
				
			//starting the thread
				t.start();
				
//				t.sleep(3000);
				
				addList(client.transfertList ());
								
			}

		} catch (IOException e) {

			e.printStackTrace();
		} 
/*		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/		
	}
	
	static public void addList(ArrayList <String> newList) {
		
		int cpt =0;
		
		for (String item : newList) {
			cpt++;
			globalList.add(newList + ".RefClient :" + clientSocket);
//			System.out.println(cpt + ": " + item);
		}
		
		shareGlobalList();
	}
	
	public static void shareGlobalList() {
		
		OutputStream os;
		ObjectOutputStream oos;
		
		globalList.add("ceci est un test d'écriture");
		
		try {
			os = clientSocket.getOutputStream();
			oos = new ObjectOutputStream(os);
			
			oos.writeObject(globalList);

			oos.flush();
//			oos.close();
			os.flush();		

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int cpt = 0;
		
		for (String item : globalList) {
			cpt++;
			System.out.println(cpt + ": " + item);
		}
	}
	

}
