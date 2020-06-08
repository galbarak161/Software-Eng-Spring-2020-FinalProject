package Hibernate.Entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Student extends User {

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "student")
	private List<StudentTest> tests;
	
	@ManyToMany(mappedBy = "courses", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Course> courses;

	public Student() {
		super();
		tests = new ArrayList<StudentTest>();
	}

	public Student(String userName, String password, String firstName, String lastName, String emailAddress) {
		super(userName, password, firstName, lastName, emailAddress);
		tests = new ArrayList<StudentTest>();
	}

	public List<StudentTest> getTests() {
		return tests;
	}

	public void addTests(StudentTest test) {
		this.tests.add(test);
	}

	public List<Course> getCourses() {
		return courses;
	}
	
	public void addcourses(Course course) {
		this.courses.add(course);
	}
	
	

}