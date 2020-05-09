package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
			
//			listEchange = listReceived ();
			
			for (int i =0; i< listEchange.size() ; i++) {	
				System.out.println(i + ": " + listEchange.get(i) );
			}
						
		}
	}
	
	

}
