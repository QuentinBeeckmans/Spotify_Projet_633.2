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
		
		this.socket = socket;
//		this.list = list;
		
		try {
	        is = socket.getInputStream();
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
								
			byte[] bytes = new byte[4096];
	        is=socket.getInputStream();

//			InputStream in = socket.getInputStream();
			out = new OutputStream() {
				
				@Override
				public void write(int b) throws IOException {
					// TODO Auto-generated method stub
					
				}
			};
//			out = new FileOutputStream(newTempFile);
			out = new FileOutputStream(newTempFile);
			 System.out.println("out  : " + out.toString()); 			          
			 System.out.println("is  : " + is.available()); 			          

	        int count = 0;
	        
//	        if ((count = in.read(bytes)) > 0){
//	        out.notify();
	        			          
	        
	        while ((count = is.read(bytes)) > 0) {
		        	
			          out.write(bytes, 0, count);
		            
		        }
	
	          out.flush();

 System.out.println("Sortie de la première boucle readList () ¨¨¨¨¨¨¨¨¨¨");			

		        bytes = new byte[0];
		        is.close();
		        out.close();
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
				    	
				    	
				    	
			
			} catch ( /* ClassNotFoundException | */ IOException e) {
				e.printStackTrace();
			} 
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
							System.out.println(item);
						}
					}					
				}
					
				try {
			        socket.close();
			        
				} catch (IOException e) {
					e.printStackTrace();
				}
			
			}

				return arraylist;
	  }
}
