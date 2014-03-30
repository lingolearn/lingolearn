/**
 * 
 */
package cscie99.team2.lingolearn.shared;


/**
 * @author YPolyanskyy
 *
 * This interface allows users of system to define their own spaced repetition algorithms. 
 */
public interface CardSelector {

	public void load(Deck deck);
	
	public Card getNextCard();
	
	public boolean hasCard (); 
	
	public UserResponse getResponse ();
	
}
