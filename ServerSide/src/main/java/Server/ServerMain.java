package Server;

import java.io.IOException;
import java.util.Scanner;
import CloneEntities.*;
import Hibernate.HibernateMain;
import OCSF.AbstractServer;
import OCSF.ConnectionToClient;
import UtilClasses.DataElements;
import UtilClasses.ExamGenerator;
import UtilClasses.Login;
import UtilClasses.StudentExamCode;
import UtilClasses.TeacherCourse;

public class ServerMain extends AbstractServer {
	static int numberOfConnectedClients;
	private ServerOperations serverHandler = null;

	public ServerMain(int port) {
		super(port);
		serverHandler = new ServerOperations();
		numberOfConnectedClients = 0;
	}

	@Override
	protected synchronized void clientDisconnected(ConnectionToClient client) {
		System.out.println("Client disconnected from server");
		super.clientDisconnected(client);
		numberOfConnectedClients = this.getNumberOfClients() - 1;
		System.out.println("Number of connected client(s): " + numberOfConnectedClients + "\n");

		if (numberOfConnectedClients == 0) {
			/*
			 * System.out.print("Do you want to close the server? (Yes \\ No): ");
			 * 
			 * try (Scanner input = new Scanner(System.in)) { String stringInput =
			 * input.nextLine().toLowerCase(); if (stringInput.equals("yes")) { try {
			 * this.close(); } catch (IOException e) { e.printStackTrace(); } } else
			 * System.out.println("Server ready!"); }
			 */

			try {
				this.close();
			} catch (IOException e) {
				e.printStackTrace();
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
				dataToClient = serverHandler.handleSendAllTimeExtensionRequestRequests();
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
			case GetAnswerToTimeExtensionRequest:
				dataToClient = serverHandler.handleUpdateTimeExtensionRequest((CloneTimeExtensionRequest) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.UpdateTimeExtensionRequest);
				de.setData(dataToClient);
				break;
			case GetStudentTestRelatedToStudentInExam:
				dataToClient = serverHandler
						.handleSendStudentTestRelatedToStudentInExam((StudentExamCode) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.SendStudentTestRelatedToStudentInExam);
				de.setData(dataToClient);
				break;
			case GetAllQuestionInExamRelatedToExam:
				dataToClient = serverHandler.handleSendAllQuestionInExamRelatedToExam((CloneExam) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.SendAllQuestionInExamRelatedToExam);
				de.setData(dataToClient);
				break;
			case UserLogIn:
				dataToClient = serverHandler.handleLogInRequest((Login) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.UserLoggedIn);
				de.setData(dataToClient);
				break;
			case UserLogOut:
				dataToClient = serverHandler.handleLogOutRequest((int) de.getData());
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
				dataToClient = serverHandler.handleCreateNewTest((CloneTest) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.CreateNewTestResult);
				de.setData(dataToClient);
				break;
			case CreateNewTimeExtensionRequest:
				dataToClient = serverHandler
						.handleCreateNewTimeExtensionRequest((CloneTimeExtensionRequest) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.CreateNewTimeExtensionRequestResult);
				de.setData(dataToClient);
				break;
			case CreateNewStudentTest:
				dataToClient = serverHandler.handleCreateNewStudentTest((CloneStudentTest) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.CreateNewStudentTestResult);
				de.setData(dataToClient);
				break;
			case StudntStartsTest:
				dataToClient = serverHandler.handleStudentStartsTest((CloneStudentTest) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.StudntStartsTestResult);
				de.setData(dataToClient);
				break;
			case StudntFinshedTest:
				dataToClient = serverHandler.handleStudntFinshedTest((CloneStudentTest) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.StudntFinshedTestResult);
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
			System.out.println("Server ready!\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
