package Client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import CloneEntities.CloneStudentTest;
import CloneEntities.CloneTest.ExamType;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;
import javafx.stage.Stage;

public class TestCodeInput {

	@FXML
	private Button startButton;

	@FXML
	private Button uploadButton;

	@FXML
	private Button submitButton;

	@FXML
	private Button enterTestButton;

	@FXML
	private Button downloadButton;

	@FXML
	private TextField IDText;

	@FXML
	private TextField codeText;

	@FXML
	private TextField fileField;

	@FXML
	private Label IDLabel;

	@FXML
	private Label codeLabel;

	@FXML
	private Label timerText;

	@FXML
	private Pane timerPane;

	private Timeline timeline = new Timeline();
	private int min = 1, hour = 2;
	private int startTimeSec, startTimeMin, startTimeHour;
	public BorderPane timeBorderPane;

	public void startTimer() {
		KeyFrame keyframe = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				startTimeSec--;
				boolean isSecondsZero = startTimeSec == 0;
				boolean isMinutesZero = startTimeMin == 0;
				boolean timeToChangeBackground = startTimeSec == 0 && startTimeMin == 0 && startTimeHour == 0;

				if (isSecondsZero) {
					if(isMinutesZero) {
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

				timerText.setText(String.format("%d hours,%d min, %02d sec", startTimeHour, startTimeMin, startTimeSec));
			}
		});
		timerText.setTextFill(Color.BLACK);
		startTimeSec = 60;
		startTimeMin = min - 1;
		startTimeHour = hour;
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(keyframe);
		timeline.playFromStart();
		timeline.play(); 

	}

	@FXML
	void OnClickedStart(ActionEvent event) {
		if (!codeText.getText().isEmpty()) {
			try {
				App.changeStage("TestController", "Test");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	void checkTestType(CloneStudentTest test) {
		codeLabel.setVisible(false);
		codeText.setVisible(false);
		startButton.setVisible(false);

		if (test.getTest().getType() == ExamType.Automated) {
			IDText.setVisible(true);
			IDLabel.setVisible(true);
			enterTestButton.setVisible(true);

		} else {
			downloadButton.setVisible(true);
			uploadButton.setVisible(true);
			submitButton.setVisible(true);
			fileField.setVisible(true);
		}
	}

	@FXML
	void onClickedEnter(ActionEvent event) {

	}

	@FXML
	void onClickedDownload(ActionEvent event) throws IOException, InterruptedException {
		// createWord();
		startTimer();
	}

	@FXML
	void onClickedSubmit(ActionEvent event) {

	}

	@FXML
	void onClickedUpload(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select Test File");
		// fileChooser.setInitialDirectory(new File("X:\\testdir\\two"));
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Word Files", "*.docx"));
		Stage stage = new Stage();
		List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stage);

		if (selectedFiles != null)
			fileField.setText(selectedFiles.get(0).getPath());
	}

	public void createWord() throws IOException {
		String line = "Sup";
		// Blank Document
		XWPFDocument document = new XWPFDocument();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Test File");
		// fileChooser.setInitialDirectory(new File("X:\\testdir\\two"));
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Word Files", "*.docx"));
		Stage stage = new Stage();
		File selectedFiles = fileChooser.showSaveDialog(stage);

		if (selectedFiles != null) {
			// Write the Document in file system
			FileOutputStream out = new FileOutputStream(selectedFiles);
			// create Paragraph
			XWPFParagraph paragraph = document.createParagraph();
			XWPFRun run = paragraph.createRun();
			run.setText("VK Number (Parameter): " + line + " here you type your text...\n");
			document.write(out);

			// Close document
			out.close();
			System.out.println("createdWord" + "_" + line + ".docx" + " written successfully");
		}
	}

}
