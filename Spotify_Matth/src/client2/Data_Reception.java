package client2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class Data_Reception {

	public static void main(String[] args) {
		
		String serverName = /*"127.0.0.1"*/"192.168.0.14";
		InetAddress serverAddress = null ;
		int port = 11200;
		
		ServerSocket mySocketServListening ;
		Socket socketExange ;
		
		// String interfaceName = "wlan";
		
		int buffer = 5;
		
		
		try {
			
			serverAddress = InetAddress.getByName(serverName);			
			socketExange  = new Socket(serverAddress, port);
			
			System.out.println("I am connected");
			
			PrintWriter printOut = new PrintWriter(socketExange.getOutputStream());
			InputStreamReader inputReader ;
			inputReader = new InputStreamReader(socketExange.getInputStream());
			
			BufferedReader BuffInput ;
			BuffInput = new BufferedReader(inputReader);
			
			String messageToSend = "Je suis connecté chez toi";
			System.out.println("J'envoie : " + messageToSend);

			printOut.println(messageToSend);
			
			printOut.flush();
			
			
			String message_distant = BuffInput.readLine();
			System.out.println("I received: " + message_distant);
			printOut.close();
			
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
