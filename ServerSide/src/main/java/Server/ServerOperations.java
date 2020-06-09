package Server;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Session;

import CloneEntities.*;
import CloneEntities.CloneStudentTest.StudentTestStatus;
import CloneEntities.CloneTest.TestStatus;
import Hibernate.HibernateMain;
import Hibernate.Entities.*;
import UtilClasses.ExamGenerator;
import UtilClasses.Login;
import UtilClasses.StudentExamCode;
import UtilClasses.TeacherCourse;

public class ServerOperations {

	//////////////////////////////////////////////
	//////////////////////////////////////////////
	/////////////// Student Test /////////////////
	//////////////////////////////////////////////
	//////////////////////////////////////////////

	/**
	 * handleSendStudentTestRelatedToStudentInExam (StudentExamCode)
	 * 
	 * @param studentExamCode
	 * @return
	 * @throws Exception
	 */
	public CloneStudentTest handleSendStudentTestRelatedToStudentInExam(StudentExamCode studentExamCode)
			throws Exception {
		CloneUser user = studentExamCode.getUser();
		String Testcode = studentExamCode.getExamCode();

		List<Student> students = HibernateMain.getDataFromDB(Student.class);
		Student s1 = null;
		for (Student student : students) {
			if (student.getId() == user.getId()) {
				s1 = student;
				break;
			}
		}
		if (s1 == null) {
			return null;
		}

		List<StudentTest> studentTests = s1.getTests();
		for (StudentTest studentTest : studentTests) {
			if (studentTest.getTest().getExecutionCode() == Testcode) {
				return studentTest.createClone();
			}
			;
		}
		return null;
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
				if (studentTest.getTest().getTestDate() == cloneTest.getTestDate()
						&& studentTest.getTest().getTestTime() == cloneTest.getTestTime()
						&& studentTest.getTest().getExecutionCode() == cloneTest.getExecutionCode()) {
					cloneStudentTests.add(studentTest.createClone());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return cloneStudentTests;
	}

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
	 * handleCreateNewStudentTest(CloneStudentTest newCloneStudentTest)
	 * 
	 * creates new, Exam based on CloneTest
	 * 
	 * @param newCloneStudentTest a Clone exam entity that has all StudentTest
	 *                            details.
	 * @return null if information is not complete , otherwise it updates the DB
	 *         with new StudentTest
	 * @throws Exception
	 */
	public CloneStudentTest handleCreateNewStudentTest(CloneStudentTest newCloneStudentTest) throws Exception {
		Student s = (Student) getUserByCloneId(newCloneStudentTest.getStudent().getId());
		Test t = getTestByCloneId(newCloneStudentTest.getTest().getId());

		StudentTest newStudentTest = new StudentTest(s, t);

		HibernateMain.insertDataToDB(newStudentTest);

		System.out.println("Student " + newStudentTest.getStudent().getId() + " has started new test.");
		return newStudentTest.createClone();
	}

	/**
	 * handleStudentStartsTest (CloneStudentTest)
	 * 
	 * The function update StudentTest status and test statistics
	 * 
	 * @param studentTest
	 * @return 1 If succeeded (Others -1)
	 * @throws Exception
	 */
	public int handleStudentStartsTest(CloneStudentTest studentTest) {
		int status = 1;
		try {
			StudentTest st = getStudntTestByCloneId(studentTest.getId());
			TestStatistics statistics = getTestStatisticsByTestId(st.getTest().getId());
			st.setStatus(StudentTestStatus.Ongoing);
			statistics.increaseNumberOfStudentsInTest();

			HibernateMain.UpdateDataInDB(st);
			Thread.sleep(100);
			HibernateMain.UpdateDataInDB(statistics);

		} catch (Exception e) {
			status = 1;
		}
		return status;
	}

	/**
	 * handleStudntFinshedTest (CloneStudentTest)
	 * 
	 * The function update StudentTest status after student finished
	 * 
	 * @param studentTest
	 * @return 1 If succeeded (Others -1)
	 * @throws Exception
	 */
	public int handleStudntFinshedTest(CloneStudentTest studentTest) {
		int status = 1;
		try {
			StudentTest st = getStudntTestByCloneId(studentTest.getId());
			TestStatistics statistics = getTestStatisticsByTestId(st.getTest().getId());
			Exam e = getExmaByCloneId(st.getTest().getExamToExecute().getId());
			st.setStatus(StudentTestStatus.WaitingForResult);

			if (st.getTest().getStatus() == TestStatus.Ongoing)
				statistics.increaseNumberOfStudentsThatFinishedInTime();

			CloneAnswerToQuestion[] answers = studentTest.getAnswers();
			for (int i = 0; i < e.getQuestionInExam().size(); i++) {
				AnswerToQuestion answer = new AnswerToQuestion(answers[i].getStudentAnswer(), st, answers[i].getQuestionId());
				HibernateMain.insertDataToDB(answer);
			}
			
			CheckAutomaticTest(st);

			HibernateMain.UpdateDataInDB(statistics);

		} catch (Exception e) {
			status = 1;
		}
		return status;
	}

	private void CheckAutomaticTest(StudentTest st) {
		HibernateMain.UpdateDataInDB(st);
		// TODO Auto-generated method stub
		
	}

	private TestStatistics getTestStatisticsByTestId(int id) throws Exception {
		List<TestStatistics> listFromDB = null;
		listFromDB = HibernateMain.getDataFromDB(TestStatistics.class);
		for (TestStatistics ts : listFromDB) {
			if (ts.getId() == id) {
				return ts;
			}
		}
		return null;
	}

	private StudentTest getStudntTestByCloneId(int id) throws Exception {
		List<StudentTest> listFromDB = null;
		listFromDB = HibernateMain.getDataFromDB(StudentTest.class);
		for (StudentTest st : listFromDB) {
			if (st.getId() == id) {
				return st;
			}
		}
		return null;
	}

	//////////////////////////////////////////////
	//////////////////////////////////////////////
	/////////////////// Exam /////////////////////
	//////////////////////////////////////////////
	//////////////////////////////////////////////

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
	private Exam getExmaByCloneId(int examId) throws Exception {
		List<Exam> listFromDB = null;
		listFromDB = HibernateMain.getDataFromDB(Exam.class);
		for (Exam exam : listFromDB) {
			if (exam.getId() == examId) {
				return exam;
			}
		}
		return null;
	}

	//////////////////////////////////////////////
	//////////////////////////////////////////////
	/////////////////// Test /////////////////////
	//////////////////////////////////////////////
	//////////////////////////////////////////////

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

	/**
	 * handleCreateNewTest(CloneTest newCloneTest)
	 * 
	 * creates new Test based on CloneTest
	 * 
	 * @param newCloneTest a Clone exam entity that has all exam details.
	 * @return null if information is not complete , otherwise it updates the DB
	 *         with new test
	 * @throws Exception
	 */
	public CloneTest handleCreateNewTest(CloneTest newCloneTest) throws Exception {
		Teacher t = (Teacher) getUserByCloneId(newCloneTest.getTeacherId());
		Exam e = getExmaByCloneId(newCloneTest.getExamToExecute().getId());

		if (t == null || e == null)
			return null;

		Test newTest = new Test(newCloneTest.getTestDate(), newCloneTest.getTestTime(), newCloneTest.getType(), t, e);
		TestStatistics statisitcs = new TestStatistics(newTest);

		HibernateMain.insertDataToDB(newTest);
		Thread.sleep(100);
		HibernateMain.insertDataToDB(statisitcs);

		System.out.println("New test added. Test id = " + newTest.getId() + ". Test execution code = "
				+ newTest.getExecutionCode());

		Course course = getCourseByCloneId(newCloneTest.getExamToExecute().getCourseId());

		List<Student> students = course.getStudents();
		for (Student student : students) {
			HibernateMain.insertDataToDB(new StudentTest(student, newTest));
		}

		return newTest.createClone();
	}

	/**
	 * handleUpdateTimeExtensionRequest (cloneTimeExtensionRequest)
	 * 
	 * function receives answer on time extension request from client and updates
	 * Test entity in DB
	 * 
	 * @param cloneTimeExtensionRequest the time request with answer
	 * @return CloneTest with updates time
	 * @throws Exception
	 */
	public CloneTest handleUpdateTimeExtensionRequest(CloneTimeExtensionRequest cloneTimeExtensionRequest)
			throws Exception {

		if (cloneTimeExtensionRequest.isRequestConfirmed() == false)
			return cloneTimeExtensionRequest.getTest();

		List<TimeExtensionRequest> timeExtensionRequests = HibernateMain.getDataFromDB(TimeExtensionRequest.class);
		TimeExtensionRequest request = null;
		for (TimeExtensionRequest timeExtensionRequest : timeExtensionRequests) {
			if (timeExtensionRequest.getId() == cloneTimeExtensionRequest.getId()) {
				request = timeExtensionRequest;
			}
		}

		if (request == null)
			return null;

		request.setRequestConfirmed(true);

		HibernateMain.UpdateDataInDB(request);
		Thread.sleep(100);
		HibernateMain.UpdateDataInDB(request.getTest());

		return request.getTest().createClone();
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
	private Test getTestByCloneId(int testid) throws Exception {
		List<Test> listFromDB = null;
		listFromDB = HibernateMain.getDataFromDB(Test.class);
		for (Test test : listFromDB) {
			if (test.getId() == testid) {
				return test;
			}
		}
		return null;
	}

	//////////////////////////////////////////////
	//////////////////////////////////////////////
	///////////// Time Extension Request /////////
	//////////////////////////////////////////////
	//////////////////////////////////////////////

	/**
	 * handleSendAllRequests()
	 * 
	 * sends all time extension requests
	 * 
	 * @return List<CloneTimeExtensionRequest> send all time extension requests
	 */
	public List<CloneTimeExtensionRequest> handleSendAllTimeExtensionRequestRequests() {
		List<TimeExtensionRequest> listFromDB = null;
		List<CloneTimeExtensionRequest> cloneRequests = new ArrayList<CloneTimeExtensionRequest>();
		try {
			listFromDB = HibernateMain.getDataFromDB(TimeExtensionRequest.class);
			for (TimeExtensionRequest Tex : listFromDB) {
				cloneRequests.add(Tex.createClone());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return cloneRequests;
	}

	/**
	 * handleCreateNewTimeExtensionRequest (CloneTimeExtensionRequest)
	 * 
	 * creates new TimeExtensionRequest based on CloneTimeExtensionRequest
	 * 
	 * @param newCloneTimeExtensionRequest a Clone TimeExtensionRequest entity that
	 *                                     has all TimeExtensionRequest details.
	 * @return null if information is not complete , otherwise it updates the DB
	 *         with new TimeExtensionRequest
	 * @throws Exception
	 * 
	 */
	public CloneTimeExtensionRequest handleCreateNewTimeExtensionRequest(
			CloneTimeExtensionRequest newCloneTimeExtensionRequest) throws Exception {
		TimeExtensionRequest timeExtensionRequest = new TimeExtensionRequest(newCloneTimeExtensionRequest.getBody(),
				newCloneTimeExtensionRequest.getTimeToExtenedInMinute());
		if (newCloneTimeExtensionRequest.getTest() == null) {
			return null;
		}
		Test test = getTestByCloneId(newCloneTimeExtensionRequest.getTest().getId());
		timeExtensionRequest.setTest(test);

		HibernateMain.insertDataToDB(timeExtensionRequest);

		return timeExtensionRequest.createClone();

	}

	//////////////////////////////////////////////
	//////////////////////////////////////////////
	////////////////// Question //////////////////
	//////////////////////////////////////////////
	//////////////////////////////////////////////

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

	//////////////////////////////////////////////
	//////////////////////////////////////////////
	////////////// Question In Exam //////////////
	//////////////////////////////////////////////
	//////////////////////////////////////////////

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

	//////////////////////////////////////////////
	//////////////////////////////////////////////
	/////////////////// Course ///////////////////
	//////////////////////////////////////////////
	//////////////////////////////////////////////

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
	private Course getCourseByCloneId(int courseId) throws Exception {
		List<Course> listFromDB = null;
		listFromDB = HibernateMain.getDataFromDB(Course.class);
		for (Course course : listFromDB) {
			if (course.getId() == courseId) {
				return course;
			}
		}
		return null;
	}

	//////////////////////////////////////////////
	//////////////////////////////////////////////
	//////////////////// User ////////////////////
	//////////////////////////////////////////////
	//////////////////////////////////////////////

	/**
	 * handleLogInRequest(Login data)
	 * 
	 * function check users's username and passwords and keeps user from login while
	 * account is till logged from another place, and updates log status in DB.
	 * 
	 * @param data contains username and password entered
	 * @return if the information is correct and he is not logged in the system
	 *         returns new CloneUsert
	 * @throws Exception
	 */
	public CloneUser handleLogInRequest(Login data) throws Exception {
		List<User> userList = HibernateMain.getDataFromDB(User.class);
		for (User user : userList) {
			if ((user.getUserName().equals(data.getUserName())) && (user.getPassword().equals(data.getPassword()))
					&& (user.isLoggedIn() == false)) {
				user.setLoggedIn(true);
				if (HibernateMain.UpdateDataInDB(user) == 1)
					return user.createClone();
			}
		}
		return null;
	}

	/**
	 * handleLogOutRequest(int userId)
	 * 
	 * function checkis user is log in and if he is the function update login status
	 * in DB
	 * 
	 * @param userId the users id , who want to log out
	 * @return -1 if user is not found to be logged in , otherwise it found the user
	 *         and updates status.
	 * @throws Exception
	 */
	public int handleLogOutRequest(int userId) throws Exception {
		List<User> userList = HibernateMain.getDataFromDB(User.class);
		for (User user : userList) {
			if (user.getId() == userId) {
				user.setLoggedIn(false);
				return HibernateMain.UpdateDataInDB(user);
			}
		}
		return -1;
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
