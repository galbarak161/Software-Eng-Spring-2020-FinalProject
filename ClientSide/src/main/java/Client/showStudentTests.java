package Client;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.POITextExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import CloneEntities.CloneStudentTest;
import CloneEntities.CloneTest;
import CloneEntities.CloneTest.ExamType;
import CloneEntities.CloneTest.TestStatus;
import UtilClasses.DataElements.ClientToServerOpcodes;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

public class showStudentTests extends AbstractController {

	@FXML
	private Button showTest;

	@FXML
	private Button approveButton;

	@FXML
	TableView<CloneStudentTest> testsList;

	@FXML
	private TableColumn<CloneStudentTest, String> nameCol;

	@FXML
	private TableColumn<CloneStudentTest, String> idCol;

	@FXML
	private TableColumn<CloneStudentTest, String> emailCol;

	@FXML
	private TableColumn<CloneStudentTest, String> gradeCol;
	
    @FXML
    private TableColumn<CloneStudentTest, String> statusCol;

	@FXML
	private Label nameLabel;

	@FXML
	private Label titleLabel;

	private CloneTest thisTest;

	@FXML
	void showTest(ActionEvent event) throws Exception {
		if (testsList.getSelectionModel().getSelectedItem().getDone().equals("Not Done")) {
			popError("Error", "Cannot show an undone test");
			return;
		}
		
		if (testsList.getSelectionModel().getSelectedItem() != null) {
			if (testsList.getSelectionModel().getSelectedItem().getTest().getType() == ExamType.Manual) {
				InputStream fis = new FileInputStream(testsList.getSelectionModel().getSelectedItem().getUploadedFile());
				POITextExtractor extractor;
				XWPFDocument doc = new XWPFDocument(fis);
				extractor = new XWPFWordExtractor(doc);
				String extractedText = extractor.getText();
		        TextArea textArea = new TextArea();
		        textArea.autosize();
		        textArea.setText(extractedText);
		        Stage stage = new Stage();
		        stage.setTitle("Test: " + testsList.getSelectionModel().getSelectedItem().getTest().getName());
		        stage.setScene(new Scene(textArea, 400, 650));
		        stage.showAndWait();
		        return;
		    }
			newWindow(testsList, new showStudentTest(), "showStudentTest.fxml",
					"Test Review");
		} else
			popError("Error", "Please choose a test");
	}

	public void initialize() {
		nameCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("StudentName"));

		idCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("StudentID"));

		emailCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("StudentEmail"));
		
		gradeCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("StudentGrade"));

		statusCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("Done"));
		
		gradeCol.setCellFactory(TextFieldTableCell.forTableColumn());
		gradeCol.setOnEditCommit(new EventHandler<CellEditEvent<CloneStudentTest, String>>() {
			public void handle(CellEditEvent<CloneStudentTest, String> t) {
				if (t.getNewValue().isEmpty()) {
					((CloneStudentTest) t.getTableView().getItems().get(t.getTablePosition().getRow())).setGrade(0);
				} else {
					((CloneStudentTest) t.getTableView().getItems().get(t.getTablePosition().getRow()))
							.setGrade(Integer.valueOf(t.getNewValue()));
				}
			}
		});
		
		testsList.getColumns().setAll(nameCol, idCol, emailCol,statusCol, gradeCol);
		testsList.setEditable(true);
	}
	
	@Override
	protected <T> void setFields(T selectedItem) {
		setFields((CloneTest)selectedItem, (TestStatus) ((CloneTest)selectedItem).getStatusEnum());
		
	}

	public void setFields(CloneTest test, TestStatus s) {
		try {
			GetDataFromDB(ClientToServerOpcodes.GetAllStudntTestRelatedToTest, test);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		nameLabel.setText(test.getName());
		if (s == TestStatus.Done) {
			titleLabel.setText("View Grades");
			approveButton.setVisible(false);
			testsList.resize(testsList.getWidth(), testsList.getHeight() + 200);
			testsList.setEditable(false);
			return;
		}
		thisTest = test;
	}

	@FXML
	void onClickedApprove(ActionEvent event) {
		List<CloneStudentTest> toSend = new ArrayList<>();
		for (CloneStudentTest st : testsList.getItems())
			toSend.add(st);
		try {
			GetDataFromDB(ClientToServerOpcodes.TeacherUpdateGrade, toSend);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	void showMsg(String title, String content) {
		Platform.runLater(() -> {
			Alert info = new Alert(Alert.AlertType.INFORMATION);
			info.setTitle(title);
			info.setHeaderText(content);
			info.showAndWait();
	        Stage stage;
	        stage=(Stage) showTest.getScene().getWindow();
	        stage.close();
		});
	}

}
