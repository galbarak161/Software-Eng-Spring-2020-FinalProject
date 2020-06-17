package Client;

import CloneEntities.CloneExam;
import CloneEntities.CloneQuestionInExam;
import UtilClasses.DataElements.ClientToServerOpcodes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class showExam extends AbstractController {

	@FXML
	private AnchorPane courseText;

	@FXML
	private TextField ExamNameText;

	@FXML
	private TextField courseNameText;

	@FXML
	private TextField hoursText;

	@FXML
	private TextField minutesText;

	@FXML
	private TextArea teachersText;

	@FXML
	private TextArea studentsComment;

	@FXML
	private Button QuestionButton;

	@FXML TableView<CloneQuestionInExam> questionsTable;

	@FXML
	private TableColumn<CloneQuestionInExam, String> SubjectCol;

	@FXML
	private TableColumn<CloneQuestionInExam, String> SubjectGrade;

	public void initialize() {
		SubjectCol.setCellValueFactory(new PropertyValueFactory<CloneQuestionInExam, String>("Name"));

		SubjectGrade.setCellValueFactory(new PropertyValueFactory<CloneQuestionInExam, String>("Grade"));

		questionsTable.getColumns().setAll(SubjectCol, SubjectGrade);
	}
	
	protected <T> void setFields(T selectedItem) {
		setFields((CloneExam)selectedItem);
	}

	void setFields(CloneExam exam) {
		this.ExamNameText.setText(exam.getExamName());
		this.courseNameText.setText(exam.getCourseName());
		this.hoursText.setText(String.valueOf(exam.getDuration()));
		
		try {
			GetDataFromDB(ClientToServerOpcodes.GetAllQuestionInExamRelatedToExam, exam);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.studentsComment.setText(exam.getStudentComments());
		this.teachersText.setText(exam.getTeacherComments());
	}

	@FXML
	void OnClickedQuestion(ActionEvent event) throws Exception {
		if (questionsTable.getSelectionModel().getSelectedItem() != null) {
			newWindow(questionsTable, new showQuestion(), "showQuestion.fxml",
					"Question " + questionsTable.getSelectionModel().getSelectedItem().getQuestion().getSubject());
		}
	}

}
