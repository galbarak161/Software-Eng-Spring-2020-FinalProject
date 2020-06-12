package Hibernate.Entities;

import javax.persistence.*;

import CloneEntities.CloneAnswerToQuestion;
import CloneEntities.CloneQuestion;

@Entity
@Table(name = "Answer_To_Question")
public class AnswerToQuestion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AnswerToQuestionId")
	private int id;

	@Column(name = "questionCode")
	private int questionCode;

	@Column(name = "studentAnswer")
	private int studentAnswer;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "StudentTestId")
	private StudentTest student;
	
	public AnswerToQuestion() {
	}

	public AnswerToQuestion(StudentTest student, int questionCode, int questionNumberInExam) {
		this.studentAnswer = -1;
		setStudent(student);
		this.questionCode = questionCode;
	}

	public CloneAnswerToQuestion createClone(CloneQuestion question) {
		CloneAnswerToQuestion clone = new CloneAnswerToQuestion(question, studentAnswer);
		return clone;
	}
	
	public int getId() {
		return id;
	}
	
	public int getQuestionCode() {
		return questionCode;
	}

	public void setQuestionCode(int questionCode) {
		this.questionCode = questionCode;
	}

	public int getStudentAnswer() {
		return studentAnswer;
	}

	public void setStudentAnswer(int studentAnswer) {
		this.studentAnswer = studentAnswer;
	}

	public StudentTest getStudent() {
		return student;
	}

	public void setStudent(StudentTest student) {
		this.student = student;
		student.addAnswer(this);
	}	
}
