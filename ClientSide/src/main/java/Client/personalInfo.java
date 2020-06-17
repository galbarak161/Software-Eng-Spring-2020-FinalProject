package Client;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class personalInfo extends AbstractController {

	@FXML
	private TextField firstField;

	@FXML
	private TextField lastField;

	@FXML
	private TextField emailField;
	
    @FXML
    private TextField IDField;

	public void initialize() {
		firstField.setText(ClientMain.getUser().getFirstName());
		lastField.setText(ClientMain.getUser().getLastName());
		emailField.setText(ClientMain.getUser().getEmailAddress());
		IDField.setText(String.valueOf(ClientMain.getUser().getIdentifyNumber()));
	}
}