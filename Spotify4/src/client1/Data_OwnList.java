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
import java.util.concurrent.Exchanger;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class Data_OwnList {
	
	private Socket sockEchange;
	Exchanger exchange = new Exchanger();
	private ListFileGiven listFichierDechange;
	private Thread echangeListThread ;

	
	public Data_OwnList (Socket sockEchange ) {
		
		this.sockEchange = sockEchange;
		
		System.out.println("Client 1");
					
		//sockEchange.setSoTimeout(30000);
		
		ArrayList<String> listFichierAEchanger = listFichierAEchange ();
		int cpt = 0;
		
		for (String item : listFichierAEchanger) {
			cpt++;
			System.out.println("Obj Data_OwnList : " + item);
//			item = cpt + " ; " + item + " ; " + sockEchange.getInetAddress();

		}
		
		this.listFichierDechange = new ListFileGiven (exchange, listFichierAEchanger);
		
		echangeListThread = new Thread ( /*listFichierDechange */ new ListFileGiven (exchange, listFichierAEchanger) );
		echangeListThread.start();
		
	}
	
	public ArrayList <String> listFichierAEchange () {
				
		String whereDataDirectory = "dataDirectory1.txt";
		File file = new File (whereDataDirectory);
		
		System.out.println("FICHIER DATA STOCK : " + file.getAbsolutePath());
		
		String dataDirectoryPath ;
		File dataDir = null ;
		
		ArrayList<String> list = new ArrayList<>() ;
		
		try {
			
			if ( ! file.exists()) {				
				chooseRepertory ();			
		}
			
			BufferedReader in = new BufferedReader(new FileReader(file));
			
			while ((dataDirectoryPath = in.readLine()) != null)
			{
				  dataDir = new File (dataDirectoryPath);
			}
			in.close();
					
			list = listFileTypeInDir (dataDir,".mp3", list) ;
			
			if (list == null || list.isEmpty()) {
				
				list.add("EMPTY/VIDE");
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;
	}
		
	private ArrayList<String> listFileTypeInDir (File file, String fileType, ArrayList<String> list) {
		
		 if (file.getAbsolutePath().endsWith(fileType)) {			
				list.add(file.getAbsolutePath() + " ; " + sockEchange.getInetAddress());
			}
						 
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
	    String saveDirectoryPath = "dataDirectory1.txt";
		

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
	
/*	public List <String> listReceived () {
		
		InputStreamReader inputReader ;
		inputReader = new InputStreamReader(sockEchange.getInputStream());
		
		BufferedReader BuffInput ;
		BuffInput = new BufferedReader(inputReader);
		
		try {	
			System.err.println("\t -> Liste vide du c�t� Client");
			listEchange = (List <String>)exchanger.exchange(listEchange);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}	
		
		return listEchange;
		
	}
*/
}
