package Client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class studentNavi extends AbstractNavi{

    @FXML
    private Label testsButton;

    @FXML
    private Label personalButton;

    @FXML
    private Label logoutButton;

    @FXML
    void switchToPersonal(MouseEvent event) {
    	switchMainPanel("personalInfo.fxml");
    }

    @FXML
    void swtichToTests(MouseEvent event) {
    	switchMainPanel("studentController.fxml");
    }

}
