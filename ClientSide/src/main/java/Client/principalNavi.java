package Client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class principalNavi extends AbstractNavi {

    @FXML
    private Label requestsList;

    @FXML
    private Label dataDisplay;

    @FXML
    private Label personalLabel;

    @FXML
    private Label logout;
    
    @FXML
    void requestsButton(MouseEvent event) {
    	switchMainPanel("principalController.fxml");
    }
    
    
    @FXML
    void dataButton(MouseEvent event) {
    	switchMainPanel("principalDataController.fxml");
    }
    
    @FXML
    void changeToInfo(MouseEvent event) {
    	switchMainPanel("personalInfo.fxml");
    }

}
