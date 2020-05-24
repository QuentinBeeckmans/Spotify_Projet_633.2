package client1;

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
import java.util.ArrayList;

public class GiveFichier implements Runnable{

	private OutputStream os;
	private File list = null;
	private Socket socket;
	private PrintWriter writer;
	
	private ObjetSerialisable objList;

	
	public GiveFichier(File list, Socket socket) {

		this.writer = writer;
		this.list = list;
		this.socket = socket;
	}
	
	@Override
	public void run() {
		
		System.out.println("FileTemp path ; GiveFichieir ; run() : " + list.getAbsolutePath());
		
		sendFile ();
		
		System.out.println("List transféré !!!!!!!!!!!!!!!!!!!!!!");
		
	}
	
	public void sendFile (){
	//	   Thread t = new Thread();
			try {
				os = socket.getOutputStream(); 
			       
				
				byte[] bytes = new byte[2048];
				InputStream in = new FileInputStream(list);

		        int count;
		        while ((count = in.read(bytes)) > 0) {
		            os.write(bytes, 0, count);
		        }
		        
		        os.flush();
//		        in.close();
		        
				
				socket.setKeepAlive(true);
								
			} catch (IOException e) {
				e.printStackTrace();
			} 
		   	 
	   }

	
	
}
