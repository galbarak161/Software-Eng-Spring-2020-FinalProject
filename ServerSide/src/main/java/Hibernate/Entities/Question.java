package Hibernate.Entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import CloneEntities.*;

@Entity
@Table(name = "Question")
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "questionId")
	private int id;

	@Column(name = "questionCode")
	private int questionCode;

	@Column(name = "subject", length = 100)
	private String subject;

	@Column(name = "questionText", length = 100)
	private String questionText;

	@Column(name = "answer_1", length = 100)
	private String answer_1;

	@Column(name = "answer_2", length = 100)
	private String answer_2;

	@Column(name = "answer_3", length = 100)
	private String answer_3;

	@Column(name = "answer_4", length = 100)
	private String answer_4;
	
	@Column(name = "correctAnswer")
	private int correctAnswer;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "courseId")
	private Course course;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "teacherId")
	private Teacher teacher;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "question_exam", joinColumns = @JoinColumn(name = "questionId"), inverseJoinColumns = @JoinColumn(name = "examId"))
	private List<Exam> exames; 
	
	public Question() {
		this.exames = new ArrayList<Exam>();
	}

	public Question(String subject, String questionText, String answer_1, String answer_2, String answer_3,
			String answer_4, int correctAnswer, Course course, Teacher teacher) {
		this.setCourse(course);
		this.setTeacher(teacher);
		this.GenerateQuestionCode();
		this.subject = subject;
		this.questionText = questionText;
		this.answer_1 = answer_1;
		this.answer_2 = answer_2;
		this.answer_3 = answer_3;
		this.answer_4 = answer_4;
		this.correctAnswer = correctAnswer;
		this.exames = new ArrayList<Exam>();
	}

	public CloneQuestion createClone() {
		CloneQuestion clone = new CloneQuestion(this.id, this.questionCode, this.subject, this.questionText,
				this.answer_1, this.answer_2, this.answer_3, this.answer_4, this.correctAnswer,
				this.course.getId(), this.teacher.getId());
		return clone;
	}

	private void GenerateQuestionCode() {
		int courseID = course.getId();
		this.questionCode = courseID * 1000 + course.getQuestions().size();
	}

	public int getId() {
		return id;
	}

	public int getQuestionCode() {
		return questionCode;
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

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course c) {
		this.course = c;
		c.getQuestions().add(this);
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
		teacher.getQuestions().add(this);
	}

	public List<Exam> getExames() {
		return exames;
	}
	
	public void addExam(Exam... exames) {
		for (Exam exam : exames) {
			this.exames.add(exam);
			exam.getQuestions().add(this);
		}
	}

	
}