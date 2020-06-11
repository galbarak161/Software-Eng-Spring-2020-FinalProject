package Client;

import CloneEntities.*;
import UtilClasses.DataElements.ClientToServerOpcodes;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

public class questionsEditor extends AbstractController {

	/**************************************
	 ************* Variables **************
	 **************************************/
	@FXML
	private MenuBar menu;

	@FXML
	private Menu help_menu;

	@FXML
	private MenuItem instru_help_menu;

	@FXML
	private TabPane mainTab;

	@FXML
	private Label title2;

	@FXML
	private Button editButton;

	@FXML
	private Tab edtiorTab;

	@FXML
	private Button submitButton;

	@FXML
	private Label title;

	@FXML
	private Label course_label;

	@FXML
	private TextField answer_line_1;

	@FXML
	private RadioButton radio_1;

	@FXML
	private ToggleGroup radioGroup;

	@FXML
	private TextField answer_line_2;

	@FXML
	private RadioButton radio_2;

	@FXML
	private TextField answer_line_3;

	@FXML
	private RadioButton radio_3;

	@FXML
	private TextField answer_line_4;

	@FXML
	private RadioButton radio_4;

	@FXML
	private TextField subject_text;

	@FXML
	private TextArea question_text;

	@FXML
	private Label question_label;

	@FXML
	ComboBox<CloneQuestion> question_combo;

	@FXML
	ComboBox<CloneCourse> course_combo;

	/**
	 * Function called automatically when GUI is starting. We get from DB all
	 * "Studies" and put them on study_combo ("Editor" tab) Then we get all the
	 * questions from DB and put them on "qList" ListView ("Selector" tab)
	 * 
	 * @throws Exception
	 * 
	 */
	public void initialize() {
		try {
			GetDataFromDB(ClientToServerOpcodes.GetAllCoursesOfTeacher, ClientMain.getUser());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		radioGroup = new ToggleGroup();
		radio_1.setToggleGroup(radioGroup);
		radio_2.setToggleGroup(radioGroup);
		radio_3.setToggleGroup(radioGroup);
		radio_4.setToggleGroup(radioGroup);

		///////////////////// Ask Liel about all questions show
		///////////////////// ///////////////////////////////
	}

	/***********************
	 * Layout functions **
	 ***********************/

	/**
	 * Handles the click on "Submit" button- send an update question query to server
	 * Updates qList with updated question retrieved from server and also
	 * question_combo if needed On a success - "Submit" button changes its color to
	 * green On a failure - "Submit" button changes its color to red
	 * 
	 * @param event - doesn't matter
	 * @throws Exception - Used for checking all fields are filled
	 **/
	@FXML
	public void onClickedSubmit(ActionEvent event) throws Exception {
		
		int correctAnswer = 1;
		try {
			StringBuilder errorsList = new StringBuilder();

			if (course_combo.getValue() == null)
				errorsList.append("No course has been chosen\n");

			if (subject_text.getText().isEmpty())
				errorsList.append("Subject is empty\n");

			if (question_text.getText().isEmpty())
				errorsList.append("Question is empty\n");

			if (answer_line_1.getText().isEmpty())
				errorsList.append("Answer 1 is empty\n");

			if (answer_line_2.getText().isEmpty())
				errorsList.append("Answer 2 is empty\n");

			if (answer_line_3.getText().isEmpty())
				errorsList.append("Answer 3 is empty\n");

			if (answer_line_4.getText().isEmpty())
				errorsList.append("Answer 4 is empty\n");

			RadioButton chk = (RadioButton) radioGroup.getSelectedToggle();
			switch (chk.getText()) {
			case "a.":
				correctAnswer = 1;
				break;
			case "b.":
				correctAnswer = 2;
				break;
			case "c.":
				correctAnswer = 3;
				break;
			case "d.":
				correctAnswer = 4;
				break;
			default:
				errorsList.append("No correct answer\n");
			}

			if (errorsList.length() != 0) {
				throw new Exception(errorsList.toString());
			}
		} catch (Exception e) {
			popError("Please fill all question fields", e.getMessage());
			return;
		}
		
		CloneQuestion q = new CloneQuestion(subject_text.getText(), question_text.getText(), answer_line_1.getText(), 
				answer_line_2.getText(), answer_line_3.getText(), answer_line_4.getText(), correctAnswer, course_combo.getValue(),
				ClientMain.getUser().getId());

		try {
			GetDataFromDB(ClientToServerOpcodes.CreateNewQuestion, q);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Limits the number of chars on TextArea to 100
	 * 
	 * @param event
	 */
	@FXML
	void countChars100(KeyEvent event) {
		TextField n = (TextField) event.getSource();
		n.setTextFormatter(
				new TextFormatter<String>(change -> change.getControlNewText().length() <= 100 ? change : null));
	}

	/**
	 * Limits the number of chars on TextArea to 180
	 * 
	 * @param event
	 */
	@FXML
	void countChars180(KeyEvent event) {
		TextArea n = (TextArea) event.getSource();
		n.setTextFormatter(
				new TextFormatter<String>(change -> change.getControlNewText().length() <= 180 ? change : null));
	}

	@FXML
	void onClickedCourse(ActionEvent event) {
		if (course_combo.getValue() != null) {
			try {
				GetDataFromDB(ClientToServerOpcodes.GetAllQuestionInCourse, course_combo.getValue());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	void onClickedQuestion(ActionEvent event) {
		if (question_combo.getValue() != null) {
			CloneQuestion q = question_combo.getValue();
			subject_text.setText(q.getSubject());
			question_text.setText(q.getQuestionText());
			answer_line_1.setText(q.getAnswer_1());
			answer_line_2.setText(q.getAnswer_2());
			answer_line_3.setText(q.getAnswer_3());
			answer_line_4.setText(q.getAnswer_4());
			switch (q.getCorrectAnswer()) {
			case 1:
				radio_1.setSelected(true);
				break;
			case 2:
				radio_2.setSelected(true);
				break;
			case 3:
				radio_3.setSelected(true);
				break;
			case 4:
				radio_4.setSelected(true);
				break;
			}
		}
	}
	
	void setCourses(ObservableList<CloneCourse> courses) {
		Platform.runLater(() -> {
			course_combo.setItems(courses);
		});
	}
	
	void setQuestions(ObservableList<CloneQuestion> questions) {
		Platform.runLater(() -> {
			question_combo.setItems(questions);
		});
		
	}

}
