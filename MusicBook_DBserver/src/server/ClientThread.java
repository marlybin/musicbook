package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;

import database.MusicBookDatabase;
import database.XMLRequest;
import database.XMLResponse;

/*
 * Une instance de cette classe est associée a chaque client.
 * La requête du client est lue, elle est effectuée et la réponse renvoyée au client.
 */
public class ClientThread extends Thread {

	private Socket socket;
	
	public ClientThread( Socket socket ){
		
		this.socket = socket;
	}
	
	public void run() {
		
		try {
			
			BufferedReader in 	= new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
			
			// Lecture de la requête du client.
			String line, file = "";
			while( ( line = in.readLine() )!=null && !line.equals( "END" ) ){
				
				file+=line+'\n';
			}
			
			System.out.println( file );
			
			// Parsing de la requete.
			XMLRequest request = new XMLRequest( file );
			
			// Lancement de la requete.
			XMLResponse response = MusicBookDatabase.doQuery( request );
			
			System.out.println(response.toString());
			
			// Renvoie au client la réponse.
			send( response.toString() );
			send( "END" );

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// Close the socket.
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Fonction qui envoie un texte au client.
	 */
	public void send( String text ) throws IOException{
		
		PrintWriter out 	= new PrintWriter( socket.getOutputStream() );
		
		out.println(text);
		out.flush();
	}
}
