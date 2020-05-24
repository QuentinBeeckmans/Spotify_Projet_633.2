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
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class GiveFichier implements Runnable{

	private OutputStream os = null;
	private /* ArrayList<String> */ File list = null;
	private Socket socket;
	private PrintWriter writer;
	private Connexion1ToServer_AcrossThread connexion;
	
	private ObjetSerialisable objList;

	
	public GiveFichier( /* File list */ File listFile, Socket socket) {

		this.writer = writer;
		this.list = listFile;
		this.socket = socket;
		
	}
	
	@Override
	public void run() {
		
//		System.out.println("Debut List transfert !!!!!!!!!!!!!!!!!!!!!!");

//		sendFile ();
		
	
		
		
	}
	
	public synchronized void sendFile (){
	//	   Thread t = new Thread();
			try {
				
				if(socket.isClosed()) {
					System.out.println("Activité socket GiveFichier STOP !!!!!!!!!!!!");
				
				}
				
				os = socket.getOutputStream(); 
//	           writer = new PrintWriter(os,true);

//		        os.notify();

System.out.println("Debut List transfert !!!!!!!!!!!!!!!!!!!!!!");
			       
				
				byte[] bytes = new byte[2048];
				InputStream in = new FileInputStream(list);
//				FileOutputStream bufo = new FileOutputStream(list);
				
//		        OutputStream out = socket.getOutputStream();

		        int count;
		        while ((count = in.read(bytes)) > 0) {
		            os.write(bytes, 0, count);
		        }
		        
		        in.close();
		        os.flush();

//		      socket.setKeepAlive(true);	        
//		        os.close();
		        
//		        os.nullOutputStream();

				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 

			System.out.println("Fin List transfert !!!!!!!!!!!!!!!!!!!!!!");
	
		   	 
	   }

	
	
}
