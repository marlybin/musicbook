package database;

public class MusicBookDatabase {

	public static XMLResponse responseQuery( XMLRequest request ){
		
		String responseTXT 		= "<xml><items><item>blabla</item></items>";
		XMLResponse response 	= new XMLResponse( responseTXT );
		
		return response;
	}
	
	public static XMLResponse actionQuery( XMLRequest request ){
		
		String responseTXT 		= "<xml><result>done</result>";
		XMLResponse response 	= new XMLResponse( responseTXT );
		
		return response;
	}
}
