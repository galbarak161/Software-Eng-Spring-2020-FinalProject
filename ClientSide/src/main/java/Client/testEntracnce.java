package Client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import CloneEntities.CloneQuestionInExam;
import CloneEntities.CloneStudentTest;
import CloneEntities.CloneStudentTest.StudentTestStatus;
import CloneEntities.CloneTest.ExamType;
import UtilClasses.DataElements.ClientToServerOpcodes;
import UtilClasses.StudentStartTest;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class testEntracnce extends AbstractTest {

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

	private String uploadedAnswer = null;

	private File uploadedFile = null;

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
				GetDataFromDB(ClientToServerOpcodes.StudentStartsTest,
						new StudentStartTest(ClientMain.getUser().getId(), codeText.getText()));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else
			popError("Error", "Please enter test code");
	}

	void checkTestType(List<Object> test) {
		finishedTest = (CloneStudentTest) test.get(0);
		currQuestions = ((List<CloneQuestionInExam>) test.get(1));
		studentController.testStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent event) {
				try {
					GetDataFromDB(ClientToServerOpcodes.StudentFinishedTest, finishedTest);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
		        //event.consume();
		    }
		});
		if (finishedTest.getStatus() == StudentTestStatus.Scheduled) {
			popError("Error", "Test didn't start yet");
			return;
		} else if (finishedTest.getStatus() == StudentTestStatus.Done ||
				finishedTest.getStatus() == StudentTestStatus.WaitingForResult) {
			popError("Error", "Test is over");
			return;
		}
			
		codeLabel.setVisible(false);
		codeText.setVisible(false);
		startButton.setVisible(false);

		if (finishedTest.getTest().getType() == ExamType.Automated) {
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
		if (String.valueOf(ClientMain.getUser().getIdentifyNumber()) != IDText.getText()) {
			Platform.runLater(() -> {
				try {
					mainAnchor.getChildren()
							.setAll((Node) FXMLLoader.load(getClass().getResource("autoTestController.fxml")));
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
		startTimer(timerText);
		sendRequest(ClientToServerOpcodes.GetAnswerToTimeExtensionRequest, finishedTest.getTest().getId());
	}

	@FXML
	void onClickedSubmit(ActionEvent event) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Test Submission");
		alert.setContentText("Are you sure you want to submit the test?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get().getButtonData() == ButtonData.OK_DONE) {
			timeline.stop();
			newHour -= startTimeHour;
			newMinute -= startTimeMin;
			finishTest();
			return;
		}
		alert.close();
	}

	@FXML
	void onClickedUpload(ActionEvent event) throws IOException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select Test File");
		// fileChooser.setInitialDirectory(new File("X:\\testdir\\two"));
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Word Files", "*.docx"));
		Stage stage = new Stage();
		List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stage);
		if (selectedFiles != null) {
			uploadedFile = selectedFiles.get(0);
			uploadedAnswer = new String(Files.readAllBytes(Paths.get(selectedFiles.get(0).toURI())),
					StandardCharsets.UTF_8);
			fileField.setText(selectedFiles.get(0).getPath());
		}

	}

	@Override
	protected void finishTest() {
		finishedTest.setactualTestDurationInMinutes((newHour * 60) + newMinute);
		if (uploadedAnswer == null || uploadedFile == null)
			uploadedAnswer = "No uploaded file";
		finishedTest.setUploadedFile(uploadedFile);
		try {
			GetDataFromDB(ClientToServerOpcodes.StudentFinishedTest, finishedTest);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		showMsg("Test's Time is up", "Test is over and will be send to review (if you didn't upload a file please notify the teacher)");
		Stage stage;
		stage = (Stage) timerText.getScene().getWindow();
		stage.close();
	}

	public void createWord() throws IOException {

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
			paragraph.setAlignment(ParagraphAlignment.LEFT);
			XWPFRun run = paragraph.createRun();
			/////////////////////////// make title bigger /////////////////////////////
			run.setText(finishedTest.getTestName());
			int i = 1;
			for (CloneQuestionInExam q : currQuestions) {
				paragraph = document.createParagraph();
				paragraph.setAlignment(ParagraphAlignment.LEFT);
				run = paragraph.createRun();
				run.setText("Question " + String.valueOf(i) + ": " + q.getQuestion().getQuestionText());
				run.addBreak();
				run = paragraph.createRun();
				run.setText("a. " + q.getQuestion().getAnswer_1());
				run.addBreak();
				run = paragraph.createRun();
				run.setText("b. " + q.getQuestion().getAnswer_2());
				run.addBreak();
				run = paragraph.createRun();
				run.setText("c. " + q.getQuestion().getAnswer_3());
				run.addBreak();
				run = paragraph.createRun();
				run.setText("d. " + q.getQuestion().getAnswer_4());
				run.addBreak();
				run = paragraph.createRun();
				run.setText("Answer: _____________________");
				run.addBreak();
				run.addBreak();
				i++;
			}

			document.write(out);

			// Close document
			out.close();
		}
	}

}
