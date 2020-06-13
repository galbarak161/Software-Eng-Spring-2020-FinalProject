package Client;

import java.io.IOException;

import CloneEntities.CloneQuestion;
import CloneEntities.CloneQuestionInExam;
import CloneEntities.CloneStudentTest;
import CloneEntities.CloneTest;
import CloneEntities.CloneStudentTest.StudentTestStatus;
import CloneEntities.CloneTest.TestStatus;
import UtilClasses.DataElements.ClientToServerOpcodes;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
	private Label nameLabel;

	@FXML
	private Label titleLabel;

	private CloneTest thisTest;

	@FXML
	void showTest(ActionEvent event) {
		if (testsList.getSelectionModel().getSelectedItem() != null) {
			Platform.runLater(() -> {
				Parent root = null;
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("showStudentTest.fxml"));
					root = (Parent) fxmlLoader.load();
					showStudentTest q = fxmlLoader.getController();
					q.setFields(testsList.getSelectionModel().getSelectedItem());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Stage stage = new Stage();
				stage.setTitle("Test Review");
				stage.setScene(new Scene(root));
				stage.show();
			});
		} else
			popError("Error", "Please choose a test");
	}

	public void initialize() {
		nameCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("StudentName"));

		idCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("StudentID"));

		emailCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("StudentEmail"));

		gradeCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("StudentGrade"));

		testsList.getColumns().setAll(nameCol, idCol, emailCol, gradeCol);
	}

	public void setFields(CloneTest test, TestStatus s) {
		try {
			GetDataFromDB(ClientToServerOpcodes.GetAllStudntTestRelatedToTest, test);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (s == TestStatus.Done) {
			titleLabel.setText("View Grades");
			approveButton.setVisible(false);
			testsList.resize(testsList.getWidth(), testsList.getHeight() + 200);
			return;
		}
		nameLabel.setText(test.getName());
		thisTest = test;
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
	}

	@FXML
	void onClickedApprove(ActionEvent event) {
		try {
			GetDataFromDB(ClientToServerOpcodes.TeacherUpdateGrade, testsList.getItems());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
