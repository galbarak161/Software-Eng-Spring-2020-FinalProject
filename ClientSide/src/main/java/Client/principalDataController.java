package Client;

import java.io.IOException;
import java.util.Observable;

import CloneEntities.*;
import CommonElements.DataElements.ClientToServerOpcodes;
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

	
	/*public <T> void DataDisplay(String fxml, String title) {
			Platform.runLater(() -> {
				Parent root = null;
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml+".fxml"));
					root = (Parent) fxmlLoader.load();
					T q = fxmlLoader.getController();
					((T) q).setFields(questionsList.getSelectionModel().getSelectedItem());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Stage stage = new Stage();
				stage.setTitle(title);
				stage.setScene(new Scene(root));
				stage.show();
			});
	}*/
	
	
	@FXML
	void onClickedQuestion(ActionEvent event) {
		if (questionsList.getSelectionModel().getSelectedItem() != null) {
			Platform.runLater(() -> {
				Parent root = null;
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("showQuestion.fxml"));
					root = (Parent) fxmlLoader.load();
					showQuestion q = fxmlLoader.getController();
					q.setFields(questionsList.getSelectionModel().getSelectedItem());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Stage stage = new Stage();
				stage.setTitle("Question " + questionsList.getSelectionModel().getSelectedItem().getSubject());
				stage.setScene(new Scene(root));
				stage.show();
			});
		}

	}

	@FXML
	void OnClickedExam(ActionEvent event) {
		if (examsList.getSelectionModel().getSelectedItem() != null) {
			Platform.runLater(() -> {
				Parent root = null;
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("showExam.fxml"));
					root = (Parent) fxmlLoader.load();
					showExam e = fxmlLoader.getController();
					e.setFields(examsList.getSelectionModel().getSelectedItem());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Stage stage = new Stage();
				stage.setTitle("Exam " + examsList.getSelectionModel().getSelectedItem().getExamName());
				stage.setScene(new Scene(root));
				stage.show();
			});
		}
	}

}
