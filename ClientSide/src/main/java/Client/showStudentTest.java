package Client;


import CloneEntities.CloneAnswerToQuestion;
import CloneEntities.CloneStudentTest;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

public class showStudentTest extends AbstractController {

	@FXML
	private TableView<CloneAnswerToQuestion> questionsTable;

	@FXML
	private TableColumn<CloneAnswerToQuestion, String> questionName;

	@FXML
	private TableColumn<CloneAnswerToQuestion, String> gradeCol;

	@FXML
	private TableColumn<CloneAnswerToQuestion, String> answerCol;


	@FXML
	private Label GradeLabel;

	@FXML
	private Label TestNameLabel;

	@FXML
	private TextArea TeacherCommentField;

	@FXML
	private Button ShowQuestionButton;
	
	public void initialize() {
		questionName.setCellValueFactory(new PropertyValueFactory<CloneAnswerToQuestion, String>("Name"));
		
		gradeCol.setCellValueFactory(new PropertyValueFactory<CloneAnswerToQuestion, String>("CorrectAnswer"));
		
		answerCol.setCellValueFactory(new PropertyValueFactory<CloneAnswerToQuestion, String>("StudnetAnswer"));
		
		questionsTable.getColumns().setAll(questionName, answerCol, gradeCol);
	}

	public void setFields(CloneStudentTest st) {
		TeacherCommentField.setText(st.getExamCheckNotes());
		GradeLabel.setText(String.valueOf(st.getGrade()));
		TestNameLabel.setText(st.getTest().getExamToExecute().getExamName());
		
		
	}
}
