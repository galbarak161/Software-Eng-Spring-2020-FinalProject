package Client;

import java.io.IOException;

import CommonElements.DataElements;
import CommonElements.DataElements.*;
import CommonElements.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class loginController {

    @FXML
    private TextField usernameText;

    @FXML
    private Label usernameLabel1;

    @FXML
    private Button signinButton;

    @FXML
    private Button clearButton;

    @FXML
    private Label errorLabel;

    @FXML
    private PasswordField passwordText;
    
    @FXML
    void clearAction(ActionEvent event) {
    	usernameText.clear();
    	passwordText.clear();
    	errorLabel.setVisible(false);
    }
    
    public void showErrorLabel() {
    	errorLabel.setVisible(true);
    }
    
    @FXML
    void signinAction(ActionEvent event) {
    	try {
    		String controllerName = this.getClass().toString().split("Client.")[1];
    		ClientMain.addController(controllerName,this);
    		ClientMain.setCurrController(controllerName);
			Login loginToServer = new Login(usernameText.getText(), passwordText.getText());
			DataElements de =  new DataElements(ClientToServerOpcodes.UserLogin, loginToServer);
			sendRequestForDataFromServer(de);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
	private int sendRequestForDataFromServer(DataElements de) {
		int status = 0;
		try {
			status = ClientMain.sendMessageToServer(de);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return status;
	}

}
