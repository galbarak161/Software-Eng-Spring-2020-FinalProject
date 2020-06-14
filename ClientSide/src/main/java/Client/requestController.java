package Client;

import CloneEntities.CloneTest;
import CloneEntities.CloneTimeExtensionRequest;
import CloneEntities.CloneTimeExtensionRequest.RequestStatus;
import UtilClasses.DataElements.ClientToServerOpcodes;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class requestController extends AbstractController {

	@FXML
	private TextField timeText;

	@FXML
	private Label usernameLabel1;

	@FXML
	private Button submitrButton;

	@FXML
	private Label errorLabel;

	@FXML
	private TextArea explaText;

	@FXML
	private Button denyButton;

	@FXML
	private Button approveButton;

	private CloneTest thisTest;

	private CloneTimeExtensionRequest thisRequest;

	public void initialize() {
		timeText.setOnKeyPressed(event -> {
			if (event.getCode().equals(KeyCode.TAB)) {
				explaText.requestFocus();
			}
			if (event.getCode().equals(KeyCode.ENTER)) {
				try {
					onClickedSubmit(new ActionEvent());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		explaText.setOnKeyPressed(event -> {
			if (event.getCode().equals(KeyCode.TAB)) {
				timeText.requestFocus();
			}
			if (event.getCode().equals(KeyCode.ENTER)) {
				try {
					onClickedSubmit(new ActionEvent());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	void setTest(CloneTest test) {
		thisTest = test;
		denyButton.setVisible(false);
		approveButton.setVisible(false);
	}

	void setRequest(CloneTimeExtensionRequest request) {
		thisRequest = request;
		if (request.getStatus() == RequestStatus.Confirmed) {
			timeText.setText(String.valueOf(request.getTimeToExtenedInMinute()));
			timeText.setDisable(true);
			timeText.resize(timeText.getWidth(), timeText.getHeight() + 66);
			explaText.setText(request.getBody());
			explaText.setDisable(true);
			submitrButton.setVisible(false);
			approveButton.setVisible(false);
			denyButton.setVisible(false);
			return;
		}
		timeText.setText(String.valueOf(request.getTimeToExtenedInMinute()));
		timeText.setDisable(true);
		explaText.setText(request.getBody());
		explaText.setDisable(true);
		submitrButton.setVisible(false);
	}

	@FXML
	void onClickedApprove(ActionEvent event) {
		thisRequest.setTimeToExtenedInMinute(Integer.valueOf(timeText.getText()));
		thisRequest.setRequestConfirmed(true);
		try {
			GetDataFromDB(ClientToServerOpcodes.UpdateTimeExtension, thisRequest);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Stage stage;
		stage = (Stage) timeText.getScene().getWindow();
		stage.close();
	}

	@FXML
	void onClickedDeny(ActionEvent event) {
		thisRequest.setRequestConfirmed(false);
		try {
			GetDataFromDB(ClientToServerOpcodes.UpdateTimeExtension, thisRequest);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Stage stage;
		stage = (Stage) timeText.getScene().getWindow();
		stage.close();
	}

	@FXML
	void onClickedSubmit(ActionEvent event) {
		try {
			StringBuilder errorsList = new StringBuilder();

			if (timeText.getText().isEmpty() || !timeText.getText().matches("[0-9]+"))
				errorsList.append("Duration is empty\n");

			if (explaText.getText().isEmpty())
				errorsList.append("Please Enter an Explanation\n");

			if (errorsList.length() != 0) {
				throw new Exception(errorsList.toString());
			}
		} catch (Exception e) {
			popError("Please fill all question fields", e.getMessage());
			return;
		}
		CloneTimeExtensionRequest req = new CloneTimeExtensionRequest(explaText.getText(),
				Integer.valueOf(timeText.getText()), thisTest);
		try {
			GetDataFromDB(ClientToServerOpcodes.CreateNewTimeExtensionRequest, req);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Stage stage;
		stage = (Stage) timeText.getScene().getWindow();
		stage.close();
	}

	@Override
	void showMsg(String title, String content) {
		Platform.runLater(() -> {
			Alert info = new Alert(Alert.AlertType.INFORMATION);
			info.setTitle(title);
			info.setHeaderText(content);
			info.showAndWait();
			Stage stage;
			stage = (Stage) timeText.getScene().getWindow();
			stage.close();
		});
	}

}
