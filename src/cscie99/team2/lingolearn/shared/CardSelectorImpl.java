package cscie99.team2.lingolearn.shared;

/**
 * This class is the concrete implementation of the CardSelector interface.
 */
public class CardSelectorImpl implements CardSelector {
	
	private String algId, 	// Unique algorithm Id
				   algDesc; // Description of the algorithm
	
	public void load(Deck deck){
		
	};
	
	public Card getNextCard() {
		return null;
	};
	
	public boolean hasCard () {
		return false;
	}; 
	
	public QuizResponse getResponse (){
		return null;
	};
	
}
