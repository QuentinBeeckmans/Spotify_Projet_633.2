
public class Main {

	public static void main(String[] args) {
		
		
		Server server = new Server();
		
		//c'est le server d'�coute
		MainServer mainServer = new MainServer(server.getPort());

	}

}
