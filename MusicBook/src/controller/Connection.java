package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Connection
 */
@WebServlet("/Connection")
public class Connection extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String VUE = "/index.jsp";
       
    public Connection() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession( true );
		String mode = request.getParameter("mode");

		if( mode!=null && mode.equals("exit") ){
			session.removeAttribute("id");
		}
		
		response.sendRedirect( "Home" );
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession( true );

		String login 		= request.getParameter("login");
		String password 	= request.getParameter("password");
		
		try{
			if( is_valid_connection( login, password ) ){
				
				session.setAttribute("id", login);
				session.setAttribute("username", login);
				this.doGet(request, response);
			}
			else{
				request.setAttribute("error", "true");
				response.sendRedirect( "Home?error=badid" );
			}
		// Si les données du formulaire n'existent pas..
		}catch( Exception e ){
			
		}
	}
	
	private Boolean is_valid_connection( String login, String password ){
		
		if( login.equals("tony") ){ return true; }
		return false;
	}
}
