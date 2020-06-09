package Hibernate.Entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Student extends User {

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "student")
	private List<StudentTest> tests;

	@ManyToMany(mappedBy = "students", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Course> courses;

	public Student() {
		courses = new ArrayList<Course>();
		tests = new ArrayList<StudentTest>();
	}

	public Student(String userName, String password, String firstName, String lastName, String emailAddress) {
		super(userName, password, firstName, lastName, emailAddress);
		courses = new ArrayList<Course>();
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

	public void addCourses(Course... courses) {
		for (Course course : courses) {
			this.courses.add(course);
			course.getStudents().add(this);
		}
	}

}