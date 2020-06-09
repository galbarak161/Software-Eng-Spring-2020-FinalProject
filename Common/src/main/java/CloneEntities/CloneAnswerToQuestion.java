package CloneEntities;

import java.io.Serializable;

public class CloneAnswerToQuestion implements Serializable{
	
	private static final long serialVersionUID = 1793964913547898831L;

	private int questionId;
	
	private int studentAnswer;

	public CloneAnswerToQuestion(int questionId, int studentAnswer) {
		this.questionId = questionId;
		this.studentAnswer = studentAnswer;
	}

	public int getQuestionId() {
		return questionId;
	}

	public int getStudentAnswer() {
		return studentAnswer;
	}
	
	
	
	
}
