
public class Main {

	public static void main(String[] args) {
		
		//Mon serveur chez mon client
		//Je pr�pare un socket server qui �coute
		Server server = new Server();
		
		//c'est le Client qui se connecte
		ClientSocket myClient = new ClientSocket(server.getPort());
		myClient.exchangeSocket(); //cela me cr�e un socket serveur
	}

}
