

import java.io.File;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class MyList {
	
	private Socket sockEchange;
	private int portListening;
	private ArrayList<String> list;
	
	public InetAddress getIp() {
		return sockEchange.getInetAddress();
	}
	
	public int getPort() {
		return portListening;
	}
	
	synchronized public void sendFileList (Dialogue d) {

		list=getArrayListMusics();
		
		if(!list.isEmpty()) {
			d.sendObject(list);
		}
		
		
	}
	
	public ArrayList<String> getMyList() {
		
		return list;
		
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
