package cscie99.team2.lingolearn.shared;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cscie99.team2.lingolearn.client.QuizResponseService;
import cscie99.team2.lingolearn.server.QuizReponseServiceImpl;

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
				 	avgQuizReactionTime,	//Average quiz question reaction time
				 	avgFlashCardReactionTime,//Average flashcard reaction time
				 	indecisionRate,			//Percentage of time user changes answers
				 	dropRate,				//Percentage of time user drops the card
				 	averageSessionTime,		//Average session length
				 	repetitionsPerWeek,		//Average number of repetitions per week
				 	percentNoClue,			//Percent of cards the user responded "I had no clue"
				 	percentSortaKnewIt,		//Percent of cards the user responded "I sorta knew it"
				 	percentDefinitelyKnewIt;//Percent of cards the user responded "I definitely knew it"
	private QuizResponseService qrs;		//The service for retrieving QuizResponse data.
	private List<QuizResponse> qResps;		//The list of quiz responses for this user.
					
	public Metrics (String gplusId) {
		this.setGplusId(gplusId);
		qrs = new QuizReponseServiceImpl();
		
		//temporary
		QuizResponse qr = new QuizResponse(null, null, gplusId, "test", true, false, 5.5f);
		qrs.storeQuizResponse(qr);
		
		qResps = qrs.getAllQuizResponsesByUser(gplusId);
		calculateRecallRate();
		calculateAvgQuizReactionTime();
		calculateAvgFlashCardReactionTime();
		calculateIndecisionRate();
		calculateDropRate();
		calculateAverageSessionTime();
		calculateRepetitionsPerWeek();
		calculateUserAssessments();
	}
	
	public void calculateUserAssessments() {
		//temporarily prepopulates data. This would normally be pulled from all FlashCardResponse objects pertaining to the user's id.
		int noClues = 200;
		int sortaKnewIt = 400;
		int definitelyKnewIt = 400;
		
		this.setPercentNoClue(noClues/(noClues+sortaKnewIt+definitelyKnewIt));
		this.setPercentSortaKnewIt(sortaKnewIt/(noClues+sortaKnewIt+definitelyKnewIt));
		this.setPercentDefinitelyKnewIt(definitelyKnewIt/(noClues+sortaKnewIt+definitelyKnewIt));
	}

	public void calculateRecallRate() {
		int correctQuizAnswers = 0;
		int questionsSeen = 0;
		for (QuizResponse qr: qResps) {
			questionsSeen++;
			if (qr.isCorrect()) {
				correctQuizAnswers++;
			}			
		}
		
		this.setRecallRate((correctQuizAnswers/questionsSeen));
		
	}
	
	public void calculateAvgQuizReactionTime() {
		
		float totalQuizTimeToAnswer = 0.0f;
		int questionsSeen = 0;
		if (!qResps.isEmpty()) {
			for (QuizResponse qr: qResps) {
				questionsSeen++;
				totalQuizTimeToAnswer = totalQuizTimeToAnswer + qr.getTimeToAnswer();
			}
		}
		
		this.setAvgQuizReactionTime((totalQuizTimeToAnswer/questionsSeen));
		
	}
	
	public void calculateAvgFlashCardReactionTime() {
		//temporarily prepopulates data. This would normally be pulled from all UserReponse objects pertaining to the user's id.
		float totalFlashCardTimeToAnswer = 8594.5f;
		int cardsSeen = 1000;
		
		this.setAvgFlashCardReactionTime((totalFlashCardTimeToAnswer/cardsSeen));
	}
	
	public void calculateIndecisionRate() {
		int changedAnswers = 0;
		int questionsSeen = 0;
		for (QuizResponse qr: qResps) {
			questionsSeen++;
			if (qr.isChanged()) {
				changedAnswers++;
			}			
		}
		
		this.setIndecisionRate((changedAnswers/questionsSeen));
		
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

	public float getAvgQuizReactionTime() {
		return avgQuizReactionTime;
	}

	public void setAvgQuizReactionTime(float avgReactionTime) {
		this.avgQuizReactionTime = avgReactionTime;
	}
	
	public float getAvgFlashCardReactionTime() {
		return avgFlashCardReactionTime;
	}

	public void setAvgFlashCardReactionTime(float avgReactionTime) {
		this.avgFlashCardReactionTime = avgReactionTime;
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
	
	public float getPercentNoClue() {
		return percentNoClue;
	}
	
	public float getPercentSortaKnewIt() {
		return percentSortaKnewIt;
	}
	
	public float getPercentDefinitelyKnewIt() {
		return percentDefinitelyKnewIt;
	}
	
	public void setPercentNoClue(float percentNoClue) {
		this.percentNoClue = percentNoClue;
	}
	
	public void setPercentSortaKnewIt(float percentSortaKnewIt) {
		this.percentSortaKnewIt = percentSortaKnewIt;
	}
	
	public void setPercentDefinitelyKnewIt(float percentDefinitelyKnewIt) {
		this.percentDefinitelyKnewIt = percentDefinitelyKnewIt;
	}

}
