
public class Main {

	public static void main(String[] args) {
		
		Server serverMain = new Server(4501);
		serverMain.listenSocket();
	}
}
