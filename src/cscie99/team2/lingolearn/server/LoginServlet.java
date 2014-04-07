package cscie99.team2.lingolearn.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LoginServlet extends HttpServlet {

  //private static final Logger log = Logger.getLogger(LoginServlet.class
   //   .getName());
	
	@Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        
		HttpSession session = req.getSession();
    	session.setAttribute(
    			UserServiceImpl.GMAIL_SESSION_KEY, null );
    	session.setAttribute( UserServiceImpl.GID_SESSION_KEY,
    							null );
    	session.setAttribute(UserServiceImpl.USER_SESSION_KEY, null);
    	resp.setContentType("text/html");
    	// Get the printwriter object from response to write the required json object to the output stream      
    	PrintWriter out = resp.getWriter();
    	out.write("<p><b>You are now logged out.</b>  You may still be logged into gmail.</p>");
    	out.write("return to <a href='/login.html'>login page</a>");
    }
	
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
              throws IOException {
    	
    	try{
	    	String gmail = req.getParameter("gmail") != null ?
	    			req.getParameter("gmail") : "";
	    	
	    	String gplusId = req.getParameter("gplusid") != null ?
	    			req.getParameter("gplusid") : "";
	    		
	    	
	    	resp.setContentType("application/json");
	    	// Get the printwriter object from response to write the required json object to the output stream      
	    	PrintWriter out = resp.getWriter();
	    	// Assuming your json object is **jsonObject**, perform the following, it will return your json object  
	    	String json = "";
	    			
	    	if( gplusId.equals("") || gmail.equals("") ){
	    		json = "false";
	    	}else{
	    	
		    	HttpSession session = req.getSession();
		    	session.setAttribute(
		    			UserServiceImpl.GMAIL_SESSION_KEY, gmail );
		    	session.setAttribute( UserServiceImpl.GID_SESSION_KEY,
		    							gplusId );
		    	json = "true";
	    	}
	    	
	    	out.print(json);
	    	out.flush();		
	    	return;
	    	
    	}catch( NumberFormatException nfe ){
    		nfe.printStackTrace();
    		return;
    	}
    }
}
