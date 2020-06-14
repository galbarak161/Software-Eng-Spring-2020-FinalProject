package Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

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
    private TextField IDField;

	@FXML
	private CheckBox editCheck;

	@FXML
	void OnClickedEdit(ActionEvent event) {
		if (editCheck.isSelected()) {
			firstField.setDisable(false);
			lastField.setDisable(false);
			emailField.setDisable(false);
			IDField.setDisable(false);
			setButton.setDisable(false);
		}
		else {
			firstField.setDisable(true);
			lastField.setDisable(true);
			emailField.setDisable(true);
			IDField.setDisable(true);
			setButton.setDisable(true);
		}
	}

	public void initialize() {
		firstField.setText(ClientMain.getUser().getFirstName());
		firstField.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.TAB)){
            	lastField.requestFocus();
            }
            if(event.getCode().equals(KeyCode.ENTER)){
            	OnClickedSetDetails(new ActionEvent());
            }
        });
		
		lastField.setText(ClientMain.getUser().getLastName());
		lastField.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.TAB)){
            	emailField.requestFocus();
            }
            if(event.getCode().equals(KeyCode.ENTER)){
            	OnClickedSetDetails(new ActionEvent());
            }
        });
		
		emailField.setText(ClientMain.getUser().getEmailAddress());
		emailField.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.TAB)){
            	IDField.requestFocus();
            }
            if(event.getCode().equals(KeyCode.ENTER)){
            	OnClickedSetDetails(new ActionEvent());
            }
        });
		
		IDField.setText(String.valueOf(ClientMain.getUser().getIdentifyNumber()));
		IDField.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)){
            	OnClickedSetDetails(new ActionEvent());
            }
        });

	}

	@FXML
	void OnClickedSetDetails(ActionEvent event) {

	}

}