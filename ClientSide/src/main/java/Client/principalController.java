package Client;

import CloneEntities.CloneTest;
import CloneEntities.CloneTimeExtensionRequest;
import UtilClasses.DataElements.ClientToServerOpcodes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class principalController extends AbstractController{

    @FXML
    private Button displayButton;

    @FXML
    private Button startButton;

    @FXML TableView<CloneTimeExtensionRequest> requestsList;

    @FXML
    private TableColumn<CloneTimeExtensionRequest, String> nameCol;

    @FXML
    private TableColumn<CloneTimeExtensionRequest, String> testName;

    @FXML
    private TableColumn<CloneTimeExtensionRequest, String> dateCol;

    @FXML
    private TableColumn<CloneTimeExtensionRequest, String> timeCol;

    @FXML
    private TableColumn<CloneTimeExtensionRequest, String> statusCol;
    
    public void initialize() {
    	
    	testName.setCellValueFactory(new PropertyValueFactory<CloneTimeExtensionRequest, String>("TestName"));

		nameCol.setCellValueFactory(new PropertyValueFactory<CloneTimeExtensionRequest, String>("Body"));

		dateCol.setCellValueFactory(new PropertyValueFactory<CloneTimeExtensionRequest, String>("TestDate"));

		timeCol.setCellValueFactory(new PropertyValueFactory<CloneTimeExtensionRequest, String>("TestTime"));

		statusCol.setCellValueFactory(new PropertyValueFactory<CloneTimeExtensionRequest, String>("Status"));

		requestsList.getColumns().setAll(testName, nameCol, dateCol, timeCol, statusCol);
		
    	sendRequest(ClientToServerOpcodes.GetAllRequests, null);
    }

}
