package database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

public class XMLResponse {

	Document document;
	
	public XMLResponse( ResultSet resultset ) throws SQLException {
		
		// Génération de l'arbre XML de réponse.
		Element racine 	= new Element("response");
		
		ResultSetMetaData metadata = resultset.getMetaData();
		
		int nbCol = metadata.getColumnCount();
		
		// Pour chaque lignes du résultat.
		while( resultset.next() ){
			
			Element row = new Element("row");
			
			// Pour chaque cellule de la ligne.
			for( int i=1; i<=nbCol; i++ ){
				
				String colName = metadata.getColumnName(i);
				Element cel = new Element(colName);
				cel.setText( resultset.getObject(i).toString() );
				row.addContent(cel);
			}
			racine.addContent(row);
		}
		
		document 		= new Document(racine);
	}
	
	public XMLResponse() {
		
		Element racine = new Element("result");
		racine.setText("done");
		document = new Document(racine);
		
	}
	
	public String toString(){
		return new XMLOutputter().outputString( document );
	}
}
