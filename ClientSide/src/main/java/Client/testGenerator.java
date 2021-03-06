package Client;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.regex.Pattern;

import CloneEntities.CloneCourse;
import CloneEntities.CloneExam;
import CloneEntities.CloneTest;
import CloneEntities.CloneTest.ExamType;
import UtilClasses.TeacherCourse;
import UtilClasses.DataElements.ClientToServerOpcodes;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;

public class testGenerator extends AbstractController {

	protected final int HOUR_TO_MINUTE = 60;

	@FXML
	private Button generateButton;

	@FXML
	ComboBox<CloneCourse> courseCombo;

	@FXML ComboBox<CloneExam> examCombo;

	@FXML
	private DatePicker datePicker;

	@FXML
	private RadioButton autoRadio;

	@FXML
	private RadioButton manRadio;

	@FXML
	private TextField beginText;

	private ToggleGroup radioGroup;

	public void initialize() {
		
		try {
			GetDataFromDB(ClientToServerOpcodes.GetAllCoursesOfTeacher, ClientMain.getUser());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		radioGroup = new ToggleGroup();
		autoRadio.setToggleGroup(radioGroup);
		manRadio.setToggleGroup(radioGroup);
		
		beginText.setOnKeyPressed(event -> {
			if (event.getCode().equals(KeyCode.ENTER)) {
				onClickedGenerate(new ActionEvent());
			}
		});
	}
	
	public void setCourses(ObservableList<CloneCourse> courses) {
		Platform.runLater(() -> {
			this.courseCombo.setItems(courses);
		});
	}

	@FXML
	void onClickedCourse(ActionEvent event) {
		if (courseCombo.getValue() != null)
		try {
			GetDataFromDB(ClientToServerOpcodes.GetAllExamsOfTeacherInCourse, new TeacherCourse(ClientMain.getUser(),courseCombo.getValue()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void setExams(ObservableList<CloneExam> exams) {
		Platform.runLater(() -> {
			this.examCombo.setItems(exams);
		});
	}

	@FXML
	void onClickedGenerate(ActionEvent event) {
		try {
			StringBuilder errorsList = new StringBuilder();
			String reg = "([0-1][0-9]:[0-5][0-9])|([2][0-3]:[0-5][0-9])";

			if (courseCombo.getValue() == null)
				errorsList.append("No course has been chosen\n");

			if (examCombo.getValue() == null)
				errorsList.append("No exam has been chosen\n");

			if (datePicker.getValue() == null || datePicker.getValue().isBefore(LocalDate.now()))
				errorsList.append("Date is empty or invalid\n");

			if (!autoRadio.isSelected() && !manRadio.isSelected())
				errorsList.append("Please choose test type\n");

			if (beginText.getText().isEmpty() || !Pattern.compile(reg).matcher(beginText.getText()).matches()
					|| ((datePicker.getValue().isEqual(LocalDate.now())) && (LocalTime.parse(beginText.getText()).isBefore(LocalTime.now()))))
				errorsList.append("Begin time is empty or invalid\n");

			if (errorsList.length() != 0) {
				throw new Exception(errorsList.toString());
			}
		} catch (Exception e) {
			popError("Please fill all test fields or fix invalid inputs", e.getMessage());
			return;
		}
		LocalDate startDate = datePicker.getValue();
		LocalTime startTime = LocalTime.of(Integer.valueOf(beginText.getText().split(":")[0]),
				Integer.valueOf(beginText.getText().split(":")[1]));
		
		ExamType type;
		
		RadioButton chk = (RadioButton) radioGroup.getSelectedToggle();
		if (chk.getText().equals("Automated"))
			type = CloneTest.ExamType.Automated;
		else
			type = CloneTest.ExamType.Manual;

		CloneTest newTest = new CloneTest(startDate, startTime,0,type,ClientMain.getUser().getId(),examCombo.getValue());	
		try {
			GetDataFromDB(ClientToServerOpcodes.CreateNewTest, newTest);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
