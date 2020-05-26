
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
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class MyList {
	
	private Socket sockEchange;
	private ArrayList<String> list;
	
	public MyList (Socket sockEchange ) {
		
		this.sockEchange = sockEchange;
		System.out.println("Je créer ma liste");	
	}
	
	public InetAddress getIp() {
		return sockEchange.getInetAddress();
	}
	
	public int getPort() {
		return sockEchange.getLocalPort();
	}
	
	public void sendFileList () {
		
		try {
			list=getArrayListMusics();
			OutputStream os = sockEchange.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			System.out.println("j'envoie bien");
			
			oos.writeObject(list);
			oos.flush();
			oos.close();

		} catch (IOException e) {
			System.out.println("Problème dans sendFileList");
			e.printStackTrace();
		} 

	}
	
	private ArrayList <String> getArrayListMusics () {
		String temp = choosePathDirectory();
		ArrayList<String> arrayTemp = new ArrayList<String>();
		ArrayList<String> toReturn = new ArrayList<String>();
		
		File directory = new File(temp);
		
		if(directory!=null) {
			arrayTemp = new ArrayList<String>(Arrays.asList(directory.list()));
		}
		for(String array : arrayTemp) {
			toReturn.add(getIp() +":" + getPort() + ":" + array);
		}
		return toReturn;
	}
		
	private String choosePathDirectory () {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    int retour = chooser.showOpenDialog(new JFrame());
	    
	    String directoryPath ;

	    if(retour == JFileChooser.APPROVE_OPTION) {
	    	directoryPath = chooser.getSelectedFile().getAbsolutePath();
	    } 
	    else {
	    	System.out.println("Le dossier n'a pas été choisi!"); 
	    	directoryPath = "none" ;
	    }  
	   
	    return directoryPath;
	}
	
	
	
	

}
