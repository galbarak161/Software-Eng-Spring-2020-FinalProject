package Client;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Timer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class TestCodeInput {

    @FXML
    private TextField codeText;

    @FXML
    private Button startButton;

    @FXML
    private TextField IDText;

    @FXML
    private Label IDLabel;

    @FXML
    private Button downloadButton;

    @FXML
    private TextField fileField;

    @FXML
    private Button uploadButton;

    @FXML
    private Button submitButton;
   
    
    @FXML
    void OnClickedStart(ActionEvent event) {
    	try {
			App.changeStage("TestController", "Test");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
	public void uploadFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select Test File");
		//fileChooser.setInitialDirectory(new File("X:\\testdir\\two"));
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Word Files", "*.docx"));
		 Stage stage = new Stage();
		List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stage);
		 
		if (selectedFiles != null)
			fileField.setText("Test Files selected [" + selectedFiles.size() + "]: " + selectedFiles.get(0).getName() + "..");
		
	}

}
