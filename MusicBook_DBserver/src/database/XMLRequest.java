package database;

import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import queries.Q;
import queries.Qcommunity_playlist;
import queries.Qcommunity_share_music;
import queries.Qlogin;
import queries.Qmusic_user;
import queries.Qsubscription;

public class XMLRequest {

	String SQLquery;
	Q query;
	
	public XMLRequest( String xmlfile ){
		
		SAXBuilder sxb = new SAXBuilder();
		Document document;

		try {
			
			document = sxb.build( new StringReader(xmlfile) );
			SQLquery = generateSQLQuery( document );
			
		} catch (JDOMException | IOException e) {

			e.printStackTrace();
		}
	}
	
	/*
	 * Génère la requête SQL lié au document XML.
	 */
	public String generateSQLQuery( Document document ){
		
		String SQLquery = null;
		
		Element root = document.getRootElement();
		
		// Traite le type de la requête.
		String type = root.getChild("type").getText();
		
		switch( type ){
		
			// Inscription et connection
		case "login":
			this.query = generateSQL_login(document);
			break;
			
		case "subscription":
			this.query = generateSQL_subscribe(document);
			break;
			
			// Les groupes.
		case "community_join":
			this.query = generateSQL_community_join(document);
			break;

		case "community_playlist":
			this.query = generateSQL_community_playlist(document);
			break;
			
		case "community_leave":
			this.query = generateSQL_join_group(document);
			break;
			
		case "community_share_music":
			this.query = generateSQL_community_share_music(document);
			break;
			
			// Les musiques.
		case "music_upload":
			this.query = generateSQL_music_upload(document);
			break;

		case "music_user":
			this.query = generateSQL_music_user(document);
			break;

		case "music_delete":
			this.query = generateSQL_music_delete(document);
			break;
		}
		
		return SQLquery;
	}

	/*
	 * Génère la requête pour ajouter un utilisateur à la base de données.
	 */
	private Q generateSQL_subscribe(Document document) {
		
		String pseudo	= document.getRootElement().getChild("pseudo").getText();;
		String mail 	= document.getRootElement().getChild("mail").getText();;
		String password	= document.getRootElement().getChild("password").getText();;
		
		return  new Qsubscription( pseudo, mail, password  );
	}

	/*
	 * Génère la requête utilisé pour savoir si le couple login/password est correct.
	 */
	private Q generateSQL_login(Document document) {
		
		String login 	= document.getRootElement().getChild("login").getText();
		String password = document.getRootElement().getChild("password").getText();

		return new Qlogin( login, password );
	}

	/*
	 * Génère la requête qui permet de partager une musique dans un groupe.
	 */
	private Q generateSQL_community_share_music(Document document) {
		
		String userid 		= document.getRootElement().getChild("userid").getText();
		String musicid 		= document.getRootElement().getChild("musicid").getText();
		String communityid 	= document.getRootElement().getChild("communityid").getText();
		
		return new Qcommunity_share_music( userid, musicid, communityid );
	}
	
	/*
	 * Génère la requête qui permet de rejoindre un groupe.
	 */
	private Q generateSQL_join_group(Document document) {
		return null;
	}

	/*
	 * Génère la requête qui récupère la liste des musiques d'un groupe.
	 */
	private Q generateSQL_community_playlist(Document document) {
		
		String userid	= document.getRootElement().getChild("userid").getText();
		String groupid	= document.getRootElement().getChild("groupid").getText();
		
		return new Qcommunity_playlist( userid, groupid );
	}

	/*
	 * Génère la requête pour rejoindre un groupe.
	 */
	private Q generateSQL_community_join(Document document) {
		return null;
	}
	
	/*
	 * Génère la requete pour supprimer une musique.
	 */
	private Q generateSQL_music_delete(Document document) {
		return null;
	}

	/*
	 * Génère la requête pour récupérer la liste des musiques d'un utilisateur.
	 */
	private Q generateSQL_music_user(Document document) {
		
		String userid	= document.getRootElement().getChild("userid").getText();
		
		return new Qmusic_user(userid);
	}

	/*
	 * Génère la requête pour enregistrer l'upload d'une musique.
	 */
	private Q generateSQL_music_upload(Document document) {
		return null;
	}

	/*
	 * Récupère l'objet qui génère la statement.
	 */
	public Q getQ(){
		return this.query;
	}
	
	public String getType(){
		return this.query.getType();
	}
	
	public PreparedStatement getStatement( Connection connection ) throws SQLException{
		
		return this.query.getStatement(connection);
	}
}
