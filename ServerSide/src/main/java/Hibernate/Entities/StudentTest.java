package Hibernate.Entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

//@Entity
//@Table(name = "StudentTest")
public class StudentTest {
	
	//@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@Column(name = "StudentTestId")
	private int id;

	//@Column
	private Test test;
	
	//@OneToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "student_id")
	private Student student; 
	
	//@Column
	private int score;
    
	//@Column
    private List<Integer> questionsAnswers; // each integer is between  1..4
    
	//@Column
    private boolean  finished;
    
	//@Column
    private String examNotes;


	public StudentTest() {
		super();
	}




	public StudentTest(Test test, Student student, int score, List<Integer> questionsAnswers, boolean finished,
			String examNotes) {
		super();
		this.test = test;
		this.student = student;
		this.score = score;
		this.questionsAnswers = questionsAnswers;
		this.finished = finished;
		this.examNotes = examNotes;
	}




	public Test getTest() {
		return test;
	}


	public void setTest(Test test) {
		this.test = test;
	}


	public Student getStudent() {
		return student;
	}


	public void setStudent(Student student) {
		this.student = student;
	}


	public int getScore() {
		return score;
	}


	public void setScore(int score) {
		this.score = score;
	}


	public List<Integer> getQuestionsAnswers() {
		return questionsAnswers;
	}


	public void setQuestionsAnswers(List<Integer> questionsAnswers) {
		this.questionsAnswers = questionsAnswers;
	}


	public boolean isFinished() {
		return finished;
	}


	public void setFinished(boolean finished) {
		this.finished = finished;
	}


	public String getExamNotes() {
		return examNotes;
	}


	public void setExamNotes(String examNotes) {
		this.examNotes = examNotes;
	}


	public int getId() {
		return id;
	}

	
	
}
