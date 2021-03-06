package Client;

import java.util.ArrayList;
import java.util.List;

import CloneEntities.*;
import UtilClasses.DataElements.ClientToServerOpcodes;
import UtilClasses.ExamGenerator;
import UtilClasses.TeacherCourse;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

public class examCreator extends AbstractController {

	@FXML
	private TextField durText;

	@FXML
	private TextField nameText;

	@FXML
	ListView<CloneQuestion> questionsList;

	@FXML
	private Button submit_button;

	@FXML
	private TextArea teachersText;

	@FXML
	ComboBox<CloneCourse> courseCombo;

	@FXML
	private TextArea studentsComment;

	@FXML
	TableView<CloneQuestionInExam> insertedQuestions;

	@FXML
	private TableColumn<CloneQuestionInExam, String> questionNameCol;

	@FXML
	private TableColumn<CloneQuestionInExam, String> questionGradeCol;

	@FXML
	private ImageView removeQuestion;

	@FXML
	private ImageView insertQuestion;

	@FXML
	private Button showQuestionButton;

	@FXML
	ComboBox<CloneExam> examCombo;

	public void initialize() {
		questionsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		insertedQuestions.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		questionNameCol.setCellValueFactory(new PropertyValueFactory<CloneQuestionInExam, String>("Name"));

		questionGradeCol.setCellValueFactory(new PropertyValueFactory<CloneQuestionInExam, String>("Grade"));
		questionGradeCol.setCellFactory(TextFieldTableCell.forTableColumn());
		questionGradeCol.setOnEditCommit(new EventHandler<CellEditEvent<CloneQuestionInExam, String>>() {
			public void handle(CellEditEvent<CloneQuestionInExam, String> t) {
				if (t.getNewValue().isEmpty()) {
					((CloneQuestionInExam) t.getTableView().getItems().get(t.getTablePosition().getRow()))
							.setPointsForQuestion(0);
				} else {
					((CloneQuestionInExam) t.getTableView().getItems().get(t.getTablePosition().getRow()))
							.setPointsForQuestion(Integer.valueOf(t.getNewValue()));
				}
			}
		});
		insertedQuestions.getColumns().setAll(questionNameCol, questionGradeCol);
		insertedQuestions.setEditable(true);

		nameText.setOnKeyPressed(event -> {
			if (event.getCode().equals(KeyCode.TAB)) {
				durText.requestFocus();
			}
			if (event.getCode().equals(KeyCode.ENTER)) {
				OnClickSubmit(new ActionEvent());
			}
		});

		durText.setOnKeyPressed(event -> {
			if (event.getCode().equals(KeyCode.TAB)) {
				teachersText.requestFocus();
			}
			if (event.getCode().equals(KeyCode.ENTER)) {
				OnClickSubmit(new ActionEvent());
			}
		});

		teachersText.setOnKeyPressed(event -> {
			if (event.getCode().equals(KeyCode.TAB)) {
				studentsComment.requestFocus();
			}
			if (event.getCode().equals(KeyCode.ENTER)) {
				OnClickSubmit(new ActionEvent());
			}
		});

		studentsComment.setOnKeyPressed(event -> {
			if (event.getCode().equals(KeyCode.ENTER)) {
				OnClickSubmit(new ActionEvent());
			}
		});

		try {
			GetDataFromDB(ClientToServerOpcodes.GetAllCoursesOfTeacher, ClientMain.getUser());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setQuestionsList(ObservableList<CloneQuestion> questions) {
		Platform.runLater(() -> {
			if (questions.isEmpty()) {
				popError("No questions on the course", "Please create questions for the course");
				return;
			}
			this.questionsList.setItems(questions);
			try {
				GetDataFromDB(ClientToServerOpcodes.GetAllExamsOfTeacherInCourse,
						new TeacherCourse(ClientMain.getUser(), courseCombo.getValue()));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	public void setCoursessList(ObservableList<CloneCourse> courses) {
		Platform.runLater(() -> {
			this.courseCombo.setItems(courses);
		});
	}

	public void setExamsList(ObservableList<CloneExam> exams) {
		Platform.runLater(() -> {
			this.examCombo.setItems(exams);
		});
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
			durText.setDisable(false);
			submit_button.setDisable(false);
			showQuestionButton.setDisable(false);
			studentsComment.setDisable(false);
			teachersText.setDisable(false);
		}
	}

	/**
	 * Used to take an existing exam and show to the teacher WE DO NOT UPDATE IT,
	 * just using it as a base to new one.
	 * 
	 * @param event
	 */
	@FXML
	void onClickedExam(ActionEvent event) {
		if (examCombo.getValue() != null) {
			CloneExam exam = examCombo.getValue();
			// nameText.setText(exam.getExamName());
			durText.setText(String.valueOf(exam.getDuration()));
			studentsComment.setText(exam.getStudentComments());
			teachersText.setText(exam.getTeacherComments());

			try {
				GetDataFromDB(ClientToServerOpcodes.GetAllQuestionInExamRelatedToExam, exam);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	void addQuestionsInExam(ObservableList<CloneQuestionInExam> questions) {
		Platform.runLater(() -> {
			insertedQuestions.setItems(questions);
			for (CloneQuestionInExam q : questions) {
				if (questionsList.getItems().contains(q.getQuestion()))
					questionsList.getItems().remove(q.getQuestion());
			}
		});
	}

	@FXML
	public void OnClickSubmit(ActionEvent event) {
		try {
			StringBuilder errorsList = new StringBuilder();

			if (nameText.getText().isEmpty())
				errorsList.append("Exam name is empty\n");

			if (durText.getText().isEmpty() || !durText.getText().matches("[0-9]+")
					|| Integer.valueOf(durText.getText()) <= 0)
				errorsList.append("Duration is empty or invalid\n");

			if (insertedQuestions.getItems().isEmpty())
				errorsList.append("Please choose at least one question\n");

			for (CloneQuestionInExam item : insertedQuestions.getItems()) {
				String data = (String) questionGradeCol.getCellObservableValue(item).getValue();
				if (data == null || data.isEmpty() || !data.matches("[0-9]+")) {
					errorsList.append("Please add grades to all questions in the exam\n");
					break;
				}
			}

			if (errorsList.length() != 0) {
				throw new Exception(errorsList.toString());
			}
		} catch (Exception e) {
			popError("Please fill all question fields correctly", e.getMessage());
			return;
		}

		List<Integer> columnData = new ArrayList<>();
		List<CloneQuestion> qToSend = new ArrayList<>();
		for (CloneQuestionInExam item : insertedQuestions.getItems()) {
			columnData.add(Integer.valueOf(questionGradeCol.getCellObservableValue(item).getValue()));
			qToSend.add(item.getQuestion());
		}

		CloneExam newExam = new CloneExam(Integer.valueOf(durText.getText()), nameText.getText(),
				teachersText.getText(), studentsComment.getText(), courseCombo.getValue().getId(),
				courseCombo.getValue().getCourseName(), ClientMain.getUser().getId(), qToSend.size());

		try {
			GetDataFromDB(ClientToServerOpcodes.CreateNewExam, new ExamGenerator(newExam, qToSend, columnData));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void moveQuestionRight(MouseEvent event) {
		if (!questionsList.getSelectionModel().getSelectedItems().isEmpty()) {
			for (CloneQuestion q : questionsList.getSelectionModel().getSelectedItems()) {
				insertedQuestions.getItems().add(new CloneQuestionInExam(-1, q));
			}
			questionsList.getItems().removeAll(questionsList.getSelectionModel().getSelectedItems());
		}

	}

	@FXML
	public void moveQuestionLeft(MouseEvent event) {
		if (!(insertedQuestions.getSelectionModel().getSelectedItems().isEmpty())) {
			for (CloneQuestionInExam q : insertedQuestions.getSelectionModel().getSelectedItems())
				questionsList.getItems().add(q.getQuestion());
			insertedQuestions.getItems().removeAll(insertedQuestions.getSelectionModel().getSelectedItems());
		}

	}

	@FXML
	public void OnClickedShowQuestion(ActionEvent event) throws Exception {
		if (questionsList.getSelectionModel().getSelectedItem() != null) {
			newWindow(questionsList, new showQuestion(), "showQuestion.fxml",
					"Question " + questionsList.getSelectionModel().getSelectedItem().getSubject());
		}
	}

}
