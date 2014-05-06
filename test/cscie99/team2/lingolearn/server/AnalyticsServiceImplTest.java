/**
 * 
 */
package cscie99.team2.lingolearn.server;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import cscie99.team2.lingolearn.server.datastore.CardDAO;
import cscie99.team2.lingolearn.server.datastore.CourseDAO;
import cscie99.team2.lingolearn.server.datastore.CourseTestDAO;
import cscie99.team2.lingolearn.server.datastore.FlashCardResponseDAO;
import cscie99.team2.lingolearn.server.datastore.LessonDAO;
import cscie99.team2.lingolearn.server.datastore.QuizDAO;
import cscie99.team2.lingolearn.server.datastore.QuizResponseDAO;
import cscie99.team2.lingolearn.server.datastore.UserDAO;
import cscie99.team2.lingolearn.server.datastore.UserSessionDAO;
import cscie99.team2.lingolearn.shared.Assessment;
import cscie99.team2.lingolearn.shared.Card;
import cscie99.team2.lingolearn.shared.Course;
import cscie99.team2.lingolearn.shared.CourseTest;
import cscie99.team2.lingolearn.shared.Deck;
import cscie99.team2.lingolearn.shared.FlashCardResponse;
import cscie99.team2.lingolearn.shared.Gender;
import cscie99.team2.lingolearn.shared.Image;
import cscie99.team2.lingolearn.shared.Language;
import cscie99.team2.lingolearn.shared.Lesson;
import cscie99.team2.lingolearn.shared.OutsideCourse;
import cscie99.team2.lingolearn.shared.Quiz;
import cscie99.team2.lingolearn.shared.QuizResponse;
import cscie99.team2.lingolearn.shared.Session;
import cscie99.team2.lingolearn.shared.SessionTypes;
import cscie99.team2.lingolearn.shared.Sound;
import cscie99.team2.lingolearn.shared.Textbook;
import cscie99.team2.lingolearn.shared.User;
import cscie99.team2.lingolearn.shared.UserSession;

/**
 * @author YPolyanskyy
 * 
 * Unit tests for AnalyticsServiceImpl class.
 * 
 * Make sure the following jars are in the classpath:
 * 
 * ${SDK_ROOT}/lib/testing/appengine-testing.jar
 * ${SDK_ROOT}/lib/impl/appengine-api.jar
 * ${SDK_ROOT}/lib/impl/appengine-api-labs.jar
 * ${SDK_ROOT}/lib/impl/appengine-api-stubs.jar
 * 
 */
public class AnalyticsServiceImplTest {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
		      new LocalDatastoreServiceTestConfig());
		 
	@BeforeClass
	  public static void setUpBeforeClass() throws Exception {
	  }
	 
	  @AfterClass
	  public static void tearDownAfterClass() throws Exception {
	  }
	 
	  @Before
	  public void setUp() throws Exception {
		  helper.setUp();
		  // create and load 3 testing cards
		  // 
		  Card tc1 = new Card();
		  tc1.setId((long) 101); 
		  tc1.setKanji("k1");
		  tc1.setHiragana("h1");
		  tc1.setKatakana("kt1");
		  tc1.setTranslation("card1");
		  tc1.setNativeLanguage("us-en");
		  tc1.setDesc("TestDeck");

		  // same kanji as in tc1
		  Card tc2 = new Card();
		  tc2.setId((long) 102); 
		  tc2.setKanji("k1");
		  tc2.setHiragana("h2");
		  tc2.setKatakana("kt2");
		  tc2.setTranslation("carte2");
		  tc2.setNativeLanguage("ca-fr");
		  tc2.setDesc("TestDeck");

		  // with Sound and Image
		  String imgUri = "data:image/png;base64,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";
		  Image myImage = new Image();
		  myImage.setImageUri(imgUri);

		  String soundUri = "data:audio/mpeg;base64,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";
		  Sound mySound = new Sound();
		  mySound.setSoundUri(soundUri);

		  Card tc3 = new Card();
		  tc3.setId((long) 103); 
		  tc3.setKanji("k3");
		  tc3.setHiragana("h3");
		  tc3.setKatakana("kt3");
		  tc3.setTranslation("card3");
		  tc3.setNativeLanguage("us-en");
		  tc3.setDesc("MultiMediaDeck");
		  tc3.setImage(myImage);
		  tc3.setSound(mySound);

		  // Store them in the local In-memory datastore
		  CardDAO cardAccessor = CardDAO.getInstance();
		  cardAccessor.storeCard(tc1);
		  cardAccessor.storeCard(tc2);
		  cardAccessor.storeCard(tc3);

		  Language lang1 = new Language("1","en-us");
		  Language lang2 = new Language("2","fr-ca");
		  OutsideCourse course1 = new OutsideCourse("11111", "JP101", "Harvard");

		  User u1 = new User("googleID", "test@gmail.com", "John",
				  "Smith", Gender.Male, lang1);
		  u1.addOutsideCourse(new OutsideCourse("123", "Fun Course", "Fun School"));
		  u1.addTextbook(new Textbook("ABC", "Sweet Book", 2005));
		  u1.addLanguages(lang2);

		  User u2 = new User("googleID2", "test2@gmail.com", "Joe",
				  "Smith", Gender.Male, lang1);
		  u2.addOutsideCourse(course1);

		  User u3 = new User("googleID3", "test2@gmail.com", "Josiane",
				  "Cadieux", Gender.Female, lang2);

		  // Store them in the local In-memory datastore
		  UserDAO userAccessor = UserDAO.getInstance();
		  userAccessor.storeUser(u1);
		  userAccessor.storeUser(u2);
		  userAccessor.storeUser(u3);	

		  // Create and store 1 test FlashCardResponse object
		  FlashCardResponse fcr1 = new FlashCardResponse(111L, 111L, 111L, 101L, "gplusID",
				  "difficultConfuser", 3, 3, false, 2.5f, Assessment.SORTAKNEWIT, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z" ).parse("2014-04-15 20:27:30 EST"), "Sequence");
		  // Store them in the local In-memory datastore
		  FlashCardResponseDAO qRespAccessor = FlashCardResponseDAO.getInstance();
		  qRespAccessor.storeFlashCardResponse(fcr1);

		  // Create and store 3 test Lessons, 2 Quizes and 2 Tests objects
		  Deck d1 = new Deck();
		  Deck d2 = new Deck();
		  d1.setId(101L);
		  d2.setId(102L);
		  Lesson l1 = new Lesson(111L, d1, 11111L, SessionTypes.Kanji_Translation);
		  Lesson l2 = new Lesson(112L, d2, 102L, SessionTypes.Kanji_Translation);
		  Lesson l3 = new Lesson(113L, d1, 103L, SessionTypes.Kanji_Translation);
		  Quiz q1 = new Quiz(114L, d1, 11111L, "learn", SessionTypes.Kanji_Translation);
		  Quiz q2 = new Quiz(115L, d1, 104L, "learn", SessionTypes.Kanji_Translation);
		  CourseTest t1 = new CourseTest(116L, d1, 105L, 30, null);
		  CourseTest t2 = new CourseTest(117L, d2, 106L, 60, null);


		  // Store them in the local In-memory datastore
		  LessonDAO lessonAccessor = LessonDAO.getInstance();
		  lessonAccessor.storeLesson(l1);
		  lessonAccessor.storeLesson(l2);
		  lessonAccessor.storeLesson(l3);

		  QuizDAO quizAccessor = QuizDAO.getInstance();
		  quizAccessor.storeQuiz(q1);
		  quizAccessor.storeQuiz(q2);

		  CourseTestDAO courseTestAccessor = CourseTestDAO.getInstance();
		  courseTestAccessor.storeTest(t1);
		  courseTestAccessor.storeTest(t2);

		  // Create and store 4 test UserSession objects
		  UserSession us1 = new UserSession(111L, 101L, "gplusId", "Kanji_Translation",
				  new Date(), new Date());
		  UserSession us2 = new UserSession(112L, 102L, "gplusId", "Kanji_Translation",
				  new Date(), new Date());
		  UserSession us3 = new UserSession(113L, 101L, "gplusId2", "Kanji_Translation",
				  new Date(), new Date());
		  UserSession us4 = new UserSession(113L, 101L, "gplusID", "Kanji_Translation",
				  new Date(), new Date());
		  
		  // Store them in the local In-memory datastore
		  UserSessionDAO uSessAccessor = UserSessionDAO.getInstance();
		  uSessAccessor.storeUserSession(us1);
		  uSessAccessor.storeUserSession(us2);
		  uSessAccessor.storeUserSession(us3); 
		  uSessAccessor.storeUserSession(us4); 
		  
		  User courseUser = userAccessor.getCourseUser();
		  Course c1 = new Course (11111L, "Best JP course ever", "JP101",
				  new Date(), new Date());
		  c1.setInstructor(courseUser);
		  Course c2 = new Course (22222L, "Best JP level2 course ever", "JP102",
				  new Date(), new Date()); 
		  c2.setInstructor(courseUser);
		  Course c3 = new Course (33333L, "Best JP level3 course ever", "JP103",
				  new Date(), new Date()); 
		  c3.setInstructor(courseUser);

		  // Store them in the local In-memory datastore
		  CourseDAO courseAccessor = CourseDAO.getInstance();
		  courseAccessor.storeCourse(c1);
		  courseAccessor.storeCourse(c2);
		  courseAccessor.storeCourse(c3);
		  
		  QuizResponse qr1 = new QuizResponse(111L, 111L, 111L, 101L, "gplusID",
				  "difficultConfuser", false, false, 2.5f, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z" ).parse("2014-04-15 20:27:30 EST"),3,"dog,bird,cow", "Sequence");
		  QuizResponseDAO qAccessor = QuizResponseDAO.getInstance();
		  qAccessor.storeQuizResponse(qr1);
	  }
	  
	  @After
	  public void tearDown() throws Exception {
	    helper.tearDown();
	  }
	  
	  @Test
	  /**
	   * Test getUserName 
	   */
	  public void testGetUserName() {
		  AnalyticsServiceImpl asImpl = new AnalyticsServiceImpl();
		  String expResult = "Smith, John";
		  String result = asImpl.getUserName("googleID");
		  assertNotNull(result);
		  assertEquals("Retrieval of the User failed: expected: " + expResult, expResult, result);
	  }
	  
	  @Test
	  public void testBio() {
		  Map<String, String> data = new HashMap<String, String>();
		  AnalyticsServiceImpl asImpl = new AnalyticsServiceImpl();
		  UserDAO uAccessor = UserDAO.getInstance();
		  User u = uAccessor.getUserByGplusId("googleID");
		  String languageString = "";
		  String textbookString = "";
		  String outsideCourseString = "";
		  for (Language lan: u.getLanguages()) {
			  languageString += lan.getLangId() + ";" + lan.getLangName() +";";
		  }
		  for (Textbook tex: u.getTextbooks()) {
			  textbookString += tex.getTextbookID() + ";" + tex.getName() +";" + tex.getYear() + ";";
		  }
		  for (OutsideCourse oc: u.getOutsideCourses()) {
			  outsideCourseString += oc.getOutsideCourseID() + ";" + oc.getName() + ";" + oc.getInstitution() + ";";
		  }

		  data.put("id", u.getUserId().toString());
		  data.put("firstName", u.getFirstName());
		  data.put("lastName", u.getLastName());
		  data.put("gmail", u.getGmail());
		  data.put("gender", u.getGender().toString());
		  data.put("nativeLanguage", u.getNativeLanguage().getLangName());
		  data.put("noLanguages", Integer.toString(u.getLanguages().size()));
		  data.put("noTextbooks", Integer.toString(u.getTextbooks().size()));
		  data.put("noOutsideCourses", Integer.toString(u.getOutsideCourses().size()));
		  data.put("languages",  languageString);
		  data.put("textbooks",  textbookString);
		  data.put("outsideCourses", outsideCourseString);
		  // Compare
		  assertEquals(data, asImpl.getBiographicalData("googleID"));
	  }
	  
	  @Test
	  public void testGetAllStudents() {
		  List<User> users = new ArrayList<User>();
		  AnalyticsServiceImpl asImpl = new AnalyticsServiceImpl();
		  UserDAO uAccessor = UserDAO.getInstance();
		  assertNotNull(uAccessor.getAllUsers());
		  users = uAccessor.getAllUsers();
		  // Compare
		  assertEquals(users, asImpl.getAllStudents());
	  }
	  
	  @Test
	  public void testGetMetricsData() {
		  AnalyticsServiceImpl asImpl = new AnalyticsServiceImpl();
		  String gplusId = "gplusID";
		  MetricsCalculator mc = new MetricsCalculator();
		  Map<String, Float> data = new HashMap<String, Float>();	
		  data.put("recallRate", mc.calculateRecallRate(gplusId));
		  data.put("noClue", mc.calculatePercentNoClue(gplusId));
		  data.put("sortaKnewIt", mc.calculatePercentSortaKnewIt(gplusId));
		  data.put("definitelyKnewIt", mc.calculatePercentDefinitelyKnewIt(gplusId));
		  // Compare
		  assertEquals(data, asImpl.getMetricsData(gplusId));
	  }
	  
	  @Test
	  public void testGetMetricsDataByUser() {
		  AnalyticsServiceImpl asImpl = new AnalyticsServiceImpl();
		  String gplusId = "gplusID";
		  MetricsCalculator mc = new MetricsCalculator();
		  Map<String, Float> data = new HashMap<String, Float>();	
		  data.put("recallRate", mc.calculateRecallRate(gplusId));
		  data.put("noClue", mc.calculatePercentNoClue(gplusId));
		  data.put("sortaKnewIt", mc.calculatePercentSortaKnewIt(gplusId));
		  data.put("definitelyKnewIt", mc.calculatePercentDefinitelyKnewIt(gplusId));
		  // Compare
		  assertEquals(data, asImpl.getMetricsDataByUser(gplusId));
	  }
	  
	  @Test
	  public void testGetMetricsDataBySessions () {
		  AnalyticsServiceImpl asImpl = new AnalyticsServiceImpl();
		  MetricsCalculator mc = new MetricsCalculator();
		  long sessionId = 111L;
		  long userSessionId = 111L;
		  Map<String, Float> data = new HashMap<String, Float>();
		  data.put("recallRate", mc.calculateRecallRateBySessions(sessionId, userSessionId));
		  data.put("noClue", mc.calculatePercentNoClueBySessions(sessionId, userSessionId));
		  data.put("sortaKnewIt", mc.calculatePercentSortaKnewItBySessions(sessionId, userSessionId));
		  data.put("definitelyKnewIt", mc.calculatePercentDefinitelyKnewItBySessions(sessionId, userSessionId));
		  data.put("flashCardCount", (float)mc.calculateFlashCardCountBySessions(sessionId, userSessionId));
		  data.put("quizQuestionCount", (float)mc.calculateQuizCountBySessions(sessionId, userSessionId));
		  // Compare
		  assertEquals(data, asImpl.getMetricsDataBySessions(sessionId, userSessionId));
	  }
	  
	  @Test
	  public void testGetMetricsDataByUserAndAssignment () {
		  AnalyticsServiceImpl asImpl = new AnalyticsServiceImpl();
		  MetricsCalculator mc = new MetricsCalculator();
		  long sessionId = 111L;
		  String gplusId = "gplusID";
		  Map<String, Float> data = new HashMap<String, Float>();
		  data.put("recallRate", mc.calculateRecallRateByUserAndAssignment(gplusId, sessionId));
		  data.put("noClue", mc.calculatePercentNoClueByUserAndAssignment(gplusId, sessionId));
		  data.put("sortaKnewIt", mc.calculatePercentSortaKnewItByUserAndAssignment(gplusId, sessionId));
		  data.put("definitelyKnewIt", mc.calculatePercentDefinitelyKnewItByUserAndAssignment(gplusId, sessionId));
		  // Compare
		  assertEquals(data, asImpl.getMetricsDataByUserAndAssignment(gplusId, sessionId));
	  }
	  
	  @Test
	  public void testGetUsersInCourse() {
		  long courseId = 11111L;
		  CourseDAO courseAccessor = CourseDAO.getInstance();
		  assertNotNull(courseAccessor.getCourseById(courseId).getStudents());
		  AnalyticsServiceImpl asImpl = new AnalyticsServiceImpl();
		  // Compare
		  assertEquals(courseAccessor.getCourseById(courseId).getStudents(), asImpl.getUsersInCourse(courseId));
	  }
	  
	  @Test
	  public void testGetUserSessions () {
		  List<UserSession> userSessions = new ArrayList<UserSession>();
		  AnalyticsServiceImpl asImpl = new AnalyticsServiceImpl();
		  String gplusId = "gplusID";
		  UserSessionDAO usAccessor = UserSessionDAO.getInstance();
		  userSessions = usAccessor.getAllUserSessionsByUser(gplusId);
		  assertNotNull(userSessions);
		  // Compare
		  assertEquals(usAccessor.getAllUserSessionsByUser(gplusId).size(), asImpl.getUserSessions(gplusId).size());
		  gplusId = "non-existing";
		  userSessions = usAccessor.getAllUserSessionsByUser(gplusId);
		  assertNull(userSessions);
	  }
	  
	  @Test
	  public void testGetCourseAssignments () {
		  long courseId = 11111L;
		  List<Session> sessions = new ArrayList<Session>();
		  AnalyticsServiceImpl asImpl = new AnalyticsServiceImpl();
		  LessonDAO lAccessor = LessonDAO.getInstance();
		  QuizDAO qAccessor = QuizDAO.getInstance();
		  assertNotNull(lAccessor.getAllLessonsByCourseId(courseId));
		  for (Lesson l: lAccessor.getAllLessonsByCourseId(courseId)) {
			  sessions.add(l);
		  }
		  assertNotNull(qAccessor.getAllQuizsByCourseId(courseId));
		  for (Quiz q: qAccessor.getAllQuizsByCourseId(courseId)) {
			  sessions.add(q);
		  }
		  // Compare
		  assertEquals(sessions.size(), asImpl.getCourseAssignments(courseId).size());
	  }
	  
	  @Test
	  public void testGetCourseBiographicalData() {
		  long courseId = 11111L;
		  AnalyticsServiceImpl asImpl = new AnalyticsServiceImpl();
		  List<User> students = asImpl.getUsersInCourse(courseId);
		  Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
		  for (User s: students) {
			  Map<String, String> bioData = asImpl.getBiographicalData (s.getGplusId());
			  data.put(s.getUserId().toString(), bioData);
		  }
		  // Compare
		  assertEquals(data, asImpl.getCourseBiographicalData(courseId));
	  }
	  	  
	  @Test
	  public void testGetCourseMetricsData() {
		  long courseId = 11111L;
		  AnalyticsServiceImpl asImpl = new AnalyticsServiceImpl();
		  List<User> students = asImpl.getUsersInCourse(courseId);
		  Map<String, Map<String, Float>> data = new HashMap<String, Map<String, Float>>();
		  for (User s: students) {
			  Map<String, Float> metricsData = asImpl.getMetricsDataByUser (s.getGplusId());
			  data.put(s.getUserId().toString(), metricsData);
		  }
		  // Compare
		  assertEquals(data, asImpl.getCourseMetricsData(courseId));
	  }
	  
	  @Test
	  public void testGetCourseMetricsDataResearcherView() {
		  long courseId = 11111L;
		  AnalyticsServiceImpl asImpl = new AnalyticsServiceImpl();
		  List<User> students = asImpl.getUsersInCourse(courseId);
		  Map<String, Map<String, Float>> data = new HashMap<String, Map<String, Float>>();
		  for (User s: students) {
			  Map<String, Float> metricsData = asImpl.getMetricsDataByUser (s.getGplusId());
			  data.put(s.getFullName(), metricsData);
		  }
		  // Compare
		  assertEquals(data, asImpl.getCourseMetricsDataResearcherView(courseId));
	  }
	  
	  @Test
	  public void testGetCourseMetricsDataStudentView() {
		  long courseId = 11111L;
		  String gplusId = "gplusID";
		  AnalyticsServiceImpl asImpl = new AnalyticsServiceImpl();
		  List<Session> assignments = asImpl.getCourseAssignments(courseId);
		  List<UserSession> sessions = asImpl.getUserSessions(gplusId);
		  List<List<Object>> data = new ArrayList<List<Object>>();
		  for (Session a: assignments) {
			  for (UserSession us: sessions) {
				  Map<String, Float> metricsData = asImpl.getMetricsDataBySessions (a.getSessionId(), us.getUserSessionId());
				  if (metricsData.get("flashCardCount") >0.8 || metricsData.get("quizQuestionCount") > 0.8) {
					  List<Object> row = new ArrayList<Object>();
					  row.add(DateFormat.getInstance().format(us.getSessStart()));
					  row.add(a.getDeck().getDesc());
					  row.add(metricsData.get("noClue"));
					  row.add(metricsData.get("sortaKnewIt"));
					  row.add(metricsData.get("definitelyKnewIt"));
					  row.add(metricsData.get("recallRate"));
					  data.add(row);	
				  }
			  }
		  }
		  // Compare
		  assertEquals(data, asImpl.getCourseMetricsDataStudentView(courseId, gplusId));
	  }

	  @Test
	  public void testGetCourseMetricsDataInstructorViewNotNull() {
		  long courseId = 11111L;
		  long sessionId = 111L;
		  AnalyticsServiceImpl asImpl = new AnalyticsServiceImpl();
		  List<User> students = asImpl.getUsersInCourse(courseId);
		  Map<String, Map<String, Float>> data = new HashMap<String, Map<String, Float>>();
		  assertNotNull(sessionId);
		  for (User s: students) {
			  Map<String, Float> metricsData = asImpl.getMetricsDataByUserAndAssignment(s.getGplusId(), sessionId);
			  data.put(s.getFullName(), metricsData);
		  }
		  // Compare
		  assertEquals(data, asImpl.getCourseMetricsDataInstructorView(courseId, sessionId));
	  }

	  @Test
	  public void testGetCourseMetricsDataInstructorViewNull() {
		  long courseId = 11111L;
		  Long sessionId = null;
		  AnalyticsServiceImpl asImpl = new AnalyticsServiceImpl();
		  assertNull(sessionId);
		  // Compare
		  assertEquals(asImpl.getCourseMetricsDataResearcherView(courseId), asImpl.getCourseMetricsDataInstructorView(courseId, sessionId) );
	  }
	  
	  
	  @Test
	  public void testGenerateCsvAllData() {
		  AnalyticsServiceImpl asImpl = new AnalyticsServiceImpl();
		  StringBuilder sb = new StringBuilder();
		  sb.append("StudentID,GMail,Gender,NativeLanguage,NumberOfLanguages,NumberOfTextbooks,NumberOfOutsideCourses,"
				  + "Languages,Textbooks,OutsideCourses,RecallRate,"
				  + "PercentNoClue,PercentSortaKnewIt,PercentDefinitelyKnewIt");
		  sb.append("\n");
		  List<User> allStudents = asImpl.getAllStudents();
		  for (User s: allStudents) {
			  Map<String, String> bioData = asImpl.getBiographicalData(s.getGplusId());
			  Map<String, Float> metricsData = asImpl.getMetricsData(s.getGplusId());
			  sb.append(s.getUserId() + ",");
			  sb.append(bioData.get("gmail") + ",");
			  sb.append(bioData.get("gender") + ",");
			  sb.append(bioData.get("nativeLanguage") + ",");
			  sb.append(bioData.get("noLanguages") + ",");
			  sb.append(bioData.get("noTextbooks") + ",");
			  sb.append(bioData.get("noOutsideCourses") + ",");
			  sb.append(bioData.get("languages") + ",");
			  sb.append(bioData.get("textbooks") + ",");
			  sb.append(bioData.get("outsideCourses") + ",");
			  sb.append(metricsData.get("recallRate") + ",");
			  sb.append(metricsData.get("noClue") + ",");
			  sb.append(metricsData.get("sortaKnewIt") + ",");
			  sb.append(metricsData.get("definitelyKnewIt") + "\n");
		  }
		  // Compare
		  assertEquals(sb.toString(), asImpl.generateCsvAllData());
	  }
	
	@Test
	public void testCalculateAvgQuizReactionTime() {
		String gplusId = "gplusID";  
		MetricsCalculator mc = new MetricsCalculator();
		float totalQuizTimeToAnswer = 0.0f;
		int questionsSeen = 0;
		List<QuizResponse> qResps = mc.getAllQuizResponsesByUser(gplusId);
		if (qResps == null) {
			assertEquals(mc.calculateAvgQuizReactionTime(gplusId), 0.0f, 0.005);
		}
		if (!qResps.isEmpty()) {
			for (QuizResponse qr: qResps) {
				questionsSeen++;
				totalQuizTimeToAnswer = totalQuizTimeToAnswer + qr.getTimeToAnswer();
			}
		}
		assertEquals(mc.calculateAvgQuizReactionTime(gplusId), totalQuizTimeToAnswer/(float)questionsSeen, 0.005);
	}
	
	@Test
	public void testCalculateAvgFlashCardReactionTime() {
		String gplusId = "gplusID";  
		MetricsCalculator mc = new MetricsCalculator();
		float totalFlashCardTimeToAnswer = 0.0f;
		int cardsSeen = 0;
		List<FlashCardResponse> fcResps = mc.getAllFlashCardResponsesByUser(gplusId);
		if (fcResps == null) {
			assertEquals(mc.getAllFlashCardResponsesByUser(gplusId), 0.0f);
		}
		for (FlashCardResponse fcr: fcResps) {
			cardsSeen++;
			totalFlashCardTimeToAnswer = totalFlashCardTimeToAnswer + fcr.getTimeToAnswer();
		}
		assertEquals(mc.calculateAvgFlashCardReactionTime(gplusId), totalFlashCardTimeToAnswer/(float)cardsSeen, 0.005);
	}
	
	@Test
	public void testCalculateIndecisionRate() {
		String gplusId = "gplusID";  
		MetricsCalculator mc = new MetricsCalculator();
		int changedAnswers = 0;
		int questionsSeen = 0;
		List<QuizResponse> qResps = mc.getAllQuizResponsesByUser(gplusId);
		if (qResps == null) {
			assertEquals(mc.calculateIndecisionRate(gplusId), 0.0f, 0.005);
		}
		for (QuizResponse qr: qResps) {
			questionsSeen++;
			changedAnswers += (qr.isChanged()) ? 1 : 0;
		}
		assertEquals(mc.calculateIndecisionRate(gplusId), (float)changedAnswers/(float)questionsSeen, 0.05);
	}
	
	@Test
	public void testCalculateDropRate() {
		String gplusId = "gplusID";  
		MetricsCalculator mc = new MetricsCalculator();
		int droppedCards = 0;
		int cardsSeen = 0;
		List<FlashCardResponse> fcResps = mc.getAllFlashCardResponsesByUser(gplusId);
		if (fcResps == null) {
			assertEquals(mc.calculateDropRate(gplusId), 0.0f, 0.005);
		}
		for (FlashCardResponse fcr: fcResps) {
			cardsSeen++;
			droppedCards += (fcr.isDropped()) ? 1 : 0;
		}
		assertEquals(mc.calculateDropRate(gplusId), (float)droppedCards/(float)cardsSeen, 0.005);
	}
	
	@Test
	public void testCalculateAverageSessionTime() {
		String gplusId = "gplusID";  
		MetricsCalculator mc = new MetricsCalculator();
		float totalSessionTime = 0.0f;
		int noSessions = 0;
		List<UserSession> usResps = mc.getAllUserSessionsByUser(gplusId);
		if (usResps == null) {
			assertEquals(mc.calculateAverageSessionTime(gplusId), 0.0f, 0.005);
		}
		for (UserSession us: usResps) {
			noSessions++;
			float length = (float)(us.getSessEnd().getTime() - us.getSessStart().getTime());
			totalSessionTime = totalSessionTime + length;
		}
		assertEquals(mc.calculateAverageSessionTime(gplusId), totalSessionTime/(float)noSessions, 0.005);
	}
}
