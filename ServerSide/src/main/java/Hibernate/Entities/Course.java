package Hibernate.Entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import CloneEntities.*;

@Entity
@Table(name = "Course")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "courseId")
	private int id;

	@Column(name = "courseName")
	private String courseName;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "course")
	private List<Question> questions;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "course")
	private List<Exam> exames;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "studyId")
	private Study study;
	
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "course_teacher", joinColumns = @JoinColumn(name = "courseId"), inverseJoinColumns = @JoinColumn(name = "teacherId"))
	private List<Teacher> teachers;

	public Course() {
		super();
		this.questions = new ArrayList<Question>();
		this.exames = new ArrayList<Exam>();
		this.teachers = new ArrayList<Teacher>();
	}

	public Course(String courseName, Study study) {
		super();
		this.courseName = courseName;
		this.setStudy(study);
		this.questions = new ArrayList<Question>();
		this.exames = new ArrayList<Exam>();
		this.teachers = new ArrayList<Teacher>();
	}

	public CloneCourse createClone() {
		CloneCourse clone = new CloneCourse(this.id, this.courseName);
		return clone;
	}

	public int getId() {
		return id;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
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

	public void addExames(Exam exam) {
		this.exames.add(exam);
	}

	public Study getStudy() {
		return study;
	}

	public void setStudy(Study study) {
		this.study = study;
		study.getCourses().add(this);
	}
	
	public List<Teacher> getTeachers() {
		return teachers;
	}
	
	public void addTeacher(Teacher... teachers) {
		for (Teacher teacher : teachers) {
			this.teachers.add(teacher);
			teacher.getCourses().add(this);
		}
	}



}
