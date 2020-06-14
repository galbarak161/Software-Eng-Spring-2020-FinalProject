package Client;

import java.io.IOException;

import CloneEntities.CloneAnswerToQuestion;
import CloneEntities.CloneTest;
import CloneEntities.CloneTimeExtensionRequest;
import CloneEntities.CloneTimeExtensionRequest.RequestStatus;
import CloneEntities.CloneTest.TestStatus;
import UtilClasses.DataElements.ClientToServerOpcodes;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
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

						if (item.getStatus() == RequestStatus.Onging)
							setStyle("-fx-background-color: #f8fc03;");
						else if (item.getStatus() == RequestStatus.Denied)
							setStyle("-fx-background-color: #ff0000;");
						else
							setStyle("-fx-background-color: #03fc35;");
		            }
		        };
		    }
		});
		
    	sendRequest(ClientToServerOpcodes.GetAllTimeExtensionRequestRequests, null);
    }
    
    @FXML
    void onClickedShow(ActionEvent event) {
		if (requestsList.getSelectionModel().getSelectedItem() != null) { // REMEMBER TO USE STATUSES LATER WHEN IT'S READY
				Platform.runLater(() -> {
					Parent root = null;
					try {
						FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("requestController.fxml"));
						root = (Parent) fxmlLoader.load();
						requestController q = fxmlLoader.getController();
						q.setRequest(requestsList.getSelectionModel().getSelectedItem());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Stage stage = new Stage();
					stage.setTitle("Time Extention Approval Form");
					stage.setScene(new Scene(root));
					stage.show();
				});
		} else
			popError("Error", "Please choose a test");
    }
    
	void updateTable(ObservableList<CloneTimeExtensionRequest> requests) {
		int keepSelection = requestsList.getSelectionModel().getSelectedIndex();
		requestsList.setItems(requests);
		requestsList.getSelectionModel().select(keepSelection);
	}

}
