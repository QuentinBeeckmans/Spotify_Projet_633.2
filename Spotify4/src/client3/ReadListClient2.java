package client3;

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

public class ReadListClient2 implements Runnable {
	
	private InputStream is = null;
	private /* ObjectInputStream */ DataInputStream readObj;
	private ArrayList<String> arraylist; 
	private File list = null;
	private boolean isRunning = false;
	private Socket socket;
	private BufferedInputStream reader = null;
	
	private File newTempFile;
	
	private ObjetSerialisable objList;
	
	public ReadListClient2( /* BufferedInputStream reader */ File list, Socket socket) {
		
//		this.readObj = readObj;
//		this.reader = reader;
		this.socket = socket;
		this.list = list;

	}
	
	@Override
	public void run() {
		
//		readList();
		isRunning = true;
		
		try {
//			t.start();
	        is = socket.getInputStream();
		       
	        reader = new BufferedInputStream(is);
			
	        newTempFile = File.createTempFile("MaListTemp", ".txt");

			
			System.out.println("Run READLIST -- ");
			
//				this.is  = socket.getInputStream();

	//	        this.readObj = new /* ObjectInputStream */ DataInputStream(is);
		        
//		        ObjectInputStream objOS = new ObjectInputStream (/* readObj */ is);
		        
//		        objList = (ObjetSerialisable)  objOS.readObject();
			
			byte[] bytes = new byte[2048];
			InputStream in = socket.getInputStream();
			OutputStream out = new FileOutputStream(newTempFile);

	        int count;
	        while ((count = in.read(bytes)) > 0) {
	            out.write(bytes, 0, count);
	        }
			
			
/*		      String response = "";
		      int stream;
		      
		      
			      while (reader.read() >= 0){
			    	  
				      byte[] b = new byte[4096];
				      while ( (stream = reader.read(b)) >=0 ) {
					      
					     response = new String (b, 0, stream);
					  }
				      
					  list.add(response);   
				      stream = reader.read(b);
				      response = new String(b, 0, stream); 
					  
				      
				      // remise à zèro du buffer (sécurité peut-être pas obligatoire)
				      b = new byte [8] ;
				      
			      } 
		      
			      if(list.isEmpty()) {
			    	  list.add("Liste vide");
			      }
*/			      
		        socket.setKeepAlive(true);
		        
		        
//				list = /* objList.getList(); */  (ArrayList)  objOS.readObject();
				
		        
//				objOS.close();
				
/*				for (String item : list) {
					System.out.println("list ReadList" + item);
				}
*/
//				is.close();
				
//				readObj.close();

//			serverList.addAll(clientList);
				
				Thread.currentThread().interrupt();
			
			} catch ( /* ClassNotFoundException | */ IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		   /*catch (InterruptedException e) {
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
		
		list = newTempFile;
		
		return list;
	}
	
	 public ArrayList<String> readList () {
		   
//		   Thread t = new Thread();


		        InputStream ips;
				try {
					ips = new FileInputStream(newTempFile);
				
			    	InputStreamReader ipsr=new InputStreamReader(ips);
			    	BufferedReader br=new BufferedReader(ipsr);
			    	String ligne;
			    	//parcour du fichier
			    	while ((ligne=br.readLine())!=null){
			    		
						arraylist.add(ligne);
	
	/*		        	    	String[] oldTableau = tableau;
			        	    	int noligne = oldTableau.length;
			        	    	tableau = new String[noligne+1]; //afin d'ajouter la ligne on augmente la capacité du tableau
			        	    	System.arraycopy(oldTableau, 0, tableau,0, noligne);//on recopie le contenu de l'ancien tableau dans le nouveau
			        	    	tableau[noligne] = ligne; //affectation de la ligne du fichier au dernier élément du tableau
	*/
			    	}
			    	
			    	br.close();
			    	
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
		    	
		    	//br.close();iter.close();
		   
		   return arraylist;
	  }
}
