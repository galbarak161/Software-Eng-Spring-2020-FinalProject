package Server;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;

import CloneEntities.*;
import Controllers.DoTestController;
import Controllers.ServerOperations;
import Controllers.UserController;
import Hibernate.HibernateMain;
import OCSF.AbstractServer;
import OCSF.ConnectionToClient;
import UtilClasses.*;

public class ServerMain extends AbstractServer {

	private static int numberOfConnectedClients;
	private ServerOperations serverHandler = null;
	private UserController userController = null;
	private DoTestController doTestController = null;

	private static Timer timer;

	public ServerMain(int port) {
		super(port);
		serverHandler = ServerOperations.getInstance();
		userController = new UserController();
		doTestController = new DoTestController();

		timer = new Timer();
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
		timer.cancel();
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
			Object dataToClient = null;
			System.out.println("Received message from client: opcode = " + de.getOpcodeFromClient());

			switch (de.getOpcodeFromClient()) {
			case GetAllExams:
				dataToClient = serverHandler.handleSendAllExams();
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.SendAllExams);
				de.setData(dataToClient);
				break;
			case GetAllTests:
				dataToClient = serverHandler.handleSendAllTests();
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.SendAllTests);
				de.setData(dataToClient);
				break;
			case GetAllQuestion:
				dataToClient = serverHandler.handleSendAllQuestions();
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.SendAllQuestion);
				de.setData(dataToClient);
				break;
			case GetAllQuestionInCourse:
				dataToClient = serverHandler.handleSendQuestionsInCourse((CloneCourse) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.SendAllQuestionInCourse);
				de.setData(dataToClient);
				break;
			case GetAllCoursesOfTeacher:
				dataToClient = serverHandler.handleSendAllCoursesOfTeacher((CloneUser) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.SendAllCoursesOfTeacher);
				de.setData(dataToClient);
				break;
			case GetAllTestsOfTeacher:
				dataToClient = serverHandler.handleSendAllTestsFromTeacher((CloneUser) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.SendAllTestsOfTeacher);
				de.setData(dataToClient);
				break;
			case GetAllExamsOfTeacher:
				dataToClient = serverHandler.handleSendAllExamsOfTeacher((CloneUser) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.SendAllExamsOfTeacher);
				de.setData(dataToClient);
				break;
			case GetAllTestsOfTeacherInCourse:
				dataToClient = serverHandler.handleSendAllTestsOfTeacherInCourse((TeacherCourse) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.SendAllTestsOfTeacherInCourse);
				de.setData(dataToClient);
				break;
			case GetAllExamsOfTeacherInCourse:
				dataToClient = serverHandler.handleSendAllExamsOfTeacherInCourse((TeacherCourse) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.SendAllExamsOfTeacherInCourse);
				de.setData(dataToClient);
				break;
			case GetAllStudentTests:
				dataToClient = serverHandler.handleSendAllStudentTests((CloneUser) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.SendAllStudentTests);
				de.setData(dataToClient);
				break;
			case GetAllTimeExtensionRequestRequests:
				dataToClient = doTestController.handleSendAllTimeExtensionRequests();
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.SendAllTimeExtensionRequestRequests);
				de.setData(dataToClient);
				break;
			case GetAllTestRelatedToTeacher:
				dataToClient = serverHandler.handleSendAllTestRelatedToTeacher((CloneUser) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.SendAllTestRelatedToTeacher);
				de.setData(dataToClient);
				break;
			case GetAllStudntTestRelatedToTest:
				dataToClient = serverHandler.handleSendAllStudntTestRelatedToTest((CloneTest) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.SendAllStudntTestRelatedToTest);
				de.setData(dataToClient);
				break;
			case GetStudentTestRelatedToStudentInExam:
				dataToClient = serverHandler
						.handleSendStudentTestRelatedToStudentInExam((StudentStartTest) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.SendStudentTestRelatedToStudentInExam);
				de.setData(dataToClient);
				break;
			case GetAllQuestionInExamRelatedToExam:
				dataToClient = serverHandler.handleSendAllQuestionInExamRelatedToExam((CloneExam) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.SendAllQuestionInExamRelatedToExam);
				de.setData(dataToClient);
				break;
			case GetAnswersToExamOfStudentTest:
				dataToClient = serverHandler.handleSendAnswersToExamOfStudentTest((CloneStudentTest) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.SendAnswersToExamOfStudentTest);
				de.setData(dataToClient);
				break;
			case GetAnswerToTimeExtensionRequest:
				dataToClient = doTestController.handleSendTimeExtensionRequestsRelatedToTest((int) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.SendTimeExtensionResult);
				de.setData(dataToClient);
				break;
			case UserLogIn:
				dataToClient = userController.handleLogInRequest((Login) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.UserLoggedIn);
				de.setData(dataToClient);
				break;
			case UserLogOut:
				dataToClient = userController.handleLogOutRequest((int) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.UserLoggedOut);
				de.setData(dataToClient);
				break;

			case CreateNewQuestion:
				dataToClient = serverHandler.handleCreateNewQuestion((CloneQuestion) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.CreateNewQuestionResult);
				de.setData(dataToClient);
				break;
			case CreateNewExam:
				dataToClient = serverHandler.handleCreateNewExam((ExamGenerator) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.CreateNewExamResult);
				de.setData(dataToClient);
				break;
			case CreateNewTest:
				dataToClient = doTestController.handleCreateNewTest((CloneTest) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.CreateNewTestResult);
				de.setData(dataToClient);
				break;
			case CreateNewTimeExtensionRequest:
				dataToClient = doTestController
						.handleCreateNewTimeExtensionRequest((CloneTimeExtensionRequest) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.CreateNewTimeExtensionRequestResult);
				de.setData(dataToClient);
				break;

			case UpdateTimeExtension:
				dataToClient = doTestController
						.handleUpdateTimeExtensionRequest((CloneTimeExtensionRequest) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.UpdateTimeExtensionRequest);
				de.setData(dataToClient);
				break;
			case StudentStartsTest:
				dataToClient = doTestController.handleStudentStartsTest((StudentStartTest) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.StudentStartsTestResult);
				de.setData(dataToClient);
				break;
			case StudentFinishedTest:
				dataToClient = doTestController.handleStudentFinishedTest((CloneStudentTest) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.StudentFinishedTestResult);
				de.setData(dataToClient);
				break;

			case TeacherUpdateGrade:
				dataToClient = doTestController.handleTeacherUpdateGrade((List<updateNotes>) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.TeacherUpdateGradeResult);
				de.setData(dataToClient);
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
	 * Entry point to server
	 * 
	 * @param args - <port>
	 * @throws IOException
	 */
	public static void main(String[] args) {
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

		try {
			server.listen();

			System.out.println("Server: Initialize timer thread... \n");

			long threadTime = 10000;
			timer.schedule(new TimerHandler(), 0, threadTime);

			System.out.println(
					"Server: Timer thread initialize. Update server status every " + threadTime + " milliseconds\n");

			System.out.println("Server ready!\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
