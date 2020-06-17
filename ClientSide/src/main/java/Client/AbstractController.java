package Client;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import UtilClasses.DataElements;
import UtilClasses.DataElements.ClientToServerOpcodes;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public abstract class AbstractController extends AbstractShow {

	protected final String ERROR_TITLE_SERVER = "An error occurred while retrieving data from server";

	protected final String ERROR_TITLE_Client = "An error occurred while the system was hanaling your actions";

	private static boolean msgRecived = false;

	static Timer timer;

	static void msgRecieved() {
		msgRecived = true;
	}
	
	/**
	 * Generic function, creates a new window and initialize it by "setFields"
	 * @param <T> Type of ListView we're currently select an item from
	 * @param <E> Type of controller we need to show on the stage
	 * @param list Get an item of a generic list- used to provide argument to "setFields"
	 * @param con Instance of the controller to show in the new created window
	 * @param name Controller name
	 * @param title Title of the window
	 * @throws Exception
	 */
	public <T, E> void newWindow(ListView<T> list, E con, String name, String title) throws Exception {
		Platform.runLater(() -> {
			Parent root = null;
			try {
				E curr;
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(name));
				root = (Parent) fxmlLoader.load();
				curr = fxmlLoader.getController();
				((AbstractShow) curr).setFields(list.getSelectionModel().getSelectedItem());
			} catch (IOException e) {
				e.printStackTrace();
			}
			Stage stage = new Stage();
			stage.setTitle(title);
			stage.setScene(new Scene(root));
			stage.show();
		});
	}
	
	/**
	 * Generic function, creates a new window and initialize it by "setFields"
	 * @param <T> Type of TableView we're currently select an item from
	 * @param <E> Type of controller we need to show on the stage
	 * @param table Get an item of a generic table- used to provide argument to "setFields"
	 * @param con Instance of the controller to show in the new created window
	 * @param name Controller name
	 * @param title Title of the window
	 * @throws Exception
	 */
	public <T, E> void newWindow(TableView<T> table, E con, String name, String title) throws Exception {
		Platform.runLater(() -> {
			Parent root = null;
			try {
				E curr;
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(name));
				root = (Parent) fxmlLoader.load();
				curr = fxmlLoader.getController();
				((AbstractShow) curr).setFields(table.getSelectionModel().getSelectedItem());
			} catch (IOException e) {
				e.printStackTrace();
			}
			Stage stage = new Stage();
			stage.setTitle(title);
			stage.setScene(new Scene(root));
			stage.show();
		});
	}
	
	/**
	 * Generic function, creates a new window and initialize it by "setFields"
	 * @param <T> Type of item we need to initialize the controller with
	 * @param <E> Type of controller we need to show on the stage
	 * @param item Used as an argument to "setFields"
	 * @param con Instance of the controller to show in the new created window
	 * @param name Controller name
	 * @param title Title of the window
	 * @throws Exception
	 */
	public <T, E> void newWindow(T item, E con, String name, String title) throws Exception {
		Platform.runLater(() -> {
			Parent root = null;
			try {
				E curr;
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(name));
				root = (Parent) fxmlLoader.load();
				curr = fxmlLoader.getController();
				((AbstractShow) curr).setFields(item);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Stage stage = new Stage();
			stage.setTitle(title);
			stage.setScene(new Scene(root));
			stage.show();
		});
	}
	
	/**
	 * Initiate a TimerTask for polling requests from server every 10 seconds
	 * @param op Opcode for server- "title" of the request
	 * @param data Contains data we should provide the server for answering the request
	 */
	protected void sendRequest(ClientToServerOpcodes op, Object data) {
		TimerTask timerTask = new TimerTask() {

			@Override
			public void run() {
				try {
					GetDataFromDB(op, data);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		timer = new Timer("RefreshingTimer");

		timer.scheduleAtFixedRate(timerTask, 30, 10000);
	}

	/**
	 * getDataFromServer(DataElements) The function calls the
	 * ClientMain.sendMessageToServer(Object) function
	 * 
	 * @param DataElements with opcode and data
	 * @return -1 for fail
	 */
	protected synchronized int sendRequestForDataFromServer(DataElements de) {
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
	public synchronized void GetDataFromDB(ClientToServerOpcodes op, Object data) throws InterruptedException {
		String initErrors = "";
		ClientMain.addController(this.getClass().toString().split("Client.")[1], this);
		ClientMain.setCurrController(this.getClass().toString().split("Client.")[1]);
		int dbStatus = sendRequestForDataFromServer(new DataElements(op, data));
		if ((dbStatus == -1)) {
			initErrors += "The system cannot retrieve studies from server\n";
		}

		if (!initErrors.isEmpty())
			popError(ERROR_TITLE_SERVER, initErrors);
		while (!msgRecived) {
			Thread.onSpinWait();
		}
		msgRecived = false;
	}
	
	/**
	 * Switch the main pane (AnchorPane) in the main controller of the tool
	 * @param Sfxml FXML name we switch to
	 */
	void switchMainPanel(String Sfxml) {
		Platform.runLater(() -> {
			((mainController) ClientService.getController("mainController")).setMainPanel(Sfxml);
		});
	}

	public void initialize() {

	}	

	/**
	 * Invokes an info alert message Mostly for a success create of objects on
	 * server
	 * 
	 * @param title-   Window title
	 * @param content- detailed info about the message
	 */
	void showMsg(String title, String content) {
		Platform.runLater(() -> {
			Alert info = new Alert(Alert.AlertType.INFORMATION);
			info.setTitle(title);
			info.setHeaderText(content);
			info.showAndWait();
		});
	}

	/**
	 * Error message
	 * 
	 * @param title        - main content of the error
	 * @param errorMessage - the detailed content of error
	 */
	public void popError(String title, String errorMessage) {
		Platform.runLater(() -> {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(title);
			alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea(errorMessage)));
			alert.showAndWait();
		});
	}
	
	/**
	 * Stops the timer- cancel the timed requests from server
	 */
	public static void stopTimer() {
		timer.cancel();
	}
}
