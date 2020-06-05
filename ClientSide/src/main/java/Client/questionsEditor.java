package Client;

import CloneEntities.*;
import CommonElements.DataElements.ClientToServerOpcodes;
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

	@FXML ComboBox<CloneCourse> course_combo;


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
			GetDataFromDB(ClientToServerOpcodes.GetAllCoursesByTeacherId, ClientMain.getUser());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		radioGroup = new ToggleGroup();
		radio_1.setToggleGroup(radioGroup);
		radio_2.setToggleGroup(radioGroup);
		radio_3.setToggleGroup(radioGroup);
		radio_4.setToggleGroup(radioGroup);
		
		///////////////////// Ask Liel about all questions show ///////////////////////////////
	}
	
	

	/**
	 * Changing Submit button color
	 * 
	 * @param color- color Submit would be changed to, #FFFFFF for example.
	 */
	void ChangeSubmitColor(String color) {
		if (color == null)
			submitButton.setStyle(color);
		else
			submitButton.setStyle(String.format("-fx-background-color: " + color + ";"));
	}

	/**
	 * Filling all text fields and radio buttons of question on "Editor" tab from
	 * "CurrentQuestion" argument
	 * 
	 * @param CurrentQuestion Contains a question selected from questions_combo or
	 *                        qList
	 */
	void parseQuestionToFields(CloneQuestion CurrentQuestion) {

		if (CurrentQuestion == null)
			return;

		// Parse all data
		subject_text.setText(CurrentQuestion.getSubject());
		question_text.setText(CurrentQuestion.getQuestionText());
		answer_line_1.setText(CurrentQuestion.getAnswer_1());
		answer_line_2.setText(CurrentQuestion.getAnswer_2());
		answer_line_3.setText(CurrentQuestion.getAnswer_3());
		answer_line_4.setText(CurrentQuestion.getAnswer_4());

		switch (CurrentQuestion.getCorrectAnswer()) {
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
		default:
			break;
		}

		// Enable data editing
		disableQuestionDataFields(false);
	}

	/**
	 * toggle enable \ disable attribute for question form elements
	 * 
	 * @param disableEdit
	 */
	public void disableQuestionDataFields(boolean disableEdit) {
		question_text.setDisable(disableEdit);
		subject_text.setDisable(disableEdit);
		answer_line_1.setDisable(disableEdit);
		answer_line_2.setDisable(disableEdit);
		answer_line_3.setDisable(disableEdit);
		answer_line_4.setDisable(disableEdit);
		radio_1.setDisable(disableEdit);
		radio_2.setDisable(disableEdit);
		radio_3.setDisable(disableEdit);
		radio_4.setDisable(disableEdit);
		submitButton.setDisable(disableEdit);
	}

	/**
	 * 
	 * we call this function every time there's change of a combo, therefore we want
	 * to clear all fields that linked to the data of a question
	 * 
	 */
	public void ClearAllFormFields() {

		subject_text.clear();
		question_text.clear();
		answer_line_1.clear();
		answer_line_2.clear();
		answer_line_3.clear();
		answer_line_4.clear();

		radio_1.setSelected(false);
		radio_2.setSelected(false);
		radio_3.setSelected(false);
		radio_4.setSelected(false);
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
	 
	@FXML
	void onClickedSubmit(ActionEvent event) throws Exception {
		CloneQuestion q = new CloneQuestion();

		try {
			StringBuilder errorsList = new StringBuilder();
			q.clone(question_combo.getValue());

			if (subject_text.getText().isEmpty())
				errorsList.append("Subject is empty\n");
			else
				q.setSubject(subject_text.getText());

			if (question_text.getText().isEmpty())
				errorsList.append("Question is empty\n");
			else
				q.setQuestionText(question_text.getText());

			if (answer_line_1.getText().isEmpty())
				errorsList.append("Answer 1 is empty\n");
			else
				q.setAnswer_1(answer_line_1.getText());

			if (answer_line_2.getText().isEmpty())
				errorsList.append("Answer 2 is empty\n");
			else
				q.setAnswer_2(answer_line_2.getText());

			if (answer_line_3.getText().isEmpty())
				errorsList.append("Answer 3 is empty\n");
			else
				q.setAnswer_3(answer_line_3.getText());

			if (answer_line_4.getText().isEmpty())
				errorsList.append("Answer 4 is empty\n");
			else
				q.setAnswer_4(answer_line_4.getText());

			RadioButton chk = (RadioButton) radioGroup.getSelectedToggle();
			switch (chk.getText()) {
			case "a.":
				q.setCorrectAnswer(1);
				break;
			case "b.":
				q.setCorrectAnswer(2);
				break;
			case "c.":
				q.setCorrectAnswer(3);
				break;
			case "d.":
				q.setCorrectAnswer(4);
				break;
			default:
				errorsList.append("No correct answer\n");
			}

			if (errorsList.length() != 0) {
				throw new Exception(errorsList.toString());
			}
		} catch (Exception e) {
			ChangeSubmitColor("#FF0000");
			popError("Please fill all question fields", e.getMessage());
			return;
		}

		int dbStatus = GetDataFromDB(ClientToServerOpcodes.UpdateQuestion, q);

		if (dbStatus == -1) {
			ChangeSubmitColor("#FF0000");
			popError(ERROR_TITLE_Client, "The system could not commit your update request.\nPlease try again");
			return;
		}

		CloneQuestion newItem = (CloneQuestion) dataRecived;

		if (question_combo.getItems().size() >= 1) {
			for (CloneQuestion q2 : question_combo.getItems()) {
				if (newItem.getId() == q2.getId()) {
					EventHandler<ActionEvent> handler = question_combo.getOnAction();
					question_combo.setOnAction(null);
					question_combo.getItems().remove(q2);
					question_combo.getItems().add(newItem);
					question_combo.setValue(newItem);
					question_combo.setOnAction(handler);
					break;
				}
			}
		}

		for (CloneQuestion q2 : qList.getItems()) {
			if (newItem.getId() == q2.getId()) {
				int index = qList.getItems().indexOf(q2);
				qList.getItems().remove(q2);
				qList.getItems().add(index, newItem);
				// qList.getItems().add(newItem);
				dataRecived = null;
				break;
			}
		}

		ChangeSubmitColor("#00FF09");
		info.setHeaderText("The question has been successfully updated!");
		info.setTitle("Success");
		info.showAndWait();

	}
*/
	
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

}
