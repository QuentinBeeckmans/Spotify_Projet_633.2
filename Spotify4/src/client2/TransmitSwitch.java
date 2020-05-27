package client2;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class TransmitSwitch implements Runnable{

	private OutputStream os = null;
	private InputStream in = null;
	private PrintWriter writerSwitch = null;
	private String choice = null;
	private Socket socket;
//	private InetAddress serverAdress;
//	private PrintWriter writer;
	
	private ObjetSerialisable objList;

	
	public TransmitSwitch( /* File list */ String choice, Socket socket) {

//		this.writer = writer;
		this.choice = choice;
		this.socket = socket;
		
	}
	
	@Override
	public void run() {
		
	}
	
	public synchronized void sendSwitch (){
		//	   Thread t = new Thread();
				try {
					
					if(socket.isClosed()) {
						System.out.println("Activité socket GiveFichier est arrêtée !!!!!!!!!!!!");
					
					}
					
					os = socket.getOutputStream(); 
										
					writerSwitch = new PrintWriter(os, true);
					
					writerSwitch.write(choice);
			
					writerSwitch.flush();
					
					System.out.println("Activité sendSwitch !!!!!!!!!!!!");

				} catch (IOException e) {
					e.printStackTrace();
				} 
	}
	
	public OutputStream getOutPutStreamBuffer () {
		return os;
	}
	
	public  PrintWriter getWriter () {
		return writerSwitch; 
	}
	
}
