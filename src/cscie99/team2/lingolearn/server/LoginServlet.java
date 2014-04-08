package cscie99.team2.lingolearn.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * This servlet is used as a service endpoint for application login and logout.
 * Since the login and logout are implemented with sessions, and for now,
 * partially ajax calls, this makes sense.  
 * 
 * @note Later this may be replaced with a more complex mechanism (oauth?)
 * 
 * @author Jeff Rabe
 *
 */
public class LoginServlet extends HttpServlet {


	private static final long serialVersionUID = 4470963089960248631L;

		/**
		 * The HTTP GET method is used for logout only.  Session variables are unset,
		 * and some basic HTML is served.
		 */
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
	
		/**
		 * the HTTP POST method is used for login.  No content is served, rather
		 * this is an endpoint for a REST ajax call.  The request parameters
		 * expect the following
		 * 				"gmail" 	: the gmail address of a user signed into gmail
		 * 				"gplusid"	: the google plus id of the same signed in user.
		 * 
		 * Currently this AJAX call is made from /login.html
		 * @return - void, but Applicaton/json is served.
		 */
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
