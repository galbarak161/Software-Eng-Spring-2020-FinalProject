package Client;

import CloneEntities.CloneQuestion;
import CloneEntities.CloneStudentTest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class showStudentTests {

    @FXML
    private Button showTest;

    @FXML
    private TableView<CloneStudentTest> testsList;

    @FXML
    private TableColumn<CloneStudentTest, String> nameCol;

    @FXML
    private TableColumn<CloneStudentTest, String> idCol;

    @FXML
    private TableColumn<CloneStudentTest, String> emailCol;

    @FXML
    private TableColumn<CloneStudentTest, String> statusCol;

    @FXML
    private TableColumn<CloneStudentTest, String> gradeCol;

    @FXML
    void showTest(ActionEvent event) {
    	
    }
    
    public void initialize() {
//    	nameCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("Subject"));
//
//    	idCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("Grade"));
//    	
//    	emailCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("Grade"));
//    	
//    	statusCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("Grade"));
//    	
//    	gradeCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("Grade"));
//
//
//    	testsList.getColumns().setAll(nameCol, idCol);
    }

}
