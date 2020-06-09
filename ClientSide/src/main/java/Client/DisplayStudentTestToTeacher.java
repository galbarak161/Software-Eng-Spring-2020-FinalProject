package Client;

import java.io.IOException;

import CloneEntities.CloneStudentTest;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class DisplayStudentTestToTeacher {

    @FXML
    private TableView<CloneStudentTest> QuestionTable;

    @FXML
    private TableColumn<CloneStudentTest, String> QuestionNumberCol;

    @FXML
    private TableColumn<CloneStudentTest, String> GradeCol;

    @FXML
    private TableColumn<CloneStudentTest, String> AnswerCol;

    @FXML
    private Label GradeLabel;

    @FXML
    private TextArea TeacherCommentField;

    @FXML
    private Button ShowQuestionButton;
    
    @FXML
    private Button approveButton;
    

    @FXML
    void OnClickedShowQuestion(ActionEvent event) {
    	/*if (QuestionTable.getSelectionModel().getSelectedItem() != null) {
			Platform.runLater(() -> {
				Parent root = null;
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("showQuestion.fxml"));
					root = (Parent) fxmlLoader.load();
					showQuestion q = fxmlLoader.getController();
					q.setFields(QuestionTable.getSelectionModel().getSelectedItem());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Stage stage = new Stage();
				stage.setTitle("Question " + questionsList.getSelectionModel().getSelectedItem().getSubject());
				stage.setScene(new Scene(root));
				stage.show();
			});
		}*/
    }


}
