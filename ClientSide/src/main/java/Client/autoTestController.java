package Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import CloneEntities.CloneAnswerToQuestion;
import CloneEntities.CloneQuestion;
import CloneEntities.CloneQuestionInExam;
import CloneEntities.CloneStudentTest;
import CloneEntities.CloneTimeExtensionRequest;
import UtilClasses.DataElements.ClientToServerOpcodes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class autoTestController extends  AbstractTest {

	@FXML
	private AnchorPane mainAnchor;

	@FXML
	private TextField answerAText;

	@FXML
	private TextField answerBText;

	@FXML
	private TextField answerCText;

	@FXML
	private TextField answerDText;

	@FXML
	private RadioButton answer_a_button;

	@FXML
	private RadioButton answer_b_button;

	@FXML
	private RadioButton answer_c_button;

	@FXML
	private RadioButton answer_d_button;

	@FXML
	private Label questionNumberLabel;

	@FXML
	private Label allQuestionsNumberLabel;

	@FXML
	private Label timerText;

	@FXML
	private Label nameLabel;

	@FXML
	private Button SubmitButton;

	@FXML
	private Button nextButton;

	@FXML
	private Button backButton;

	@FXML
	private TextArea questionText;

	@FXML
	private TextArea commentsText;

	private int newHour;

	private int newMinute;

	private ToggleGroup radioGroup;

	private List<CloneAnswerToQuestion> questionsAns;

	private int currQuestionNum = 0;

	public void initialize() {
		questionsAns = new ArrayList<>();
		for (CloneQuestionInExam q : currQuestions) {
			questionsAns.add(new CloneAnswerToQuestion(q.getQuestion()));
		}
		sendRequest(ClientToServerOpcodes.GetAnswerToTimeExtensionRequest, finishedTest.getTest().getId());
		setFields();
	}

	public void setFields() {
		nameLabel.setText(finishedTest.getTestName());
		backButton.setVisible(false);
		if (finishedTest.getAnswers().length == 1)
			nextButton.setVisible(false);
		changeCurrQuestion(currQuestions.get(currQuestionNum));

		commentsText.setText(finishedTest.getTest().getExamToExecute().getStudentComments());
		questionNumberLabel.setText("1");
		allQuestionsNumberLabel.setText(String.valueOf(currQuestions.size()));

		radioGroup = new ToggleGroup();
		answer_a_button.setToggleGroup(radioGroup);
		answer_b_button.setToggleGroup(radioGroup);
		answer_c_button.setToggleGroup(radioGroup);
		answer_d_button.setToggleGroup(radioGroup);

		startTimer(timerText);
	}

	public void changeCurrQuestion(CloneQuestionInExam question) {
		CloneQuestion currQuestion = question.getQuestion();
		questionText.setText(currQuestion.getQuestionText());
		answerAText.setText(currQuestion.getAnswer_1());
		answerBText.setText(currQuestion.getAnswer_2());
		answerCText.setText(currQuestion.getAnswer_3());
		answerDText.setText(currQuestion.getAnswer_4());
		if (questionsAns.get(currQuestionNum).getStudentAnswer() != -1) {
			switch (questionsAns.get(currQuestionNum).getStudentAnswer()) {
			case 1:
				answer_a_button.setSelected(true);
				break;
			case 2:
				answer_b_button.setSelected(true);
				break;
			case 3:
				answer_c_button.setSelected(true);
				break;
			case 4:
				answer_d_button.setSelected(true);
				break;
			default:
				break;
			}
		}

	}

	public void radioReset() {
		answer_a_button.setSelected(false);
		answer_b_button.setSelected(false);
		answer_c_button.setSelected(false);
		answer_d_button.setSelected(false);
	}
	
	@Override
	protected void setStudentAnswer() {
		RadioButton chk = (RadioButton) radioGroup.getSelectedToggle();
		if (chk != null) {
			switch (chk.getText()) {
			case "a.":
				questionsAns.get(currQuestionNum).setAnswer(1);
				break;
			case "b.":
				questionsAns.get(currQuestionNum).setAnswer(2);
				break;
			case "c.":
				questionsAns.get(currQuestionNum).setAnswer(3);
				break;
			case "d.":
				questionsAns.get(currQuestionNum).setAnswer(4);
				break;
			default:
				break;
			}
		}
	}

	@FXML
	void onClickedNext(ActionEvent event) {
		setStudentAnswer();
		currQuestionNum++;
		backButton.setVisible(true);
		radioReset();
		changeCurrQuestion(currQuestions.get(currQuestionNum));
		if (currQuestions.size() == currQuestionNum + 1)
			nextButton.setVisible(false);
		questionNumberLabel.setText(String.valueOf(currQuestionNum + 1));
	}

	@FXML
	void onClickedBack(ActionEvent event) {
		setStudentAnswer();
		currQuestionNum--;
		nextButton.setVisible(true);
		radioReset();
		changeCurrQuestion(currQuestions.get(currQuestionNum));
		if (currQuestionNum - 1 <= 0)
			backButton.setVisible(false);
		questionNumberLabel.setText(String.valueOf(currQuestionNum + 1));
	}

	@FXML
	void OnClickedSubmit(ActionEvent event) {
		setStudentAnswer();
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Test Submission");
		alert.setContentText("Are you sure you want to submit the test?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get().getButtonData() == ButtonData.OK_DONE) {
			timeline.stop();
			setStudentAnswer();
			newHour -= startTimeHour;
			newMinute -= startTimeMin;
			finishTest();
			return;
		}
		alert.close();
	}
	
	@Override
	protected void finishTest() {
		finishedTest.setactualTestDurationInMinutes((newHour * 60) + newMinute);
		finishedTest.setAnswers(questionsAns);
		try {
			GetDataFromDB(ClientToServerOpcodes.StudentFinishedTest, finishedTest);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		showMsg("Test Finished", "Test is over and will be send to review");
		Stage stage;
		stage = (Stage) timerText.getScene().getWindow();
		stage.close();
	}

}
