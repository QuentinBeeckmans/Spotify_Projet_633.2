package client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;

import LogsConstructor.LoggerWithFileHandler;

/**
 * This runnable class is used to transfer music
 * 
 * @author Quentin Beeckmans - Mathieu Roux
 * @version 1.0
 * @since 2020-05-30
 */
public class ClientServeur implements Runnable {

	private Socket clientSocket;
	private LoggerWithFileHandler logsServer;
	private BufferedReader reader;

	/**
	 * That construct a server Client that's allows to wait a connection
	 * 
	 * @param clientSocket
	 * @param logsServer
	 */
	public ClientServeur(Socket clientSocket, LoggerWithFileHandler logsServer) {
		this.clientSocket = clientSocket;
		this.logsServer = logsServer;
	}

	/**
	 * Runnable method to do a thread for reading
	 */
	@Override
	public void run() {
		try {
			reading();
		} catch (Exception e) {
			logsServer.addHandler(ClientServeur.class.getName(), Level.SEVERE, "Client listen socket turned on",
					e.toString());
			e.printStackTrace();
		}

	}

	/**
	 * This method sends as argument's String from readLine method into streaMusic
	 * method
	 */
	synchronized public void reading() {

		try {
			String line = null;
			line = readLine();
			streamMusic(line);
			// logsServer.addHandler(ClientServeur.class.getName(), Level.WARNING, "Client
			// received list from server","");

		} catch (ClassNotFoundException | IOException e) {
			logsServer.addHandler(ClientServeur.class.getName(), Level.SEVERE, "Client didn't receive list",
					e.toString());
			e.printStackTrace();
		}

	}

	/**
	 * This method simply returns the first String that this method read from buffer
	 * 
	 * @return String to reading method
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private String readLine() throws IOException, ClassNotFoundException {
		reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		String line = null;
		while (true) {
			line = reader.readLine();

			if (line != null) {
				break;
			}
		}

		return line;
	}

	/**
	 * This method sends the number of byte audio into Socket Outputstream
	 * 
	 * @param path music choice
	 */
	public void streamMusic(String path) {
		try {
			File myFile = new File(path);
			byte[] mybytearrea = new byte[(int) myFile.length()];

			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile)); // explications on doit
																							// aller chercher ce qu'il y
																							// a dans le fichier et le
																							// mettre dans le buffer
			bis.read(mybytearrea, 0, mybytearrea.length);

			OutputStream os = clientSocket.getOutputStream();

			os.write(mybytearrea, 0, mybytearrea.length);
			os.flush();

			// logsServer.addHandler(ClientServeur.class.getName(), Level.WARNING,
			// "Streaming is going on",""); //PROVOQUE ERREUR

		} catch (IOException e) {
			logsServer.addHandler(ClientServeur.class.getName(), Level.SEVERE, "Streaming crashed", e.toString());
			e.printStackTrace();
		}

	}

}
