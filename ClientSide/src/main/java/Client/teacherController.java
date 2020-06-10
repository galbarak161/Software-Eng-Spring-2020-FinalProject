package Client;

import java.io.IOException;
import java.util.List;

import CloneEntities.CloneCourse;
import CloneEntities.CloneStudentTest;
import CloneEntities.CloneTest;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class teacherController extends AbstractController {

	@FXML
	private Button viewButton;

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

	@FXML
	void approveButton(ActionEvent event) {

	}

	@FXML
	void timeRequestButton(ActionEvent event) {

	}
	
    @FXML
    void onClickedView(ActionEvent event) {
		if (testsList.getSelectionModel().getSelectedItem() != null) {
			Platform.runLater(()->{
			    Parent root = null;
			    try {
	                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("showStudentTests.fxml"));
	                root = (Parent) fxmlLoader.load();
	                showStudentTests q = fxmlLoader.getController();
	                q.setFields(testsList.getSelectionModel().getSelectedItem());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    Stage stage = new Stage();
			    stage.setTitle("Students' tests of " + testsList.getSelectionModel().getSelectedItem().getName() + " exam");
			    stage.setScene(new Scene(root));  
			    stage.show();
			});
		}
    }

	@Override
	public void initialize() {
		courseCol.setCellValueFactory(new PropertyValueFactory<CloneTest, String>("CourseName"));

		nameCol.setCellValueFactory(new PropertyValueFactory<CloneTest, String>("Name"));

		dateCol.setCellValueFactory(new PropertyValueFactory<CloneTest, String>("TestDate"));

		timeCol.setCellValueFactory(new PropertyValueFactory<CloneTest, String>("TestTime"));

		codeCol.setCellValueFactory(new PropertyValueFactory<CloneTest, String>("ExecutionCode"));

		statusCol.setCellValueFactory(new PropertyValueFactory<CloneTest, String>("Status"));

		testsList.getColumns().setAll(courseCol, nameCol, dateCol, timeCol, codeCol, statusCol);
		
		sendRequest(ClientToServerOpcodes.GetAllTestsOfTeacher, ClientMain.getUser());
        

	}

}