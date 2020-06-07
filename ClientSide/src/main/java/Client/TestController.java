package Client;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class TestController extends AbstractController {

    @FXML
    private AnchorPane TestNameLabel;

    @FXML
    private TextArea questionText;

    @FXML
    private TextField answerAText;

    @FXML
    private TextField AnswerBText;

    @FXML
    private TextField answerCText;

    @FXML
    private TextField answerDText;

    @FXML
    private RadioButton answer_a_button;

    @FXML
    private RadioButton answer_b_button;

    @FXML
    private RadioButton answer_c_button;

    @FXML
    private RadioButton answer_d_button;

    @FXML
    private Label questionNumberLabel;

    @FXML
    private Label allQuestionsNumberLabel;

    @FXML
    private Label endTimeLabel;

    @FXML
    private Button SubmitButton;

    @FXML
    private Button nextButton;

    @FXML
    private Button backButton;

    @FXML
    void OnClickedSubmit(ActionEvent event) {
		try {
			App.changeStage("mainController", "High School Tests System");
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

}
