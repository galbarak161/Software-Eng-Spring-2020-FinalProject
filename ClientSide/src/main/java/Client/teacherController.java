package Client;

import java.io.IOException;
import java.util.List;

import CloneEntities.CloneCourse;
import CloneEntities.CloneStudentTest;
import CloneEntities.CloneTest;
import CloneEntities.CloneTest.ExamType;
import CloneEntities.CloneTest.TestStatus;
import UtilClasses.TeacherCourse;
import UtilClasses.DataElements.ClientToServerOpcodes;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class teacherController extends AbstractController {

	@FXML
	private Button testButton;

	@FXML
	TableView<CloneTest> testsList;

	@FXML
	private TableColumn<CloneTest, String> courseCol;

	@FXML
	private TableColumn<CloneTest, String> nameCol;

	@FXML
	private TableColumn<CloneTest, String> dateCol;

	@FXML
	private TableColumn<CloneTest, String> timeCol;

	@FXML
	private TableColumn<CloneTest, String> codeCol;

	@FXML
	private TableColumn<CloneTest, String> statusCol;
	
	@Override
	public void initialize() {
		courseCol.setCellValueFactory(new PropertyValueFactory<CloneTest, String>("CourseName"));

		nameCol.setCellValueFactory(new PropertyValueFactory<CloneTest, String>("Name"));

		dateCol.setCellValueFactory(new PropertyValueFactory<CloneTest, String>("TestDateInFormat"));

		timeCol.setCellValueFactory(new PropertyValueFactory<CloneTest, String>("TestTime"));

		codeCol.setCellValueFactory(new PropertyValueFactory<CloneTest, String>("ExecutionCode"));

		statusCol.setCellValueFactory(new PropertyValueFactory<CloneTest, String>("Status"));

		testsList.getColumns().setAll(courseCol, nameCol, dateCol, timeCol, codeCol, statusCol);

		sendRequest(ClientToServerOpcodes.GetAllTestsOfTeacher, ClientMain.getUser());

	}

	@FXML
	void timeRequestButton(ActionEvent event) throws Exception {
		if (testsList.getSelectionModel().getSelectedItem() != null) {
			if (testsList.getSelectionModel().getSelectedItem().getStatusEnum() == TestStatus.Ongoing) {
				newWindow(testsList, new requestController(), "requestController.fxml",
						"Time Extention Request Form");
			} else {
				popError("Error", "This test is not ongoing, you cannot send a new request");
				return;
			}
		} else
			popError("Error", "Please choose a test");
	}

	@FXML
	void onClickedTest(ActionEvent event) throws Exception {
		TestStatus status;
		if (testsList.getSelectionModel().getSelectedItem() != null) {
			if (testsList.getSelectionModel().getSelectedItem().getStatusEnum() == TestStatus.Done) {
				status = TestStatus.Done;
			} else if (testsList.getSelectionModel().getSelectedItem().getStatusEnum() == TestStatus.PendingApproval) {
				status = TestStatus.PendingApproval;
			} else {
				popError("Error", "You can watch students' tests only when they're PendingApproval or Done");
				return;
			}
			newWindow(testsList, new showStudentTests(), "showStudentTests.fxml",
					"Students' tests of " + testsList.getSelectionModel().getSelectedItem().getName() + " exam");
		} else 
			popError("Error", "Please choose a test");
	}
	
	void updateTable(ObservableList<CloneTest> tests) {
		int keepSelection = testsList.getSelectionModel().getSelectedIndex();
		testsList.setItems(tests);
		testsList.getSelectionModel().select(keepSelection);
	}

}