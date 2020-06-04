package Client;

import java.io.IOException;

import CloneEntities.CloneUser;
import CommonElements.DataElements.ServerToClientOpcodes;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class teacherNavi {

	@FXML
	private Label testsList;

	@FXML
	private Label questionEdtior;

	@FXML
	private Label examsEditor;

	@FXML
	private Label testGenerator;

	@FXML
	private Label logout;
	
	private mainController controller;
	
	public void initialize() {
		
	}
	
	void switchMainPanel(String Sfxml) {
		Object o = ClientService.getController(mainController.class);
		if(!o.getClass().equals(String.class)) {
	    	Platform.runLater(() -> {
	    		((mainController)o).setMainPanel(Sfxml);
	    	}); 
		}
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
    void changeToTests(MouseEvent event) {
		switchMainPanel("teacherController.fxml");
    }

}