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
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ReadList implements Runnable {
	

	private InputStream is = null;
	private OutputStream out = null;
	private DataInputStream readObj;
	private ArrayList<String> arraylist = new ArrayList<>(); 
	private File list = null;
	private boolean isRunning = false;
	private Socket socket;
//	private ServerSocket servSock;
	private BufferedInputStream reader = null;
	
	private File newTempFile;
	
	private ObjetSerialisable objList;
	
	public ReadList(Socket socket) {
		
		this.socket = socket;
//		this.list = list;
		
		try {
//	        is = socket.getInputStream();
//			is.reset();
			
		       
//	        reader = new BufferedInputStream(is);
			
	        newTempFile = File.createTempFile("MaListTemp", ".txt");

//			servSock = new ServerSocket(4505);

			
			} catch (IOException e) {
				e.printStackTrace();
			} 

	}
	
	@Override
	public void run() {
		
		isRunning = true;
		
 /*       try {
			socket = servSock.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
		
		System.out.println("Run READLIST -- ");

		list = newTempFile;
		
		return list;
	}
	
	 public synchronized ArrayList<String> readList () {
		 
//System.out.println("LIST arrayList ; ReadList ; readList() :  TEST INIT ");
		
			try {
//				 while (arraylist.size()<1 && arraylist.contains("EMPTY LIST")) {
					
					 System.out.println(("EMPTY LIST TEST : " + arraylist.contains("EMPTY LIST")));
					 
//					 is = socket.getInputStream();

								
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

		        bytes = new byte[0];
		        	        
		        InputStream ips = null;
		        
						ips = new FileInputStream(newTempFile);
					
				    	InputStreamReader ipsr=new InputStreamReader(ips);
				    	BufferedReader br=new BufferedReader(ipsr);
				    	String ligne;
				    	
				    	if ((ligne=br.readLine())!=null) {
						      
						      //parcour du fichier
					    	while ((ligne=br.readLine())!=null){
					    		
					    		arraylist.add(ligne);
			
					    	}
				    	}
				    	
				    	if (arraylist.isEmpty()) {
							arraylist.add("EMPTY LIST");
							System.out.println("En attente de réception d'une list non vide");
						}
//				 }  	
			
			} catch ( /* ClassNotFoundException | */ IOException e) {
				e.printStackTrace();
			} 
			finally {
				
				
//				else {
					if (arraylist.size()>1) {					
						for (String item : arraylist) {
							if (item.contentEquals("EMPTY LIST")) {
								arraylist.remove(item);
							}
						}
					}					
//				}
					
/*				try {
			        is.close();
			        out.close();
//			        socket.close();
//			        servSock.close();
			        
				} catch (IOException e) {
					e.printStackTrace();
				}
*/			
			}

				return arraylist;
	  }
	 
	 public OutputStream getOutPutStreamBuffer () {
		return out;
	}
	
	public  InputStream getInPutStreamBuffer () {
		return is; 
	}
	 
}
