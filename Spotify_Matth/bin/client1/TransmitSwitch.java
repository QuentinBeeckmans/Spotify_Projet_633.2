package client2;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class TransmitSwitch implements Runnable {

	private OutputStream os = null;
	private InputStream in = null;
	private PrintWriter writerSwitch = null;
	private String choice = null;
	private Socket socket;

	public TransmitSwitch(String choice, Socket socket) {

		this.choice = choice;
		this.socket = socket;

	}

	@Override
	public void run() {

	}

	public synchronized void sendSwitch() {

		try {

			os = socket.getOutputStream();

			writerSwitch = new PrintWriter(os, true);

			writerSwitch.write(choice);

			writerSwitch.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() throws IOException {

		os.close();
		writerSwitch.close();
	}
}
