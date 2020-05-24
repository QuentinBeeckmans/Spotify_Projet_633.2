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
import java.util.ArrayList;

public class GiveFichier implements Runnable{

	private OutputStream os = null;
	private /* ArrayList<String> */ File list = null;
	private Socket socket;
	private PrintWriter writer;
	
	private ObjetSerialisable objList;

	
	public GiveFichier( /* ArrayList<String> */ File list, Socket socket) {

		this.writer = writer;
		this.list = list;
	}
	
	@Override
	public void run() {
		
		System.out.println("ListFile.getAbsPath ; GiveFichier ; run() : " + list.getAbsolutePath());
		sendFile ();
		
		System.out.println("List transféré !!!!!!!!!!!!!!!!!!!!!!");
		
	}
	
	public void sendFile (){
	//	   Thread t = new Thread();
			try {
				os = socket.getOutputStream(); 
//	           writer = new PrintWriter(os,true);
			       
				
				byte[] bytes = new byte[2048];
				InputStream in = new FileInputStream(list);
//				FileOutputStream bufo = new FileOutputStream(list);
				
//		        OutputStream out = socket.getOutputStream();

		        int count;
		        while ((count = in.read(bytes)) > 0) {
		            os.write(bytes, 0, count);
		        }
		        
		        os.flush();
//		        in.close();
		        
//				t.start();
//				os  = socket.getOutputStream();
				
/*				objList= new ObjetSerialisable(list);
//				DataOutputStream dos = new  DataOutputStream(os);
		        
				ObjectOutputStream writeObj = new ObjectOutputStream ( os); 
*/
/*				for (String item : list) {
					
					writer.write(item);
					
				}
*/
//				writeObj.writeObject(list);
				
//				dos.flush();
//				writeObj.flush();
				
//				writer.flush();
				
				socket.setKeepAlive(true);
				
//				writeObj.reset();
	//			writeObj.close();
				
//				Thread.currentThread().interrupt();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		   	 
	   }

	
	
}
