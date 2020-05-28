package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.*;
import java.rmi.server.SocketSecurityException;
import java.util.*;
import java.util.concurrent.Exchanger;

public class ConnexionMulti_In {

	private ArrayList <String> globalList = new ArrayList<String>();
	private Socket clientSocket = null ;
	private InetAddress localAddress = null;
	private ServerSocket mySkServer;
	private String interfaceName = /* "eth4" */ "eth2";
	private int port;
	
//	public static void main(String[] args) {
	public ConnexionMulti_In(int port) {
		
		this.port = port;
		
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
			mySkServer = new ServerSocket(5000 ,10,localAddress);
			System.out.println("Default Timeout :" + mySkServer.getSoTimeout());
			System.out.println("Used IpAddress :" + mySkServer.getInetAddress());
			System.out.println("Listening to Port :" + mySkServer.getLocalPort());
			
			//wait for a client connection
			
//			while(!mySkServer.isClosed())	{
				
				AcceptClientD client ;
				clientSocket = mySkServer.accept();
				System.out.println("connection request received");
				
				Thread t = new Thread(client = new AcceptClientD(clientSocket,ClientNo, 5000));
				ClientNo++;
				

	//				globalList = client.getGlobalList();
					
				//starting the thread
					t.start();
//				t.sleep(3000);
				
//				addList(client.transfertList ());
								
//			}

		} catch (IOException e) {

			e.printStackTrace();
		} 
/*		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/		
	}
	
	public InetAddress getInetLocalAddress () {
		return localAddress;
		
	}
	
	public void addList(ArrayList <String> newList) {
		
		int cpt =0;
		
		if (!newList.isEmpty()) {
			for (String item : newList) {
				cpt++;
				globalList.add(item + ".RefClient :" + clientSocket);
	//			System.out.println(cpt + ": " + item);
			}
		}
		
//		shareGlobalList();
	}
	

}
