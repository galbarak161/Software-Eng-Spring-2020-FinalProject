package Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class personalInfo extends AbstractController {

	@FXML
	private Button setButton;

	@FXML
	private TextField firstField;

	@FXML
	private TextField lastField;

	@FXML
	private TextField emailField;

	@FXML
	private CheckBox editCheck;

	@FXML
	void OnClickedEdit(ActionEvent event) {
		if (editCheck.isSelected()) {
			firstField.setDisable(false);
			lastField.setDisable(false);
			emailField.setDisable(false);
			setButton.setDisable(false);
		}
		else {
			firstField.setDisable(true);
			lastField.setDisable(true);
			emailField.setDisable(true);
			setButton.setDisable(true);
		}
	}

	public void initialize() {
		firstField.setText(ClientMain.getUser().getFirstName());
		lastField.setText(ClientMain.getUser().getLastName());
		emailField.setText(ClientMain.getUser().getEmailAddress());
	}

	@FXML
	void OnClickedSetDetails(ActionEvent event) {

	}

}