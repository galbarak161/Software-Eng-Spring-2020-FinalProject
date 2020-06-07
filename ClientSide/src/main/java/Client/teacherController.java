package Client;

import java.util.List;

import CloneEntities.CloneCourse;
import CloneEntities.CloneTeacherCourse;
import CloneEntities.CloneTest;
import CommonElements.DataElements.ClientToServerOpcodes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class teacherController extends AbstractController {

	@FXML
	ComboBox<CloneCourse> courseCombo;

	@FXML
	private Button viewButton;
    @FXML TableView<CloneTest> testsList;

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
		ClientMain.addController(this.getClass().toString().split("Client.")[1], this);
		try {
			GetDataFromDB(ClientToServerOpcodes.GetAllCoursesOfTeacher, ClientMain.getUser());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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