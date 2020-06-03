package Hibernate.Entities;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.lang.String;

import org.hibernate.annotations.ManyToAny;

//@Entity
//@Table(name = "Exam")
public class Exam {
	

	//@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@Column(name = "examId")
	private int id;
	
	//@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	//@JoinTable(name = "questions", joinColumns = @JoinColumn(name = "examId"), inverseJoinColumns = @JoinColumn(name = "questionId"))
	private List<Question> questions; 
	
	//@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	//@JoinColumn(name = "teacherId")
	private Teacher creator;	

	//@Column
    private List<Integer> questionsPoints;   
	
	//@Column
    private int duration; // Duration of exam in minutes
    
	//@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	//@JoinColumn(name = "studyId")
    private Study study;
    
    //@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	//@JoinColumn(name = "courseId")
	private Course course;

    //@Column(name = "TeacherComments")
	private String TeacherComments;
    
    //@Column(name = "StudentComments")
	private String StudentComments;
    
    //@Column(name = "examCode")
    private int examCode;

	public Exam() {
		super();
	}

	public Exam(List<Question> questions, Teacher creator, List<Integer> questionsPoints, int duration,
			Study study, Course course, String teacherComments, String studentComments) {
		super();
		this.questions = questions;
		this.creator = creator;
		this.questionsPoints = questionsPoints;
		this.duration = duration;
		this.study = study;
		this.course = course;
		TeacherComments = teacherComments;
		StudentComments = studentComments;
		this.examCode = 1; 
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public Teacher getCreator() {
		return creator;
	}

	public void setCreator(Teacher creator) {
		this.creator = creator;
	}

	public List<Integer> getQuestionsPoints() {
		return questionsPoints;
	}

	public void setQuestionsPoints(List<Integer> questionsPoints) {
		this.questionsPoints = questionsPoints;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Study getStudy() {
		return study;
	}

	public void setStudy(Study study) {
		this.study = study;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getTeacherComments() {
		return TeacherComments;
	}

	public void setTeacherComments(String teacherComments) {
		TeacherComments = teacherComments;
	}

	public String getStudentComments() {
		return StudentComments;
	}

	public void setStudentComments(String studentComments) {
		StudentComments = studentComments;
	}

	public int getExamCode() {
		return examCode;
	}

	public void setExamCode(int examCode) {
		this.examCode = examCode;
	}

	public int getId() {
		return id;
	}
}
