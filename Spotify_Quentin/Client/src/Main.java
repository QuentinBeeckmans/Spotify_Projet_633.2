
public class Main {

	public static void main(String[] args) {
		
		//Mon serveur chez mon client
		//Je prépare un socket server qui écoute
		Server server = new Server();
		
		//c'est le Client qui se connecte
		ClientSocket myClient = new ClientSocket(server.getPort());
		myClient.exchangeSocket(); //cela me crée un socket serveur
	}

}
