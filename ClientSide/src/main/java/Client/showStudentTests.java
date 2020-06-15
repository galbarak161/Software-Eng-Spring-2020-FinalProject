package Client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import CloneEntities.CloneQuestion;
import CloneEntities.CloneQuestionInExam;
import CloneEntities.CloneStudentTest;
import CloneEntities.CloneTest;
import CloneEntities.CloneStudentTest.StudentTestStatus;
import CloneEntities.CloneTest.ExamType;
import CloneEntities.CloneTest.TestStatus;
import UtilClasses.DataElements.ClientToServerOpcodes;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

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
				Label title = new Label(testsList.getSelectionModel().getSelectedItem().getTest().getName());
		        TextArea textArea = new TextArea();
		        textArea.autosize();
		        textArea.setText(testsList.getSelectionModel().getSelectedItem().getMaunalTest());
		        VBox box = new VBox(2, title, textArea);
		        Stage stage = new Stage();
		        stage.setScene(new Scene(box, 400, 400));
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
		for (CloneStudentTest st : toSend) {
			System.out.println(st.getTestName() + " " + st.getGrade());
		}
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
