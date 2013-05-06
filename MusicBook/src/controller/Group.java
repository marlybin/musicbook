package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Group
 */
@WebServlet("/Group")
public class Group extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONNECTION_PAGE = "/connection.jsp";
	private static final String PAGE = "/groupe.jsp";
	private static final String ONE_GROUP = "/one_group.jsp";
	private static final String NOCHROME = "/gotochrome.jsp";
       
    public Group() {
    	
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
				
				// Si la page groupe de l'utilisateur est demandée.
				if( request.getParameter("id") == null )
					this.getServletContext().getRequestDispatcher( PAGE ).forward(request, response);
				// Sinon il s'agit de la page d'un groupe en particulier.
				else
					this.getServletContext().getRequestDispatcher( ONE_GROUP ).forward(request, response);
				
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
