package client2;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ReadList implements Runnable {

	private InputStream is = null;
	private InputStream ips;
	private InputStreamReader ipsr;
	private OutputStream out = null;
	private BufferedReader br;
	private ArrayList<String> arraylist = new ArrayList<>();
	private File list = null;
	private boolean isRunning = false;
	private Socket socket;
	private BufferedInputStream reader = null;
	private File newTempFile;

	public ReadList(Socket socket) {

		this.socket = socket;

		try {
			newTempFile = File.createTempFile("MaListTemp", ".txt");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {

		isRunning = true;

	}

	public boolean isRunning() {

		if (isRunning) {
			return true;
		}

		return false;
	}

	public File getTempListFile() {

		System.out.println("Run READLIST -- ");

		list = newTempFile;

		return list;
	}

	public ArrayList<String> readList() {

		try {

			byte[] bytes = new byte[4096];

			is = socket.getInputStream();
			out = new FileOutputStream(newTempFile);

			int count = 0;

			while ((count = is.read(bytes)) > 0) {
				out.write(bytes, 0, count);

			}

			out.flush();

			bytes = new byte[0];

			ips = null;

			ips = new FileInputStream(newTempFile);

			ipsr = new InputStreamReader(ips);
			br = new BufferedReader(ipsr);
			String ligne;

			if ((ligne = br.readLine()) != null) {

				// parcour du fichier
				while ((ligne = br.readLine()) != null) {

					arraylist.add(ligne);

				}
			}

			if (arraylist.isEmpty()) {
				System.out.println("En attente de réception d'une list non vide");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return arraylist;
	}

	public void close() throws IOException {

		is.close();
		ips.close();
		ipsr.close();
		out.close();
		br.close();

	}

}
