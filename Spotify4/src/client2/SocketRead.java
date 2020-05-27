package client2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketRead {

	private Socket socket;
	private ServerSocket servSock;

	public SocketRead(int port) throws IOException {

		this.servSock = new ServerSocket(port);
		this.socket = servSock.accept();
	}

	public Socket getSocket() {
		return socket;
	}

	public ServerSocket getServerSocket() {
		return servSock;
	}

}
