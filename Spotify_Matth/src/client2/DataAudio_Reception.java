package client2;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.channels.FileChannel;

public class DataAudio_Reception {

	public static void main(String[] args) {
		
		BufferedInputStream bis ;
		BufferedOutputStream bos ;
		
		String test = "vais-je réussir à ecrire sur ton ordi ?"; 
		FileChannel in;
		try {
			in = new FileInputStream("source.txt").getChannel();
		
		FileChannel out = new FileOutputStream("destination.txt").getChannel();
	
		out.transferFrom(in, 0, in.size());
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String serverName = /*"127.0.0.1"*/"10.93.2.141";
		InetAddress serverAddress = null ;
		int port = 5555;
		
		ServerSocket mySocketServListening ;
		Socket socketExange ;
		
		String interfaceName = "wlan1";
		
	//	InetAddress serverAddress = null ;
		int buffer = 5;
		
		
		try {
			
			serverAddress = InetAddress.getByName(serverName);
			
			socketExange  = new Socket(serverAddress, port);
			
			System.out.println("I am connected");
			
			
			
			socketExange.close();
			
			System.out.println("All sockets are disconneted !");
	
			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}	
}
