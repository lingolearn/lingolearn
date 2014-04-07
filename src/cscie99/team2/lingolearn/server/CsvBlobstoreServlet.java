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
import cscie99.team2.lingolearn.server.tools.CardFileLoader;
import cscie99.team2.lingolearn.shared.Card;
import cscie99.team2.lingolearn.shared.error.FileLoadException;

@SuppressWarnings("serial")
public class CsvBlobstoreServlet extends HttpServlet {

  private BlobstoreService blobstoreService = BlobstoreServiceFactory
      .getBlobstoreService();
	
	@Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
		
	}
	
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
              throws IOException {
    	
    	try{
	    	
	      Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
	      BlobKey blobKey = blobs.get(ImportPresenter.CSV_UPLOAD_NAME).get(0);
	      BlobstoreInputStream is = new BlobstoreInputStream(blobKey);
	      
	      BufferedReader reader = new BufferedReader( new InputStreamReader(is) );
	      CardFileLoader cardLoader = new CardFileLoader();
	      List<Card> importedCards = cardLoader.loadCards(reader);
	      Iterator<Card> cardItr = importedCards.iterator();
	      while( cardItr.hasNext() ){
	      	Card card = cardItr.next();
	      	System.out.print(card.getHiragana());
	      	System.out.print(" ");
	      	System.out.print(card.getKanji());
	      	System.out.print(" ");
	      	System.out.print(card.getKatakana());
	      	System.out.println();
	      }
    	}catch(FileLoadException fle ){
    		fle.printStackTrace();
    	}
    }
}
