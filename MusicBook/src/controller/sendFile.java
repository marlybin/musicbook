package controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;



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
		/*
		 boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		    if (isMultipart) {
		        FileItemFactory factory = new DiskFileItemFactory();
		        ServletFileUpload upload = new ServletFileUpload(factory);

		        try {
		            List items = upload.parseRequest(request);
		            Iterator iterator = items.iterator();
		            while (iterator.hasNext()) {
		                FileItem item = (FileItem) iterator.next();
		                if (!item.isFormField()) {
		                    String fileName = item.getName();

		                    File path = new File("/home/tony/upload/test.mp3");
		                    if (!path.exists()) {
		                        boolean status = path.mkdirs();
		                    }

		                    File uploadedFile = new File(path + "/" + fileName);
		                    item.write(uploadedFile);
		                }
		            }
		        } catch (Exception e) {}
	}*/}
}
