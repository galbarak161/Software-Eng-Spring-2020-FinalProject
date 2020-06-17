package Client;

import java.io.IOException;

import CloneEntities.CloneStudentTest;
import CloneEntities.CloneStudentTest.StudentTestStatus;
import UtilClasses.DataElements.ClientToServerOpcodes;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class studentController extends AbstractController {

	@FXML
	private Button displayButton;

	@FXML
	private Button startButton;

	@FXML
	TableView<CloneStudentTest> testsTable;

	@FXML
	private TableColumn<CloneStudentTest, String> nameCol;

	@FXML
	private TableColumn<CloneStudentTest, String> dateCol;

	@FXML
	private TableColumn<CloneStudentTest, String> timeCol;

	@FXML
	private TableColumn<CloneStudentTest, String> statusCol;

	@FXML
	private TableColumn<CloneStudentTest, String> gradeCol;

	public static Stage testStage = new Stage();

	@Override
	public void initialize() {

		nameCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("TestName"));

		dateCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("TestDate"));

		timeCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("TestTime"));

		statusCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("StringStatus"));

		gradeCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("StringGrade"));

		testsTable.getColumns().setAll(nameCol, dateCol, timeCol, statusCol, gradeCol);
		
		if (!(testStage.getModality() == Modality.APPLICATION_MODAL))
			testStage.initModality(Modality.APPLICATION_MODAL);
		

		sendRequest(ClientToServerOpcodes.GetAllStudentTests, ClientMain.getUser());

	}

	@FXML
	void OnClickedStartTest(ActionEvent event) {
		Parent root = null;
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("testEntrance.fxml"));
			root = (Parent) fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		stopTimer();
		testStage.setTitle("Test Start");
		testStage.setScene(new Scene(root));
		testStage.showAndWait();
		sendRequest(ClientToServerOpcodes.GetAllStudentTests, ClientMain.getUser());
	}

	@FXML
	void OnClickedDisplay(ActionEvent event) throws Exception {
		if (testsTable.getSelectionModel().getSelectedItem() != null
				&& testsTable.getSelectionModel().getSelectedItem().getStatus() == StudentTestStatus.Done) {
			newWindow(testsTable, new showStudentTest(), "showStudentTest.fxml",
					"Test Review");
		} else
			popError("Error", "Cannot display the test, either you didn't choose any or it hasn't been done yet");
	}
	
	void updateTable(ObservableList<CloneStudentTest> tests) {
		int keepSelection = testsTable.getSelectionModel().getSelectedIndex();
		testsTable.setItems(tests);
		testsTable.getSelectionModel().select(keepSelection);
	}

}
