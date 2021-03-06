package Client;


import CloneEntities.CloneTimeExtensionRequest;
import UtilClasses.DataElements.ClientToServerOpcodes;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class principalController extends AbstractController{

    @FXML
    private Button showButton;

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
    
    @SuppressWarnings("unchecked")
	public void initialize() {
    	
    	testName.setCellValueFactory(new PropertyValueFactory<CloneTimeExtensionRequest, String>("TestName"));

		nameCol.setCellValueFactory(new PropertyValueFactory<CloneTimeExtensionRequest, String>("Body"));

		dateCol.setCellValueFactory(new PropertyValueFactory<CloneTimeExtensionRequest, String>("TestDate"));

		timeCol.setCellValueFactory(new PropertyValueFactory<CloneTimeExtensionRequest, String>("TestTime"));

		statusCol.setCellValueFactory(new PropertyValueFactory<CloneTimeExtensionRequest, String>("Status"));

		requestsList.getColumns().setAll(testName, nameCol, dateCol, timeCol, statusCol);
		
		requestsList.setRowFactory((Callback<TableView<CloneTimeExtensionRequest>, TableRow<CloneTimeExtensionRequest>>) new Callback<TableView<CloneTimeExtensionRequest>, TableRow<CloneTimeExtensionRequest>>() 
		{
		    @Override public TableRow<CloneTimeExtensionRequest> call(TableView<CloneTimeExtensionRequest> requestsListView) 
		    {
		        return new TableRow<CloneTimeExtensionRequest>() 
		        {
		            @Override protected void updateItem(CloneTimeExtensionRequest item, boolean b) 
		            {
		                super.updateItem(item, b);

		                if (item == null)
		                    return;
		                
		                switch (item.getStatus()) {
		                case Ongoing:
		                	setStyle("-fx-background-color: #f8fc03;");
		                	break;
		                case Denied:
		                	setStyle("-fx-background-color: #ff0000;");
		                	break;
		                case Confirmed:
		                	setStyle("-fx-background-color: #03fc35;");
		                	break;
		                default:
		                	break;
		                	
		                }
		            }
		        };
		    }
		});
		
    	sendRequest(ClientToServerOpcodes.GetAllTimeExtensionRequestRequests, null);
    }
    
    @FXML
    void onClickedShow(ActionEvent event) throws Exception {
		if (requestsList.getSelectionModel().getSelectedItem() != null) { 
			newWindow(requestsList, new requestController(), "requestController.fxml",
					"Request " + requestsList.getSelectionModel().getSelectedItem().getTestName());
		} else
			popError("Error", "Please choose a test");
    }
    
	void updateTable(ObservableList<CloneTimeExtensionRequest> requests) {
		int keepSelection = requestsList.getSelectionModel().getSelectedIndex();
		requestsList.setItems(requests);
		requestsList.getSelectionModel().select(keepSelection);
	}

}
