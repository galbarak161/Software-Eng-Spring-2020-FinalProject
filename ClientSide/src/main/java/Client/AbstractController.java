package Client;

import java.io.IOException;
import CommonElements.DataElements;
import CommonElements.DataElements.ClientToServerOpcodes;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import com.jfoenix.controls.JFXTreeTableView;

public abstract class AbstractController {

	protected Alert alert = new Alert(Alert.AlertType.ERROR);

	protected Alert info = new Alert(Alert.AlertType.INFORMATION);
	
	protected static final String ERROR_TITLE_SERVER = "An error occurred while retrieving data from server";
	
	protected static final String ERROR_TITLE_Client = "An error occurred while the system was hanaling your actions";
	
	private static boolean msgRecived = false;
	
	static void msgRecieved() {
		msgRecived = true;
	}

	/**
	 * getDataFromServer(DataElements) The function calls the
	 * ClientMain.sendMessageToServer(Object) function
	 * 
	 * @param DataElements with opcode and data
	 * @return -1 for fail
	 */
	protected int sendRequestForDataFromServer(DataElements de) {
		int status;
		try {
			status = ClientMain.sendMessageToServer(de);
		} catch (IOException e) {
			status = -1;
			String errorMessage = "The system could not receive data from server. please reconnect and try again";
			popError(ERROR_TITLE_SERVER, errorMessage);
			e.printStackTrace();
		}

		return status;
	}
	
	
	/**
	 * Creating request to get data from server
	 * 
	 * @param op   - what type of request do we want (Enum)
	 * @param data - the date we want to send to server
	 * @return
	 * @throws InterruptedException Pause the main GUI thread
	 */
	public int GetDataFromDB(ClientToServerOpcodes op, Object data) throws InterruptedException {
		ClientMain.addController(this);
		int status = sendRequestForDataFromServer(new DataElements(op, data));
		while(!msgRecived) {
			Thread.onSpinWait();
		}
		msgRecived = false;
		return status;
	}
	
	
	/**
	 * Activate as a respond for an unknown exception in client
	 * 
	 * @param object Contains the error description
	 */
	public void popError(String title, String errorMessage) {
		alert.setHeaderText(title);
		alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea(errorMessage)));
		alert.showAndWait();
	}

}
