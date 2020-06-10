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
    

    @FXML
    private Button denyButton;

    @FXML
    private Button approveButton;
    
    private CloneTest thisTest;
    
    private CloneTimeExtensionRequest thisRequest;

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
    
    void setTest(CloneTest test) {
    	thisTest = test;
    	denyButton.setVisible(false);
    	approveButton.setVisible(false);
    }
    
    void setRequest(CloneTimeExtensionRequest request) {
    	thisRequest = request;
    	timeText.setText(String.valueOf(request.getTimeToExtenedInMinute()));
    	timeText.setDisable(true);
    	explaText.setText(request.getBody());
    	explaText.setDisable(true);
    	submitrButton.setVisible(false);
    	// ADD control of VISIBILITY OF BUTTONS APPROVE AND DENY IN CONDITION OF ENUM STATUS
    }
    
    @FXML
    void onClickedApprove(ActionEvent event) {
    	thisRequest.setTimeToExtenedInMinute(Integer.valueOf(timeText.getText()));
    	thisRequest.setRequestConfirmed(true);
		try {
			GetDataFromDB(ClientToServerOpcodes.GetAnswerToTimeExtensionRequest, thisRequest);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void onClickedDeny(ActionEvent event) {
    	thisRequest.setRequestConfirmed(false);
		try {
			GetDataFromDB(ClientToServerOpcodes.GetAnswerToTimeExtensionRequest, thisRequest);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }

}
