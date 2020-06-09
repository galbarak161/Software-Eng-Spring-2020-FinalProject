package Hibernate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Settings;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.service.ServiceRegistry;

import CloneEntities.*;
import CloneEntities.CloneTest.ExamType;
import Hibernate.Entities.*;
import Server.ServerOperations;
import UtilClasses.Login;

public class HibernateMain {
	static Session session;
	static boolean hibernateSessionStatus = false;

	/**
	 * The function create the session between the server and DB
	 * 
	 * @return new session to DB
	 * @throws HibernateException
	 */
	private static SessionFactory getSessionFactory() throws HibernateException {
		// Turn off hibernate warning & info messages
		Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

		// Configure all objects annotations
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(Study.class);
		configuration.addAnnotatedClass(Course.class);
		configuration.addAnnotatedClass(Question.class);
		configuration.addAnnotatedClass(User.class);
		configuration.addAnnotatedClass(Student.class);
		configuration.addAnnotatedClass(Teacher.class);
		configuration.addAnnotatedClass(Principal.class);
		configuration.addAnnotatedClass(Exam.class);
		configuration.addAnnotatedClass(QuestionInExam.class);
		configuration.addAnnotatedClass(StudentTest.class);
		configuration.addAnnotatedClass(Test.class);
		configuration.addAnnotatedClass(TimeExtensionRequest.class);
		configuration.addAnnotatedClass(AnswerToQuestion.class);
		configuration.addAnnotatedClass(TestStatistics.class);

		// Create new service and return it as session to DB
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();

		return configuration.buildSessionFactory(serviceRegistry);
	}

	/**
	 * Get data collections from DB
	 * 
	 * @param <T>    - Type of entity
	 * @param entity
	 * @return List of entities from type <T>
	 * @throws Exception
	 */
	public static <T> List<T> getDataFromDB(Class<T> entity) throws Exception {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = builder.createQuery(entity);
		Root<T> rootEntry = criteriaQuery.from(entity);
		CriteriaQuery<T> allCriteriaQuery = criteriaQuery.select(rootEntry);
		TypedQuery<T> allQuery = session.createQuery(allCriteriaQuery);
		return allQuery.getResultList();
	}

	/**
	 * Function to handle Errors in Hibernate Every Bibernate exception will be
	 * handle by this function
	 */
	public static void hibernateRollBake() {
		if (session != null) {
			session.getTransaction().rollback();
		}
		System.err.println("Hibernate: An error occured, changes have been rolled back.");
	}

	public static <T> int insertDataToDB(T newEntitiy) {
		int status = 1;
		try {
			session.beginTransaction();
			session.save(newEntitiy);
			session.flush();
			session.clear();
			session.getTransaction().commit();			
		} catch (Exception e) {
			hibernateRollBake();
			status = -1;
		}
		return status;
	}

	public static <T> int UpdateDataInDB(T entitiy) {
		int status = 1;
		try {
			session.beginTransaction();
			session.evict(entitiy);
			session.update(entitiy);
			session.getTransaction().commit();			
		} catch (Exception e) {
			hibernateRollBake();
			status = -1;
		}
		return status;
	}

	/**
	 * Print object data using override toString() function
	 * 
	 * @param <T>        - Type of entity
	 * @param objectList
	 */
	public static <T> void printObjects(List<T> objectList) {
		for (int i = 0; i < objectList.size(); i++) {
			System.out.println(objectList.get(i));
		}
	}

	/**
	 * Initialize new objects and set connections
	 * 
	 * @throws Exception
	 */
	private static void initializeData() throws Exception {

		final int NUMBER_OF_STUDIES = 5;
		final int NUMBER_OF_COURSES = 10;
		final int NUMBER_OF_QUESTIONS = 13;

		// Generate users
		Student s1 = new Student("GalB", "123", "Gal", "Barak", "Check@Check.com");
		session.save(s1);
		Student s2 = new Student("abedI", "123", "Abed", "Idres", "Check@Check.com");
		session.save(s2);
		Student s3 = new Student("OrA", "123", "Or", "Ashkenazi", "Check@Check.com");
		session.save(s3);
		Student s4 = new Student("SadiG", "123", "Sagi", "Gvili", "Check@Check.com");
		session.save(s4);
		Teacher t1 = new Teacher("MalkiG", "123", "Malki", "Grossman", "Check@Check.com");
		session.save(t1);
		Teacher t2 = new Teacher("LielF", "123", "Liel", "Fridman", "Check@Check.com");
		session.save(t2);
		Principal d1 = new Principal("DaniK", "123", "Dani", "Keren", "Check@Check.com");
		session.save(d1);

		session.flush();

		// Generate fields of study
		Study[] studies = new Study[NUMBER_OF_STUDIES];
		String[] studiesName = new String[NUMBER_OF_STUDIES];
		studiesName[0] = "Math";
		studiesName[1] = "English";
		studiesName[2] = "History";
		studiesName[3] = "Computer Science";
		studiesName[4] = "Biology";
		for (int i = 0; i < NUMBER_OF_STUDIES; i++) {
			Study s = new Study(studiesName[i]);
			studies[i] = s;
			session.save(s);
		}
		session.flush();

		// Generate courses
		Course[] courses = new Course[NUMBER_OF_COURSES];
		String[] coursesName = new String[NUMBER_OF_COURSES];
		coursesName[0] = "Geometry";
		coursesName[1] = "Algebra";
		coursesName[2] = "Grammer";
		coursesName[3] = "Vocabulary";
		coursesName[4] = "20th Century";
		coursesName[5] = "19th Century";
		coursesName[6] = "Intro to C";
		coursesName[7] = "Data Structures";
		coursesName[8] = "Digestive System";
		coursesName[9] = "Animals";
		for (int i = 0, j = 0; i < NUMBER_OF_COURSES && j < NUMBER_OF_STUDIES; j++) {
			Course c1 = new Course(coursesName[i], studies[j]);
			c1.addTeacher(t1);
			c1.addStudent(s1);
			c1.addStudent(s2);
			courses[i] = c1;
			i++;
			session.save(c1);
			Course c2 = new Course(coursesName[i], studies[j]);
			c2.addTeacher(t2);
			c2.addStudent(s1);
			c2.addStudent(s3);
			courses[i] = c2;

			i++;
			session.save(c2);
		}
		session.flush();

		// Generate questions
		Question[] questions = new Question[NUMBER_OF_QUESTIONS];
		String[] questionsSubject = new String[NUMBER_OF_QUESTIONS];
		String[] questionsText = new String[NUMBER_OF_QUESTIONS];
		String[][] questionsAnswers = new String[NUMBER_OF_QUESTIONS][4];
		int[] correctAnswer = new int[NUMBER_OF_QUESTIONS];

		questionsText[0] = "How many edges does a triangle have?";
		questionsSubject[0] = "Triangles";
		questionsAnswers[0][0] = "4";
		questionsAnswers[0][1] = "3";
		questionsAnswers[0][2] = "5";
		questionsAnswers[0][3] = "6";
		correctAnswer[0] = 2;

		questionsText[1] = "How many edges does a square have?";
		questionsSubject[1] = "Squares";
		questionsAnswers[1][0] = "7";
		questionsAnswers[1][1] = "9";
		questionsAnswers[1][2] = "4";
		questionsAnswers[1][3] = "3";
		correctAnswer[1] = 3;

		questionsText[2] = "How many equations do we need to solve a 4 parameters matrix?";
		questionsSubject[2] = "Matrices";
		questionsAnswers[2][0] = "4";
		questionsAnswers[2][1] = "1";
		questionsAnswers[2][2] = "2";
		questionsAnswers[2][3] = "6";
		correctAnswer[2] = 1;

		questionsText[3] = "When do we use a 'do' or a 'does'?";
		questionsSubject[3] = "Present Simple";
		questionsAnswers[3][0] = "To describe a person";
		questionsAnswers[3][1] = "We use it for asking questions, but only a 'do'";
		questionsAnswers[3][2] = "To describe somebody's act";
		questionsAnswers[3][3] = "We use it for asking questions, 'does' for singular and 'do' for plural";
		correctAnswer[3] = 4;

		questionsText[4] = "What is a Pavement?";
		questionsSubject[4] = "Hard words";
		questionsAnswers[4][0] = "Kind of a kitchen tool";
		questionsAnswers[4][1] = "A laptop seating";
		questionsAnswers[4][2] = "A passage on the street for walking people";
		questionsAnswers[4][3] = "Main road";
		correctAnswer[4] = 3;

		questionsText[5] = "When did World War I occurr?";
		questionsSubject[5] = "World War I";
		questionsAnswers[5][0] = "1914-1918";
		questionsAnswers[5][1] = "1920-1940";
		questionsAnswers[5][2] = "1939-1945";
		questionsAnswers[5][3] = "1908-1911";
		correctAnswer[5] = 1;

		questionsText[6] = "When did The American Civil War occurr?";
		questionsSubject[6] = "The American Civil War";
		questionsAnswers[6][0] = "1850-1855";
		questionsAnswers[6][1] = "1861-1865";
		questionsAnswers[6][2] = "1820-1830";
		questionsAnswers[6][3] = "1888-1890";
		correctAnswer[6] = 2;

		questionsText[7] = "How do we focre-stopping a loop?";
		questionsSubject[7] = "Loops";
		questionsAnswers[7][0] = "Using 'Continue'";
		questionsAnswers[7][1] = "Using 'Break'";
		questionsAnswers[7][2] = "Using 'End'";
		questionsAnswers[7][3] = "Using 'Stop'";
		correctAnswer[7] = 2;

		questionsText[8] = "What command is used for printing to command prompt?";
		questionsSubject[8] = "I/O";
		questionsAnswers[8][0] = "printf";
		questionsAnswers[8][1] = "cout";
		questionsAnswers[8][2] = "print";
		questionsAnswers[8][3] = "write";
		correctAnswer[8] = 2;

		questionsText[9] = "What is the lower bound (in time complexity) of sorting?";
		questionsSubject[9] = "Sorting";
		questionsAnswers[9][0] = "O(1)";
		questionsAnswers[9][1] = "O(n)";
		questionsAnswers[9][2] = "O(n^2)";
		questionsAnswers[9][3] = "O(nlogn)";
		correctAnswer[9] = 4;

		questionsText[10] = "How long does it take to digest bread?";
		questionsSubject[10] = "Digest of carbohydrates";
		questionsAnswers[10][0] = "An hour";
		questionsAnswers[10][1] = "Two hours";
		questionsAnswers[10][2] = "An hour and a half";
		questionsAnswers[10][3] = "Three hours";
		correctAnswer[10] = 3;

		questionsText[11] = "How long is the pregnancy of a dog?";
		questionsSubject[11] = "Reproduction";
		questionsAnswers[11][0] = "8 months";
		questionsAnswers[11][1] = "3 months";
		questionsAnswers[11][2] = "2 months";
		questionsAnswers[11][3] = "9 months";
		correctAnswer[11] = 2;

		questionsText[12] = "Who is at the top of the food chain?";
		questionsSubject[12] = "The Food Chain";
		questionsAnswers[12][0] = "Apex predator";
		questionsAnswers[12][1] = "Predators";
		questionsAnswers[12][2] = "Mammals";
		questionsAnswers[12][3] = "Humans";
		correctAnswer[12] = 1;

		for (int i = 0; i < NUMBER_OF_QUESTIONS; i++) {
			Course c = new Course();
			if (i == 0)
				c = courses[i];
			else if (0 < i && i <= 7)
				c = courses[i - 1];
			else if (i > 7 && i < 12)
				c = courses[i - 2];
			else if (i >= 12)
				c = courses[i - 3];

			questions[i] = new Question(questionsSubject[i], questionsText[i], questionsAnswers[i][0],
					questionsAnswers[i][1], questionsAnswers[i][2], questionsAnswers[i][3], correctAnswer[i], c, t1);
			session.save(questions[i]);
		}

		session.flush();

		// Generate exams
		Question[] questionList = new Question[2];
		questionList[0] = questions[1];
		questionList[1] = questions[2];

		List<Integer> questionPoints = new ArrayList<>();
		questionPoints.add(50);
		questionPoints.add(50);

		int duration = 60;

		String name = "exam name";
		String teacherCommString = " working hard or hardly working";
		String studentCommString = " help me";

		Exam e1 = new Exam(name, t1, duration, courses[0], teacherCommString, studentCommString);
		session.save(e1);
		session.flush();
		QuestionInExam qe1 = new QuestionInExam(50, e1, questionList[0]);
		QuestionInExam qe2 = new QuestionInExam(50, e1, questionList[1]);

		session.save(qe1);
		session.save(qe2);
		session.flush();
		// e1.addQuestion(questionList);
		// session.save(e1);

		session.flush();

		// Generate tests

		final int year = 2020;
		final int month = 6;
		final int dayOfMonth = 16;
		final int hour = 14;
		final int minute = 30;
		LocalDate testDate = LocalDate.of(year, month, dayOfMonth);
		LocalTime testTime = LocalTime.of(hour, minute);

		Test test1 = new Test(testDate, testTime, ExamType.Automated, t1, e1);
		session.save(test1);

		TimeExtensionRequest request = new TimeExtensionRequest("Bima tem odd zman", 50);
		test1.setExtensionRequests(request);
		session.save(request);
		session.flush();
		request.setRequestConfirmed(true);
		
		Test test2 = new Test(testDate, testTime, ExamType.Automated, t1, e1);
		session.save(test2);
		Test test3= new Test(testDate, testTime, ExamType.Automated, t1, e1);
		session.save(test2);
		session.flush();

		StudentTest st1 = new StudentTest(s1, test1);
		session.save(st1);
		StudentTest st2 = new StudentTest(s2, test1);
		session.save(st2);
		StudentTest st3 = new StudentTest(s2, test2);
		session.save(st3);
		
		AnswerToQuestion a1 = new AnswerToQuestion(1,st1,questions[1].getId());
		session.save(a1);
		
		AnswerToQuestion a2 = new AnswerToQuestion(3,st1,questions[2].getId());
		session.save(a2);
		
		AnswerToQuestion a3 = new AnswerToQuestion(4,st2,questions[1].getId());
		session.save(a3);
		
		
//		List<CloneTimeExtensionRequest> cloneTimeExtensionRequests= ServerOperations.handleSendAllRequests();
//		for (CloneTimeExtensionRequest clest : cloneTimeExtensionRequests) {
//			System.out.println(clest.getTimeToExtenedInMinute());
//		}
		
//		List<CloneQuestionInExam> cloneQuestionInExams = ServerOperations.handleSendAllQuestionInExamRelatedToExam(e1.createClone());
//		for (CloneQuestionInExam cloneQuestionInExam : cloneQuestionInExams) {
//			System.out.println(cloneQuestionInExam.getPointsForQuestion());
//		}
		session.flush();

		session.clear();

		//////////////////////////////////////////////
		//////////////////////////////////////////////
		//////////////// Tests ///////////////////////
		//////////////////////////////////////////////
		//////////////////////////////////////////////

//		ServerOperations sp = new ServerOperations();
//		
//		System.out.println("Hibernate: Committing all queries before closing connection...\n");
//		session.getTransaction().commit();
//		System.out.println("create question");
//		
//		CloneQuestion cQuestion = new CloneQuestion("subject","q1","a1","a2","a3","a4",4,courses[0].createClone(),t1.getId());
//		sp.handleCreateNewQuestion(cQuestion);
//		
//		System.out.println("create exam");
//		List<CloneQuestion> cqList= new ArrayList<>();
//		cqList.add(questions[0].createClone());
//		List<Integer> cqpList= new ArrayList<>();
//		cqpList.add(100);
//		CloneExam cExam = new CloneExam(cqList, cqpList, 60, "exam name" , "", "", 10, t1.getId());
//		ServerOperations.handleCreateNewExam(cExam);
//		
//		System.out.println("create test");
//		CloneTest cTest = new CloneTest(testDate, testTime, "idfk", 0, 60, ExamType.Automated, t1.getId(), e1.createClone());
//		ServerOperations.handleCreateNewTest(cTest);
//		
//		System.out.println("create studentTest");
//		CloneStudentTest cStudentTest = new CloneStudentTest(s1.createClone(), cTest);
//		ServerOperations.handleCreateNewStudentTest(cStudentTest);
	}

	public static void main(String[] args) {
		try {
			initHibernate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession();
		}
	}

	public static void closeSession() {
		session.close();
		System.out.println("Hibernate: Close session to DB!\n");
	}

	public static boolean initHibernate() {
		boolean status = false;
		try {
			System.out.println("Hibernate: Create new session using getSessionFactory...\n");
			SessionFactory sessionFactory = getSessionFactory();

			System.out.println("Hibernate: Open session...\n");
			session = sessionFactory.openSession();

			System.out.println("Hibernate: Check session properties...\n");
			SessionFactoryImpl sessionImpl = (SessionFactoryImpl) session.getSessionFactory();
			Settings setting = sessionImpl.getSettings();

			// Insert basic data to DB only for "create" property
			if (setting.isAutoCreateSchema()) {
				System.out.println("Hibernate: Begin Transaction...\n");
				session.beginTransaction();

				System.out.println("Hibernate: Generate and insert data to DB...\n");
				initializeData();

				System.out.println("Hibernate: Committing all queries before closing connection...\n");
				session.getTransaction().commit();
			}

			status = true;

		} catch (Exception exception) {
			hibernateRollBake();
			status = false;
			exception.printStackTrace();
		}
		hibernateSessionStatus = status;
		return status;
	}
}
