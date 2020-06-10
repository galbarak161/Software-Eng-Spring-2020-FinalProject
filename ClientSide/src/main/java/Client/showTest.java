package Client;

import java.io.IOException;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import CloneEntities.CloneExam;
import CloneEntities.CloneQuestion;
import CloneEntities.CloneQuestionInExam;
import CloneEntities.CloneTest;
import UtilClasses.DataElements.ClientToServerOpcodes;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class showTest extends AbstractController {

	@FXML
	TableView<CloneQuestionInExam> QuestionTable;

	@FXML
	private TableColumn<CloneQuestionInExam, String> QuestionNumberCol;

	@FXML
	private TableColumn<CloneQuestionInExam, String> GradeCol;

	@FXML
	private Label TestNameLabel;

	@FXML
	private TextArea TeacherCommentField;

	@FXML
	private Button ShowQuestionButton;

	@FXML
	private Label CourseNameLabel;

	@FXML
	private Label DateLabel;

	@FXML
	private RadioButton AutomatRadio;

	@FXML
	private RadioButton ManualRadio;

	@FXML
	private Label StatusLabel;

	@FXML
	private Label StartTimeLabel;

	@FXML
	private Label EndTimeLabel;

	private ToggleGroup radioGroup;

	public void initialize() {

		QuestionNumberCol.setCellValueFactory(new PropertyValueFactory<CloneQuestionInExam, String>("Name"));

		GradeCol.setCellValueFactory(new PropertyValueFactory<CloneQuestionInExam, String>("Grade"));

		QuestionTable.getColumns().setAll(QuestionNumberCol, GradeCol);

		radioGroup = new ToggleGroup();
		AutomatRadio.setToggleGroup(radioGroup);
		ManualRadio.setToggleGroup(radioGroup);
	}

	void setFields(CloneTest test) {
		this.DateLabel.setText(test.getTestDate().toString());
		this.StartTimeLabel.setText(test.getTestTime().toString());
		if (test.getStatusEnum() == CloneTest.TestStatus.PendingApproval
				|| test.getStatusEnum() == CloneTest.TestStatus.Done) {
			int hours = test.getTestDuration() / 60;
			int minutes = test.getTestDuration() % 60;
			this.EndTimeLabel.setText(String.valueOf(
					LocalTime.of(test.getTestTime().getHour() + hours, test.getTestTime().getMinute() + minutes)));
		} else
			this.EndTimeLabel.setText("N/A");
		this.StatusLabel.setText(test.getStatus().toString());
		this.CourseNameLabel.setText(test.getExamToExecute().getCourseName());
		this.TeacherCommentField.setText(test.getExamToExecute().getTeacherComments());
		if (test.getType() == CloneTest.ExamType.Automated)
			AutomatRadio.setSelected(true);
		else
			ManualRadio.setSelected(true);
		this.TestNameLabel.setText(test.getExamToExecute().getExamName());

		try {
			GetDataFromDB(ClientToServerOpcodes.GetAllQuestionInExamRelatedToExam, test.getExamToExecute());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	void OnClickedQuestion(ActionEvent event) {
		if (QuestionTable.getSelectionModel().getSelectedItem() != null) {
			Platform.runLater(() -> {
				Parent root = null;
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("showQuestion.fxml"));
					root = (Parent) fxmlLoader.load();
					showQuestion q = fxmlLoader.getController();
					q.setFields(QuestionTable.getSelectionModel().getSelectedItem().getQuestion());
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
