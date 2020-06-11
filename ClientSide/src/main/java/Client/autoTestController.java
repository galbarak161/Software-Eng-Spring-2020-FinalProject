package Client;

import CloneEntities.CloneAnswerToQuestion;
import CloneEntities.CloneQuestion;
import CloneEntities.CloneStudentTest;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class autoTestController {

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
    private Button SubmitButton;

    @FXML
    private Button nextButton;

    @FXML
    private Button backButton;

    @FXML
    private TextArea questionText;
    
	private Timeline timeline = new Timeline();
	private int startTimeSec, startTimeMin, startTimeHour;
	public BorderPane timeBorderPane;

    @FXML
    void OnClickedSubmit(ActionEvent event) {

    }
    
    public void setFields(CloneStudentTest test) {
    	CloneQuestion currQuestion = (test.getAnswers())[0].getQuestion();
    	questionText.setText(currQuestion.getQuestionText());
    	answerAText.setText(currQuestion.getAnswer_1());
    	answerBText.setText(currQuestion.getAnswer_2());
    	answerCText.setText(currQuestion.getAnswer_3());
    	answerDText.setText(currQuestion.getAnswer_4());
    	startTimer(test.getTest().getTestDuration()/60, test.getTest().getTestDuration()%60);
    }
    
	public void startTimer(int min, int hour) {
		
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

				timerText
						.setText(String.format("%d hours,%d min, %02d sec", startTimeHour, startTimeMin, startTimeSec));
			}
		});
		timerText.setTextFill(Color.BLACK);
		startTimeSec = 60;
		startTimeMin = min - 1;
		startTimeHour = hour;
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(keyframe);
//		timeline.setOnFinished((e) ->{
//			System.out.println("Sup");
//		});
		timeline.playFromStart();
		timeline.play();
	}

}
