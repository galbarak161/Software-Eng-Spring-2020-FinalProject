package Client;

import CloneEntities.CloneTimeExtensionRequest;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class principalController {

    @FXML
    private Button displayButton;

    @FXML
    private Button startButton;

    @FXML
    private TableView<CloneTimeExtensionRequest> requestsList;

    @FXML
    private TableColumn<CloneTimeExtensionRequest, String> nameCol;

    @FXML
    private TableColumn<CloneTimeExtensionRequest, String> testName;

    @FXML
    private TableColumn<CloneTimeExtensionRequest, String> codeCol;

    @FXML
    private TableColumn<CloneTimeExtensionRequest, String> dateCol;

    @FXML
    private TableColumn<CloneTimeExtensionRequest, String> timeCol;

    @FXML
    private TableColumn<CloneTimeExtensionRequest, String> statusCol;

}
