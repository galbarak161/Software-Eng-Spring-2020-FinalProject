package Client;

import CloneEntities.CloneTest;
import CloneEntities.CloneTimeExtensionRequest;
import UtilClasses.DataElements.ClientToServerOpcodes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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
    
    private CloneTest thisTest;

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
    	CloneTimeExtensionRequest req = new CloneTimeExtensionRequest(explaText.getText(), Integer.valueOf(timeText.getText()),thisTest);
		try {
			GetDataFromDB(ClientToServerOpcodes.CreateNewTimeExtensionRequest, req);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} // REMEBER TELL GAL AND ABED ABOUT CHANGING TEST STATUS TO ONGOING REQUESTED
    }
    
    void setFields(CloneTest t) {
    	thisTest = t;
    }

}
