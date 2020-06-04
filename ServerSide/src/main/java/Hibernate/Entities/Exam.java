package Hibernate.Entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "Exam")
public class Exam {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "examId")
	private int id;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "teacherId")
	private Teacher creator;

	@ManyToMany(mappedBy = "exames", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Question> questions;

	@Column(name = "examName")
	private String examName;
	
	@Column(name = "durationInMinutes")
	private int duration;

	@Column(name = "TeacherComments")
	private String TeacherComments;

	@Column(name = "StudentComments")
	private String StudentComments;

	@Column(name = "examCode")
	private int examCode;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "courseId")
	private Course course;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "examToExecute")
	private List<Test> tests;

	public Exam() {
		this.questions = new ArrayList<Question>();
		tests = new ArrayList<Test>();
	}

	public Exam(String examName,Teacher creator, List<Integer> questionsPoints, int duration, Study study, Course course,
			String teacherComments, String studentComments) {
		this.examName = examName;
		this.duration = duration;
		TeacherComments = teacherComments;
		StudentComments = studentComments;
		setCreator(creator);
		setCourse(course);
		GenerateExamCode();
		this.questions = new ArrayList<Question>();
		tests = new ArrayList<Test>();
	}

	private void GenerateExamCode() {
		this.examCode = 5;
		int studyID = this.course.getStudy().getId();
		int courseID = this.course.getId();
		this.examCode = (studyID * 10000) + (courseID * 100) + course.getExames().size();
	}

	public int getId() {
		return id;
	}

	public int getExamCode() {
		return examCode;
	}

	public Teacher getCreator() {
		return creator;
	}

	public void setCreator(Teacher creator) {
		this.creator = creator;
		creator.getExames().add(this);
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void addQuestion(Question... questions) {
		for (Question question : questions) {
			this.questions.add(question);
			question.getExames().add(this);
		}
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
		course.getExames().add(this);
	}

	public List<Test> getTests() {
		return tests;
	}

	public void addTest(Test test) {
		this.tests.add(test);
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
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

	public String getExamName() {
		return examName;
	}
	
}
