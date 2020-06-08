package Client;



import UtilClasses.DataElements;
import UtilClasses.Login;
import UtilClasses.DataElements.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class loginController extends AbstractController {

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
    
    /**
     * The function is called when the user is pressing "sign in"
     * it gets the current controller name 
     * in this case - login controller
     * then it adds the controller to the conroller's map data structure
     * @param event
     */
    @FXML
    void signinAction(ActionEvent event) {
    	try {
    		String controllerName = this.getClass().toString().split("Client.")[1];
    		ClientMain.addController(controllerName,this);
    		ClientMain.setCurrController(controllerName);
			Login loginToServer = new Login(usernameText.getText(), passwordText.getText());
			DataElements de =  new DataElements(ClientToServerOpcodes.UserLogIn, loginToServer);
			sendRequestForDataFromServer(de);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
 

}
