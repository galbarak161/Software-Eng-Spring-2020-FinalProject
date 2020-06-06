package CloneEntities;

public class AnsweredQuestion extends CloneQuestion {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4339573051432171486L;
	private int StudentAnswer;
	
	
	public AnsweredQuestion(int id, int questionCode, String subject, String questionText, String answer_1,
			String answer_2, String answer_3, String answer_4, int correctAnswer,
			CloneCourse course, int teacherId, int StudentAnswer)
	{
		super(id,questionCode,subject, questionText, answer_1,answer_2,answer_3,answer_4, 
				correctAnswer,course,teacherId);
		this.StudentAnswer = StudentAnswer;
	}
	
	public int getStudentAnswer() {
		return StudentAnswer;
	}
	


}
