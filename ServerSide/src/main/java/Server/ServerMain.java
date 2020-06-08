package Server;

import java.io.IOException;
import java.util.Scanner;
import CloneEntities.*;
import CommonElements.DataElements;
import CommonElements.Login;
import Hibernate.HibernateMain;
import OCSF.AbstractServer;
import OCSF.ConnectionToClient;

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
			Object dataFromDB = null;
			System.out.println("Received message from client: opcode = " + de.getOpcodeFromClient());

			switch (de.getOpcodeFromClient()) {
			case GetAllExams:
				dataFromDB = serverHandler.handleSendAllExams();
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.SendAllExams);
				de.setData(dataFromDB);
				break;
			case GetAllTests:
				dataFromDB = serverHandler.handleSendAllTests();
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.SendAllTests);
				de.setData(dataFromDB);
				break;
			case GetAllQuestion:
				dataFromDB = serverHandler.handleSendAllQuestions();
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.SendAllQuestion);
				de.setData(dataFromDB);
				break;
			case GetAllQuestionInCourse:
				dataFromDB = serverHandler.handleSendQuestionsInCourse((CloneCourse) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.SendAllQuestionInCourse);
				de.setData(dataFromDB);
				break;
			case GetAllCoursesOfTeacher:
				dataFromDB = serverHandler.handleSendAllCoursesOfTeacher((CloneUser) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.SendAllCoursesOfTeacher);
				de.setData(dataFromDB);
				break;
			case GetAllTestsOfTeacher:
				dataFromDB = serverHandler.handleSendAllTestsFromTeacher((CloneUser) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.SendAllTestsOfTeacher);
				de.setData(dataFromDB);
				break;
			case GetAllExamsOfTeacher:
				dataFromDB = serverHandler.handleSendAllExamsOfTeacher((CloneUser) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.SendAllExamsOfTeacher);
				de.setData(dataFromDB);
				break;
			case GetAllTestsOfTeacherInCourse:
				dataFromDB = serverHandler.handleSendAllTestsOfTeacherInCourse((CloneTeacherCourse) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.SendAllTestsOfTeacherInCourse);
				de.setData(dataFromDB);
				break;
			case GetAllExamsOfTeacherInCourse:
				dataFromDB = serverHandler.handleSendAllExamsOfTeacherInCourse((CloneTeacherCourse) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.SendAllExamsOfTeacherInCourse);
				de.setData(dataFromDB);
				break;
			case GetAllStudentTests:
				dataFromDB = serverHandler.handleSendAllStudentTests((CloneUser) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.SendAllStudentTests);
				de.setData(dataFromDB);
				break;
			case UserLogIn:
				dataFromDB = serverHandler.handleLogInRequest((Login) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.UserLoggedIn);
				de.setData(dataFromDB);
				break;
			case UserLogOut:
				dataFromDB = serverHandler.handleLogOutRequest((int) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.UserLoggedOut);
				de.setData(dataFromDB);
				break;
			case CreateNewQuestion:
				dataFromDB = serverHandler.handleCreateNewQuestion((CloneQuestion) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.CreateNewQuestionResult);
				de.setData(dataFromDB);
				break;
			case GetAllRequests:
				dataFromDB = serverHandler.handleSendAllRequests();
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.SendAllRequests);
				de.setData(dataFromDB);
				break;
			case GetAllTestRelatedToTeacher:
				dataFromDB = serverHandler.handleSendAllTestRelatedToTeacher((CloneUser) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.SendAllTestRelatedToTeacher);
				de.setData(dataFromDB);
				break;
			case GetAllStudntTestRelatedToTest:
				dataFromDB = serverHandler.handleSendAllStudntTestRelatedToTest((CloneTest) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.SendAllStudntTestRelatedToTest);
				de.setData(dataFromDB);
				break;
			case CreateNewExam:
				dataFromDB = serverHandler.handleCreateNewExam((CloneExam) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.CreateNewExamResult);
				de.setData(dataFromDB);
				break;
			case CreateNewTest:
				dataFromDB = serverHandler.handleCreateNewTest((CloneTest) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.CreateNewTestResult);
				de.setData(dataFromDB);
				break;
			case CreateNewTimeExtensionRequest:
				dataFromDB = serverHandler.handleCreateNewTimeExtensionRequest((CloneTimeExtensionRequest) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.CreateNewTimeExtensionRequestResult);
				de.setData(dataFromDB);
				break;
			case GetAnswerToTimeExtensionRequest:
				dataFromDB = serverHandler.handleUpdateTimeExtensionRequest((CloneTimeExtensionRequest) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.UpdateTimeExtensionRequest);
				de.setData(dataFromDB);
				break;
			case CreateNewStudentTest:
				dataFromDB = serverHandler.handleCreateNewStudentTest((CloneStudentTest) de.getData());
				de.setOpCodeFromServer(DataElements.ServerToClientOpcodes.CreateNewStudentTestResult);
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
