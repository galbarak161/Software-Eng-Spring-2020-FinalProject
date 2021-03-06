package CloneEntities;

import java.io.Serializable;

public class CloneAnswerToQuestion implements Serializable{
	
	private static final long serialVersionUID = 1793964913547898831L;

	private CloneQuestion question;
	
	private int studentAnswer;
	
	private String points;

	public CloneAnswerToQuestion(CloneQuestion question) {
		this.question = question;
		this.studentAnswer = -1;
	}

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
	
	public String getName() {
		return question.getSubject();
	}
	
	public String getCorrectAnswer() {
		return String.valueOf(question.getCorrectAnswer());
	}
	
	public String getYourAnswer() {
		return String.valueOf(studentAnswer);
	}
	
	public void setAnswer(int answer) {
		studentAnswer = answer;
	}
	
	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}
	
}
