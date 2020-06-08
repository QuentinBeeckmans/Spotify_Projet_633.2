package client;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;

import LogsConstructor.LoggerWithFileHandler;

/**
 * This runnable class is used to ask what client wants to do
 * 
 * @author Quentin Beeckmans - Matthieu Roux
 * @version 1.0
 * @since 2020-05-30
 */

public class Dialogue implements Runnable {

	private Scanner scan = new Scanner(System.in);

	private Socket clientSocketOnServer;
	private LoggerWithFileHandler logsServer;

	private ObjectOutputStream send;
	private ObjectInputStream reader;

	public MyList mList;
	public ArrayList<String> serverList = new ArrayList<String>();

	private int portListening;

	private boolean emptylist = false;

	/**
	 * This constructor implements an interface to ask some questions at the client
	 * 
	 * @param clientSocket
	 * @param port         which is listening
	 * @param mList
	 */
	public Dialogue(Socket clientSocket, int port, MyList mList, LoggerWithFileHandler logsClient) {
		this.clientSocketOnServer = clientSocket;
		this.portListening = port;
		this.mList = mList;
		this.logsServer = logsClient;

		logsServer.addHandler(Dialogue.class.getName(), Level.INFO,
				"New client IP: " + clientSocketOnServer.getInetAddress(),
				"port listening " + String.valueOf(port));
	}

	/**
	 * That returns port listener from client server in integer
	 * 
	 * @return Integer
	 */
	public String getPort() {
		return Integer.toString(portListening);
	}

	/**
	 * That returns ip from client server
	 * 
	 * @return
	 */
	public String getIp() {
		return clientSocketOnServer.getInetAddress().toString();
	}

	/**
	 * This method allows to choice to send and listen a music from an other client
	 * 
	 * @throws IOException if closing socket have error
	 */
	@Override
	public void run() {
		String choix;
		System.out.println("Welcom at Spotify:");

		// FIRST QUESTION
		do {
			System.out.println("Add music: (y)");
			choix = scan.next();
		} while (!choix.toLowerCase().equals("y"));

		mList.sendFileList(this);
		logsServer.addHandler(Dialogue.class.getName(), Level.INFO, "Client choosed : add music on server", "");

		readList();

		// SECOND QUESTION
		if (!emptylist) {

			do {
				System.out.println("Display available musics on the server : (y)");
				choix = scan.next();
			} while (!choix.toLowerCase().equals("y"));
			logsServer.addHandler(Dialogue.class.getName(), Level.INFO, "Client choosed : display available music", "");
			displayMusics(serverList);
			
			
		} else {
			System.out.println("You are alone on this server. There are no list to stream.");
		}
		
		try {
			clientSocketOnServer.close();
			reader.close();
			send.close();
		} catch (IOException e) {
			logsServer.addHandler(ClientSocket.class.getName(), Level.SEVERE, "Problem with closing socket.",
					e.toString());
			e.printStackTrace();
		}
		
	}

	/**
	 * This method allows to get an arrayList from socket InputStream
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	synchronized public void readList() {

		try {
			reader = new ObjectInputStream(clientSocketOnServer.getInputStream());
			@SuppressWarnings("unchecked")
			ArrayList<String> arrayList = (ArrayList<String>) reader.readObject();
			if (arrayList == null) {
				emptylist = true;
			}
			serverList = arrayList;

		} catch (ClassNotFoundException | IOException e) {
			logsServer.addHandler(ClientSocket.class.getName(), Level.SEVERE, "Problem with list of music reception",
					e.toString());
			e.printStackTrace();
		}

	}

	/**
	 * This method allows send whatever an arrayList from socket OutputStream
	 * 
	 * @throws IOException
	 */
	public void sendObject(ArrayList<String> list) {
		try {
			send = new ObjectOutputStream(clientSocketOnServer.getOutputStream());
			send.writeObject(list);
			send.flush();
		} catch (IOException e) {
			logsServer.addHandler(Dialogue.class.getName(), Level.SEVERE, "sendObject crash", e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * This method allows to display an arrayList and choose a music to play
	 * 
	 * @throws IllegalThreadStateException
	 * @throws ArrayIndexOutOfBoundsException
	 * @throws NullPointerException
	 */
	public void displayMusics(ArrayList<String> serverList2) {
		int cpt = 0;
		try {
			System.out.println("Choose a music :");

			for (String lineList : serverList2) {
				if (lineList == null) {
					System.out.println("No list of music to share");

				} else {
					System.out.println(cpt + " : " + lineList.substring(lineList.lastIndexOf("\\") + 1));
					cpt++;
				}
			}
			int m = scan.nextInt();

			int check=cpt-1;
			while(m <0 || m >check) {
				System.out.println("Please choose from the range of proposals :");
				m = scan.nextInt();
			}
			
			logsServer.addHandler(Dialogue.class.getName(), Level.INFO, "Music choice: " + String.valueOf(m), "");

			String choice = serverList2.get(m);
			String[] address = choice.split(";");
			String str = address[1];
			String strNew = str.replace("/", "");

			newServerConnection(strNew, address[0], address[2]); // to connect to other client to play music

		} catch (IllegalThreadStateException | ArrayIndexOutOfBoundsException | NullPointerException e) {
			logsServer.addHandler(Dialogue.class.getName(), Level.SEVERE, "Display list crashed", e.toString());
		}
	}

	/**
	 * This method allows you to connect with the client that has the chosen music
	 * and get the audio stream after
	 * 
	 * @throws Exception
	 */
	private void newServerConnection(String Ipaddress, String port, String musiquePath) {
		try {
			Socket exchangeSocket = new Socket(Ipaddress, Integer.parseInt(port));
			System.out.println("I'm connected to the client to listen music.");

			logsServer.addHandler(Dialogue.class.getName(), Level.INFO, "Client connected", "");

			PrintWriter writerPath = new PrintWriter(exchangeSocket.getOutputStream());
			writerPath.println(musiquePath);
			writerPath.flush();

			InputStream is = new BufferedInputStream(exchangeSocket.getInputStream());

			SimpleAudioPlayer player = new SimpleAudioPlayer(is, logsServer);
			player.play();

			logsServer.addHandler(Dialogue.class.getName(), Level.INFO, "Début du streaming", musiquePath);
			
			System.out.println("To stop music press S");
			String reponse = scan.next();
			
			while (player.clip.isRunning()) {
				if (reponse.toLowerCase().equals("s")) {
					player.pause();
					player.close();
				}
			}
			is.close();
			exchangeSocket.close();
		} catch (Exception e) {
			logsServer.addHandler(Dialogue.class.getName(), Level.SEVERE, "New Server Connexion crash", e.getMessage());
			e.printStackTrace();
		}
	}
}