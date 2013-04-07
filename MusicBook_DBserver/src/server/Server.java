package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {

	private static ServerSocket serverSocket = null;
	private static int PORT = 9999;
	private static Boolean stop_server;
	
	public static void main(String[] args) {
		
		System.out.println("Démarrage du serveur...");
		
		try {
			serverSocket = new ServerSocket( PORT );
			System.out.println("Ecoute du port " + PORT );
			
			stop_server = false;
			
			// Boucle qui attends des connexions.
			while( !stop_server ){
				
				Socket socket = serverSocket.accept();
				Thread th = new ClientThread( socket );
				th.start();
					
			}
			
			System.out.println("Extinction du serveur");

			serverSocket.close();
			
		} catch (IOException e) {
			System.out.println(" Erreur d'initialisation de la socket serveur. ");
		}

	}
	
	public static void stopServer() throws UnknownHostException, IOException{
		
		stop_server = true;
		Socket s = new Socket( "127.0.0.1",PORT );
		s.close();
	}	

}
