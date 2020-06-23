package Client;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout.Alignment;

import org.apache.poi.POITextExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import CloneEntities.CloneStudentTest;
import CloneEntities.CloneTest;
import CloneEntities.CloneTest.ExamType;
import CloneEntities.CloneTest.TestStatus;
import UtilClasses.updateNotes;
import UtilClasses.DataElements.ClientToServerOpcodes;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class showStudentTests extends AbstractController {

	@FXML
	private Button showTest;

	@FXML
	private Button approveButton;

	@FXML
	TableView<CloneStudentTest> testsList;

	@FXML
	private TableColumn<CloneStudentTest, String> nameCol;

	@FXML
	private TableColumn<CloneStudentTest, String> idCol;

	@FXML
	private TableColumn<CloneStudentTest, String> emailCol;

	@FXML
	private TableColumn<CloneStudentTest, String> gradeCol;

	@FXML
	private TableColumn<CloneStudentTest, String> statusCol;

	@FXML
	private Label nameLabel;

	@FXML
	private Label titleLabel;

	private CloneTest thisTest;

	@SuppressWarnings("resource")
	@FXML
	void showTest(ActionEvent event) throws Exception {
		if (testsList.getSelectionModel().getSelectedItem().getDone().equals("Not Done")) {
			popError("Error", "Cannot show an undone test");
			return;
		}

		if (testsList.getSelectionModel().getSelectedItem() != null) {
			newWindow(testsList, new showStudentTest(), "showStudentTest.fxml", "Test Review");
		} else
			popError("Error", "Please choose a test");
	}

	public void shutdown() {
		EventBus.getDefault().unregister(this);
	}

	@SuppressWarnings("unchecked")
	public void initialize() {
		EventBus.getDefault().register(this);
		nameCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("StudentName"));

		idCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("StudentID"));

		emailCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("StudentEmail"));

		gradeCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("StudentGrade"));

		statusCol.setCellValueFactory(new PropertyValueFactory<CloneStudentTest, String>("Done"));

		gradeCol.setCellFactory(TextFieldTableCell.forTableColumn());
		gradeCol.setOnEditCommit(new EventHandler<CellEditEvent<CloneStudentTest, String>>() {
			public void handle(CellEditEvent<CloneStudentTest, String> t) {
				if (t.getNewValue().isEmpty()) {
					((CloneStudentTest) t.getTableView().getItems().get(t.getTablePosition().getRow())).setGrade(0);
				} else {
					((CloneStudentTest) t.getTableView().getItems().get(t.getTablePosition().getRow()))
							.setGrade(Integer.valueOf(t.getNewValue()));
				}
			}
		});
		testsList.getColumns().setAll(nameCol, idCol, emailCol, statusCol, gradeCol);
		testsList.setEditable(true);

	}

	@Override
	protected <T> void setFields(T selectedItem) {
		setFields((CloneTest) selectedItem, (TestStatus) ((CloneTest) selectedItem).getStatusEnum());

	}

	public void setFields(CloneTest test, TestStatus s) {
		try {
			GetDataFromDB(ClientToServerOpcodes.GetAllStudntTestRelatedToTest, test);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		nameLabel.setText(test.getName());
		if (s == TestStatus.Done) {
			titleLabel.setText("View Grades");
			approveButton.setVisible(false);
			testsList.resize(testsList.getWidth(), testsList.getHeight() + 200);
			testsList.setEditable(false);
			return;
		}
		thisTest = test;
	}

	@FXML
	void onClickedApprove(ActionEvent event) {
		List<updateNotes> toSend = new ArrayList<>();
		for (CloneStudentTest st : testsList.getItems()) {
			updateNotes toAdd = new updateNotes(st.getExamCheckNotes(), st);
			toAdd.setGrade(st.getGrade());
			toSend.add(toAdd);
		}
		

		try {
			GetDataFromDB(ClientToServerOpcodes.TeacherUpdateGrade, toSend);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	void showMsg(String title, String content) {
		Platform.runLater(() -> {
			Alert info = new Alert(Alert.AlertType.INFORMATION);
			info.setTitle(title);
			info.setHeaderText(content);
			info.showAndWait();
			Stage stage;
			stage = (Stage) showTest.getScene().getWindow();
			stage.close();
		});
	}

	@Subscribe
	public void onEvent(updateNotes notes) {
		testsList.getItems().get(testsList.getItems().indexOf(notes.getStudentTest()))
				.setExamCheckNotes(notes.getNotesToUpdate());
	}

}
