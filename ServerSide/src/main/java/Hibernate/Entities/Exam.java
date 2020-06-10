package Hibernate.Entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import CloneEntities.CloneExam;
import CloneEntities.CloneQuestionInExam;

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

	@Column(name = "examName")
	private String examName;

	@Column(name = "durationInMinutes")
	private int duration;

	@Column(name = "teacherComments")
	private String teacherComments;

	@Column(name = "studentComments")
	private String studentComments;

	@Column(name = "examCode")
	private int examCode;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "courseId")
	private Course course;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "examToExecute")
	private List<Test> tests;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "exam")
	private List<QuestionInExam> questionInExam;

	private int numberOfQuestionInExam;

	public Exam() {
		tests = new ArrayList<Test>();
		questionInExam = new ArrayList<QuestionInExam>();
		numberOfQuestionInExam = 0;
	}

	public Exam(String examName, Teacher creator, int duration, Course course, String teacherComments,
			String studentComments) {
		this.examName = examName;
		this.duration = duration;
		this.teacherComments = teacherComments;
		this.studentComments = studentComments;
		setCreator(creator);
		setCourse(course);
		GenerateExamCode();
		tests = new ArrayList<Test>();
		questionInExam = new ArrayList<QuestionInExam>();
		numberOfQuestionInExam = 0;
	}

	public CloneExam createClone() {
		CloneExam clone = new CloneExam(id, duration, examName, teacherComments, studentComments, course.getId(),
				course.getCourseName(), creator.getId());
		return clone;
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

	public List<QuestionInExam> getQuestionInExam() {
		return questionInExam;
	}

	public void addQuestionInExam(QuestionInExam questionInExam) {
		this.questionInExam.add(questionInExam);
		this.numberOfQuestionInExam++;
	}

	
	public int getNumberOfQuestionInExam() {
		return numberOfQuestionInExam;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getTeacherComments() {
		return teacherComments;
	}

	public void setTeacherComments(String teacherComments) {
		this.teacherComments = teacherComments;
	}

	public String getStudentComments() {
		return studentComments;
	}

	public void setStudentComments(String studentComments) {
		this.studentComments = studentComments;
	}

	public String getExamName() {
		return examName;
	}
}
