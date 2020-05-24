package client1;

import java.net.Socket;
import java.util.ArrayList;

public class TransfertList implements Runnable {

	ReadList entryList;
	GiveFichier sortiList;
	ArrayList <String> listEntrante;
	ArrayList <String> listSortante;
	Socket socket;
	
	public TransfertList(ArrayList <String> list, Socket socket) {

		this.listEntrante = list;
		this.socket = socket;

	}
	
	
	@Override
	public void run() {
		
		if (listEntrante.isEmpty()) {
			listEntrante.add("Liste vide");
		}
		sortiList = new GiveFichier (listEntrante, socket);
//		sortiList.run();
		
		entryList = new ReadList (socket);
//		entryList.run();
		listSortante = entryList.readList();
		
	}
	
	public ArrayList <String> getList() {
		
		
		
		return listSortante;
		
	}
	
}
