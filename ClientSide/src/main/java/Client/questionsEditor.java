package Client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import CloneEntities.*;
import CommonElements.DataElements;
import CommonElements.DataElements.ClientToServerOpcodes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class questionsEditor {

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
	private Label question_label;

	@FXML
	private Label course_label;

	@FXML
	private Label study_label;

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
	private ListView<CloneQuestion> qList;

	@FXML
	private ComboBox<CloneQuestion> question_combo;

	@FXML
	private ComboBox<CloneCourse> course_combo;

	@FXML ComboBox<CloneStudy> study_combo;

	private static boolean msgRecived = false;

	private static Object dataRecived = null;

	private Alert alert = new Alert(Alert.AlertType.ERROR);

	private Alert info = new Alert(Alert.AlertType.INFORMATION);
	
	private static final String ERROR_TITLE_SERVER = "An error occurred while retrieving data from server";
	
	private static final String ERROR_TITLE_Client = "An error occurred while the system was hanaling your actions";

	/**
	 * Function called automatically when GUI is starting. We get from DB all
	 * "Studies" and put them on study_combo ("Editor" tab) Then we get all the
	 * questions from DB and put them on "qList" ListView ("Selector" tab)
	 * 
	 * @throws Exception
	 * 
	 */
	public void initialize() {
		ClientMain.addController(this);
		String initErrors = "";
		try {
			int dbStatus = GetDataFromDB(ClientToServerOpcodes.GetAllStudies, null);
			if ((dbStatus == -1)) {
				initErrors += "The system cannot retrieve studies from server\n";
				study_combo.setDisable(true);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		radioGroup = new ToggleGroup();
		radio_1.setToggleGroup(radioGroup);
		radio_2.setToggleGroup(radioGroup);
		radio_3.setToggleGroup(radioGroup);
		radio_4.setToggleGroup(radioGroup);
		
		///////////////////// Ask Liel about all questions show ///////////////////////////////

		if (!initErrors.isEmpty())
			popError(ERROR_TITLE_SERVER,initErrors);
	}

	/**
	 * getDataFromServer(DataElements) The function calls the
	 * ClientMain.sendMessageToServer(Object) function
	 * 
	 * @param DataElements with opcode and data
	 * @return -1 for fail
	 */
	private int sendRequestForDataFromServer(DataElements de) {
		int status;
		try {
			status = ClientMain.sendMessageToServer(de);
		} catch (IOException e) {
			status = -1;
			String errorMessage = "The system could not receive data from server. please reconnect and try again";
			popError(ERROR_TITLE_SERVER, errorMessage);
			e.printStackTrace();
		}

		return status;
	}

	/**
	 * Creating request to get data from server
	 * 
	 * @param op   - what type of request do we want (Enum)
	 * @param data - the date we want to send to server
	 * @return
	 * @throws InterruptedException Pause the main GUI thread
	 */
	public int GetDataFromDB(ClientToServerOpcodes op, Object data) throws InterruptedException {
		return sendRequestForDataFromServer(new DataElements(op, data));
	}

	/**
	 * Activate as a respond for an unknown exception in client
	 * 
	 * @param object Contains the error description
	 */
	public void popError(String title, String errorMessage) {
		alert.setHeaderText(title);
		alert.getDialogPane().setExpandableContent(new ScrollPane(new TextArea(errorMessage)));
		alert.showAndWait();
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
	 * Opens the Instructions window when clicked "Help" on the menubar
	 * 
	 * @param event- doesn't matter
	 */
	@FXML
	void openInstructions(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Instructions.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setTitle("Insructions");
			stage.getIcons().add(new Image(App.class.getResource("help_icon.jpeg").toExternalForm()));
			stage.setScene(new Scene(root1));
			stage.setResizable(false);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Handles double-click on the qList (Like clicking on "Edit" button)
	 * 
	 * @param event - Contains the clicking event on the mouse
	 */
	@FXML
	void onDoubleClick(MouseEvent event) {
		if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2)
			onClickedEdit(new ActionEvent());
	}

	/**
	 * Handle clicking on "Edit" button and presenting the selected question from
	 * qList
	 * 
	 * @param actionEvent
	 */
	@FXML
	void onClickedEdit(ActionEvent actionEvent) {
		ObservableList<CloneQuestion> selected_q = qList.getSelectionModel().getSelectedItems();
		if (selected_q.isEmpty()) {
			popError(ERROR_TITLE_Client,"No question has been selected. \nPlease select a question");
			return;
		}

		try {
			dataRecived = null;

			int dbStatus = GetDataFromDB(ClientToServerOpcodes.GetAllQuestionInCourse, selected_q.get(0).getCourse());
			if ((dbStatus == -1) || dataRecived == null) {
				popError(ERROR_TITLE_Client,"The system cannot retrieve question data from server. \nPlease try again");
				return;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			popError(ERROR_TITLE_Client,"The system cannot retrieve question data from server. \nPlease try again");
			return;
		}

		int qIndex = (selected_q.get(0).getQuestionCode() % 1000) - 1;
		if (qIndex < 0) {
			popError(ERROR_TITLE_Client,"The system cannot retrieve question data from server. \nPlease try again");
			return;
		}

		EventHandler<ActionEvent> handler;

		handler = study_combo.getOnAction();
		study_combo.setOnAction(null);
		study_combo.getSelectionModel().clearSelection();
		study_combo.setOnAction(handler);

		handler = course_combo.getOnAction();
		course_combo.setOnAction(null);
		course_combo.getItems().clear();
		List<CloneCourse> tempCourse = new ArrayList<CloneCourse>();
		tempCourse.add(selected_q.get(0).getCourse());
		course_combo.setItems(FXCollections.observableArrayList(tempCourse));
		course_combo.setValue(tempCourse.get(0));
		course_combo.setDisable(false);
		course_combo.setOnAction(handler);

		handler = question_combo.getOnAction();
		question_combo.setOnAction(null);
		question_combo.getItems().clear();
		ObservableList<CloneQuestion> temp = FXCollections.observableArrayList((List<CloneQuestion>) dataRecived);
		question_combo.setItems(temp);
		question_combo.setValue(temp.get(qIndex));
		parseQuestionToFields(question_combo.getValue());
		question_combo.setDisable(false);
		question_combo.setOnAction(handler);

		mainTab.getSelectionModel().select(edtiorTab);
		ChangeSubmitColor(null);
	}

	/**
	 * Display the retrieved "Studies" from server on study_combo Reset other fields
	 * on "Editor" tab
	 * 
	 * @param event - doesn't matter
	 */
	@FXML
	void onClickedStudy(ActionEvent event) {
		if (study_combo.getValue() == null)
			return;

		try {
			dataRecived = null;
			int dbStatus = GetDataFromDB(ClientToServerOpcodes.GetAllCoursesInStudy, study_combo.getValue());
			if ((dbStatus == -1) || dataRecived == null) {
				popError(ERROR_TITLE_Client,"The system cannot retrieve courses from server \nPlease try again");
				return;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			popError(ERROR_TITLE_Client,"The system cannot retrieve courses from server \nPlease try again");
			return;
		}

		EventHandler<ActionEvent> handler;

		handler = course_combo.getOnAction();
		course_combo.getItems().clear();
		course_combo.setDisable(false);
		course_combo.setItems(FXCollections.observableArrayList((List<CloneCourse>) dataRecived));
		course_combo.setValue(null);
		course_combo.setOnAction(handler);

		handler = question_combo.getOnAction();
		question_combo.getItems().clear();
		question_combo.setDisable(true);
		question_combo.setValue(null);
		disableQuestionDataFields(true);
		question_combo.setOnAction(handler);

		ClearAllFormFields();
		ChangeSubmitColor(null);
	}

	/**
	 * Display the retrieved "Courses" from server on course_combo Reset other
	 * fields on "Editor" tab, except study_combo
	 * 
	 * @param event - doesn't matter
	 */
	@FXML
	void onCourseClicked(ActionEvent event) {
		if (course_combo.getValue() == null)
			return;

		try {
			dataRecived = null;
			int dbStatus = GetDataFromDB(ClientToServerOpcodes.GetAllQuestionInCourse, course_combo.getValue());
			if ((dbStatus == -1) || dataRecived == null) {
				throw new InterruptedException();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			popError(ERROR_TITLE_Client, "The system cannot retrieve questions from server. \nPlease try again");
			return;
		}

		ClearAllFormFields();

		EventHandler<ActionEvent> handler;

		handler = question_combo.getOnAction();
		question_combo.setDisable(false);
		question_combo.setItems(FXCollections.observableArrayList((List<CloneQuestion>) dataRecived));
		question_combo.setValue(null);
		question_combo.setOnAction(handler);

		ChangeSubmitColor(null);
		disableQuestionDataFields(true);
	}

	/**
	 * Display the a retrieved "Question" from server on the question text fields
	 * and radio buttons, also enable submit button.
	 * 
	 * @param event - doesn't matter
	 */
	@FXML
	void onClickedQuestion(ActionEvent event) {
		if (question_combo.getValue() == null)
			return;

		parseQuestionToFields(question_combo.getValue());
		ChangeSubmitColor(null);
	}

	/**
	 * Handles the click on "Submit" button- send an update question query to server
	 * Updates qList with updated question retrieved from server and also
	 * question_combo if needed On a success - "Submit" button changes its color to
	 * green On a failure - "Submit" button changes its color to red
	 * 
	 * @param event - doesn't matter
	 * @throws Exception - Used for checking all fields are filled
	 */
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

		dataRecived = null;
		int dbStatus = GetDataFromDB(ClientToServerOpcodes.UpdateQuestion, q);

		if ((dbStatus == -1) || dataRecived == null) {
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
