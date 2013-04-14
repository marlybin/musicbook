package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MusicBookDatabase {

	/*
	 * Requetes SELECT
	 */
	public static XMLResponse doQuery( XMLRequest request ) throws ClassNotFoundException, SQLException{
		
		// Connexion à la base de données.
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost/musicbook";
		String user = "root";
		String password = "m@mrchalnes0";
		Connection connection = DriverManager.getConnection(url,user,password);
		
		PreparedStatement statement = request.getStatement(connection);
		String type = request.getType();

		XMLResponse response;
		
		// Si il s'agit d'une requete de type SELECT.
		if( type.equals("select") ){
			
			// Exécution de la requête.
			ResultSet resultset = statement.executeQuery();
			
			// Réponse.
			response = new XMLResponse( resultset );
			
		}
		// Sinon il s'agit d'une requête INSERT ou DELETE, on exécute et on renvoie "done"
		else{
			
			// Exécution de la requête.
			statement.executeUpdate();
			
			// Réponse.
			response = new XMLResponse();
		}
		
		return response;
	}
	
}
