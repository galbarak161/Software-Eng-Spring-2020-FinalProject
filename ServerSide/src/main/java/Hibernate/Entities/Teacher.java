package Hibernate.Entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
public class Teacher extends User {

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "teacher")
	private List<Course> courses;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "teacher")
	private List<Question> questions;

	public Teacher() {
		courses = new ArrayList<Course>();
		questions = new ArrayList<Question>();
	}
	
	public Teacher(String userName, String password, String firstName, String lastName, String identityNumber) {
		super(userName, password, firstName, lastName, identityNumber);
		courses = new ArrayList<Course>();
		questions = new ArrayList<Question>();
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void addCourses(Course course) {
		this.courses.add(course);
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void addQuestions(Question question) {
		this.questions.add(question);
	}
	

}
