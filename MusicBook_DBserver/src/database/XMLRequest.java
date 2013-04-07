package database;

public class XMLRequest {

	String SQLquery;
	
	public XMLRequest( String xmlfile ){
		
		// Parsing de la requete XML et génération d'une requête SQL.
		SQLquery = "SELECT * FORM user WHERE user.group=1";
	}
	
	public String getSQLQuery(){
		
		return SQLquery;
	}
}
