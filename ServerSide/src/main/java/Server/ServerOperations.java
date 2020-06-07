package Server;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import CloneEntities.*;
import CommonElements.Login;
import Hibernate.HibernateMain;
import Hibernate.Entities.*;

public class ServerOperations {

	
	
	
	
	
	/**
	 *  function send all exam the teacher created
	 * @param cloneUser Teacher 
	 * @return	List<CloneExam> of all the exams that the teacher created
	 */
	public List<CloneExam> handleSendAllExamsOfTeacher(CloneUser cloneUser) {
		List<Exam> listFromDB = null;
		List<CloneExam> cloneExams = new ArrayList<CloneExam>();
		try {
			listFromDB = HibernateMain.getDataFromDB(Exam.class);
			for (Exam exam : listFromDB) {
				if (exam.getCreator().getId() == cloneUser.getId()) {
					cloneExams.add(exam.createClone());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return cloneExams;
	}
	
	
	/**
	 * handleSendAllTestRelatedToTeacher get all studentsTest clones that are
	 * related to a specific test.
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
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return cloneStudentTests;
	}

	/**
	 * handleSendAllTestRelatedToTeacher function returns all tests related to
	 * teacher, (if teacher created the exam the test relay upon)
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
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return cloneTests;
	}

	/**
	 * 
	 * @return send all time extension requests
	 */
	public List<CloneTimeExtensionRequest> handleSendAllRequests() {
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
	 * 
	 * @return send all exams in system
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
	 * 
	 * @return send all tests in system
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
	 * handleSendAllQuestions() The function sends back to user list of all
	 * questions in DB
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
	 * handleSendQuestionsInCourse(CloneCourse)
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
	 * handleSendAllCoursesOfTeacher(CloneUser)
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
	 * handleSendAllTestsFromTeacher(CloneUser data)
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

	public CloneUser handleLoginRequest(Login data) throws Exception {
		List<User> userList = HibernateMain.getDataFromDB(User.class);
		for (User user : userList) {
			if ((user.getUserName().equals(data.getUserName())) && (user.getPassword().equals(data.getPassword()))) {
				return user.createClone();
			}
		}
		return null;
	}

	/**
	 * handleSendAllTestsOfTeacherInCourse returns all test that the teacher is an
	 * executor and the test in a specific course
	 * 
	 * @param a class working as a container to teacher and course
	 * @return List<CloneTest> all test that the teacher is an executor and the test
	 *         in a specific course
	 */
	public List<CloneTest> handleSendAllTestsOfTeacherInCourse(CloneTeacherCourse data) {
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
	 * handleSendAllExamsOfTeacherInCourse returns all exams that the teacher is a
	 * creator and the exam is in a specific course
	 * 
	 * @param a class working as a container to teacher and course
	 * @return List<CloneExam> all test that the teacher is a creator and the exam
	 *         in a specific course
	 */
	public List<CloneExam> handleSendAllExamsOfTeacherInCourse(CloneTeacherCourse data) {
		CloneUser cloneUser = data.getTeacher();
		CloneCourse cloneCourse = data.getCourse();
		List<Exam> listFromDB1 = null;
		List<CloneExam> exams = new ArrayList<CloneExam>();
		try {
			listFromDB1 = HibernateMain.getDataFromDB(Exam.class);
			for (Exam exam : listFromDB1) {
				if (exam.getCreator().getId() == cloneUser.getId()
						&& exam.getCourse().getId() == cloneCourse.getId()) {

					exams.add(exam.createClone());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return exams;
	}

	/**
	 * handleSendAllStudentTests return all test related to a specific student
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
	 * 
	 * @param newCloneQuestion
	 * @return
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
		
		System.out.println("New qustion added. Qustion id = " + newQuestion.getId() + ". Question code = " + newQuestion.getQuestionCode());
		return newQuestion.createClone();
	}

	public CloneExam handleCreateNewExam(CloneExam newCloneExam) throws Exception {
		Teacher t = (Teacher) getUserByCloneId(newCloneExam.getTeacherId());
		Course c = getCourseByCloneId(newCloneExam.getCourseId());

		if (t == null || c == null)
			return null;

		Exam newExam = new Exam(newCloneExam.getExamName(), t, null, newCloneExam.getDuration(), c,
				newCloneExam.getTeacherComments(), newCloneExam.getStudentComments());

		HibernateMain.insertDataToDB(newCloneExam);
		
		System.out.println("New exam added. Exam id = " + newExam.getId() + ". Exam code = " + newExam.getExamCode());
		return newExam.createClone();
	}

	public CloneTest handleCreateNewTest(CloneTest newCloneTest) throws Exception {
		Teacher t = (Teacher) getUserByCloneId(newCloneTest.getTeacherId());
		Exam e = getExmaByCloneId(newCloneTest.getExamToExecute().getId());

		if (t == null || e == null)
			return null;

		Test newTest = new Test(newCloneTest.getTestDate(), newCloneTest.getTestTime(), newCloneTest.getType(), t, e);

		HibernateMain.insertDataToDB(newTest);

		System.out.println("New test added. Test id = " + newTest.getId() + ". Test execution code = " + newTest.getExecutionCode());
		return newTest.createClone();
	}
	
	public CloneStudentTest handleCreateNewStudentTest(CloneStudentTest newCloneStudentTest) throws Exception {
		Student s = (Student) getUserByCloneId(newCloneStudentTest.getStudent().getId());
		Test t = getTestByCloneId(newCloneStudentTest.getTest().getId());
		if (t == null || s == null)
			return null;

		StudentTest newStudentTest = new StudentTest(s, t);
				
		HibernateMain.insertDataToDB(newStudentTest);

		System.out.println("Student " + newStudentTest.getStudent().getId() + " has started new test.");
		return newStudentTest.createClone();
	}

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

//////////////////////////////
//	Update Entity Template	//
//////////////////////////////	
//	/**
//	 * handleUpdateQuestion(CloneQuestion) Update Question object in DB according to
//	 * CloneQuestion received from DB Get the original Question from DB Set all
//	 * properties according to CloneQustion Update Question in DB
//	 * 
//	 * @param questionToUpdate
//	 * @return
//	 */
//	public CloneQuestion handleUpdateQuestion(CloneQuestion questionToUpdate) {
//		Question originalQustion = null;
//		List<Question> listFromDB = null;
//		try {
//			listFromDB = HibernateMain.getDataFromDB(Question.class);
//			for (Question question : listFromDB) {
//				if (question.getId() == questionToUpdate.getId()) {
//					originalQustion = question;
//					break;
//				}
//			}
//
//			if (originalQustion == null)
//				throw new Exception("Question with id " + questionToUpdate.getId() + " was not found!");
//
//			originalQustion.setAnswer_1(questionToUpdate.getAnswer_1());
//			originalQustion.setAnswer_2(questionToUpdate.getAnswer_2());
//			originalQustion.setAnswer_3(questionToUpdate.getAnswer_3());
//			originalQustion.setAnswer_4(questionToUpdate.getAnswer_4());
//			originalQustion.setCorrectAnswer(questionToUpdate.getCorrectAnswer());
//			originalQustion.setQuestionText(questionToUpdate.getQuestionText());
//			originalQustion.setSubject(questionToUpdate.getSubject());
//
//			int updateResult = HibernateMain.questionToUpdate(originalQustion);
//
//			if (updateResult == -1)
//				originalQustion = null;
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//
//		return originalQustion.createClone();
//	}
}
