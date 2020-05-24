import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;

public class Client implements Runnable {
	private Socket clientSocket;
	private String clientId;
	
	private Hashtable <String, ArrayList> globalList = new Hashtable<String, ArrayList>();
	private ArrayList<String> clientList ;
	
	private ObjectInputStream reader;
	private ObjectOutputStream send;
	
	private BufferedReader bufferReader;
	//ip
	private String address; 
	private int port;
	
	private String reponse;
	
	public Client(Socket clientSocket, int clientId, ArrayList<String> clientList) {
		this.clientSocket=clientSocket;
		this.clientId=Integer.toString(clientId);
		this.clientList=clientList;
	}

	@Override
	public void run() {
		try {
			System.out.println("Client n° " + clientId + " IP" + clientSocket.getInetAddress());
			
			reader = new ObjectInputStream(clientSocket.getInputStream());
			send = new ObjectOutputStream(clientSocket.getOutputStream());
			
			bufferReader= new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			addListClientToServer();
			
			reponse = readLine();
			
			switch( reponse){
              
			case "1" : //getlist
				System.out.println("Côté serveur on me demande d'envoyer la liste des musiques");
 	          sendList(this); //je n'envoie rien si j'ai le même id
              break;
                 
            case "3" :
               System.out.println("La connexion va être arrêtée");
               
               Thread.sleep(5000);          
               remove(this);
               reader.close();
               send.close();
               bufferReader.close();
               clientSocket.close();
               break;
             
            default : 
            	System.out.println("Commande inconnu !");                     
           }

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public int getClientId() {
		return Integer.parseInt(clientId);
	}
	public String getIpAddress() {
		return address;
	}

	public void addListClientToServer() {

		   try {
				clientList = (ArrayList<String>) reader.readObject();
				
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		   
			globalList.put(clientId, clientList);	
	}
	
	private void sendList (Client c) throws IOException{
		Set<String> clients = globalList.keySet();
		ArrayList<String> lisToSend;
		
		for(String key: clients){
			if(key != Integer.toString(c.getClientId())) {
				lisToSend = globalList.get(key);
				send.writeObject(lisToSend);
				send.flush();
			}
        }
   
	}

	 public void remove(Client c) {
		 globalList.remove(c.getClientId());
	 }
	 
	 private String readLine() throws IOException{
			String line = null;
			while(true) {
				line = bufferReader.readLine();
				
				if(line != null) {
					break;
				}
			}
			
			return line;
		}
}





