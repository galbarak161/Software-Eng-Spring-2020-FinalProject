package CloneEntities;

import java.io.Serializable;

public class CloneQuestion implements Serializable {

	private static final long serialVersionUID = 5561029632903930936L;

	private int id;

	private int questionCode;

	private String subject;

	private String questionText;

	private String answer_1;

	private String answer_2;

	private String answer_3;

	private String answer_4;

	private int correctAnswer;

	private CloneCourse course;

	private int teacherId;
	
	public CloneQuestion() {
	}

	public CloneQuestion(int id, int questionCode, String subject, String questionText, String answer_1,
			String answer_2, String answer_3, String answer_4, int correctAnswer, CloneCourse course, int teacherId) {
		this.id = id;
		this.questionCode = questionCode;
		this.subject = subject;
		this.questionText = questionText;
		this.answer_1 = answer_1;
		this.answer_2 = answer_2;
		this.answer_3 = answer_3;
		this.answer_4 = answer_4;
		this.correctAnswer = correctAnswer;
		this.course = course;
		this.teacherId = teacherId;
	}

	@Override
	public String toString() {
		return this.getSubject();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuestionCode() {
		return questionCode;
	}

	public void setQuestionCode(int questionCode) {
		this.questionCode = questionCode;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public String getAnswer_1() {
		return answer_1;
	}

	public void setAnswer_1(String answer_1) {
		this.answer_1 = answer_1;
	}

	public String getAnswer_2() {
		return answer_2;
	}

	public void setAnswer_2(String answer_2) {
		this.answer_2 = answer_2;
	}

	public String getAnswer_3() {
		return answer_3;
	}

	public void setAnswer_3(String answer_3) {
		this.answer_3 = answer_3;
	}

	public String getAnswer_4() {
		return answer_4;
	}

	public void setAnswer_4(String answer_4) {
		this.answer_4 = answer_4;
	}

	public int getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(int correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public CloneCourse getCourse() {
		return course;
	}

	public int getTeacherId() {
		return teacherId;
	}

	
}