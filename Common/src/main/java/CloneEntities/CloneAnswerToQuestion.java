package CloneEntities;

import java.io.Serializable;

public class CloneAnswerToQuestion implements Serializable{
	
	private static final long serialVersionUID = 1793964913547898831L;

	private CloneQuestion question;
	
	private int studentAnswer;

	public CloneAnswerToQuestion(CloneQuestion question, int studentAnswer) {
		this.question = question;
		this.studentAnswer = studentAnswer;
	}

	public CloneQuestion getQuestion() {
		return question;
	}

	public int getStudentAnswer() {
		return studentAnswer;
	}
	
}
