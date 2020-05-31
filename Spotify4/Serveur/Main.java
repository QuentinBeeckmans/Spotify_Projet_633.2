import java.util.logging.Level;

import ClientsLogsConstruct.LoggerWithFileHandler;
import Serveur.Main;
import Serveur.Server;

/**
 * Main
 * to run the server
 * @author Administrator
 *
 */
public class Main {

	public static void main(String[] args) {
		
		LoggerWithFileHandler logsServer = new LoggerWithFileHandler("ServerLogs");

		try {
			Server server = new Server(4501, logsServer);
		} catch (IllegalArgumentException e){
			logsServer.addHandler(Main.class.getName(), Level.SEVERE, "Initialisation of Server services crashed", e.toString());
		}
	}
}
