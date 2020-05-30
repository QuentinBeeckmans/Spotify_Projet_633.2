import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Logger;

/**
 * This class implements Client Socket.
 * @author Quentin Beeckmans - Mathieu Roux
 * @version 1.0
 * @since 2020-05-30
 */
public class ClientSocket {
	public final static Logger ClientLogger = Logger.getLogger("ClientLog");
	
    private Socket clientSocket;

    private InetAddress serverAddress;
    private String serverName = "192.168.56.1";   //"10.0.2.5"
    private MyList mList = new MyList();
    
	private int listenerPort; //c'est le port d'écoute de mon client

	private Calendar currentDate = Calendar.getInstance();
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-H-mm-ss");
	private String dateNow = formatter.format(currentDate.getTime());

	/**
	 * Client socket creating
	 * @param port listener 
	 */
   public ClientSocket(int port) {
	   this.listenerPort=port;  
	   exchangeSocket();
   }
   
   public void exchangeSocket() {
		try {
			serverAddress = InetAddress.getByName(serverName);
			clientSocket = new Socket(serverAddress, 4501); //pour crï¿½er mon socketClient je me connecte au port dispo PAS le port d'ï¿½coute
			} catch (Exception e) {
				ClientLogger.severe("Exception " + e.toString());
				e.printStackTrace();
			}
			
			Thread t = new Thread(new Dialogue (clientSocket, listenerPort, mList));
			t.start();
		}
    		
    	
   
   
   
   }

