package Client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import CloneEntities.CloneStudentTest;
import CloneEntities.CloneTest.ExamType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
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
	void onClickedDownload(ActionEvent event) throws IOException {
		createWord();
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
