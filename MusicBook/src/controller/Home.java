package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONNECTION_PAGE = "/connection.jsp";
	private static final String HOME_PAGE = "/home.jsp";
	private static final String NOCHROME = "/gotochrome.jsp";
       
    public Home() {
    	
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Test the user navigator version.
		String navigatorVersion = request.getHeader("user-agent");
		if( navigatorVersion.indexOf("Chrome")==-1 ){
			this.getServletContext().getRequestDispatcher( NOCHROME ).forward( request, response );
		}
		else{
			
			HttpSession session = request.getSession( true );
			
			// Si l'utilisateur est connecté.
			if( session.getAttribute("id")!=null ){
				
				this.getServletContext().getRequestDispatcher( HOME_PAGE ).forward( request, response );
			}
			// Si l'utilisateur n'est pas connecté.
			else{
				
				this.getServletContext().getRequestDispatcher( CONNECTION_PAGE ).forward( request, response );
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
}