package Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import CloneEntities.*;
import CommonElements.DataElements;
import Hibernate.HibernateMain;
import Hibernate.Entities.*;
import OCSF.AbstractServer;
import OCSF.ConnectionToClient;

public class ServerMain extends AbstractServer {
	static int numberOfConnectedClients;

	public ServerMain(int port) {
		super(port);
		numberOfConnectedClients = 0;
	}

	@Override
	protected synchronized void clientDisconnected(ConnectionToClient client) {
		System.out.println("Client disconnected from server");
		super.clientDisconnected(client);
		numberOfConnectedClients = this.getNumberOfClients() - 1;
		System.out.println("Number of connected client(s): " + numberOfConnectedClients + "\n");

		if (numberOfConnectedClients == 0) {
			System.out.print("Do you want to close the server? (Yes \\ No): ");

			try (Scanner input = new Scanner(System.in)) {
				String stringInput = input.nextLine().toLowerCase();
				if (stringInput.equals("yes")) {
					try {
						this.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else
					System.out.println("Server ready!");
			}
		}

	}

	@Override
	protected void clientConnected(ConnectionToClient client) {
		System.out.println("New client connected.");
		super.clientConnected(client);
		numberOfConnectedClients = this.getNumberOfClients();
		System.out.println("Number of connected client(s): " + numberOfConnectedClients + "\n");
	}

	@Override
	protected void serverClosed() {
		HibernateMain.closeSession();
		super.serverClosed();
	}

	/**
	 * The function gets new msg from client Parsing the opcode and data Handle the
	 * client request Send back results
	 */
	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		DataElements de = null;
		try {
			de = (DataElements) msg;
			Object dataFromDB = null;
			System.out.println("Received message from client: opcode = " + de.getOpcodeFromClient());

			switch (de.getOpcodeFromClient()) {
			case GetAllStudies:
				dataFromDB = handleSendStudiesToUser();
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.SendAllStudies);
				de.setData(dataFromDB);
				break;
			case GetAllCoursesInStudy:
				dataFromDB = handleSendCoursesFromStudy((CloneStudy) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.SendAllCoursesInStudy);
				de.setData(dataFromDB);
				break;
			case GetAllQuestionInCourse:
				dataFromDB = handleSendQuestionsFromCourse((CloneCourse) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.SendAllQuestionInCourse);
				de.setData(dataFromDB);
				break;
			case GetAllQuestion:
				dataFromDB = handleSendAllQuestions();
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.SendAllQuestion);
				de.setData(dataFromDB);
				break;
			case UpdateQuestion:
				dataFromDB = handleUpdateQuestion((CloneQuestion) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.UpdateQuestionResult);
				de.setData(dataFromDB);
				break;
			default:
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.Error);
				de.setData("handleMessageFromClient: Unknown Error");
			}

		} catch (Exception e) {
			e.printStackTrace();
			de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.Error);
			de.setData(e.getMessage());

		} finally {
			if (de.getData() == null) {
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.Error);
				de.setData("handleMessageFromClient: Unknown Error");
			}

			System.out.println("Send result to user! opcode = " + de.getOpCodeFromServer() + "\n");
			sendToAllClients(de);
		}
	}

	/**
	 * handleUpdateQuestion(CloneQuestion) Update Question object in DB according to
	 * CloneQuestion received from DB Get the original Question from DB Set all
	 * properties according to CloneQustion Update Question in DB
	 * 
	 * @param questionToUpdate
	 * @return
	 */
	private CloneQuestion handleUpdateQuestion(CloneQuestion questionToUpdate) {
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
	private List<CloneQuestion> handleSendAllQuestions() {
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
	private List<CloneQuestion> handleSendQuestionsFromCourse(CloneCourse cloneCourse) {
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
	private List<CloneStudy> handleSendStudiesToUser() {
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
	private List<CloneCourse> handleSendCoursesFromStudy(CloneStudy cloneStudy) {
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
	 * Entry point to server
	 * 
	 * @param args - <port>
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.out.println("Required argument: <port>");
			return;
		}

		ServerMain server = new ServerMain(Integer.parseInt(args[0]));
		boolean hibernateStatus = HibernateMain.initHibernate();
		if (hibernateStatus == false) {
			System.out.println("Error during Hibernate initialization");
			return;
		}

		server.listen();
		System.out.println("Server ready!\n");

	}
}
