
package client2;

import server.ConnexionMulti_In;

public class MainClient {

	public static void main(String[] args) {

		Connexion1ToServer_AcrossThread client1 = new Connexion1ToServer_AcrossThread(4500);
		
		ConnexionMulti_In ClientBecomingServeur = new ConnexionMulti_In(5000);
		
		ConnexionToClient clientForStream1 = new ConnexionToClient(5000);


	}

}
