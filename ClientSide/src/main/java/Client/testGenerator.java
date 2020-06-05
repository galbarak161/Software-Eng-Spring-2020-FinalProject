package Client;

import CloneEntities.CloneCourse;
import CloneEntities.CloneExam;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class testGenerator extends AbstractController{

    @FXML
    private Button generateButton;

    @FXML
    private ComboBox<CloneCourse> courseCombo;

    @FXML
    private ComboBox<CloneExam> examCombo;

    @FXML
    private DatePicker datePicker;

}
