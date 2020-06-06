package Client;

import java.util.List;

import CloneEntities.CloneCourse;
import CloneEntities.CloneTeacherCourse;
import CloneEntities.CloneTest;
import CommonElements.DataElements.ClientToServerOpcodes;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.util.Callback;

public class teacherController extends AbstractController {

	@FXML
	ComboBox<CloneCourse> courseCombo;

	@FXML
	private Button viewButton;

	@FXML
	TreeTableView<CloneTest> testsList;

	@FXML
	static TreeTableColumn<CloneTest, String> nameCol;

	@FXML
	static TreeTableColumn<CloneTest, String> dateCol;

	@FXML
	static TreeTableColumn<CloneTest, String> timeCol;

	@FXML
	static TreeTableColumn<CloneTest, String> codeCol;

	@FXML
	static TreeTableColumn<CloneTest, String> statusCol;

	@FXML
	void approveButton(ActionEvent event) {

	}

	@FXML
	void timeRequestButton(ActionEvent event) {

	}

	@Override
	public void initialize() {
		ClientMain.addController(this.getClass().toString().split("Client.")[1], this);
		try {
			GetDataFromDB(ClientToServerOpcodes.GetAllCoursesOfTeacher, ClientMain.getUser());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		testsList = new TreeTableView<CloneTest>();

		nameCol = new TreeTableColumn<>("Test Name");
		dateCol = new TreeTableColumn<>("Date");
		timeCol = new TreeTableColumn<>("Time");
		codeCol = new TreeTableColumn<>("Test Code");
		statusCol = new TreeTableColumn<>("Status");
	}

	public static void PraseTable(List<CloneTest> testsOfTeacher) {

		for (CloneTest test : testsOfTeacher) {
			nameCol.setCellValueFactory(new TreeItemPropertyValueFactory(test.getExamToExecute().getExamName()));
			dateCol.setCellValueFactory(new TreeItemPropertyValueFactory(test.getTestDate().toString()));
			timeCol.setCellValueFactory(new TreeItemPropertyValueFactory(test.getTestTime().toString()));
			codeCol.setCellValueFactory(new TreeItemPropertyValueFactory(test.getExecutionCode()));
			statusCol.setCellValueFactory(new TreeItemPropertyValueFactory("add get status"));

		}
	}

	@FXML
	void OnClickedCourse(ActionEvent event) {
		try {
			GetDataFromDB(ClientToServerOpcodes.GetAllExamsOfTeacherInCourse,new CloneTeacherCourse(ClientMain.getUser(), courseCombo.getValue()) );
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}