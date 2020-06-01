import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

//COTE CLIENT
public class ClientServeur implements Runnable {
	private Socket clientSocket;

	private BufferedReader reader;
	
	
	//je rï¿½cupï¿½re les infos de mon client 2 pour lui envoyer la musique
	public ClientServeur(Socket clientSocket) {
		this.clientSocket=clientSocket;
	}

	@Override
	public void run() {
		try {
			
			reading();
			
			//clientSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void reading() {
		while(true) {
			try {
				String line = null;
				line=readLine();
				System.out.println(line);
				streamMusic(line);
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}
	}
	
	 private String readLine() throws IOException, ClassNotFoundException{
		 reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		 String line = null;
			while(true) {
				line = reader.readLine();
				
				if(line != null) {
					break;
				}
			}
			
			return line;
	}

	public void streamMusic(String path) {
		try {
			File myFile = new File(path);
			byte[] mybytearrea = new byte[(int)myFile.length()]; //trï¿½s important de connaitre la taille de notre fichier !!!!
			System.out.println("taille fichier: " + myFile.length()); //il va falloir trouver comment envoyer la taille du fichier
			
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile)); //explications on doit aller chercher ce qu'il y a dans le fichier et le mettre dans le buffer
			bis.read(mybytearrea,0,mybytearrea.length);
			
			OutputStream os = clientSocket.getOutputStream();
			os.write(mybytearrea,0,mybytearrea.length);
			os.flush();
			//os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}




