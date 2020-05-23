
public class Main {

	public static void main(String[] args) {
		
		
		Server server = new Server();
		
		//c'est le server d'écoute
		MainServer mainServer = new MainServer(server.getPort());

	}

}
