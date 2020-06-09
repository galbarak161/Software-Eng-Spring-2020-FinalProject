package Client;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import CloneEntities.CloneExam;
import CloneEntities.CloneQuestion;
import CloneEntities.CloneTest;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

public class showTest {

	@FXML
	private TableView<CloneQuestion> QuestionTable;

	@FXML
	private TableColumn<CloneQuestion, String> QuestionNumberCol;

	@FXML
	private TableColumn<CloneQuestion, String> GradeCol;

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

		QuestionNumberCol.setCellValueFactory(new PropertyValueFactory<CloneQuestion, String>("Subject"));

		GradeCol.setCellValueFactory(new PropertyValueFactory<CloneQuestion, String>("Grade"));

		QuestionTable.getColumns().setAll(QuestionNumberCol, GradeCol);

		radioGroup = new ToggleGroup();
		AutomatRadio.setToggleGroup(radioGroup);
		ManualRadio.setToggleGroup(radioGroup);
	}

	void setFields(CloneTest test) {
		this.DateLabel.setText(test.getTestDate().toString());
		this.StartTimeLabel.setText(test.getTestTime().toString());
		if (test.getStatusEnum() == CloneTest.TestStatus.PendingApproval || test.getStatusEnum() == CloneTest.TestStatus.Done) {
			int hours = test.getTestDuration() / 60;
			int minutes = test.getTestDuration() % 60;
			this.EndTimeLabel.setText(String.valueOf(LocalTime.of(test.getTestTime().getHour() + hours, test.getTestTime().getMinute() + minutes)));
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

		// this.QuestionTable.setItems(FXCollections.observableArrayList(test.getExamToExecute()));

	}

}
