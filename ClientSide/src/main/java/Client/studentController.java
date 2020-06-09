package Client;

import java.io.IOException;

import CloneEntities.CloneStudentTest;
import UtilClasses.DataElements.ClientToServerOpcodes;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class studentController extends AbstractController {


    @FXML
    private Button displayButton;

    @FXML
    private Button startButton;

    @FXML TableView<CloneStudentTest> testsTable;

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
    
    
	@Override
	public void initialize() {
		

		nameCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("TestName"));
		
		dateCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("TestDate"));
		

		timeCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("TestTime"));
		
		statusCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("StringStatus"));
		
		gradeCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("StringGrade"));
		
		testsTable.getColumns().setAll(nameCol,dateCol , timeCol, statusCol, gradeCol);
		
		
		sendRequest(ClientToServerOpcodes.GetAllStudentTests, ClientMain.getUser());

	}
	
	@FXML
	void OnClickedStartTest(ActionEvent event) {
			switchMainPanel("TestCodeInput.fxml");

	}	
	
	@FXML
    void OnClickedDisplay(ActionEvent event) {
		if (testsTable.getSelectionModel().getSelectedItem() != null) {
			Platform.runLater(()->{
			    Parent root = null;
			    try {
	                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DisplayStudentTest.fxml"));
	                root = (Parent) fxmlLoader.load();
	                showStudentTest q = fxmlLoader.getController();
	              //  q.setFields(testsTable.getSelectionModel().getSelectedItem().getValue());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    Stage stage = new Stage();
			    stage.setTitle("Test Review");
			    stage.setScene(new Scene(root));  
			    stage.show();
			});
		}
    }

}
