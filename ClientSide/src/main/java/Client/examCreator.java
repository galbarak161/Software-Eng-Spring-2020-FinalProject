package Client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import CloneEntities.*;
import UtilClasses.DataElements.ClientToServerOpcodes;
import UtilClasses.ExamGenerator;
import UtilClasses.TeacherCourse;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
	private TextField teachersText;

	@FXML
	ComboBox<CloneCourse> courseCombo;

	@FXML
	private TextField studentsComment;

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

		try {
			GetDataFromDB(ClientToServerOpcodes.GetAllCoursesOfTeacher, ClientMain.getUser());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void SetList(ObservableList<CloneQuestion> questions) {
		Platform.runLater(() -> {
			this.questionsList.setItems(questions);
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

			try {
				GetDataFromDB(ClientToServerOpcodes.GetAllExamsOfTeacherInCourse,
						new TeacherCourse(ClientMain.getUser(), courseCombo.getValue()));
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
			nameText.setText(exam.getExamName());
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
		insertedQuestions.setItems(questions);
		for (CloneQuestionInExam q : questions) {
			if (questionsList.getItems().contains(q.getQuestion()))
				questionsList.getItems().remove(q.getQuestion());
		}
	}

	@FXML
	public void OnClickSubmit(ActionEvent event) {
		try {
			StringBuilder errorsList = new StringBuilder();

			if (nameText.getText().isEmpty())
				errorsList.append("Exam name is empty\n");

			if (durText.getText().isEmpty() || !durText.getText().matches("[0-9]+"))
				errorsList.append("Duration is empty\n");

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
			popError("Please fill all question fields", e.getMessage());
			return;
		}
		System.out.print("Good job!");
		CloneExam newExam = new CloneExam(Integer.valueOf(durText.getText()), nameText.getText(),
				teachersText.getText(), studentsComment.getText(), courseCombo.getValue().getId(),
				courseCombo.getValue().getCourseName(), ClientMain.getUser().getId());

		List<Integer> columnData = new ArrayList<>();
		List<CloneQuestion> qToSend = new ArrayList<>();
		for (CloneQuestionInExam item : insertedQuestions.getItems()) {
			columnData.add(Integer.valueOf(questionGradeCol.getCellObservableValue(item).getValue()));
			qToSend.add(item.getQuestion());
		}

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
	public void OnClickedShowQuestion(ActionEvent event) {
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

}
