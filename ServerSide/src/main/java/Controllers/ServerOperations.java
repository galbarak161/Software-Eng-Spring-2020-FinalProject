package Controllers;

import java.util.ArrayList;
import java.util.List;

import CloneEntities.*;
import CloneEntities.CloneTest.TestStatus;
import CloneEntities.CloneTimeExtensionRequest.RequestStatus;
import Hibernate.HibernateMain;
import Hibernate.Entities.*;
import Server.SendEmail;
import Server.SendEmail.MessageType;
import UtilClasses.ExamGenerator;
import UtilClasses.*;

public class ServerOperations {

	// static variable single_instance of type Singleton
	private static ServerOperations single_instance = null;

	private SendEmail mailer;

	private ServerOperations() {
		mailer = new SendEmail();
	}

	public SendEmail getMailer() {
		return mailer;
	}

	// static method to create instance of Singleton class
	public static ServerOperations getInstance() {
		if (single_instance == null)
			single_instance = new ServerOperations();
		return single_instance;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////// Exams
	/////////////////////////////////////////////////////////////////////////////////////////////////////////// ////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * handleSendAllExams()
	 * 
	 * sends all exams in DB
	 * 
	 * @return List<CloneExam> send all exams in system
	 */
	public List<CloneExam> handleSendAllExams() {
		List<Exam> listFromDB = null;
		List<CloneExam> cloneExams = new ArrayList<CloneExam>();
		try {
			listFromDB = HibernateMain.getDataFromDB(Exam.class);
			for (Exam exam : listFromDB) {
				cloneExams.add(exam.createClone());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return cloneExams;
	}

	/**
	 * handleSendAllExamsOfTeacher(CloneUser)
	 * 
	 * Function sends all the exams that the teacher has created
	 * 
	 * @param cloneUser Teacher
	 * @return List<CloneExam> of all the exams that the teacher created
	 */
	public List<CloneExam> handleSendAllExamsOfTeacher(CloneUser cloneUser) {
		List<Exam> listFromDB = null;
		List<CloneExam> cloneExams = new ArrayList<CloneExam>();
		try {
			listFromDB = HibernateMain.getDataFromDB(Exam.class);
			for (Exam exam : listFromDB) {
				if (exam.getCreator().getId() == cloneUser.getId()) {
					cloneExams.add(exam.createClone());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return cloneExams;
	}

	/**
	 * handleSendAllExamsOfTeacherInCourse(TeacherCourse)
	 * 
	 * returns all exams that the teacher is a creator and the exam is in a specific
	 * course
	 * 
	 * @param a class working as a container to teacher and course
	 * @return List<CloneExam> all test that the teacher is a creator and the exam
	 *         in a specific course
	 */
	public List<CloneExam> handleSendAllExamsOfTeacherInCourse(TeacherCourse data) {
		CloneUser cloneUser = data.getTeacher();
		CloneCourse cloneCourse = data.getCourse();
		List<Exam> listFromDB1 = null;
		List<CloneExam> exams = new ArrayList<CloneExam>();
		try {
			listFromDB1 = HibernateMain.getDataFromDB(Exam.class);
			for (Exam exam : listFromDB1) {
				if (exam.getCreator().getId() == cloneUser.getId() && exam.getCourse().getId() == cloneCourse.getId()) {

					exams.add(exam.createClone());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return exams;
	}

	/**
	 * handleCreateNewExam(CloneExam newCloneExam)
	 * 
	 * creates new Exam based on CloneExam
	 * 
	 * @param newExam a Clone exam entity that has all exam details.
	 * @return null if information is not complete , otherwise it updates the DB
	 *         with new exam
	 * @throws Exception
	 */
	public CloneExam handleCreateNewExam(ExamGenerator newGeneratedExam) throws Exception {
		CloneExam exam = newGeneratedExam.getExam();
		Teacher t = (Teacher) getUserByCloneId(exam.getTeacherId());
		Course c = getCourseByCloneId(exam.getCourseId());

		if (t == null || c == null)
			return null;

		Exam newExam = new Exam(exam.getExamName(), t, exam.getDuration(), c, exam.getTeacherComments(),
				exam.getStudentComments());

		HibernateMain.insertDataToDB(newExam);

		for (int i = 0; i < newGeneratedExam.getQuestions().size(); i++) {
			for (Question tempQuestion : HibernateMain.getDataFromDB(Question.class)) {
				if (tempQuestion.getId() == newGeneratedExam.getQuestions().get(i).getId()) {
					QuestionInExam questionInExam = new QuestionInExam(newGeneratedExam.getQuestionsPoint().get(i),
							newExam, tempQuestion);
					HibernateMain.insertDataToDB(questionInExam);
					newExam.addQuestionInExam(questionInExam);
				}
			}
		}

		System.out.println("New exam added. Exam id = " + newExam.getId() + ". Exam code = " + newExam.getExamCode());
		return newExam.createClone();
	}

	/**
	 * getExmaByCloneId (int)
	 * 
	 * Function receives CloneExam return Exam from DB that match the CloneExam.
	 * 
	 * @param examId
	 * @return null if not found otherwise it return Exam object
	 * @throws Exception
	 */
	public Exam getExmaByCloneId(int examId) throws Exception {
		List<Exam> listFromDB = null;
		listFromDB = HibernateMain.getDataFromDB(Exam.class);
		for (Exam exam : listFromDB) {
			if (exam.getId() == examId) {
				return exam;
			}
		}
		return null;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////// Tests
	/////////////////////////////////////////////////////////////////////////////////////////////////////////// ////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * handleSendAllStudentTests (CloneUser)
	 * 
	 * send all test related to a specific student
	 * 
	 * @param cloneUser a clone student
	 * @return all test related to student
	 */
	public List<CloneStudentTest> handleSendAllStudentTests(CloneUser cloneUser) {
		List<Student> students = null;
		List<CloneStudentTest> studentTest = new ArrayList<CloneStudentTest>();
		try {
			students = HibernateMain.getDataFromDB(Student.class);
			for (Student student : students) {
				if (student.getId() == cloneUser.getId()) {
					student.getTests().forEach(test -> studentTest.add(test.createClone()));
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return studentTest;
	}

	/**
	 * handleSendStudentTestRelatedToStudentInExam (StudentExamCode)
	 * 
	 * @param studentExamCode
	 * @return
	 * @throws Exception
	 */
	public CloneStudentTest handleSendStudentTestRelatedToStudentInExam(StudentStartTest studentExamCode)
			throws Exception {
		int userId = studentExamCode.getUserId();
		String Testcode = studentExamCode.getEexecutionCode();

		List<Student> students = HibernateMain.getDataFromDB(Student.class);
		Student s1 = null;
		for (Student student : students) {
			if (student.getId() == userId) {
				s1 = student;
				break;
			}
		}
		if (s1 == null) {
			return null;
		}

		List<StudentTest> studentTests = s1.getTests();
		for (StudentTest studentTest : studentTests) {
			if (studentTest.getTest().getExecutionCode().equals(Testcode)) {
				return studentTest.createClone();
			}
		}
		return null;
	}

	/**
	 * handleSendAnswersToExamOfStudentTest (CloneStudentTest)
	 * 
	 * function receives ClonesQuestionTest returns a list of CloneAnswerToQuestioin
	 * that are related to that student in CloneStudentTest
	 * 
	 * @param cloneStudentTest
	 * @return List<CloneAnswerToQuestion> , null if question code is not correct.
	 * @throws Exception if no student with given student id was found.
	 */
	public List<CloneAnswerToQuestion> handleSendAnswersToExamOfStudentTest(CloneStudentTest cloneStudentTest)
			throws Exception {
		StudentTest studentTest = getStudntTestByCloneId(cloneStudentTest.getId());
		List<AnswerToQuestion> answerToQuestions = studentTest.getAnswers();
		List<CloneAnswerToQuestion> cloneAnswerToQuestions = new ArrayList<CloneAnswerToQuestion>();

		for (AnswerToQuestion answerToQ : answerToQuestions) {
			CloneQuestion cloneQuestion = getQuestionByQuestionCode(answerToQ.getQuestionCode()).createClone();
			CloneAnswerToQuestion cloneAnswerToQuestion = answerToQ.createClone(cloneQuestion);
			cloneAnswerToQuestion.setAnswer(answerToQ.getStudentAnswer());
			cloneAnswerToQuestions.add(cloneAnswerToQuestion);
		}
		return cloneAnswerToQuestions;
	}

	/**
	 * handleSendAllTestRelatedToTeacher (CloneTest)
	 * 
	 * Get all studentsTest clones that are related to a specific test.
	 * 
	 * @param cloneTest test clone
	 * @return List<CloneStudentTest> all student test based upon cloneTest
	 */
	public List<CloneStudentTest> handleSendAllStudntTestRelatedToTest(CloneTest cloneTest) {
		List<StudentTest> listFromDB = null;
		List<CloneStudentTest> cloneStudentTests = new ArrayList<CloneStudentTest>();
		try {
			listFromDB = HibernateMain.getDataFromDB(StudentTest.class);
			for (StudentTest studentTest : listFromDB) {
				if ((studentTest.getTest().getTestDate().equals(cloneTest.getTestDateInFormat()))
						&& (studentTest.getTest().getExecutionCode().equals(cloneTest.getExecutionCode())))
					cloneStudentTests.add(studentTest.createClone());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return cloneStudentTests;
	}

	/**
	 * getStudntTestByCloneId(int) return studentTest object from DB match to it's
	 * Clone id
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public StudentTest getStudntTestByCloneId(int id) throws Exception {
		List<StudentTest> listFromDB = null;
		listFromDB = HibernateMain.getDataFromDB(StudentTest.class);
		for (StudentTest st : listFromDB) {
			if (st.getId() == id) {
				return st;
			}
		}
		return null;
	}

	/**
	 * handleSendAllTests()
	 * 
	 * send all test in DB
	 * 
	 * @return List<CloneTest> send all tests in system
	 */
	public List<CloneTest> handleSendAllTests() {
		List<Test> listFromDB = null;
		List<CloneTest> cloneTests = new ArrayList<CloneTest>();
		try {
			listFromDB = HibernateMain.getDataFromDB(Test.class);
			for (Test test : listFromDB) {
				cloneTests.add(test.createClone());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return cloneTests;
	}

	/**
	 * handleSendAllTestRelatedToTeacher (CloneUser)
	 * 
	 * Function returns all tests related to teacher, (if teacher created the exam
	 * the test relay upon)
	 * 
	 * @param cloneUser teacher
	 * @return List<CloneTest> that has all the test that are based upon an exam the
	 *         teacher created.
	 */
	public List<CloneTest> handleSendAllTestRelatedToTeacher(CloneUser cloneUser) {
		List<Test> listFromDB = null;
		List<CloneTest> cloneTests = new ArrayList<CloneTest>();
		try {
			listFromDB = HibernateMain.getDataFromDB(Test.class);
			for (Test test : listFromDB) {
				if (test.getExamToExecute().getCreator().getId() == cloneUser.getId()) {
					cloneTests.add(test.createClone());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return cloneTests;
	}

	/**
	 * handleSendAllTestsFromTeacher(CloneUser data)
	 * 
	 * Send all test related to teacher, he was the (executor)
	 * 
	 * @param CloneUser - Teacher wants to see all the tests that he was executed
	 * @return list of Tests
	 */
	public List<CloneTest> handleSendAllTestsFromTeacher(CloneUser cloneUser) {
		List<Teacher> listFromDB = null;
		List<CloneTest> tests = new ArrayList<CloneTest>();
		try {
			listFromDB = HibernateMain.getDataFromDB(Teacher.class);
			for (Teacher teacher : listFromDB) {
				if (teacher.getId() == cloneUser.getId()) {
					teacher.getTests().forEach(test -> tests.add(test.createClone()));
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return tests;
	}
	
	/**
	 * handleSendAllTestsOfTeacherInCourse (TeacherCourse)
	 * 
	 * send all test that the teacher is an executor and the test in a specific
	 * course
	 * 
	 * @param a class working as a container to teacher and course
	 * @return List<CloneTest> all test that the teacher is an executor and the test
	 *         in a specific course
	 */
	public List<CloneTest> handleSendAllTestsOfTeacherInCourse(TeacherCourse data) {
		CloneUser cloneUser = data.getTeacher();
		CloneCourse cloneCourse = data.getCourse();
		List<Test> listFromDB1 = null;
		List<CloneTest> tests = new ArrayList<CloneTest>();
		try {
			listFromDB1 = HibernateMain.getDataFromDB(Test.class);
			for (Test test : listFromDB1) {
				if (test.getExecutor().getId() == cloneUser.getId()
						&& test.getExamToExecute().getCourse().getId() == cloneCourse.getId()) {

					tests.add(test.createClone());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return tests;
	}

	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////// Question
	/////////////////////////////////////////////////////////////////////////////////////////////////////////// ///////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * handleSendAllQuestions()
	 * 
	 * The function sends back to user list of all questions in DB
	 * 
	 * @return list of CloneQuestion
	 */
	public List<CloneQuestion> handleSendAllQuestions() {
		List<Question> listFromDB = null;
		List<CloneQuestion> cloneQuestion = new ArrayList<CloneQuestion>();
		try {
			listFromDB = HibernateMain.getDataFromDB(Question.class);
			for (Question q : listFromDB) {
				cloneQuestion.add(q.createClone());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return cloneQuestion;
	}

	/**
	 * handleSendQuestionsInCourse (CloneCourse)
	 * 
	 * Send all the question related to a specific course
	 * 
	 * @param cloneCourse - User chose this course and asked for it's questions
	 * @return all the questions that are associated with this course
	 */
	public List<CloneQuestion> handleSendQuestionsInCourse(CloneCourse cloneCourse) {
		List<Course> listFromDB = null;
		List<CloneQuestion> questionsFromCourse = new ArrayList<CloneQuestion>();
		try {
			listFromDB = HibernateMain.getDataFromDB(Course.class);
			for (Course c : listFromDB) {
				if (c.getId() == cloneCourse.getId()) {
					c.getQuestions().forEach(q -> questionsFromCourse.add(q.createClone()));
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return questionsFromCourse;
	}

	/**
	 * handleCreateNewQuestion(CloneQuestion newCloneQuestion)
	 * 
	 * created new question, based on constructor in CloneQuestion and adds it to DB
	 * 
	 * @param newCloneQuestion the question we want to create
	 * @return null if information is not complete , otherwise it updates the Db
	 *         with new question
	 * @throws Exception
	 */
	public CloneQuestion handleCreateNewQuestion(CloneQuestion newCloneQuestion) throws Exception {

		Teacher t = (Teacher) getUserByCloneId(newCloneQuestion.getTeacherId());
		Course c = getCourseByCloneId(newCloneQuestion.getCourse().getId());

		if (t == null || c == null)
			return null;

		Question newQuestion = new Question(newCloneQuestion.getSubject(), newCloneQuestion.getQuestionText(),
				newCloneQuestion.getAnswer_1(), newCloneQuestion.getAnswer_2(), newCloneQuestion.getAnswer_3(),
				newCloneQuestion.getAnswer_4(), newCloneQuestion.getCorrectAnswer(), c, t);

		HibernateMain.insertDataToDB(newQuestion);

		System.out.println("New qustion added. Qustion id = " + newQuestion.getId() + ". Question code = "
				+ newQuestion.getQuestionCode());
		return newQuestion.createClone();
	}

	/**
	 * handleSendAllQuestionInExamRelatedToExam (CloneExam) Send all Exam's
	 * questions list (question & points)
	 * 
	 * @param cloneExam
	 * @return
	 * @throws Exception
	 */
	public List<CloneQuestionInExam> handleSendAllQuestionInExamRelatedToExam(CloneExam cloneExam) throws Exception {
		List<Exam> listFromDB = null;
		listFromDB = HibernateMain.getDataFromDB(Exam.class);
		Exam exam = null;
		for (Exam exam1 : listFromDB) {
			if (exam1.getId() == cloneExam.getId()) {
				exam = exam1;
				break;
			}
		}
		if (exam == null) {
			return null;
		}
		List<CloneQuestionInExam> cloneQuestionInExams = new ArrayList<CloneQuestionInExam>();
		for (QuestionInExam questionInExam : exam.getQuestionInExam()) {
			cloneQuestionInExams.add(questionInExam.createClone());
		}
		return cloneQuestionInExams;
	}

	/**
	 * getQuestionByQuestionCode (int)
	 * 
	 * Function receives QuestionCode returns Question from DB that match the
	 * QuesrionCode.
	 * 
	 * @param questionCode
	 * @return null if not found otherwise it return Question object
	 * @throws Exception
	 */
	public Question getQuestionByQuestionCode(int questionCode) throws Exception {
		List<Question> listFromDB = null;
		listFromDB = HibernateMain.getDataFromDB(Question.class);
		for (Question question : listFromDB) {
			if (question.getQuestionCode() == questionCode) {
				return question;
			}
		}
		return null;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////// Course
	/////////////////////////////////////////////////////////////////////////////////////////////////////////// ////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * handleSendAllCoursesOfTeacher(CloneUser)
	 * 
	 * Send all courses who are taught by teachers
	 * 
	 * @param CloneUser - Teacher wants to see all courses he teaches
	 * @return all the courses that are associated with this Teacher
	 */
	public List<CloneCourse> handleSendAllCoursesOfTeacher(CloneUser cloneUser) {
		List<Teacher> listFromDB = null;
		List<CloneCourse> courses = new ArrayList<CloneCourse>();
		try {
			listFromDB = HibernateMain.getDataFromDB(Teacher.class);
			for (Teacher teacher : listFromDB) {
				if (teacher.getId() == cloneUser.getId()) {
					teacher.getCourses().forEach(course -> courses.add(course.createClone()));
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return courses;
	}

	/**
	 * getCourseByCloneId (int)
	 * 
	 * Function receives CloneCourse returns Course from DB that match the
	 * CloneCourse.
	 * 
	 * @param courseId
	 * @return null if not found otherwise it return Course object
	 * @throws Exception
	 */
	public Course getCourseByCloneId(int courseId) throws Exception {
		List<Course> listFromDB = null;
		listFromDB = HibernateMain.getDataFromDB(Course.class);
		for (Course course : listFromDB) {
			if (course.getId() == courseId) {
				return course;
			}
		}
		return null;
	}

	public Principal getPrincipalUser() throws Exception {
		List<User> listFromDB = null;
		listFromDB = HibernateMain.getDataFromDB(User.class);
		for (User user : listFromDB) {
			if (user.isPrincipal() == true) {
				return (Principal) user;
			}
		}
		return null;
	}

	public StudentTest getStudntTestInTestIdByUserId(Test t, int userId) {
		for (StudentTest student : t.getStudents()) {
			if (student.getStudent().getId() == userId) {
				return student;
			}

		}
		return null;
	}

	public Test getTestByExamCode(String executionCode) throws Exception {
		List<Test> listFromDB = null;
		listFromDB = HibernateMain.getDataFromDB(Test.class);
		for (Test test : listFromDB) {
			if (test.getExecutionCode().equals(executionCode))
				return test;
		}
		return null;
	}

	public TestStatistics getTestStatisticsByTestId(int id) throws Exception {
		List<TestStatistics> listFromDB = null;
		listFromDB = HibernateMain.getDataFromDB(TestStatistics.class);
		for (TestStatistics ts : listFromDB) {
			if (ts.getId() == id) {
				return ts;
			}
		}
		return null;
	}

	/**
	 * getTestByCloneId (int)
	 * 
	 * function receives CloneTest returns Test from DB that match the CloneTest.
	 * 
	 * @param testid
	 * @return null if not found otherwise it return Test object
	 * @throws Exception
	 */
	public Test getTestByCloneId(int testid) throws Exception {
		List<Test> listFromDB = null;
		listFromDB = HibernateMain.getDataFromDB(Test.class);
		for (Test test : listFromDB) {
			if (test.getId() == testid) {
				return test;
			}
		}
		return null;
	}

	/**
	 * getUserByCloneId (int)
	 * 
	 * Function receives userId returns User from DB that match the CloneUser.
	 * 
	 * @param userId
	 * @return null if not found otherwise it return User object
	 * @throws Exception
	 */
	public User getUserByCloneId(int userId) throws Exception {
		List<User> listFromDB = null;
		listFromDB = HibernateMain.getDataFromDB(User.class);
		for (User user : listFromDB) {
			if (user.getId() == userId) {
				return user;
			}
		}
		return null;
	}

}
