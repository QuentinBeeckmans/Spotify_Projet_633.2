package client1;

public class MainClient {

	public static void main(String[] args) {

		
		Connexion1ToServer_AcrossThread client1 = new Connexion1ToServer_AcrossThread();
		
		client1.dialogWithServeur();
		
	}

}
