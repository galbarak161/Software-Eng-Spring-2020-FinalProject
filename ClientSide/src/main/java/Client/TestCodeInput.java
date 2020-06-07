package Client;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class TestCodeInput extends AbstractController {

    @FXML
    private TextField codeText;

    @FXML
    private Button startButton;
    
   
    
    @FXML
    void OnClickedStart(ActionEvent event) {
    	try {
			App.changeStage("TestController", "Test");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
