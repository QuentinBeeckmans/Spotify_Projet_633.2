package client2;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class GiveFichier implements Runnable{

	private OutputStream os = null;
	private InputStream in = null;
	private /* ArrayList<String> */ File list = null;
	private Socket socket;
//	private InetAddress serverAdress;
	private PrintWriter writer;
	
	private ObjetSerialisable objList;

	
	public GiveFichier( /* File list */ File listFile, Socket socket) {

		this.writer = writer;
		this.list = listFile;
		this.socket = socket;
		
//		this.serverAdress = serverAdress;
		
		
		
	}
	
	@Override
	public void run() {
		
//		System.out.println("Debut List transfert !!!!!!!!!!!!!!!!!!!!!!");

//		sendFile ();
		
/*		try {
			this.socket = new Socket(serverAdress, 4505);
		} catch (IOException e) {
			e.printStackTrace();
		}
/*		
		while (socket.isConnected()) {
			sendFile();
		}
*/		
		
	}
	
	public synchronized void sendFile (){
	//	   Thread t = new Thread();
			try {
				
				if(socket.isClosed()) {
					System.out.println("Activité socket GiveFichier est arrêtée !!!!!!!!!!!!");
				
				}
				
				os = socket.getOutputStream(); 
//	           writer = new PrintWriter(os,true);

//		        os.notify();

System.out.println("Debut List transfert !!!!!!!!!!!!!!!!!!!!!!");
			       
				
				byte[] bytes = new byte[4096];
				in = new FileInputStream(list);
//				FileOutputStream bufo = new FileOutputStream(list);
				
//		        OutputStream out = socket.getOutputStream();

		        int count;
		        while ((count = in.read(bytes)) > 0) {
		            os.write(bytes, 0, count);
		        }
		        
		        os.flush();

			} catch (IOException e) {
				e.printStackTrace();
			} 

/*			finally {
				try {
//			        in.close();
//					os.close();
//			        socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
*/			
			
			System.out.println("Fin List transfert !!!!!!!!!!!!!!!!!!!!!!");
	
		   	 
	   }

	
	public OutputStream getOutPutStreamBuffer () {
		return os;
	}
	
	public  InputStream getInPutStreamBuffer () {
		return in; 
	}
	
	
}
