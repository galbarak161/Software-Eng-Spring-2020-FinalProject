package Client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class studentController {

    @FXML
    private TableView<?> testsTable;

    @FXML
    private TableColumn<?, ?> nameCol;

    @FXML
    private TableColumn<?, ?> dateCol;

    @FXML
    private TableColumn<?, ?> timeCol;

    @FXML
    private TableColumn<?, ?> codeCol;

    @FXML
    private TableColumn<?, ?> statusCol;

    @FXML
    private TableColumn<?, ?> gradeCol;

    @FXML
    private Button displayButton;

    @FXML
    private Button startButton;

}
