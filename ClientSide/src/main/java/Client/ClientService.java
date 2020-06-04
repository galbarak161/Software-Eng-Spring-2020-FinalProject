package Client;

import java.io.IOException;
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
					case SendAllStudies:
						((questionsEditor) o).study_combo.setItems(FXCollections.observableArrayList((List<CloneStudy>) de.getData()));
					}
				}
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
