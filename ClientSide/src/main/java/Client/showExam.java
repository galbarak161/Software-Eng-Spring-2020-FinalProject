package Client;

import java.io.IOException;

import CloneEntities.CloneCourse;
import CloneEntities.CloneExam;
import CloneEntities.CloneQuestion;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class showExam {

    @FXML
    private AnchorPane courseText;

    @FXML
    private TextField hoursText;

    @FXML
    private TextField minutesText;

    @FXML
    private TextField ExamNameText;

    @FXML
    private TextArea teachersText;

    @FXML
    private TextArea studentsComment;

    @FXML
    private ListView<CloneQuestion> questionsList;

    @FXML
    private TextField courseNameText;

    @FXML
    private Button QuestionButton;
    
	void setFields(CloneExam exam) {
		this.ExamNameText.setText(exam.getExamName());
		//this.courseNameText.setText(exam.);
		this.hoursText.setText(String.valueOf(exam.getDuration()));
		//this.questionsList.getItems().setAll(FXCollections.observableArrayList(exam.getQuestions()));
		this.studentsComment.setText(exam.getStudentComments());
		this.teachersText.setText(exam.getTeacherComments());
	}

    @FXML
    void OnClickedQuestion(ActionEvent event) {
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
				stage.setTitle("Question");
				stage.setScene(new Scene(root));
				stage.show();
			});
		}
    }

}
