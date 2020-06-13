package Client;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import CloneEntities.CloneAnswerToQuestion;
import CloneEntities.CloneQuestion;
import CloneEntities.CloneQuestionInExam;
import CloneEntities.CloneStudentTest;
import UtilClasses.DataElements.ClientToServerOpcodes;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class autoTestController extends AbstractController {

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

	private int hour, min;

	private int newHour;

	private int newMinute;

	private ToggleGroup radioGroup;

	private CloneStudentTest finishedTest;

	private List<CloneQuestionInExam> qInTest;

	private List<CloneAnswerToQuestion> questionsAns;

	private int currQuestionNum = 0;

	private Timeline timeline = new Timeline();
	private int startTimeSec, startTimeMin, startTimeHour;
	public BorderPane timeBorderPane;

	public void initialize() {
		finishedTest = testEntracnce.thisTest;
		qInTest = testEntracnce.currQuestions;
		questionsAns = new ArrayList<>();
		for (CloneQuestionInExam q : qInTest) {
			questionsAns.add(new CloneAnswerToQuestion(q.getQuestion()));
		}
		setFields();
	}

	public void setFields() {
		nameLabel.setText(finishedTest.getTestName());
		backButton.setVisible(false);
		if (finishedTest.getAnswers().length == 1)
			nextButton.setVisible(false);
		changeCurrQuestion(qInTest.get(currQuestionNum));

		commentsText.setText(finishedTest.getTest().getExamToExecute().getStudentComments());
		questionNumberLabel.setText("1");
		allQuestionsNumberLabel.setText(String.valueOf(qInTest.size()));

		radioGroup = new ToggleGroup();
		answer_a_button.setToggleGroup(radioGroup);
		answer_b_button.setToggleGroup(radioGroup);
		answer_c_button.setToggleGroup(radioGroup);
		answer_d_button.setToggleGroup(radioGroup);

		int originalHour = finishedTest.getTest().getTestTime().getHour();

		int originalMinute = finishedTest.getTest().getTestTime().getMinute();

		int durHour = finishedTest.getTest().getTestDuration() / 60;

		int durMinute = finishedTest.getTest().getTestDuration() % 60;

		newHour = Math.abs(durHour - Math.abs(LocalTime.now().getHour() - originalHour));

		newMinute = durMinute - Math.abs(LocalTime.now().getMinute() - originalMinute);

		if (newMinute <= 0) {
			newHour--;
			newMinute = 60 + newMinute;
		}

		if (newHour < 0) {
			newHour = 0;
		}

		hour = newHour;

		min = newMinute;

		startTimer();
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

	public void setStudentAnswer() {
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
		changeCurrQuestion(qInTest.get(currQuestionNum));
		if (qInTest.size() == currQuestionNum + 1)
			nextButton.setVisible(false);
		questionNumberLabel.setText(String.valueOf(currQuestionNum + 1));
	}

	@FXML
	void onClickedBack(ActionEvent event) {
		setStudentAnswer();
		currQuestionNum--;
		nextButton.setVisible(true);
		radioReset();
		changeCurrQuestion(qInTest.get(currQuestionNum));
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
			newHour -= startTimeHour;
			newMinute -= startTimeMin;
			finishedTest.setactualTestDurationInMinutes((newHour * 60) + newMinute);
			finishedTest.setAnswers(questionsAns);
			try {
				GetDataFromDB(ClientToServerOpcodes.StudentFinishedTest, finishedTest);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			alert.close();
			studentController.testStage.close();
			return;
		}
		alert.close();
	}

	public void updateTimer(int addedTime) {
		hour += addedTime / 60;
		if ((min + addedTime % 60) >= 60) {
			min = (min + addedTime) % 60;
			hour++;
		} else
			min += addedTime % 60;

	}

	public void startTimer() {

		KeyFrame keyframe = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				startTimeSec--;
				boolean isSecondsZero = startTimeSec == 0;
				boolean isMinutesZero = startTimeMin == 0;
				boolean timeToChangeBackground = startTimeSec == 0 && startTimeMin == 0 && startTimeHour == 0;

				if (isSecondsZero) {
					if (isMinutesZero) {
						startTimeHour--;
						startTimeMin = 60;
					}
					startTimeMin--;
					startTimeSec = 59;

				}
				if (timeToChangeBackground) {
					timeline.stop();
					startTimeMin = 0;
					startTimeSec = 0;
					startTimeHour = 0;
					timerText.setTextFill(Color.RED);

				}

				timerText.setText(
						String.format("%d hours, %d min, %02d sec", startTimeHour, startTimeMin, startTimeSec));
			}
		});
		timerText.setTextFill(Color.BLACK);
		startTimeSec = 60;
		startTimeMin = min - 1;
		startTimeHour = hour;
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(keyframe);
		timeline.setOnFinished((e) -> {
			finishedTest.setactualTestDurationInMinutes((newHour * 60) + newMinute);
			finishedTest.setAnswers(questionsAns);
			try {
				GetDataFromDB(ClientToServerOpcodes.StudentFinishedTest, finishedTest);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			showMsg("Test's Time is up", "Test is over and sent to review");
		});
		timeline.playFromStart();
		timeline.play();
	}

}
