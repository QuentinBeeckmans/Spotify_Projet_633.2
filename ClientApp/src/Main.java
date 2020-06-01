/**
 * The Main program implements an application that start two class Client
 * one simply Client with Client server port and one server Client.
 * 
 * @author Quentin Beeckmans - Mathieu Roux
 * @version 1.0
 * @since 2020-05-30
 */
public class Main {

	public static void main(String[] args) {

		Server server = new Server();
		
		ClientSocket myClient = new ClientSocket(server.getPort());
	}

}
