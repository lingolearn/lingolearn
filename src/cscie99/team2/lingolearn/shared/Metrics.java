package cscie99.team2.lingolearn.shared;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author nichols
 *
 * This class contains the metrics data for each student.
 *
 */
public class Metrics implements Serializable{

	private String 	gplusId;				//Id of the user
	private float 	recallRate,				//Success rate on flash cards
				 	avgReactionTime,		//Average reaction time
				 	indecisionRate,			//Percentage of time user changes answers
				 	dropRate,				//Percentage of time user drops the card
				 	averageSessionTime,		//Average session length
				 	repetitionsPerWeek;		//Average number of repetitions per week
	public Metrics (String gplusId) {
		this.setGplusId(gplusId);
		calculateRecallRate();
		calculateAvgReactionTime();
		calculateIndecisionRate();
		calculateDropRate();
		calculateAverageSessionTime();
		calculateRepetitionsPerWeek();
	}

	public void calculateRecallRate() {
		//temporarily prepopulates data. This would normally be pulled from all UserReponse objects pertaining to the user's id.
		int correctAnswers = 500;
		int cardsSeen = 1000;
		
		this.setRecallRate((correctAnswers/cardsSeen));
		
	}
	public void calculateAvgReactionTime() {
		//temporarily prepopulates data. This would normally be pulled from all UserReponse objects pertaining to the user's id.
		float totalTimeToAnswer = 8594.5f;
		int cardsSeen = 1000;
		
		this.setAvgReactionTime((totalTimeToAnswer/cardsSeen));
	}
	public void calculateIndecisionRate() {
		//temporarily prepopulates data. This would normally be pulled from all UserReponse objects pertaining to the user's id.
		int changedAnswers = 333;
		int cardsSeen = 1000;
		
		this.setIndecisionRate((changedAnswers/cardsSeen));
		
	}
	public void calculateDropRate() {
		//temporarily prepopulates data. This would normally be pulled from all UserReponse objects pertaining to the user's id.
		int droppedCards = 200;
		int cardsSeen = 1000;
		
		this.setDropRate((droppedCards/cardsSeen));
		
	}
	public void calculateAverageSessionTime() {
		//temporarily prepopulates data. This would normally be pulled from all Session objects pertaining to the user's id.
		float totalSessionTime = 15456.3f;
		int noSessions = 4;
		
		this.setAverageSessionTime((totalSessionTime/noSessions));
		
	}
	public void calculateRepetitionsPerWeek() {
		//temporarily prepopulates data. This would normally be pulled from all Sessions objects pertaining to the user's id.
		int noSessions = 4;
		long timeSinceRegistration = 1728000000L;
		
		this.setRepetitionsPerWeek((noSessions/ (timeSinceRegistration/604800000)));
		
	}

	public float getRecallRate() {
		return recallRate;
	}

	public void setRecallRate(float recallRate) {
		this.recallRate = recallRate;
	}

	public String getGplusId() {
		return gplusId;
	}

	public void setGplusId(String gplusId) {
		this.gplusId = gplusId;
	}

	public float getAvgReactionTime() {
		return avgReactionTime;
	}

	public void setAvgReactionTime(float avgReactionTime) {
		this.avgReactionTime = avgReactionTime;
	}

	public float getIndecisionRate() {
		return indecisionRate;
	}

	public void setIndecisionRate(float indecisionRate) {
		this.indecisionRate = indecisionRate;
	}

	public float getDropRate() {
		return dropRate;
	}

	public void setDropRate(float dropRate) {
		this.dropRate = dropRate;
	}

	public float getAverageSessionTime() {
		return averageSessionTime;
	}

	public void setAverageSessionTime(float averageSessionTime) {
		this.averageSessionTime = averageSessionTime;
	}

	public float getRepetitionsPerWeek() {
		return repetitionsPerWeek;
	}

	public void setRepetitionsPerWeek(float repetitionsPerWeek) {
		this.repetitionsPerWeek = repetitionsPerWeek;
	}

}
