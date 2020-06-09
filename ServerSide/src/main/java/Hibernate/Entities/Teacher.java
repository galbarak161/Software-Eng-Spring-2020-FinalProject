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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "executor")
	private List<Test> tests;

	public Teacher() {
		courses = new ArrayList<Course>();
		questions = new ArrayList<Question>();
		exames = new ArrayList<Exam>();
		tests = new ArrayList<Test>();
	}

	public Teacher(String userName, String password, String firstName, String lastName, String emailAddress) {
		super(userName, password, firstName, lastName, emailAddress);
		courses = new ArrayList<Course>();
		questions = new ArrayList<Question>();
		exames = new ArrayList<Exam>();
		tests = new ArrayList<Test>();
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

	public List<Test> getTests() {
		return tests;
	}

	public void addTest(Test test) {
		this.tests.add(test);
	}

}
