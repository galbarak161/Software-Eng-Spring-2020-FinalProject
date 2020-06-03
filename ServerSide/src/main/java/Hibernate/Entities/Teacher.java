package Hibernate.Entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
public class Teacher extends User {

	@ManyToMany(mappedBy = "teachers", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Course> courses;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "teacher")
	private List<Question> questions;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "creator")
	private List<Exam> exames;
	
	public Teacher() {
		courses = new ArrayList<Course>();
		questions = new ArrayList<Question>();
		exames = new ArrayList<Exam>();
	}
	
	public Teacher(String userName, String password, String firstName, String lastName, String identityNumber, String emailAddress) {
		super(userName, password, firstName, lastName, identityNumber, emailAddress);
		courses = new ArrayList<Course>();
		questions = new ArrayList<Question>();
		exames = new ArrayList<Exam>();
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void addCourses(Course... courses) {
		for (Course course : courses) {
			this.courses.add(course);
			course.getTeachers().add(this);
		}
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void addQuestions(Question question) {
		this.questions.add(question);
	}

	public List<Exam> getExames() {
		return exames;
	}

	public void addExame(Exam exam) {
		this.exames.add(exam);
	}
	
	
	

}
