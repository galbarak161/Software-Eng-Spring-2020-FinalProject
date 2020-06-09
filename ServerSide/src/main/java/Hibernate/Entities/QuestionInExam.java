package Hibernate.Entities;

import javax.persistence.*;

import UtilClasses.CloneQuestionInExam;

@Entity
@Table(name = "Question_In_Exam")
public class QuestionInExam {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "QuestionInExamId")
	private int id;

	@Column(name = "pointsForQuestion")
	private int pointsForQuestion;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "examId")
	private Exam exam;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "questionId")
	private Question question;

	public QuestionInExam() {
	}

	public QuestionInExam(int pointsForQuestion, Exam exam, Question question) {
		this.pointsForQuestion = pointsForQuestion;
		setExam(exam);
		setQuestion(question);
	}
	
	public CloneQuestionInExam createClone() {
		CloneQuestionInExam cloneQuestionInExam = new CloneQuestionInExam(pointsForQuestion, question.createClone());
		return cloneQuestionInExam;
		
	}

	public int getId() {
		return id;
	}

	public int getPointsForQuestion() {
		return pointsForQuestion;
	}

	public void setPointsForQuestion(int pointsForQuestion) {
		this.pointsForQuestion = pointsForQuestion;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
		exam.getQuestionInExam().add(this);
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
		question.getQuestionInExam().add(this);
	}
}
