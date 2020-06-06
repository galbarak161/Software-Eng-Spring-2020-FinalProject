package Client;

import com.jfoenix.controls.JFXTreeTableView;

import CloneEntities.CloneCourse;
import CommonElements.DataElements.ClientToServerOpcodes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TreeTableColumn;

public class teacherController extends AbstractController {

    @FXML ComboBox<CloneCourse> courseCombo;

    @FXML
    private Button viewButton;

    @FXML
    private JFXTreeTableView<?> testsTable;

    @FXML
    private TreeTableColumn<?, ?> nameCol;

    @FXML
    private TreeTableColumn<?, ?> dateCol;

    @FXML
    private TreeTableColumn<?, ?> timeCol;

    @FXML
    private TreeTableColumn<?, ?> codeCol;

    @FXML
    private TreeTableColumn<?, ?> statusCol;
    
	public void initialize() {
		ClientMain.addController(this.getClass().toString().split("Client.")[1],this);
		try {
			GetDataFromDB(ClientToServerOpcodes.GetAllCoursesOfTeacher, ClientMain.getUser());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

    @FXML
    void approveButton(ActionEvent event) {

    }

    @FXML
    void timeRequestButton(ActionEvent event) {

    }

}