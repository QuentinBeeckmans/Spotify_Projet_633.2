

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
   
    private ServerSocket serverSocket = null;
   private MusicRepository musicRepository;
   private int port;
   
   public Server() {
	   try {
		   serverSocket = new ServerSocket();
		} catch (Exception e) {
			// TODO: handle exception
		}
	   
	   port = serverSocket.getLocalPort();
	   listen();
   }
   
   public int getPort() {return port;}

    public void setMusicRepository (MusicRepository musicRepository) {
    	this.musicRepository = musicRepository;
    }
    
    private void listen() {
    	new Thread(new Runnable() {
    		public void run() {
    			while(true) {
    				Socket clientSocket = null;
    				
    				try {
						clientSocket = serverSocket.accept();
					} catch (Exception e) {
						e.printStackTrace();
					}
    				
    				System.out.println("connection request received");
    				
    				Thread t = new Thread(new Client(clientSocket, musicRepository));
    				t.start();
    			}
    		}
    	}).start();
    }
    }


    
    @Override
    public void run() {

        try {
            listeningSocket = new ServerSocket(4501);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true){
            Socket exchangeSocket = null;

            try {
                exchangeSocket = listeningSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String msg = null;
            try {
                msg = readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(msg != null);{
               if(msg.equals("I want this music")){
                   try {
                       sendFile(exchangeSocket);
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }
            }
            try {
                exchangeSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private String readLine() throws IOException
    {
        BufferedReader sin = null;
        String line = null;
        while (true) {
            line = sin.readLine();

            if (line != null) {
                break;
            }
        }

        return line;
    }

    public void sendFile(Socket exchangeSocket) throws IOException {
        String msg = readLine();
        toReturn = client.getFile(msg);
        byte[] mybytearrea = new byte[(int)toReturn.length()];

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(toReturn));
        bis.read(mybytearrea,0,mybytearrea.length);

        OutputStream os = exchangeSocket.getOutputStream();
        os.write(mybytearrea,0,mybytearrea.length);
        os.flush();
        os.close();
    }
}
