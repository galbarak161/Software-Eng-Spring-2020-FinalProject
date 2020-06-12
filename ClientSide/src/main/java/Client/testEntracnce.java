package Client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import CloneEntities.CloneStudentTest;
import CloneEntities.CloneTest.ExamType;
import UtilClasses.DataElements.ClientToServerOpcodes;
import UtilClasses.StudentStartTest;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;
import javafx.stage.Stage;

public class testEntracnce extends AbstractController {

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
	private AnchorPane mainAnchor;
	
	private CloneStudentTest currTest;

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
//		timeline.setOnFinished((e) -> {
//			System.out.println("Sup");
//		});
		timeline.playFromStart();
		timeline.play();

	}

	/**
	 * After user fills in the test code, we send it to the server via
	 * "StudentStartTest" object The server checks whether the code it valid and the
	 * test is ongoing
	 * 
	 * @param event
	 */

	@FXML
	void OnClickedStart(ActionEvent event) {
		if (!codeText.getText().isEmpty()) {
			try {
				GetDataFromDB(ClientToServerOpcodes.GetStudentTestRelatedToStudentInExam,
						new StudentStartTest(ClientMain.getUser().getId(), codeText.getText()));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else
			popError("Error", "Please enter test code");
	}

	void checkTestType(Object test) {
		if (test instanceof CloneStudentTest) {
			CloneStudentTest currTest = (CloneStudentTest) test;
			codeLabel.setVisible(false);
			codeText.setVisible(false);
			startButton.setVisible(false);

			if (currTest.getTest().getType() == ExamType.Automated) {
				IDText.setVisible(true);
				IDLabel.setVisible(true);
				enterTestButton.setVisible(true);

			} else {
				downloadButton.setVisible(true);
				uploadButton.setVisible(true);
				submitButton.setVisible(true);
				fileField.setVisible(true);
			}
			currTest = (CloneStudentTest) test;
		} else
			popError("Error", "Your code is invalid or your test didn't start yet");

	}

	@FXML
	void onClickedEnter(ActionEvent event) {
		if (String.valueOf(ClientMain.getUser().getId()) != IDText.getText()) {
			Platform.runLater(() -> {
				try {
					autoTestController con = new autoTestController();
					con.setFields(currTest);
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("autoTestController.fxml"));
					fxmlLoader.setRoot(mainAnchor);
					fxmlLoader.setController(con);
					fxmlLoader.load();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} else
			popError("Error", "Wrong ID, please try again");
	}

	@FXML
	void onClickedDownload(ActionEvent event) throws IOException, InterruptedException {
		createWord();
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
