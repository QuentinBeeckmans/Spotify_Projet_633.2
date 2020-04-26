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

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class Data_OwnList {
	
	private Socket sockEchange;
//	private ServerSocket sockEcoute;
	
	public Data_OwnList (Socket sockEchange /* ownSocket */) {
		
		this.sockEchange = sockEchange;
//		this.sockEcoute = sockEcoute;
		
		System.out.println("Etape 1; list");
					
			//sockEcoute.setSoTimeout(30000);
			
			System.out.println("Je suis le client");
				
			System.out.println("One client is conneted !");
			
			System.out.println("Etape 2; list");
		
	}
	
	public void sendFileList () {
		
		String whereDataDirectory = "dataDirectory.txt";
//		String currentDirectoryPath = "test";
		File file = new File (whereDataDirectory);
		
		System.out.println("FICHIER DATA STOCK : " + file.getAbsolutePath());
		
		String dataDirectoryPath ;
		File dataDir = null ;
		
		ArrayList<String> list = new ArrayList<>() ;
		
//		PrintWriter writer;
		try {
			
			if ( ! file.exists()) {
				
				chooseRepertory ();
			
		}
			
		BufferedReader in = new BufferedReader(new FileReader(file));
//		String line;
		
		/////   A remplacer par :   line = in.readLine()
		while ((dataDirectoryPath = in.readLine()) != null)
		{
	      // Afficher le contenu du fichier
			  System.out.println (dataDirectoryPath + " 1");
			  dataDir = new File (dataDirectoryPath);
		}
		in.close();
				
		list = listFileTypeInDir (dataDir,".txt", list) ;
		
		OutputStream os = sockEchange.getOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(os);

		oos.writeObject(list);

		oos.flush();
//		oos.close();
		os.flush();		
		
		 /* socketExange.close(); */   // A ne pas fermer ici (normalement)
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
/*		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
*/			e.printStackTrace();
		}
		
	}
	
	private ArrayList<String> listFileTypeInDir (File file, String fileType, ArrayList<String> list) {
		
		// A mettre dans le main ou la fonction appelant cette méthode
				/*
						File repertoireCourant = null;
				        try {
				            repertoireCourant = new File(".").getCanonicalFile();
				            System.out.println("Répertoire courant : " + repertoireCourant);
				        } catch(IOException e) {}
						
						ArrayList<String> list = new ArrayList<>() ;
						
						list = listFileTypeInDir (repertoireCourant,".txt", list) ;
						
						for (String item : list) {
							
							System.out.println(item);
							
						}
				 */
				
		
	 if (file.getAbsolutePath().endsWith(fileType)) {			
			list.add(file.getAbsolutePath());
		}
		
		 System.out.println(file.getAbsolutePath());
		 
	        if (file.isDirectory()) {	 
	            File[] children = file.listFiles();
	 
	            for (File child : children) {	            	
	                // Récursive
	                listFileTypeInDir(child, fileType, list);	                
	            }	            
	        }
		
	        return list ;
	}
		
	
	
	public void chooseRepertory () {
		 JFileChooser choix = new JFileChooser();
		 choix.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		 
	    int retour = choix.showOpenDialog(new JFrame());
	    String directoryPath ;
	    String saveDirectoryPath = "dataDirectory.txt";
		

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
