package CloneEntities;


public class CloneQuestionInExam {

	private int id;
	
	private int pointsForQuestion;

	private CloneExam exam;

	private CloneQuestion question;

	public CloneQuestionInExam(int pointsForQuestion, CloneExam exam, CloneQuestion question) {
		super();
		this.pointsForQuestion = pointsForQuestion;
		this.exam = exam;
		this.question = question;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPointsForQuestion() {
		return pointsForQuestion;
	}

	public void setPointsForQuestion(int pointsForQuestion) {
		this.pointsForQuestion = pointsForQuestion;
	}

	public CloneExam getExam() {
		return exam;
	}

	public void setExam(CloneExam exam) {
		this.exam = exam;
	}

	public CloneQuestion getQuestion() {
		return question;
	}

	public void setQuestion(CloneQuestion question) {
		this.question = question;
	}
	
	

}
