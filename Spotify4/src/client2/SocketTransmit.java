package client2;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class SocketTransmit {

	private Socket socket;

	public SocketTransmit(InetAddress inetAddress, int port) throws IOException {

		this.socket = new Socket(inetAddress, port);

	}

	public Socket getSocket() {
		return socket;
	}

}
