import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;
//COTE CLIENT
public class Client implements Runnable {
	private Socket clientSocket;
	private String musicChoice;
	
	private String reponse;
	
	//je récupère les infos de mon client 2 pour lui envoyer la musique
	public Client(Socket clientSocket, String musiqueChoice) {
		this.clientSocket=clientSocket;
		this.musicChoice=musiqueChoice;
	}

	@Override
	public void run() {
		try {
			streamMusic(musicChoice);
			clientSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void streamMusic(String path) {
		try {
			File myFile = new File(path);
			byte[] mybytearrea = new byte[(int)myFile.length()]; //très important de connaitre la taille de notre fichier !!!!
			System.out.println("taille fichier: " + myFile.length()); //il va falloir trouver comment envoyer la taille du fichier
			
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile)); //explications on doit aller chercher ce qu'il y a dans le fichier et le mettre dans le buffer
			bis.read(mybytearrea,0,mybytearrea.length);
			
			OutputStream os = clientSocket.getOutputStream();
			os.write(mybytearrea,0,mybytearrea.length);
			os.flush();
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}




