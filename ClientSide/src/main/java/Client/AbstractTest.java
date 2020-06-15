package Client;

import java.time.LocalTime;
import java.util.List;

import CloneEntities.CloneQuestionInExam;
import CloneEntities.CloneStudentTest;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AbstractTest extends AbstractController {

	protected Timeline timeline = new Timeline();
	protected int startTimeSec, startTimeMin, startTimeHour, newHour, newMinute;
	protected boolean hasBeenExtened = false;
	static CloneStudentTest finishedTest;
	static List<CloneQuestionInExam> currQuestions;

	public void startTimer(Label timerText) {
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
					setStudentAnswer();
					showMsg("Test's Time is up", "Test is over and will be send to review");
					finishTest();
					return;

				}

				timerText.setText(
						String.format("%d hours, %d min, %02d sec", startTimeHour, startTimeMin, startTimeSec));
			}
		});
		timerText.setTextFill(Color.BLACK);
		startTimeSec = 60;
		startTimeMin = newMinute - 1;
		startTimeHour = newHour;
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(keyframe);
		timeline.play();
	}

	public void updateTimer(int addedTime) {
		if (hasBeenExtened)
			return;
		startTimeHour += addedTime / 60;
		if ((startTimeMin + addedTime % 60) >= 60) {
			startTimeMin = (startTimeMin + addedTime) % 60;
			startTimeHour++;
		} else
			startTimeMin += addedTime % 60;
		showMsg("Time extension approved", addedTime + " minutes has been added to the test");
		hasBeenExtened = true;
	}

	protected void setStudentAnswer() {

	}

	protected void finishTest() {

	}
}
