package Client;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import CommonElements.DataElements;
import CommonElements.DataElements.*;
import CloneEntities.*;
import OCSF.AbstractClient;
import javafx.collections.FXCollections;

public class ClientService extends AbstractClient {
	private static final Logger LOGGER = Logger.getLogger(ClientService.class.getName());
	private ClientMain clientM;
	public static List<Object> controllersList;

	/*
	 * Takes host and port, init. in AbstractClient, then init. local clientM init.
	 * clientS in CLientMain controllersList contains all controllers we have in our
	 * project Using it for handling changes from client requests
	 */
	public ClientService(String host, int port) {
		super(host, port);
		this.clientM = new ClientMain(this);
		controllersList = new ArrayList<>();
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

	static Object getController(Class c) {
		for (Object o : controllersList)
			if (o.getClass() == c)
				return o;
		return new String("There is no such a Controller");
	}

	/**
	 * The function gets new message from server Parsing the opcode and data Handle
	 * the server results
	 */
	@Override
	protected void handleMessageFromServer(Object msg) {
		try {
			
			DataElements de = (DataElements) msg;
			System.out.println("Received message from server: opcode = " + de.getOpcodeFromClient());
			Object o;
			
			if (!(o = getController(loginController.class)).getClass().equals(String.class)) {
				if (de.getOpCodeFromServer() == ServerToClientOpcodes.Error) {
					((loginController) o).showErrorLabel();
				} else {
					ClientMain.setUser((CloneUser) de.getData());
					App.changeStage("mainController", "High School Test System");
					controllersList.remove(o);
				}
				return;
			}
			
			if (!(o = getController(questionsEditor.class)).getClass().equals(String.class)) {
				if (de.getOpCodeFromServer() == ServerToClientOpcodes.Error) {
					((questionsEditor) o).popError("Error", "Couldn't get info from server");
				} else {
//					SendAllStudies(10), SendAllCoursesInStudy(11), SendAllQuestionInCourse(12), UpdateQuestionResult(13),
//					SendAllQuestion(14), UserLoggedIn(15), Error(-1);
					switch (de.getOpCodeFromServer()) {
					case SendAllCoursesFromTeacher:
						((questionsEditor) o).course_combo.setItems(FXCollections.observableArrayList((List<CloneCourse>) de.getData()));
						questionsEditor.msgRecieved();
					}
				}
				controllersList.remove(o);
				return;
			}
			if (!(o = getController(examCreator.class)).getClass().equals(String.class)) {
				if (de.getOpCodeFromServer() == ServerToClientOpcodes.Error) {
					((examCreator) o).popError("Error", "Couldn't get info from server");
				} else {
//					SendAllStudies(10), SendAllCoursesInStudy(11), SendAllQuestionInCourse(12), UpdateQuestionResult(13),
//					SendAllQuestion(14), UserLoggedIn(15), Error(-1);
					switch (de.getOpCodeFromServer()) {
					case SendAllCoursesFromTeacher:
						((examCreator) o).courseCombo.setItems(FXCollections.observableArrayList((List<CloneCourse>) de.getData()));
						System.out.println("after data");
						examCreator.msgRecieved();
					}
				}
				controllersList.remove(o);
				return;
			}
			
			if (!(o = getController(teacherController.class)).getClass().equals(String.class)) {
				if (de.getOpCodeFromServer() == ServerToClientOpcodes.Error) {
					((teacherController) o).popError("Error", "Couldn't get info from server");
				} else {
					switch (de.getOpCodeFromServer()) {
					case SendAllCoursesFromTeacher:
						((teacherController) o).courseCombo.setItems(FXCollections.observableArrayList((List<CloneCourse>) de.getData()));
						System.out.println("after data");
						teacherController.msgRecieved();
					}
				}
				controllersList.remove(o);
				return;
			}
			
			if (!(o = getController(principalDataController.class)).getClass().equals(String.class)) {
				if (de.getOpCodeFromServer() == ServerToClientOpcodes.Error) {
					((principalDataController) o).popError("Error", "Couldn't get info from server");
				} else {
					switch (de.getOpCodeFromServer()) {
					case SendAllQuestion:
						((principalDataController) o).questionsList.setItems(FXCollections.observableArrayList((List<CloneQuestion>) de.getData()));
						System.out.println("after data");
						principalDataController.msgRecieved();
					}
				}
				controllersList.remove(o);
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
