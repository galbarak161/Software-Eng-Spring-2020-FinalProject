package Client;

import CloneEntities.CloneQuestion;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class showQuestion {

    @FXML
    private TextField answer_line_1;

    @FXML
    private RadioButton radio_1;

    @FXML
    private TextField answer_line_2;

    @FXML
    private RadioButton radio_2;

    @FXML
    private TextField answer_line_3;

    @FXML
    private RadioButton radio_3;

    @FXML
    private TextField answer_line_4;

    @FXML
    private RadioButton radio_4;

    @FXML
    private TextField subject_text;

    @FXML
    private TextArea question_text;

    @FXML
    private TextField course_text;
	
	public void setFields(CloneQuestion question) {
		this.course_text.setText(question.getCourse().getCourseName());
		this.subject_text.setText(question.getSubject());
		this.question_text.setText(question.getQuestionText());
		this.answer_line_1.setText(question.getAnswer_1());
		this.answer_line_2.setText(question.getAnswer_2());
		this.answer_line_3.setText(question.getAnswer_3());
		this.answer_line_4.setText(question.getAnswer_4());
		switch (question.getCorrectAnswer()) {
			case 1:
				radio_1.setSelected(true);
				break;
			case 2:
				radio_2.setSelected(true);
				break;
			case 3:
				radio_3.setSelected(true);
				break;
			case 4:
				radio_4.setSelected(true);
				break;
		}
	}
	

}
