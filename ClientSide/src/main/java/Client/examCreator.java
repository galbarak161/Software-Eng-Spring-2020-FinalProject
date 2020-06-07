package Client;

import CloneEntities.*;
import CommonElements.DataElements.ClientToServerOpcodes;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class examCreator extends AbstractController {

	@FXML
	private TextField hoursText;

	@FXML
	private TextField minutesText;

	@FXML
	private TextField nameText;

	@FXML
	ListView<CloneQuestion> questionsList;

	@FXML
	private Button submit_button;

	@FXML
	private TextField teachersText;

	@FXML
	ComboBox<CloneCourse> courseCombo;

	@FXML
	private TextField studentsComment;

	@FXML
	private TableView<CloneQuestion> InsertedQuestions;

	@FXML
	private TableColumn<CloneQuestion, String> questionNameCol;

	@FXML
	private TableColumn<CloneQuestion, String> QuestionGradeCol;

	@FXML
	private ImageView removeQuestion;

	@FXML
	private ImageView insertQuestion;

	@FXML
	private Button showQuestionButton;

	public void initialize() {
		questionsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		InsertedQuestions.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		questionNameCol.setCellValueFactory(new PropertyValueFactory<CloneQuestion, String>("Subject"));

		QuestionGradeCol.setCellFactory(TextFieldTableCell.forTableColumn());

		InsertedQuestions.getColumns().setAll(questionNameCol, QuestionGradeCol);
		InsertedQuestions.setEditable(true);
		try {
			GetDataFromDB(ClientToServerOpcodes.GetAllCoursesOfTeacher, ClientMain.getUser());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void SetList(ObservableList<CloneQuestion> questions) {
		this.questionsList.setItems(questions);
	}

	@FXML
	public void OnCourseClicked(ActionEvent event) {
		if (courseCombo.getValue() != null) {
			try {
				GetDataFromDB(ClientToServerOpcodes.GetAllQuestionInCourse, courseCombo.getValue());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			nameText.setDisable(false);
			minutesText.setDisable(false);
			hoursText.setDisable(false);
			submit_button.setDisable(false);
			showQuestionButton.setDisable(false);
			studentsComment.setDisable(false);
			teachersText.setDisable(false);
		}
	}

	@FXML
	public void OnClickSubmit(ActionEvent event) {
		CloneExam newExam = new CloneExam();
		newExam.setQuestions(InsertedQuestions.getItems());
		// newExam.setCourseId(courseId);
		newExam.setExamName(nameText.getText());
		newExam.setStudentComments(studentsComment.getText());
		newExam.setTeacherComments(teachersText.getText());
		newExam.setTeacherId(ClientMain.getUser().getId());
		// newExam.setPoints();
	}

	@FXML
	public void moveQuestionRight(MouseEvent event) {
		if (!(questionsList.getSelectionModel().getSelectedItems().isEmpty())) {
			InsertedQuestions.getItems().addAll(questionsList.getSelectionModel().getSelectedItems());
			questionsList.getItems().removeAll(questionsList.getSelectionModel().getSelectedItems());
		}

	}

	@FXML
	public void moveQuestionLeft(MouseEvent event) {
		if (!(InsertedQuestions.getSelectionModel().getSelectedItems().isEmpty())) {
			questionsList.getItems().addAll(InsertedQuestions.getSelectionModel().getSelectedItems());
			InsertedQuestions.getItems().removeAll(InsertedQuestions.getSelectionModel().getSelectedItems());
		}

	}

	@FXML
	public void OnClickedShowQuestion(ActionEvent event) {

	}

}
