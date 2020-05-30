

import java.io.File;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 * This class implements method to manage functions to create list from each client
 * @author Quentin Beeckmans - Mathieu Roux
 * @version 1.0
 * @since 2020-05-30
 */
public class MyList {
	
	public ArrayList<String> list;
	
	/**
	 * Allows to create arrayList with port listener from client server and Ip from client
	 * @param d
	 */
	synchronized public void sendFileList (Dialogue d) {
		ArrayList<String> temp = new ArrayList<String>();
		list=getArrayListMusics();
		
		if(!list.isEmpty()) {
			for(String item:list) {
				temp.add(d.getPort() + ";" + d.getIp() + ";" +  item);
			}
			d.sendObject(temp);
		}
	}
	/**
	 * This method allows 
	 * @return ArrayList
	 */
	public ArrayList<String> getMyList() {
		
		return list;
		
	}
	
	/**
	 * This method allows to get back an ArrayList from a file
	 * @return an ArrayList
	 */
	public ArrayList <String> getArrayListMusics () {
		ArrayList<String> arrayTemp = new ArrayList<String>();
		
		String temp = choosePathDirectory();
		File[] files = new File(temp).listFiles();

		
		for(File file : files){
			  if(file.isFile()){
				  arrayTemp.add(file.getAbsolutePath());
			  }
			}
		
		return arrayTemp;
	}
		
	/**
	 * This method allows to open a JFileChooser and get back path from directories only
	 * @return
	 */
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
