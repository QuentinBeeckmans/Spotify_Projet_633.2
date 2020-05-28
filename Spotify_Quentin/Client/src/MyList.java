

import java.io.File;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class MyList {
	
	public ArrayList<String> list;
	
	
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
	
	public ArrayList<String> getMyList() {
		
		return list;
		
	}
	
	public ArrayList <String> getArrayListMusics () {
		ArrayList<String> arrayTemp = new ArrayList<String>();
		
		String temp = choosePathDirectory();
		//File directory = new File(temp);
		File[] files = new File(temp).listFiles();
		/*
		if(directory!=null) {
			arrayTemp = new ArrayList<String>(Arrays.asList(directory.list()));
		}*/
		
		for(File file : files){
			  if(file.isFile()){
				  arrayTemp.add(file.getAbsolutePath());
			  }
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
