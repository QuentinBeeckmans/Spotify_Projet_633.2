package client2;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ReadSwitch implements Runnable {
	
	private InputStream is = null;
//	private OutputStream out = null;
//	private DataInputStream readObj;
	private BufferedInputStream reader;

	private Socket socket;
	
	private String response = null;

	
	public ReadSwitch(Socket socket) {

		this.socket = socket;
	
	}
	
	@Override
	public void run() {
		
//		isRunning = true;
		


	}

	public synchronized String readSwitch() {
	
		
		System.out.println("test socketReadSwitch : " + socket.getPort());
		
		
		try {
/*			Thread.currentThread().sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
*/		
		response = null;
		int stream;

//		byte[] b = new byte[0];

		byte [] b = new byte[4096];
		
		/* isRead */ is = socket.getInputStream();
		reader = new BufferedInputStream(is);

//		while (response == null && response =="") {

		stream = reader.read(b);
		response = new String(b, 0, stream);

		// remise à zèro du buffer (sécurité peut-être pas obligatoire)
//		b = new byte[0];
				
//		}
		
		System.out.println("response " + response);
		
		System.out.println("reader : " + reader.toString());
		
		}catch ( /* ClassNotFoundException | */ IOException e) {
			e.printStackTrace();
		} 

		   	
		return response;
	 
 
	 
}

public BufferedInputStream getReader() {
	return reader;
}

public  InputStream getInPutStreamBuffer() {
	return is; 
}
	
}
