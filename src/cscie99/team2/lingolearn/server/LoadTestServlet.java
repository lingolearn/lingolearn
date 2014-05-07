package cscie99.team2.lingolearn.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cscie99.team2.lingolearn.server.datastore.CardDAO;
import cscie99.team2.lingolearn.server.datastore.DeckDAO;
import cscie99.team2.lingolearn.shared.Card;
import cscie99.team2.lingolearn.shared.Deck;

public class LoadTestServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2905137864394222347L;
	public static final String ALL_DECKS_KEY = "alldecks";
	public static final String ALL_CARDS_KEY = "allcards";
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		HttpSession session = req.getSession();
		boolean testDecks = false;
		boolean testCards = false;
		String getDecks = (String)req.getParameter(ALL_DECKS_KEY);
		String getCards = (String)req.getParameter(ALL_CARDS_KEY);
		if( getDecks != null && getDecks.equals("true"))
			testDecks = true;
		
		if( getCards != null && getCards.equals("true"))
			testCards = true;
		
		if( testDecks){
			testDecks(req, resp);
		}else if( testCards ){
			testCards(req, resp);
		}
	}
	
	private void testDecks(HttpServletRequest req, HttpServletResponse resp)
	throws IOException
	{
		StringBuffer buf = new StringBuffer();
		try{
			DeckDAO deckAccessor = DeckDAO.getInstance();
			
			List<Deck> allDecks = deckAccessor.getAllDecks();
			
			
			for( Deck deck : allDecks ){
				buf.append(deck.getId());
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			PrintWriter out = resp.getWriter();
			out.write(buf.toString());
			out.close();
		}
	}
	
	private void testCards(HttpServletRequest req, HttpServletResponse resp)
	throws IOException
	{
		StringBuffer buf = new StringBuffer();
		try{
			
			CardDAO cardAccessor = CardDAO.getInstance();
			List<Card> allCards = cardAccessor.getAllCards();
			
			
			for( Card card : allCards ){
				buf.append(card.getTranslation());
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			PrintWriter out = resp.getWriter();
			out.write(buf.toString());
			out.close();
		}
	}
}
