package Client;

import CloneEntities.CloneQuestion;
import CloneEntities.CloneStudentTest;
import CloneEntities.CloneTest;
import UtilClasses.DataElements.ClientToServerOpcodes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class showStudentTests extends AbstractController{

    @FXML
    private Button showTest;

    @FXML TableView<CloneStudentTest> testsList;

    @FXML
    private TableColumn<CloneStudentTest, String> nameCol;

    @FXML
    private TableColumn<CloneStudentTest, String> idCol;

    @FXML
    private TableColumn<CloneStudentTest, String> emailCol;
    
    @FXML
    private TableColumn<CloneStudentTest, String> gradeCol;

    @FXML
    void showTest(ActionEvent event) {
    	
    }
    
    public void initialize() {
    	nameCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("StudentName"));

    	idCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("StudentID"));
    	
    	emailCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("StudentEmail"));
    	
    	gradeCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("StudentGrade"));


    	testsList.getColumns().setAll(nameCol, idCol,emailCol,gradeCol);
    }
    
    public void setFields(CloneTest test) {
		try {
			GetDataFromDB(ClientToServerOpcodes.GetAllStudntTestRelatedToTest, test);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
