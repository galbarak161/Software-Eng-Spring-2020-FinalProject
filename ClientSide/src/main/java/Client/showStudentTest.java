package Client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.POITextExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.greenrobot.eventbus.EventBus;

import CloneEntities.CloneAnswerToQuestion;
import CloneEntities.CloneExam;
import CloneEntities.CloneQuestionInExam;
import CloneEntities.CloneStudentTest;
import CloneEntities.CloneTest;
import CloneEntities.CloneTest.ExamType;
import CloneEntities.CloneTest.TestStatus;
import CloneEntities.CloneUser.UserType;
import UtilClasses.updateNotes;
import UtilClasses.DataElements.ClientToServerOpcodes;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class showStudentTest extends AbstractController {

	@FXML
	private TableView<CloneAnswerToQuestion> questionsTable;

	@FXML
	private TableColumn<CloneAnswerToQuestion, String> questionName;

	@FXML
	private TableColumn<CloneAnswerToQuestion, String> gradeCol;

	@FXML
	private TableColumn<CloneAnswerToQuestion, String> answerCol;

	@FXML
	private TableColumn<CloneAnswerToQuestion, String> pointsCol;

	@FXML
	private Label GradeLabel;

	@FXML
	private Label TestNameLabel;

	@FXML
	private TextArea TeacherCommentField;

	@FXML
	private Button ShowQuestionButton;

	@FXML
	private Button saveButton;

	@FXML
	private TextArea manualText;

	private CloneStudentTest thisTest;

	public void initialize() {
		questionName.setCellValueFactory(new PropertyValueFactory<CloneAnswerToQuestion, String>("Name"));

		gradeCol.setCellValueFactory(new PropertyValueFactory<CloneAnswerToQuestion, String>("CorrectAnswer"));

		answerCol.setCellValueFactory(new PropertyValueFactory<CloneAnswerToQuestion, String>("YourAnswer"));

		pointsCol.setCellValueFactory(new PropertyValueFactory<CloneAnswerToQuestion, String>("Points"));

		questionsTable.getColumns().setAll(questionName, answerCol, gradeCol, pointsCol);
	}

	@Override
	protected <T> void setFields(T selectedItem) {
		try {
			setFields((CloneStudentTest) selectedItem);
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}

	}

	public void setFields(CloneStudentTest st) throws InterruptedException, IOException {
		TeacherCommentField.setText(st.getExamCheckNotes());
		if (ClientMain.getUser().getUserType() == UserType.Teacher
				&& st.getTest().getStatusEnum() == TestStatus.PendingApproval) {
			TeacherCommentField.setEditable(true);
			saveButton.setVisible(true);
		}
		
		thisTest = st;
		GradeLabel.setText(String.valueOf(st.getGrade()));
		TestNameLabel.setText(st.getTest().getExamToExecute().getExamName());

		if (st.getTest().getType() == ExamType.Manual) {
			questionsTable.setVisible(false);
			ShowQuestionButton.setVisible(false);
			manualText.setVisible(true);
			String extractedText;
			if (st.getUploadedFile() == null)
				extractedText = "No test has been uploaded";
			else {
				InputStream fis = new FileInputStream(
						st.getUploadedFile());
				POITextExtractor extractor;
				XWPFDocument doc = new XWPFDocument(fis);
				extractor = new XWPFWordExtractor(doc);
				extractedText = extractor.getText();
			}
			manualText.setText(extractedText);
			return;
		}

		questionsTable.setRowFactory(
				(Callback<TableView<CloneAnswerToQuestion>, TableRow<CloneAnswerToQuestion>>) new Callback<TableView<CloneAnswerToQuestion>, TableRow<CloneAnswerToQuestion>>() {
					@Override
					public TableRow<CloneAnswerToQuestion> call(TableView<CloneAnswerToQuestion> questionsTableView) {
						return new TableRow<CloneAnswerToQuestion>() {
							@Override
							protected void updateItem(CloneAnswerToQuestion item, boolean b) {
								super.updateItem(item, b);

								if (item == null)
									return;

								if (item.getCorrectAnswer().equals(item.getYourAnswer()))
									setStyle("-fx-background-color: #03fc35;");
								else
									setStyle("-fx-background-color: #ff0000;");
							}
						};
					}
				});

		try {
			GetDataFromDB(ClientToServerOpcodes.GetAnswersToExamOfStudentTest, st);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Thread.sleep(800);

	}

	void setQuestions(ObservableList<CloneAnswerToQuestion> questions) {
		Platform.runLater(() -> {
			questionsTable.setItems(questions);
			try {
				GetDataFromDB(ClientToServerOpcodes.GetAllQuestionInExamRelatedToExam,
						thisTest.getTest().getExamToExecute());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}

	void setAnswers(List<CloneQuestionInExam> questions) {

		for (int i = 0; i < questionsTable.getItems().size(); i++) {
			questionsTable.getItems().get(i).setPoints(questions.get(i).getGrade());
		}
	}

	@FXML
	void onClickedQuestion(ActionEvent event) throws Exception {
		if (questionsTable.getSelectionModel().getSelectedItem() != null) {
			newWindow(questionsTable, new showQuestion(), "showQuestion.fxml",
					"Question " + questionsTable.getSelectionModel().getSelectedItem().getQuestion().getSubject());
		}
	}

	@FXML
	void onClickedSave(ActionEvent event) {
		EventBus.getDefault().post(new updateNotes(TeacherCommentField.getText(), thisTest));
		Stage stage;
		stage = (Stage) GradeLabel.getScene().getWindow();
		stage.close();
	}
}
