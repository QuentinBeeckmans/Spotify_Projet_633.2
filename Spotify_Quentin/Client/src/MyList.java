
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
	private int portListening;
	private ArrayList<String> list;
	
	private BufferedReader reader;
	//private PrintWriter send;
	
	public MyList (Socket sockEchange, int portListening ) {
		
		this.sockEchange = sockEchange;
		this.portListening = portListening;
		System.out.println("Je créer ma liste");	
	}
	
	public InetAddress getIp() {
		return sockEchange.getInetAddress();
	}
	
	public int getPort() {
		return portListening;
	}
	
	synchronized public void sendFileList (DialogueActionGUI d) {
		synchronized(this) {
			list=getArrayListMusics();
		}
		
		new Thread(new Runnable() {
			public void run() {
				for(String temp:list) {
					//System.out.println("add|" + getIp() + "|" + getPort() + "|" +  temp);
					d.toSend("add|" + getIp() + "|" + getPort() + "|" +  temp);
					 
				}
				
			}
		}).start();
		

	}
	
	public ArrayList getMyList() {
		
		return list;
		
	}
	
	public void removeFileList (DialogueActionGUI d) {
		
		ArrayList <String> temp=getMyList();

		System.out.println("removeFileList");
		
		for(String temp1:temp) {
			d.toSend("rem|" + getIp() + "|" + getPort() + "|" +  temp1);
		}
		

	}
	
	public ArrayList <String> getArrayListMusics () {
		ArrayList<String> arrayTemp = new ArrayList<String>();
		
		String temp = choosePathDirectory();
		File directory = new File(temp);

		if(directory!=null) {
			arrayTemp = new ArrayList<String>(Arrays.asList(directory.list()));
		}
		
		return arrayTemp;
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
