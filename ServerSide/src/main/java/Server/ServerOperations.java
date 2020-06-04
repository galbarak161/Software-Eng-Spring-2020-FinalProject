package Server;

import java.util.ArrayList;
import java.util.List;

import CloneEntities.CloneCourse;
import CloneEntities.CloneQuestion;
import CloneEntities.CloneStudy;
import CloneEntities.CloneUser;
import CommonElements.Login;
import Hibernate.HibernateMain;
import Hibernate.Entities.Course;
import Hibernate.Entities.Question;
import Hibernate.Entities.Study;
import Hibernate.Entities.Teacher;
import Hibernate.Entities.User;

public class ServerOperations {

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
	 * handleUpdateQuestion(CloneQuestion) Update Question object in DB according to
	 * CloneQuestion received from DB Get the original Question from DB Set all
	 * properties according to CloneQustion Update Question in DB
	 * 
	 * @param questionToUpdate
	 * @return
	 */
	public CloneQuestion handleUpdateQuestion(CloneQuestion questionToUpdate) {
		Question originalQustion = null;
		List<Question> listFromDB = null;
		try {
			listFromDB = HibernateMain.getDataFromDB(Question.class);
			for (Question question : listFromDB) {
				if (question.getId() == questionToUpdate.getId()) {
					originalQustion = question;
					break;
				}
			}

			if (originalQustion == null)
				throw new Exception("Question with id " + questionToUpdate.getId() + " was not found!");

			originalQustion.setAnswer_1(questionToUpdate.getAnswer_1());
			originalQustion.setAnswer_2(questionToUpdate.getAnswer_2());
			originalQustion.setAnswer_3(questionToUpdate.getAnswer_3());
			originalQustion.setAnswer_4(questionToUpdate.getAnswer_4());
			originalQustion.setCorrectAnswer(questionToUpdate.getCorrectAnswer());
			originalQustion.setQuestionText(questionToUpdate.getQuestionText());
			originalQustion.setSubject(questionToUpdate.getSubject());

			int updateResult = HibernateMain.questionToUpdate(originalQustion);

			if (updateResult == -1)
				originalQustion = null;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		// System.out.println("Question " + originalQustion.getId() + " (QuestionCode =
		// "
		// + originalQustion.getQuestionCode() + ") - Was updated.");
		return originalQustion.createClone();
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

		// cloneQuestion.forEach(q -> System.out.println(q.getQuestionCode() + " - " +
		// q.getSubject()));
		return cloneQuestion;
	}

	/**
	 * handleSendQuestionsFromCourse(CloneCourse)
	 * 
	 * @param cloneCourse - User chose this course and asked for it's questions
	 * @return all the questions that are associated with this course
	 */
	public List<CloneQuestion> handleSendQuestionsFromCourse(CloneCourse cloneCourse) {
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

		// System.out.println("List of questions from course " +
		// cloneCourse.getCourseName());
		// questionsFromCourse.forEach(q -> System.out.println(q.getQuestionCode()));
		return questionsFromCourse;
	}

	/**
	 * handleSendStudiesToUser() The function sends back to user list of all studies
	 * in DB
	 * 
	 * @return list of cloneStudy
	 */
	public List<CloneStudy> handleSendStudiesToUser() {
		List<Study> listFromDB = null;
		List<CloneStudy> cloneStudies = new ArrayList<CloneStudy>();
		try {
			listFromDB = HibernateMain.getDataFromDB(Study.class);
			for (Study study : listFromDB) {
				cloneStudies.add(study.createClone());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		// cloneStudies.forEach(s -> System.out.println(s.getStudyName()));
		return cloneStudies;
	}

	/**
	 * handleSendCoursesFromStudy(CloneStudy)
	 * 
	 * @param cloneStudy - User chose this study and asked for it's courses
	 * @return all the courses that are associated with this study
	 */
	public List<CloneCourse> handleSendCoursesFromStudy(CloneStudy cloneStudy) {
		List<Study> listFromDB = null;
		List<CloneCourse> courses = new ArrayList<CloneCourse>();
		try {
			listFromDB = HibernateMain.getDataFromDB(Study.class);
			for (Study study : listFromDB) {
				if (study.getId() == cloneStudy.getId()) {
					study.getCourses().forEach(course -> courses.add(course.createClone()));
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		// System.out.println("List of courses from study " +
		// cloneStudy.getStudyName());
		// courses.forEach(q -> System.out.println(q.getCourseName()));
		return courses;
	}
	
	/**
	 * handleSendAllCoursesFromTeacher(CloneUser)
	 * 
	 * @param CloneUser - Teacher wants to see all courses he teaches
	 * @return all the courses that are associated with this Teacher
	 */
	public List<CloneCourse> handleSendAllCoursesFromTeacher(CloneUser cloneUser) {
		List<Teacher> listFromDB = null;
		List<CloneCourse> courses = new ArrayList<CloneCourse>();
		try {
			listFromDB = HibernateMain.getDataFromDB(Teacher.class);
			for (Teacher teacher : listFromDB) {
				if (teacher.getIdentityNumber() == cloneUser.getIdentityNumber()) {
					teacher.getCourses().forEach(course -> courses.add(course.createClone()));
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		// System.out.println("List of courses from study " +
		// cloneStudy.getStudyName());
		// courses.forEach(q -> System.out.println(q.getCourseName()));
		return courses;
	}
}
