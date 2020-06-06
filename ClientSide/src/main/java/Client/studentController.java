package Client;

import java.io.IOException;

import CloneEntities.CloneTest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class studentController extends AbstractController {

    @FXML
    private TableView<CloneTest> testsTable;

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
    private TableColumn<CloneTest, String> gradeCol;

    @FXML
    private Button displayButton;

    @FXML
    private Button startButton;
    
    @Override
    public void initialize() {
    	System.out.println("Hi!");
    }
    
    @FXML
    void OnClickedStartTest(ActionEvent event) {
    	try {
			App.changeStage("TestCodeInput", "Exam Entrance Code");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }


}
