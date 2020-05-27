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
	private BufferedInputStream reader = null;

	private Socket socket;

	private String response = null;

	public ReadSwitch(Socket socket) {

		this.socket = socket;

	}

	@Override
	public void run() {

	}

	public synchronized String readSwitch() {

		try {

			response = null;
			int stream;
			byte[] b = new byte[4096];

			is = socket.getInputStream();
			reader = new BufferedInputStream(is);

			stream = reader.read(b);
			response = new String(b, 0, stream);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return response;

	}

	public void close() throws IOException {
		reader.close();
		is.close();
	}
}
