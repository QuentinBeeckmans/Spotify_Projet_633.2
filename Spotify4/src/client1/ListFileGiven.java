package client1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

public class ListFileGiven implements Runnable {
	
	private List <String> listFileForServer = new ArrayList<String>() ;
	private List <String> listFileForServerTemp = new ArrayList<String>() ;
	Exchanger exchanger ;
	private String nomClientWhoGive;
	private String itemList;
	
	public ListFileGiven (Exchanger exchange, ArrayList <String> list) {
		this.exchanger = exchange;
		nomClientWhoGive = "Client Test 1";
		
		this.listFileForServer = list;
/*		for (String item : list) {
			
			listFileForServerTemp.add(item + " - chez : " + itemList);			
		}
*/				
	}
	
	public void run() {
				
		System.out.println(" - Liste c�t� client -");
		
		while (true) {
			
//			listFileForServer = listFileForServerTemp;		
		
			System.out.println("ListFileGiven listFileForServer" + listFileForServer);
			
			try {
				System.err.println("\t -> Liste vide du c�t� Client");
				listFileForServer = (List<String>) exchanger.exchange(listFileForServer);
			} catch (InterruptedException e){
				e.printStackTrace();
			}
		}
	}

}
