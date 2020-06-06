package Client;

import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class teacherNavi extends AbstractNavi {

	@FXML
	private Label testsList;

	@FXML
	private Label questionEdtior;

	@FXML
	private Label examsEditor;

	@FXML
	private Label testGenerator;
	
    @FXML
    private Label personalLabel;

	@FXML
	private Label logout;

	@FXML
	void changeToTests(MouseEvent event) {
		switchMainPanel("teacherController.fxml");
	}

	@FXML
	void changeToQEdit(MouseEvent event) {
		switchMainPanel("questionsEditor.fxml");
	}

	@FXML
	void changeToExamCreator(MouseEvent event) {
		switchMainPanel("examCreator.fxml");

	}

	@FXML
	void changeToTestsGen(MouseEvent event) {
		switchMainPanel("testGenerator.fxml");
	}
	

    @FXML
    void changeToInfo(MouseEvent event) {
    	switchMainPanel("personalInfo.fxml");
    }

}
