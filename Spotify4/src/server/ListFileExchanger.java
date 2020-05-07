package server;

import java.util.List;
import java.util.concurrent.Exchanger;

public class ListFileExchanger implements Runnable {
	
	
	private List <String> listEchange ;
	Exchanger  exchanger;
	
	public ListFileExchanger (Exchanger exchange) {
		
		this.exchanger = exchange;
//		this.client = client;
		
	}
	
	public void run () {
		
		while (true) {
			
			for (int i =0; i< listEchange.size() ; i++) {	
				System.out.println(i + ": " + listEchange.get(i) );
			}
			
			try {	
				System.err.println("\t -> Liste vide du côté Client");
				listEchange = (List <String>)exchanger.exchange(listEchange);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}		
		}
	}

}
