package database;

public class XMLResponse {

	String file;
	
	public XMLResponse( String XMLtext ) {
		
		file = XMLtext;
	}
	
	public String toString(){
		return file;
	}
}
