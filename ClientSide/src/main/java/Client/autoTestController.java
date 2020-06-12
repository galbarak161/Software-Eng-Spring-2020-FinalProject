package Client;

import java.time.LocalTime;

import CloneEntities.CloneAnswerToQuestion;
import CloneEntities.CloneQuestion;
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

public class autoTestController extends AbstractController{

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
    
    @FXML
    private TextArea commentsText;
    
    private int hour, min;
    
    private int newHour;
    
    private int newMinute;
    
    private ToggleGroup radioGroup;
    
    private CloneStudentTest finishedTest;
    
    private int currQuestionNum = 0;
    
	private Timeline timeline = new Timeline();
	private int startTimeSec, startTimeMin, startTimeHour;
	public BorderPane timeBorderPane;
    
	
	public void initialize() {
		finishedTest = testEntracnce.studTest;
		setFields();
	}
    public void setFields() {
    	backButton.setVisible(false);
    	if (finishedTest.getAnswers().length == 1)
    		nextButton.setVisible(false);
    	CloneAnswerToQuestion temp = finishedTest.getAnswerAtIndex(0);
    	questionText.setText(temp.getQuestion().getQuestionText());
    	answerAText.setText(temp.getQuestion().getAnswer_1());
    	answerBText.setText(temp.getQuestion().getAnswer_2());
    	answerCText.setText(temp.getQuestion().getAnswer_3());
    	answerDText.setText(temp.getQuestion().getAnswer_4());
    //	changeCurrQuestion(finishedTest.getAnswerAtIndex(currQuestionNum));
    	//questionText.setText(finishedTest.getAnswerAtIndex(0));//TODO
    	commentsText.setText(finishedTest.getTest().getExamToExecute().getStudentComments());
    	questionNumberLabel.setText("1");
    	allQuestionsNumberLabel.setText(String.valueOf(finishedTest.getAnswersLength()));
    	
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
		
		newMinute = Math.abs(durMinute - Math.abs(LocalTime.now().getMinute() - originalMinute));
		
		hour = newHour;
		
		min = newMinute;
    	
    	startTimer();
    }
    
    public void changeCurrQuestion(CloneAnswerToQuestion currAnsQuestion) {
    	CloneQuestion currQuestion = currAnsQuestion.getQuestion();
    	questionText.setText(currQuestion.getQuestionText());
    	answerAText.setText(currQuestion.getAnswer_1());
    	answerBText.setText(currQuestion.getAnswer_2());
    	answerCText.setText(currQuestion.getAnswer_3());
    	answerDText.setText(currQuestion.getAnswer_4());
    	if(currAnsQuestion.getStudentAnswer() != 0) {
    		switch(currAnsQuestion.getStudentAnswer()) {
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
    		}
    	}
    		
    }
    
    public void setStudentAnswer() {
		RadioButton chk = (RadioButton) radioGroup.getSelectedToggle();
		switch (chk.getText()) {
		case "a.":
			finishedTest.setAnswerAtIndex(currQuestionNum, 1);
			break;
		case "b.":
			finishedTest.setAnswerAtIndex(currQuestionNum, 2);
			break;
		case "c.":
			finishedTest.setAnswerAtIndex(currQuestionNum, 3);
			break;
		case "d.":
			finishedTest.setAnswerAtIndex(currQuestionNum, 4);
			break;
		}
    }
    
    @FXML
    void onClickedNext(ActionEvent event) {
    	currQuestionNum++;
    	setStudentAnswer();
    	backButton.setVisible(true);
    	changeCurrQuestion(finishedTest.getAnswerAtIndex(currQuestionNum));
    	if (finishedTest.getAnswersLength()== currQuestionNum+1)
    		nextButton.setVisible(false);
    	questionNumberLabel.setText(String.valueOf(currQuestionNum + 1));
    }
    
    @FXML
    void onClickedBack(ActionEvent event) {
    	currQuestionNum--;
    	setStudentAnswer();
    	nextButton.setVisible(true);
    	changeCurrQuestion(finishedTest.getAnswerAtIndex(currQuestionNum));
    	if (currQuestionNum-1 == 0)
    		backButton.setVisible(false);
    	questionNumberLabel.setText(String.valueOf(currQuestionNum + 1));
    }
    
    @FXML
    void OnClickedSubmit(ActionEvent event) {
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    	alert.setTitle("Test Submission");
    	alert.setContentText("Are you sure you want to submit the test?");
    	ButtonType okButton = new ButtonType("Yes", ButtonData.YES);
    	ButtonType noButton = new ButtonType("No", ButtonData.NO);
    	alert.getButtonTypes().setAll(okButton, noButton);
    	alert.showAndWait().ifPresent(type -> {
    	        if (type == ButtonType.OK) {
    	        	newHour -= startTimeHour;
    	        	newMinute -= startTimeMin;
    	        	finishedTest.setactualTestDurationInMinutes((newHour*60)+newMinute);
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
    	});
    }
    
    public void updateTimer(int addedTime) {
    	hour += addedTime/60;
    	if ((min + addedTime%60) >= 60) {
    		min = (min + addedTime) % 60;
    		hour++;
    	} else 
    		min +=addedTime%60;
    	
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

				timerText.setText(String.format("%d hours, %d min, %02d sec", startTimeHour, startTimeMin, startTimeSec));
			}
		});
		timerText.setTextFill(Color.BLACK);
		startTimeSec = 60;
		startTimeMin = min - 1;
		startTimeHour = hour;
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(keyframe);
		timeline.setOnFinished((e) ->{
			finishedTest.setactualTestDurationInMinutes((newHour*60)+newMinute);
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
