
public class Main {

	public static void main(String[] args) {
		
		
		Server server = new Server();
		
		//c'est le SERVEUR d'�coute (qui attend qu'on se connecte)
		MainServer mainServer = new MainServer(server.getPort());
		mainServer.exchangeSocket(); //cela me cr�e un socket serveur
	}

}
