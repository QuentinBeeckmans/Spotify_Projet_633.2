package client1;

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
	
	public ReadList(File list, Socket socket) {
		
		this.socket = socket;
		this.list = list;

	}
	
	@Override
	public void run() {
		
		isRunning = true;
		
		try {
	        is = socket.getInputStream();
		       
	        reader = new BufferedInputStream(is);
			
	        newTempFile = File.createTempFile("MaListTemp", ".txt");

			
			System.out.println("Run READLIST -- ");
/*						
			byte[] bytes = new byte[2048];
			InputStream in = socket.getInputStream();
			OutputStream out = new FileOutputStream(newTempFile);

	        int count;
	        
	        if ((count = in.read(bytes)) > 0){
		        while ((count = in.read(bytes)) > 0) {
		            out.write(bytes, 0, count);
		        }
		        
		        bytes = new byte[0];
			
		        out.flush();
		        
		        InputStream ips;
		        
//			    if (newTempFile.isFile()) {
						ips = new FileInputStream(newTempFile);
					
				    	InputStreamReader ipsr=new InputStreamReader(ips);
				    	BufferedReader br=new BufferedReader(ipsr);
				    	String ligne;
				    	
				    	if ((ligne=br.readLine())!=null) {
//						      
						      //parcour du fichier
					    	while ((ligne=br.readLine())!=null){
					    		
								arraylist.add(ligne);
			
					    	}
				    	}
//		        out.close();
	        }
*/
	        //		        socket.setKeepAlive(true);
				
//				Thread.currentThread().interrupt();
			
			} catch ( /* ClassNotFoundException | */ IOException e) {
				e.printStackTrace();
			} 
		   /*catch (InterruptedException e) {
				e.printStackTrace();
			}
		  */
	}

	public boolean isRunning() {
		
		if (isRunning) {
			return true;
		}
		
		return false;
	}
	
	
	public File getTempListFile () {
		
		list = newTempFile;
		
		return list;
	}
	
	 public ArrayList<String> readList () {
		   
			System.out.println("LIST arrayList ; ReadList ; readList() :  TEST INIT ");

		 
//		   Thread t = new Thread();
/*		 arraylist = new ArrayList<>();

		        InputStream ips;
		        
//		    if (newTempFile.isFile()) {
				try {
					ips = new FileInputStream(newTempFile);
				
			    	InputStreamReader ipsr=new InputStreamReader(ips);
			    	BufferedReader br=new BufferedReader(ipsr);
			    	String ligne;
			    	
			    	if ((ligne=br.readLine())!=null) {
//					      
					      //parcour du fichier
				    	while ((ligne=br.readLine())!=null){
				    		
							arraylist.add(ligne);
		
				    	}
			    	}
			    	
			    	br.close();
			    	ipsr.close();
			    	ips.close();
			    	
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} 
//		        }   
*/				
			try {	
			byte[] bytes = new byte[2048];
			InputStream in = socket.getInputStream();
			OutputStream out = new FileOutputStream(newTempFile);

	        int count;
	        
	        if ((count = in.read(bytes)) > 0){
		        while ((count = in.read(bytes)) > 0) {
		            out.write(bytes, 0, count);
		        }
		        
		        bytes = new byte[0];
			
		        out.flush();
		        
		        InputStream ips;
		        
//			    if (newTempFile.isFile()) {
						ips = new FileInputStream(newTempFile);
					
				    	InputStreamReader ipsr=new InputStreamReader(ips);
				    	BufferedReader br=new BufferedReader(ipsr);
				    	String ligne;
				    	
				    	if ((ligne=br.readLine())!=null) {
//						      
						      //parcour du fichier
					    	while ((ligne=br.readLine())!=null){
					    		
								arraylist.add(ligne);
			
					    	}
				    	}
//		        out.close();
	        }
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
				for (String item : arraylist) {
					System.out.println("LIST arrayList ; ReadList : " + item);
				}

				if (arraylist.isEmpty()) {
					arraylist.add("EMPTY LIST");
				}
			}
			
				return arraylist;
	  }
}
