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

	@ManyToMany(mappedBy = "studies", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Course> courses;

	public Study() {
		super();
		this.courses = new ArrayList<Course>();
	}

	public Study(String studyName) {
		super();
		this.studyName = studyName;
		this.courses = new ArrayList<Course>();
	}

	public CloneStudy createClone() {
		CloneStudy clone = new CloneStudy(this.id, this.studyName);
		return clone;
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

	public void addCourses(Course... courses) {
		for (Course course : courses) {
			this.courses.add(course);
			course.getStudies().add(this);
		}
	}
}
