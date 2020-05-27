
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;


public class Dialogue implements Runnable {

	private Socket clientSocketOnServer;
	
	private ObjectOutputStream send;
	private ObjectInputStream reader;
	
	private MyList mList;
	
	private ArrayList<String> serverList = new ArrayList<String>() ;
	
	private int portListening; //pour savoir le port pour d�livrer la musique
	private Scanner scan = new Scanner(System.in);
	
	public Dialogue (Socket clientSocket, int port, MyList mList) {
		this.clientSocketOnServer = clientSocket;
		this.portListening=port;
		this.mList=mList;	
	}

	@Override
	public void run() {
		int choix;
    	
		do {
			System.out.println("Que voulez-vous faire ?");
			System.out.println("1 : Ajouter des musiques");
			System.out.println("2 : Ecouter des musiques");
			System.out.println("3 : Se d�connecter");
			choix = scan.nextInt();
		
	         switch(choix) {
	         
	         case 1: 
	        	 System.out.println("J'ajoute des musiques au serveur");
	        	 mList.sendFileList(this);
	        	 readList();
	        	 break;
	        	 
	         case 2: 
	        	 System.out.println("J'affiche les musiques du serveur: ");
	        	 
	        	 displayMusics(serverList);
	        	 break;
	        	 
	         case 3: 
		         System.out.println("Je sors");
		         try {
					Thread.sleep(5000);
					send.close();
			         reader.close();
			         clientSocketOnServer.close();
				} catch (InterruptedException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		         
	        	 break;
	         }
		}while(choix!=3);
	}

		     
	        

	synchronized public void readList () {
		
		try {
			reader = new ObjectInputStream(clientSocketOnServer.getInputStream());
			ArrayList<String> arrayList = (ArrayList<String>) reader.readObject();
			serverList = arrayList;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void sendObject(ArrayList<String> list) {
		try {
			send = new ObjectOutputStream(clientSocketOnServer.getOutputStream());
			send.writeObject(list);
			send.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	public void displayMusics(ArrayList<String> serverList2){
		   int cpt = 0;
		   System.out.println("Choisissez une musique: ");
		   for(String lineList: serverList2) {
			   System.out.println(cpt + " : "  + lineList);
			   cpt++;
		   }
		   int m=scan.nextInt();
		   
		   String choice = serverList2.get(m);
		   String [] address = choice.split("|");
		   
		   
		   System.out.println(address[0] + "-> "+ address[1] + "-> "+ address[2]);
		   //newServerConnection(address[0], address[1], address[2]);
		  
	}




	

	/*private void newServerConnection(String Ipaddress, String port, String musiquePath) {
		try {
			Socket exchangeSocket = new Socket(Ipaddress, Integer.parseInt(port));
			System.out.println("Je suis connect� au client pour �couter");
			PrintWriter writerPath = new PrintWriter(exchangeSocket.getOutputStream());
			writerPath.write(musiquePath);
			InputStream is = new BufferedInputStream(exchangeSocket.getInputStream());
			
			//j'ajoute pour stream
			SimpleAudioPlayer player = new SimpleAudioPlayer(is);
			player.play();
			Thread.sleep(player.clip.getMicrosecondLength());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}*/
	   

}
