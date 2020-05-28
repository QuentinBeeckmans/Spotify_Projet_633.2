package client2;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class TransmitList implements Runnable {

	private OutputStream os = null;
	private InputStream in = null;
	private File list = null;
	private Socket socket;

	public TransmitList(File listFile, Socket socket) {

		this.list = listFile;
		this.socket = socket;

	}

	@Override
	public void run() {

	}

	public synchronized void sendFile() {

		try {

			if (socket.isClosed()) {
				System.out
						.println("Activité socket de transfert du fichier est arrêtée ! \n Veuillez nous en excuser.");

			}

			os = socket.getOutputStream();

			byte[] bytes = new byte[4096];
			in = new FileInputStream(list);

			int count;
			while ((count = in.read(bytes)) > 0) {
				os.write(bytes, 0, count);
			}

			os.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void close() throws IOException {

		in.close();
		os.close();

	}

}
