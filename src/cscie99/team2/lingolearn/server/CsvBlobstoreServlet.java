package cscie99.team2.lingolearn.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreInputStream;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import cscie99.team2.lingolearn.client.presenter.ImportPresenter;
import cscie99.team2.lingolearn.server.datastore.DeckDAO;
import cscie99.team2.lingolearn.server.tools.CardFileLoader;
import cscie99.team2.lingolearn.shared.Card;
import cscie99.team2.lingolearn.shared.Deck;
import cscie99.team2.lingolearn.shared.error.FileLoadException;

/**
 * CsvBlobstoreServlet.java
 * 
 * This servlet is used to parse imported files, and store their contained data
 * in the blobstore. Each row is meant to represent an entity (e.g. Card)
 */
@SuppressWarnings("serial")
public class CsvBlobstoreServlet extends HttpServlet {

	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	private DeckDAO deckAccessor = DeckDAO.getInstance();

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String deckIdString = req.getParameter("deckId");
		resp.setContentType("text/html");
		resp.getWriter().println(deckIdString);
	}

	/**
	 * The post handler is used as a callback to parse the CSV file(s) provided
	 * by the import form / view.
	 */
	// TODO: Add robust exception handling in catch block
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		try {
			Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
			BlobKey blobKey = blobs.get(ImportPresenter.CSV_UPLOAD_NAME).get(0);
			BlobstoreInputStream is = new BlobstoreInputStream(blobKey);

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			CardFileLoader cardLoader = new CardFileLoader();
			List<Card> importedCards = cardLoader.loadCards(reader);
			Iterator<Card> cardItr = importedCards.iterator();

			Deck deck = new Deck();
			while (cardItr.hasNext()) {
				//TODO break cards into separate decks based on description?
				Card c = cardItr.next();
				deck.add(c);
				deck.setDesc(c.getDesc());
			}
			deck = deckAccessor.storeDeck(deck);

			resp.sendRedirect("/import?deckId=" + deck.getId());
		} catch (FileLoadException fle) {
			fle.printStackTrace();
		}
	}
}
