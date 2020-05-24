package client2;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ReadList implements Runnable {
	
	private Connexion1ToServer_AcrossThread connexion;

	private InputStream is = null;
	private DataInputStream readObj;
	private ArrayList<String> arraylist = new ArrayList<>(); 
	private File list = null;
	private boolean isRunning = false;
	private Socket socket;
	private BufferedInputStream reader = null;
	
	private File newTempFile;
	
	private ObjetSerialisable objList;
	
	public ReadList(Socket socket) {
		
//		connexion = new Connexion1ToServer_AcrossThread();

		this.socket = socket;
//		this.list = list;
		
		try {
	        is = socket.getInputStream().nullInputStream();
//			is.reset();
			
		       
//	        reader = new BufferedInputStream(is);
			
	        newTempFile = File.createTempFile("MaListTemp", ".txt");

			
			
			} catch (IOException e) {
				e.printStackTrace();
			} 

	}
	
	@Override
	public void run() {
		
		isRunning = true;
		
/*		while (true) {
			
			this.socket = connexion.getSocket();
		}
*/		

	}

	public boolean isRunning() {
		
		if (isRunning) {
			return true;
		}
		
		return false;
	}
	
	
	public synchronized File getTempListFile () {
		
		System.out.println("Run READLIST -- ");

		list = newTempFile;
		
		return list;
	}
	
	 public synchronized ArrayList<String> readList () {
		 OutputStream out = null;
//System.out.println("LIST arrayList ; ReadList ; readList() :  TEST INIT ");
		
//		 do {		
			try {
								
			byte[] bytes = new byte[1024];
	        is=socket.getInputStream();

//			InputStream in = socket.getInputStream();
			out = new OutputStream() {
				
				@Override
				public void write(int b) throws IOException {
					// TODO Auto-generated method stub
					
				}
			};
//			out = new FileOutputStream(newTempFile);
			out = new FileOutputStream(newTempFile).nullOutputStream();
			 System.out.println("out  : " + out.toString()); 			          
			 System.out.println("is  : " + is.available()); 			          

	        int count = 0;
	        
//	        if ((count = in.read(bytes)) > 0){
//	        out.notify();
	        
	        count = is.read(bytes);
 System.out.println("COUNT reaList()  : " + count); 			          
	        
	        while (count > 0) {
//		        	count += is.read(bytes);
		        	
			          out.write(bytes, 0, count);
			          count = is.read(bytes);
		            
			          System.out.println("COUNT boucle1 reaList()  : " + count); 			          
		          System.out.println("newTempFile getPath : " + newTempFile); 

		          out.flush();
//		          out.close();		            
		        }
	
 System.out.println("Sortie de la première boucle readList () ¨¨¨¨¨¨¨¨¨¨");			

		        bytes = new byte[0];
			
//		        out.close();
//		    	socket.setKeepAlive(true);	        
	        
		        InputStream ips = null;
		        
//			    if (newTempFile.isFile()) {
						ips = new FileInputStream(newTempFile);
					
				    	InputStreamReader ipsr=new InputStreamReader(ips);
				    	BufferedReader br=new BufferedReader(ipsr);
				    	String ligne;
				    	
				    	if ((ligne=br.readLine())!=null) {
//						      
						      //parcour du fichier
					    	while ((ligne=br.readLine())!=null){
					    		
					    		System.out.println("Entrée dans la deuxième boucle readList () ¨¨¨¨¨¨¨¨¨¨");			

					    		arraylist.add(ligne);
			
					    	}
				    	}
				    	
				    	br.close();
				    	ips.close();
				    	ipsr.close();
				    	

//	        }
//		        socket.setKeepAlive(true);
				
//				Thread.currentThread().interrupt();
			
			} catch ( /* ClassNotFoundException | */ IOException e) {
				e.printStackTrace();
			} 
		   /*catch (InterruptedException e) {
				e.printStackTrace();
			}
		  */

			finally {
				
				if (arraylist.isEmpty()) {
					arraylist.add("EMPTY LIST");
					System.out.println("En attente de réception d'une list non vide");
				}
				else {
					if (arraylist.size()>1) {
						
						for (String item : arraylist) {
							if (item.contentEquals("EMPTY LIST")) {
								arraylist.remove(item);
							}
							System.out.println("LIST arrayList ; ReadList : " + item);
						}
					}
					
				}
				
				
			}
//	 } while (arraylist.contains("EMPTY LIST"));
/*		br.close();
    	ips.close();
    	ipsr.close();
 */
/*	try {
	out.close();
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
*/
				return arraylist;
	  }
}
