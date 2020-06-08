package Client;

import java.util.List;

import CloneEntities.CloneQuestion;
import CloneEntities.CloneStudentTest;
import UtilClasses.AnsweredQuestion;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;

public class DisplayStudentTest extends AbstractController {

	@FXML
	private TableView<AnsweredQuestion> QuestionTable;

	@FXML
	private TableColumn<AnsweredQuestion, String> QuestionNumberCol;

	@FXML
	private TableColumn<AnsweredQuestion, String> GradeCol;

	@FXML
	private TableColumn<AnsweredQuestion, String> AnswerCol;

	@FXML
	private Label GradeLabel;

	@FXML
	private Label TestNameLabel;

	@FXML
	private TextArea TeacherCommentField;

	@FXML
	private Button ShowQuestionButton;

	/*public void setFields(CloneStudentTest st) {
		TeacherCommentField.setText(st.getExamCheckNotes());
		GradeLabel.setText(String.valueOf(st.getGrade()));
		TestNameLabel.setText(st.getTest().getExamToExecute().getExamName());

		QuestionTable = new TableView<AnsweredQuestion>();

		QuestionNumberCol = new TableColumn<>("Question Number");
		GradeCol = new TableColumn<>("Grade");
		AnswerCol = new TableColumn<>("Your Answer");
		
		QuestionTable.setItems(FXCollections.observableArrayList(st.getAnsweredQuestions()));
		//GradeCol.setCellValueFactory(new PropertyValueFactory("Grade is missing"));
		
		AnswerCol.setCellValueFactory(new PropertyValueFactory("StudentAnswer"));
		QuestionNumberCol.setCellValueFactory(new PropertyValueFactory("Key"));
	}
*/
}
