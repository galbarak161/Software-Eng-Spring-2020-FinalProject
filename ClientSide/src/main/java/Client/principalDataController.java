package Client;

import java.io.IOException;
import java.util.Observable;

import CloneEntities.*;
import UtilClasses.DataElements.ClientToServerOpcodes;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class principalDataController extends AbstractController {

	@FXML
	ListView<CloneTest> testsList;

	@FXML
	ListView<CloneExam> examsList;

	@FXML
	ListView<CloneQuestion> questionsList;

	@FXML
	private Button questionsButton;

	@FXML
	private Button examsButton;

	@FXML
	private Button testsButton;

	@Override
	public void initialize() {
		try {
			GetDataFromDB(ClientToServerOpcodes.GetAllQuestion, null);
			Thread.sleep(50);
			GetDataFromDB(ClientToServerOpcodes.GetAllExams, null);
			Thread.sleep(50);
			GetDataFromDB(ClientToServerOpcodes.GetAllTests, null);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void onClickedQuestion(ActionEvent event) throws Exception {
		if (questionsList.getSelectionModel().getSelectedItem() != null) {
			newWindow(questionsList, new showQuestion(), "showQuestion.fxml",
					"Question " + questionsList.getSelectionModel().getSelectedItem().getSubject());
		}

	}

	@FXML
	void OnClickedExam(ActionEvent event) throws Exception {
		if (examsList.getSelectionModel().getSelectedItem() != null) {
			newWindow(examsList, new showExam(), "showExam.fxml",
					"Exam " + examsList.getSelectionModel().getSelectedItem().getExamName());
		}
	}

	@FXML
	void OnClickedTest(ActionEvent event) throws Exception {
		if (testsList.getSelectionModel().getSelectedItem() != null) {
			newWindow(testsList, new showTest(), "showTest.fxml",
					"Test " + testsList.getSelectionModel().getSelectedItem().getName());

		}

	}

}
