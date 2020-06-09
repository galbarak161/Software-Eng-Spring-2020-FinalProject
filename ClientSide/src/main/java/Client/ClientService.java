package Client;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import CloneEntities.*;
import OCSF.AbstractClient;
import UtilClasses.DataElements;
import UtilClasses.DataElements.*;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;

public class ClientService extends AbstractClient {
	private static final Logger LOGGER = Logger.getLogger(ClientService.class.getName());
	private ClientMain clientM;
	public static Map<String, Object> controllers;

	/*
	 * Takes host and port, init. in AbstractClient, then init. local clientM init.
	 * clientS in CLientMain controllersList contains all controllers we have in our
	 * project Using it for handling changes from client requests
	 */
	public ClientService(String host, int port) {
		super(host, port);
		this.clientM = new ClientMain(this);
		controllers = new HashMap<String, Object>();
	}

	@Override
	protected void connectionEstablished() {
		super.connectionEstablished();
		LOGGER.info("Connected to server.");
	}

	@Override
	protected void connectionClosed() {
		super.connectionClosed();
		clientM.closeConnection();
	}

	public static Object getController(String controller) {
		Object o = controllers.get(controller);
		return o;
	}

	/**
	 * The function gets new message from server Parsing the opcode and data Handle
	 * the server results List of OpCodes from server SendAllExams(101),
	 * SendAllTests(102), SendAllQuestion(103), SendAllQuestionInCourse(104),
	 * SendAllCoursesOfTeacher(105), SendAllTestsOfTeacher(106),
	 * SendAllExamsOfTeacherInCourse(107), SendAllStudentTests(108),
	 * UserLoggedIn(109), CreateNewQuestionResult(110), CreateNewExamResult(111),
	 * CreateNewTestResult(112), SendAllRequests(113),Error(-1);
	 */
	@SuppressWarnings({ "unchecked", "incomplete-switch" })
	@Override
	protected void handleMessageFromServer(Object msg) {
		DataElements de = (DataElements) msg;
		System.out.println("Received message from server: opcode = " + de.getOpcodeFromClient());
		String currControlName = (String) controllers.get("curr");
		Object o = controllers.get(currControlName);

		/**
		 * it checks if returns error from the server if it does, we check if we are in
		 * the login form, or other form that's because we should let the user to input
		 * wrong details in the login if the user inputs wrong details, the system will
		 * show him "wrong details label"
		 * 
		 */
		if (de.getOpCodeFromServer() == ServerToClientOpcodes.Error || de.getOpCodeFromServer() == null) {
			if (currControlName.equals("loginController"))
				((loginController) o).showErrorLabel();
			else
				((AbstractController) o).popError("Error", "Couldn't get info from server");
			return;
		}

		/*
		 * 
		 */
		switch (currControlName) {
		case "loginController":
			ClientMain.setUser((CloneUser) de.getData());
			try {
				App.changeStage("mainController", "High School Test System");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "questionsEditor":
			switch (de.getOpCodeFromServer()) {
			case SendAllCoursesOfTeacher:
				((questionsEditor) o).course_combo
						.setItems(FXCollections.observableArrayList((List<CloneCourse>) de.getData()));
				break;
			case SendAllQuestionInCourse:
				((questionsEditor) o).question_combo
						.setItems(FXCollections.observableArrayList((List<CloneQuestion>) de.getData()));
				break;
			case CreateNewQuestionResult:
				((questionsEditor) o).showMsg("Success","The question has been successfully created!");
				break;
			}
			break;

		case "examCreator":
			switch (de.getOpCodeFromServer()) {
			case SendAllCoursesOfTeacher:
				((examCreator) o).courseCombo
						.setItems(FXCollections.observableArrayList((List<CloneCourse>) de.getData()));
				break;
			case SendAllQuestionInCourse:
				((examCreator) o).SetList(FXCollections.observableArrayList((List<CloneQuestion>) de.getData()));

				break;
			case CreateNewExamResult:
				((examCreator) o).showMsg("Success","The exam has been successfully created!");
				break;

			}
			break;

		case "testGenerator":
			switch (de.getOpCodeFromServer()) {
			case SendAllCoursesOfTeacher:
				((testGenerator) o).courseCombo
						.setItems(FXCollections.observableArrayList((List<CloneCourse>) de.getData()));
				break;
			case SendAllExamsOfTeacherInCourse:
				((testGenerator) o).examCombo
						.setItems(FXCollections.observableArrayList((List<CloneExam>) de.getData()));
				break;
			case CreateNewTestResult:
				((testGenerator) o).showMsg("Success","The test has been successfully created!");
				break;
			}
			break;

		case "studentController":
			switch (de.getOpCodeFromServer()) {
			case SendAllStudentTests:
				((studentController) o).testsTable
						.setItems(FXCollections.observableArrayList((List<CloneStudentTest>) de.getData()));
				break;
			}
			break;

		case "teacherController":
			switch (de.getOpCodeFromServer()) {
			case SendAllTestsOfTeacher:
				((teacherController) o).testsList
						.setItems(FXCollections.observableArrayList((List<CloneTest>) de.getData()));
				break;
			}
			break;

		case "principalDataController":
			switch (de.getOpCodeFromServer()) {
			case SendAllQuestion:
				((principalDataController) o).questionsList.getItems()
						.setAll(FXCollections.observableArrayList((List<CloneQuestion>) de.getData()));
				break;
			case SendAllExams:
				((principalDataController) o).examsList.getItems()
						.setAll(FXCollections.observableArrayList((List<CloneExam>) de.getData()));
				break;
			case SendAllTests:
				((principalDataController) o).testsList.getItems()
						.setAll(FXCollections.observableArrayList((List<CloneTest>) de.getData()));

				break;
			}
			break;
		}
		controllers.remove(currControlName);
		AbstractController.msgRecieved();
	}

}