package client1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class Data_OwnList {
	
	private Socket sockEchange;
	String dataDirectory = "dataDirectory.txt";
	private Thread echangeListThread ;
	private Thread receptListThread;
	private File newTempFile;
	private PrintWriter writer;
	
	public Data_OwnList (Socket sockEchange ) {
		
		this.sockEchange = sockEchange;
		System.out.println("Client 1");
					
		//sockEchange.setSoTimeout(30000);
		

		/* ArrayList<String> */ File listFichierAEchanger = listFichierAEchange ();
/*		
		for (String item : listFichierAEchanger) {
			System.out.println("Test contenu List : " + item);
//			item = cpt + " ; " + item + " ; " + sockEchange.getInetAddress();

		}
*/				
	}
	
	public /* ArrayList <String> */ File listFichierAEchange () {
				
		String whereDataDirectory = dataDirectory;
		File file = new File (whereDataDirectory);
		File list = null;
		
		String dataDirectoryPath ;
		File dataDir = null ;
		
//		ArrayList<String>  list = new  ArrayList<>();
		
		try {
			newTempFile = File.createTempFile("MaListTemp", ".txt");
	
			if ( ! file.exists()) {				
				chooseRepertory ();			
		}
			
			BufferedReader in = new BufferedReader(new FileReader(file));
			
			while ((dataDirectoryPath = in.readLine()) != null)
			{
				  dataDir = new File (dataDirectoryPath);
			}
			in.close();
					
			list /* newTempFile */ = listFileTypeInDir (dataDir,".mp3", newTempFile) ;
			
			if (list == null || list.length() == 0 /* isEmpty() */ ) {
				writer = new PrintWriter(list);
				writer.write("EMPTY/VIDE");
				writer.close();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;
	}
		
	private /* ArrayList<String> */ File listFileTypeInDir (File file, String fileType, File newTempFile /* ArrayList<String> list */ ) {
		
		File newFile = null;
		
		try {
			newFile = File.createTempFile("MaListTemp", ".txt");

			writer = new PrintWriter(newFile);
			
			
				
		
		 if (file.getAbsolutePath().endsWith(fileType)) {			
//				list.add(file.getAbsolutePath() + ";" + sockEchange.getInetAddress() + ";4550");
				
				writer.println(file.getAbsolutePath() + ";" + sockEchange.getInetAddress() + ";4550");
//				newFile.add(file.getAbsolutePath() + ";" + sockEchange.getInetAddress() + ";4550");
			}
						 
		        if (file.isDirectory()) {	 
		            File[] children = file.listFiles();
		 
		            for (File child : children) {	            	
		                // Récursive
		                listFileTypeInDir(child, fileType, /* list* */ newFile);	                
		            }	            
		        }

		        writer.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		catch (IOException e) {
			e.printStackTrace();
		}
	        
		return newFile ;
	}
		
	
	
	public void chooseRepertory () {
		 JFileChooser choix = new JFileChooser();
		 choix.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		 
	    int retour = choix.showOpenDialog(new JFrame());
	    String directoryPath ;
	    String saveDirectoryPath = dataDirectory;
		

	    if(retour == JFileChooser.APPROVE_OPTION) {

	       // un fichier a été choisi ( sortie par OK)

	       choix.getSelectedFile().getName();       // nom du fichier

	                                                 // choisi

	       directoryPath = choix.getSelectedFile().getAbsolutePath();// chemin absolu du

	                                                 // fichier choisi

	       PrintWriter writer;
			try {
				writer = new PrintWriter(saveDirectoryPath);
				
				writer.println(directoryPath);
				writer.close();
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    } 
	    else {
	    	System.out.println("Le dossier n'a pas été choisi!"); 
	    	directoryPath = "none" ;
	    } // pas de fichier choisi	    
	    
	}	
	

}
