package Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class showExam extends AbstractController {

    @FXML
    private AnchorPane courseText;

    @FXML
    private TextField hoursText;

    @FXML
    private TextField minutesText;

    @FXML
    private TextField ExamNameText;

    @FXML
    private TextField teachersText;

    @FXML
    private TextField studentsComment;

    @FXML
    private ListView<?> questionsList;

    @FXML
    private TextField courseNameText;

    @FXML
    private Button QuestionButton;

    @FXML
    void OnClickedQuestion(ActionEvent event) {

    }

}