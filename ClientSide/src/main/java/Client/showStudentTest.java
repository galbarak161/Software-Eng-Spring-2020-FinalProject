package Client;

import java.util.List;

import org.greenrobot.eventbus.EventBus;

import CloneEntities.CloneAnswerToQuestion;
import CloneEntities.CloneExam;
import CloneEntities.CloneQuestionInExam;
import CloneEntities.CloneStudentTest;
import CloneEntities.CloneTest;
import CloneEntities.CloneTest.TestStatus;
import CloneEntities.CloneUser.UserType;
import UtilClasses.DataElements.ClientToServerOpcodes;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
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
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void setFields(CloneStudentTest st) throws InterruptedException {
		TeacherCommentField.setText(st.getExamCheckNotes());
		if (ClientMain.getUser().getUserType() == UserType.Teacher
				&& st.getTest().getStatusEnum() == TestStatus.PendingApproval) {
			TeacherCommentField.setEditable(true);
			saveButton.setVisible(true);
		}

		GradeLabel.setText(String.valueOf(st.getGrade()));
		TestNameLabel.setText(st.getTest().getExamToExecute().getExamName());

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

		thisTest = st;

		try {
			GetDataFromDB(ClientToServerOpcodes.GetAnswersToExamOfStudentTest, st);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Thread.sleep(150);

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
