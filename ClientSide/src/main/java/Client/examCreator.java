package Client;



import com.jfoenix.controls.JFXListView;

import CloneEntities.*;
import CommonElements.DataElements.ClientToServerOpcodes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class examCreator extends AbstractController {

    @FXML
    private TextField hoursText;

    @FXML
    private TextField minutesText;

    @FXML
    private TextField nameText;

    @FXML ListView<CloneQuestion> questionsList;

    @FXML
    private Button submit_button;

    @FXML
    private TextField teachersText;

    @FXML ComboBox<CloneCourse> courseCombo;

    @FXML
    private TextField studentsComment;



    public void initialize() {
    	ClientMain.addController(this.getClass().toString().split("Client.")[1],this);
    	try {
			  GetDataFromDB(ClientToServerOpcodes.GetAllCoursesOfTeacher, ClientMain.getUser());
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
    }
   /* 
    @FXML
    void OnCourseClicked(ActionEvent event) {
    	
    	int dbStatus = GetDataFromDB(ClientToServerOpcodes.GetAllQuestionInCourse, selected_q.get(0).getCourse());

    }
*/

}
