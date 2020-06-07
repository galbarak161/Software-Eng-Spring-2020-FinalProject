package Client;

import java.util.List;

import CloneEntities.CloneCourse;
import CloneEntities.CloneStudentTest;
import CloneEntities.CloneTeacherCourse;
import CloneEntities.CloneTest;
import CommonElements.DataElements.ClientToServerOpcodes;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class teacherController extends AbstractController {

	@FXML
	ComboBox<CloneCourse> courseCombo;

	@FXML
	private Button viewButton;
	@FXML
	TableView<CloneTest> testsList;

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

	@FXML
	void approveButton(ActionEvent event) {

	}

	@FXML
	void timeRequestButton(ActionEvent event) {

	}

	@Override
	public void initialize() {
		nameCol.setCellValueFactory(new PropertyValueFactory<CloneTest, String>("Name"));
		
		dateCol.setCellValueFactory(new PropertyValueFactory<CloneTest, String>("Date"));

		timeCol.setCellValueFactory(new PropertyValueFactory<CloneTest, String>("Time"));
		
		codeCol.setCellValueFactory(new PropertyValueFactory<CloneTest, String>("ExecutionCode"));
		
		statusCol.setCellValueFactory(new PropertyValueFactory<CloneTest, String>("Status"));
		
		testsList.getColumns().setAll(nameCol,dateCol , timeCol, codeCol, statusCol);
	
		
		try {
			GetDataFromDB(ClientToServerOpcodes.GetAllCoursesOfTeacher, ClientMain.getUser());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void setCourses(ObservableList<CloneCourse> c) {
		courseCombo.setItems(c);
	}

	@FXML
	void OnClickedCourse(ActionEvent event) {
		try {
			GetDataFromDB(ClientToServerOpcodes.GetAllTestsOfTeacherInCourse,new CloneTeacherCourse(ClientMain.getUser(), courseCombo.getValue()) );
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}