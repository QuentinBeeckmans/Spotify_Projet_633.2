package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.Exchanger;

public class ListFileExchanger implements Runnable {
	
	
	private List <String> listEchange ;
	Exchanger  exchanger;
	
	public ListFileExchanger (Exchanger exchange, List <String> listEchange) {
		
		this.listEchange = listEchange;
		this.exchanger = exchange;
//		this.client = client;
		
	}
	
	public void run () {
		

		
		while (true) {
			System.out.println("ListFileExchanger + ListFileExchanger + ListFileExchanger");
		
//			listEchange = listReceived ();
				
				listEchange = receptList ();
				
				for (int i =0; i< listEchange.size() ; i++) {	
					System.out.println(i + ": " + listEchange.get(i) );
				}
				
				
				System.out.println(listEchange);
		}
		
	}
	
	public List <String> receptList () {
		
		try {
			System.err.println("\t -> Liste vide côté client !");
			listEchange = (List<String>)exchanger.exchange(listEchange);
			
			for (int i =0; i< listEchange.size() ; i++) {	
				System.out.println(i + ": " + listEchange.get(i) );
			}
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return listEchange;
	}
	

}
