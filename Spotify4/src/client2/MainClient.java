

package client2;

import server.ConnexionMulti_In;

public class MainClient {

	public static void main(String[] args) {

		Connexion1ToServer_AcrossThread client1 = new Connexion1ToServer_AcrossThread();
		
		ConnexionToClient connexxionToClient1 = new ConnexionToClient();
		
//		ConnexionMulti_In serveurinit = new ConnexionMulti_In(5000);


	}

}
