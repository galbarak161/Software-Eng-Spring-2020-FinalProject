package Client;

import CloneEntities.CloneExam;
import CloneEntities.CloneStudentTest;
import CloneEntities.CloneTest;
import CommonElements.DataElements.ClientToServerOpcodes;
import javafx.collections.ObservableArray;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ApproveGrades extends AbstractController {

	@FXML
	private Button DisplayTestButton;

	@FXML
	private Button ConfirmButton;

	@FXML
	TableView<CloneStudentTest> testsList;

	@FXML
	private TableColumn<CloneStudentTest, String> FirstNameCol;

	@FXML
	private TableColumn<CloneStudentTest, String> LastNameCol;

	@FXML
	private TableColumn<CloneStudentTest, String> idCol;

	@FXML
	private TableColumn<CloneStudentTest, String> EmailCol;

	@FXML
	private TableColumn<CloneStudentTest, String> GradeCol;

	@FXML
	void OnClickConfirm(ActionEvent event) {

	}

	

	void setFields(CloneTest st) {
		
		FirstNameCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("FirstName"));

		LastNameCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("LastName"));

		idCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("StudentId"));

		EmailCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("Email"));

		GradeCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("Grade"));

		testsList.getColumns().setAll(FirstNameCol, LastNameCol, idCol, EmailCol, GradeCol);
		
		try {
			GetDataFromDB(ClientToServerOpcodes.GetAllStudntTestRelatedToTest, st);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	void OnClickDisplayTest(ActionEvent event) {
		
	}

}
