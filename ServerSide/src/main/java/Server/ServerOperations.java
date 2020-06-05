package Server;

import java.util.ArrayList;
import java.util.List;

import CloneEntities.*;
import CommonElements.Login;
import Hibernate.HibernateMain;
import Hibernate.Entities.*;

public class ServerOperations {

	public Object handleSendAllExams() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object handleSendAllTests() {
		// TODO Auto-generated method stub
		return null;
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

	public List<CloneExam> handleSendAllExamsOfTeacherInCourse(CloneTeacherCourse data) {
		CloneUser cloneUser = data.getTeacher();
		CloneCourse cloneCourse = data.getCourse();
		List<Exam> listFromDB1 = null;
		List<CloneExam> exams = new ArrayList<CloneExam>();
		try {
			listFromDB1 = HibernateMain.getDataFromDB(Exam.class);
			for (Exam exam : listFromDB1) {
				if (exam.getCreator().getId() == cloneUser.getId() && exam.getCourse().getId() == cloneCourse.getId()) {

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

	public CloneQuestion handleCreateNewQuestion(CloneQuestion newCloneQuestion) throws Exception {

		Teacher t = getTeacherByCloneId(newCloneQuestion.getTeacherId());
		Course c = getCourseByCloneId(newCloneQuestion.getCourseId());

		if (t == null || c == null)
			return null;

		Question newQustion = new Question(newCloneQuestion.getSubject(), newCloneQuestion.getQuestionText(),
				newCloneQuestion.getAnswer_1(), newCloneQuestion.getAnswer_2(), newCloneQuestion.getAnswer_3(),
				newCloneQuestion.getAnswer_4(), newCloneQuestion.getCorrectAnswer(), c, t);

		HibernateMain.insertDataToDB(newQustion);

		Thread.sleep(10);

		List<Question> listFromDB = null;
		try {
			listFromDB = HibernateMain.getDataFromDB(Question.class);
			for (Question question : listFromDB) {
				if (question.getId() == newQustion.getId())
					newQustion = question;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return newQustion.createClone();
	}

	public CloneExam handleCreateNewExam(CloneExam newCloneExam) throws Exception {
		Teacher t = getTeacherByCloneId(newCloneExam.getTeacherId());
		Course c = getCourseByCloneId(newCloneExam.getCourseId());

		if (t == null || c == null)
			return null;

		Exam newExam = new Exam(newCloneExam.getExamName(), t, null, newCloneExam.getDuration(), c,
				newCloneExam.getTeacherComments(), newCloneExam.getStudentComments());

		HibernateMain.insertDataToDB(newCloneExam);

		Thread.sleep(10);

		List<Exam> listFromDB = null;
		try {
			listFromDB = HibernateMain.getDataFromDB(Exam.class);
			for (Exam exam : listFromDB) {
				if (exam.getId() == newExam.getId())
					newExam = exam;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return newExam.createClone();
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

	public Teacher getTeacherByCloneId(int userId) throws Exception {
		List<Teacher> listFromDB = null;
		listFromDB = HibernateMain.getDataFromDB(Teacher.class);
		for (Teacher teacher : listFromDB) {
			if (teacher.getId() == userId) {
				return teacher;
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
