package Hibernate.Entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import CloneEntities.*;

@Entity
@Table(name = "Study")
public class Study {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "studyId")
	private int id;

	@Column(name = "studyName")
	private String studyName;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "study")
	private List<Course> courses;

	public Study() {
		this.courses = new ArrayList<Course>();
	}

	public Study(String studyName) {
		this.studyName = studyName;
		this.courses = new ArrayList<Course>();
	}

	public int getId() {
		return id;
	}

	public String getStudyName() {
		return studyName;
	}

	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void addCourse(Course course) {
		this.courses.add(course);
	}
}
