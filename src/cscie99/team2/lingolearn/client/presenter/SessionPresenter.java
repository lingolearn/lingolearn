package cscie99.team2.lingolearn.client.presenter;

import java.util.ArrayList;
import java.util.List;

import cscie99.team2.lingolearn.client.BasicRandomization;
import cscie99.team2.lingolearn.client.CardServiceAsync;
import cscie99.team2.lingolearn.client.CourseServiceAsync;
import cscie99.team2.lingolearn.client.LeitnerSystem;
import cscie99.team2.lingolearn.client.Notice;
import cscie99.team2.lingolearn.client.SpacedRepetition;
import cscie99.team2.lingolearn.client.event.AnalyticsEvent;
import cscie99.team2.lingolearn.client.view.CardView;
import cscie99.team2.lingolearn.client.view.QuizView;
import cscie99.team2.lingolearn.client.view.SessionView;
import cscie99.team2.lingolearn.shared.Assessment;
import cscie99.team2.lingolearn.shared.Course;
import cscie99.team2.lingolearn.shared.FlashCardResponse;
import cscie99.team2.lingolearn.shared.Lesson;
import cscie99.team2.lingolearn.shared.Quiz;
import cscie99.team2.lingolearn.shared.QuizResponse;
import cscie99.team2.lingolearn.shared.Session;
import cscie99.team2.lingolearn.shared.SessionTypes;
import cscie99.team2.lingolearn.shared.SpacedRepetitionOption;
import cscie99.team2.lingolearn.shared.User;
import cscie99.team2.lingolearn.shared.UserSession;
import cscie99.team2.lingolearn.shared.error.SpacedRepetitionException;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;

public class SessionPresenter implements Presenter {

	private final HandlerManager eventBus;
	private final SessionView display;
	private final CourseServiceAsync courseService;
	private final CardPresenter cardPresenter;
	private final QuizPresenter quizPresenter;
	private Session session;
	private UserSession userSession;
	private User currentUser;
	private Long currentCardId;
	private SpacedRepetition spacedRepetitionSystem;
	private Course course;

	public SessionPresenter(CourseServiceAsync courseService, CardServiceAsync cardService, User currentUser,
			HandlerManager eventBus, SessionView display) {
		this.courseService = courseService;
		this.cardPresenter = new CardPresenter(cardService, eventBus, new CardView());
		this.quizPresenter = new QuizPresenter(cardService, eventBus, new QuizView(), this);
		this.currentUser = currentUser;
		this.eventBus = eventBus;
		this.display = display;
	}

	public void bind() {
		// Bind the basic event handlers
		cardPresenter.getDisplay().getKnowledgeHighButton()
				.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						recordKnowledge(Assessment.DEFINITELYKNEWIT);
					}
				});
		cardPresenter.getDisplay().getKnowledgeMediumButton()
				.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						recordKnowledge(Assessment.SORTAKNEWIT);
					}
				});
		cardPresenter.getDisplay().getKnowledgeLowButton()
				.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						recordKnowledge(Assessment.NOCLUE);
					}
				});
		quizPresenter.getDisplay().getNextButton()
				.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						gotoNextCard();
					}
				});

		// Add support for keyboard events
		Event.addNativePreviewHandler(new NativePreviewHandler() {
			@Override
			public void onPreviewNativeEvent(NativePreviewEvent event) {
				NativeEvent ne = event.getNativeEvent();

				if (event.getTypeInt() == Event.ONKEYDOWN) {
					if (ne.getKeyCode() == KeyCodes.KEY_ENTER || ne.getKeyCode() == KeyCodes.KEY_SPACE) {
						ne.preventDefault();
						if (session instanceof Lesson) {
							cardPresenter.flipCard();
						} else {
							if (quizPresenter.getDisplay().isAnswered()) {
								gotoNextCard();
							} else {
								quizPresenter.submit();
							}
						}
					}
					if (session instanceof Lesson) {
						if (ne.getKeyCode() == KeyCodes.KEY_ONE || ne.getKeyCode() == KeyCodes.KEY_NUM_ONE) {
							ne.preventDefault();
							recordKnowledge(Assessment.NOCLUE);
						} else if (ne.getKeyCode() == KeyCodes.KEY_TWO || ne.getKeyCode() == KeyCodes.KEY_NUM_TWO) {
							ne.preventDefault();
							recordKnowledge(Assessment.SORTAKNEWIT);
						} else if (ne.getKeyCode() == KeyCodes.KEY_THREE || ne.getKeyCode() == KeyCodes.KEY_NUM_THREE) {
							ne.preventDefault();
							recordKnowledge(Assessment.DEFINITELYKNEWIT);
						}
					} else {
						if (ne.getKeyCode() == KeyCodes.KEY_ONE
								|| ne.getKeyCode() == KeyCodes.KEY_TWO
								|| ne.getKeyCode() == KeyCodes.KEY_THREE
								|| ne.getKeyCode() == KeyCodes.KEY_FOUR) {
							ne.preventDefault();
							quizPresenter.getDisplay().setSelectedAnswer(ne.getKeyCode() - KeyCodes.KEY_ONE);
						}

					}

				}
			}
		});
	}

	public void go(final HasWidgets container) {
		bind();
		container.clear();
		container.add(display.asWidget());

		try {
			// Set session based on query parameter in URL
			Long sessionId = 0l;
			sessionId = Long.valueOf(Window.Location.getParameter("sessionId"));
			this.setSession(sessionId);
		} catch (NumberFormatException nfe) {
			Notice.showNotice("The study session specified is invalid.", "error");
		}
	}

	/*
	 * Sets and starts a session
	 */
	public void setSession(Long sessionId) {
		courseService.getSessionById(sessionId, new AsyncCallback<Session>() {
			public void onSuccess(Session returnedSession) {
				session = returnedSession;

				// Set "return to course" link of session
				display.setReturnToCourseLink(returnedSession.getCourseId());
				courseService.getCourseById(returnedSession.getCourseId(),
						new AsyncCallback<Course>() {

							@Override
							public void onFailure(Throwable caught) {
								Notice.showNotice("Unable to get course of session.", "warning");
							}

							@Override
							public void onSuccess(Course result) {
								course = result;
								if (course.getSpacedRepetitionOption() == SpacedRepetitionOption.LEITNER) {
									spacedRepetitionSystem = new LeitnerSystem();
								} else {
									spacedRepetitionSystem = new BasicRandomization();
								}

								// Get the session type -- either from the query string, or from 
								// the quiz instance variable set by the instructor
								String sessionType = Window.Location.getParameter("type") == null ? ""
										: Window.Location.getParameter("type");

								SessionTypes type = SessionTypes.Kanji_Translation;
								if (session instanceof Quiz) {
									Quiz q = (Quiz) session;
									type = q.getSessionType();
									boolean response = q.getMode().equals("yes");
									quizPresenter.setUseConfusers(response, q.getSessionType());
								} else {
									try {
										type = SessionTypes.getEnum(sessionType);
									} catch (IllegalArgumentException iae) {
										Notice.showNotice("Unable to parse the session type (" + type + ")", "error");
										return;
									}
								}

								courseService.createUserSession(
										session.getSessionId(),
										currentUser.getGplusId(), type,
										new AsyncCallback<UserSession>() {

											public void onSuccess(UserSession returnedUserSession) {
												userSession = returnedUserSession;
												if (session instanceof Lesson) {
													cardPresenter.go(display.getCardContainer());
												} else {
													quizPresenter.go(display.getCardContainer());
												}
												display.setSessionName(session.getDeck().getDesc());
												spacedRepetitionSystem.setDeck(session.getDeck());
												try {
													spacedRepetitionSystem.shuffleDeck();
												} catch (SpacedRepetitionException e) {
													e.printStackTrace();
												}
												gotoNextCard();
											}

											public void onFailure(Throwable caught) {
												Window.alert("An unhandled error occured: " + caught.getMessage());
											}
										});
							}
						});
			}

			public void onFailure(Throwable caught) {
				Window.alert("Error fetching session");
			}
		});
	}

	private void recordKnowledge(Assessment knowledge) {
		// Send knowledge to the analytics service
		FlashCardResponse flashCardResponse = new FlashCardResponse();
		flashCardResponse.setGplusId(currentUser.getGplusId());
		flashCardResponse.setCardId(currentCardId);
		flashCardResponse.setSessionId(session.getSessionId());
		flashCardResponse.setUserSessionId(userSession.getUserSessionId());
		flashCardResponse.setAssessment(knowledge);
		flashCardResponse.setSeq(this.userSession.getSessionType().toString());
		AnalyticsEvent flashCardEvent = new AnalyticsEvent(flashCardResponse);
		eventBus.fireEvent(flashCardEvent);
		// Move to the next card
		gotoNextCard();
	}

	public void recordQuizResponse(QuizResponse quizResponse) {
		quizResponse.setGplusId(currentUser.getGplusId());
		quizResponse.setSessionId(session.getSessionId());
		quizResponse.setUserSessionId(userSession.getUserSessionId());
		quizResponse.setSeq(this.userSession.getSessionType().toString());
		AnalyticsEvent quizEvent = new AnalyticsEvent(quizResponse);
		eventBus.fireEvent(quizEvent);
	}

	public void gotoNextCard() {
		boolean cardDrawn = true;
		if (spacedRepetitionSystem.cardsRemaining()) {
			try {
				currentCardId = spacedRepetitionSystem.drawCard();
				if (session instanceof Lesson) {
					cardPresenter.setCardData(currentCardId);
				} else {
					quizPresenter.setCardData(currentCardId,
							selectThreeOtherCardsFromDeck(),
							session.getSessionType());
				}
			} catch (SpacedRepetitionException e) {
				cardDrawn = false;
			}
		} else {
			cardDrawn = false;
		}

		if (!cardDrawn) {
			Notice.showNotice("Deck complete! Good job!", "success");
			if (session instanceof Lesson) {
				cardPresenter.getDisplay().disableButtons();
			} else {
				quizPresenter.getDisplay().hideButtons();
			}
		}
	}

	private ArrayList<Long> selectThreeOtherCardsFromDeck() {
		ArrayList<Long> selectedIds = new ArrayList<Long>();
		List<Long> allIds = session.getDeck().getCardIds();
		while ((selectedIds.size() < 3) && (selectedIds.size() != (allIds.size() - 1))) {
			int idx = Random.nextInt(allIds.size());
			boolean isValid = true;
			for (int i = 0; i < selectedIds.size(); i++) {
				if (selectedIds.get(i) == allIds.get(idx)) {
					isValid = false;
				}
			}
			if (allIds.get(idx).equals(currentCardId)) {
				isValid = false;
			}
			if (isValid) {
				selectedIds.add(allIds.get(idx));
			}
		}
		return selectedIds;
	}

}
