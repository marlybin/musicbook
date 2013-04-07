package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class sendFile
 */
@WebServlet("/sendFile")
public class sendFile extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	public static final String VUE = "/upload.jsp";
       
    public sendFile() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Part part = request.getPart( "music" );
		System.out.println(part);
		/*
		String fileName = getNomFichier( part );
		if ( fileName != null && !fileName.isEmpty() ) {
	        String nomChamp = part.getName();
	        request.setAttribute( nomChamp, fileName );
	        System.out.println(fileName);
	    }*/
		
		this.doGet(request, response);
	}

	private static String getNomFichier( Part part ) {

	    for ( String contentDisposition : part.getHeader( "content-disposition" ).split( ";" ) ) {
	        if ( contentDisposition.trim().startsWith("filename") ) {
	            return contentDisposition.substring( contentDisposition.indexOf( '=' ) + 1 );
	        }
	    }
	    return null;
	}
	
}
