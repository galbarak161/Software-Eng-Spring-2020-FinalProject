package Client;

import java.io.IOException;

import CloneEntities.*;
import UtilClasses.DataElements.ClientToServerOpcodes;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
	private TableView<CloneQuestion> insertedQuestions;

	@FXML
	private TableColumn<CloneQuestion, String> questionNameCol;

	@FXML
	private TableColumn<CloneQuestion, String> questionGradeCol;

	@FXML
	private ImageView removeQuestion;

	@FXML
	private ImageView insertQuestion;

	@FXML
	private Button showQuestionButton;
	
    @FXML
    private ComboBox<CloneExam> examCourse;

	public void initialize() {
		questionsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		insertedQuestions.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		questionNameCol.setCellValueFactory(new PropertyValueFactory<CloneQuestion, String>("Subject"));

		//questionGradeCol.setCellValueFactory(new PropertyValueFactory<CloneQuestion, String>("Grade"));

		insertedQuestions.getColumns().setAll(questionNameCol);
		insertedQuestions.setEditable(true);
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
			durText.setDisable(false);
			submit_button.setDisable(false);
			showQuestionButton.setDisable(false);
			studentsComment.setDisable(false);
			teachersText.setDisable(false);
		}
	}
	
	/**
	 * Used to take an existing exam and show to the teacher
	 * WE DO NOT UPDATE IT, just using it as a base to new one.
	 * @param event
	 */
    @FXML
    void onClickedExam(ActionEvent event) {
//		if (courseCombo.getValue() != null) {
//			try {
//				GetDataFromDB(ClientToServerOpcodes.GetAllExa, courseCombo.getValue());
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			nameText.setDisable(false);
//			minutesText.setDisable(false);
//			hoursText.setDisable(false);
//			submit_button.setDisable(false);
//			showQuestionButton.setDisable(false);
//			studentsComment.setDisable(false);
//			teachersText.setDisable(false);
//		}
    }

	@FXML
	public void OnClickSubmit(ActionEvent event) {
		try {
			StringBuilder errorsList = new StringBuilder();

			if (nameText.getText().isEmpty())
				errorsList.append("Exam name is empty\n");

			if (durText.getText().isEmpty() || !durText.getText().matches("[0-9]+"))
				errorsList.append("Please choose test type\n");

			if (errorsList.length() != 0) {
				throw new Exception(errorsList.toString());
			}
		} catch (Exception e) {
			popError("Please fill all question fields", e.getMessage());
			return;
		}
		CloneExam newExam = null;

		
		try {
			GetDataFromDB(ClientToServerOpcodes.CreateNewExam, newExam);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void moveQuestionRight(MouseEvent event) {
		if (!(questionsList.getSelectionModel().getSelectedItems().isEmpty())) {
			insertedQuestions.getItems().addAll(questionsList.getSelectionModel().getSelectedItems());
			questionsList.getItems().removeAll(questionsList.getSelectionModel().getSelectedItems());
		}

	}

	@FXML
	public void moveQuestionLeft(MouseEvent event) {
		if (!(insertedQuestions.getSelectionModel().getSelectedItems().isEmpty())) {
			questionsList.getItems().addAll(insertedQuestions.getSelectionModel().getSelectedItems());
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
