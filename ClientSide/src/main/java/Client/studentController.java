package Client;

import java.io.IOException;
import java.util.List;

import CloneEntities.CloneStudentTest;
import CloneEntities.CloneTest;
import CommonElements.DataElements.ClientToServerOpcodes;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
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
    private TableColumn<CloneStudentTest, String> codeCol;

    @FXML
    private TableColumn<CloneStudentTest, String> statusCol;

    @FXML
    private TableColumn<CloneStudentTest, String> gradeCol;
    
    
	@Override
	public void initialize() {
		

		nameCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("TestName"));
		
		dateCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("Date"));
		

		timeCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("Time"));
		
		codeCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("ExecutionCode"));
		
		statusCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("Status"));
		
		gradeCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("Grade"));
		
		testsTable.getColumns().setAll(nameCol,dateCol , timeCol, codeCol, statusCol, gradeCol);
		
		try {
			GetDataFromDB(ClientToServerOpcodes.GetAllStudentTests, ClientMain.getUser());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		

	}

	void switchMainPanel(String Sfxml) {
		Platform.runLater(() -> {
			((mainController) ClientService.getController("mainController")).setMainPanel(Sfxml);
		});
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
	                DisplayStudentTest q = fxmlLoader.getController();
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
