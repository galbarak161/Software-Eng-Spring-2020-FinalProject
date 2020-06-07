package Client;

import CloneEntities.CloneTimeExtensionRequest;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class principalController extends AbstractController {

    @FXML
    private TableView<CloneTimeExtensionRequest> testsTable;

    @FXML
    private TableColumn<CloneTimeExtensionRequest, String> nameCol;

    @FXML
    private TableColumn<CloneTimeExtensionRequest, String> dateCol;

    @FXML
    private TableColumn<CloneTimeExtensionRequest, String> timeCol;

    @FXML
    private TableColumn<CloneTimeExtensionRequest, String> codeCol;

    @FXML
    private TableColumn<CloneTimeExtensionRequest, String> statusCol;

    @FXML
    private TableColumn<CloneTimeExtensionRequest, String> gradeCol;

    @FXML
    private Button displayButton;

    @FXML
    private Button startButton;

}
