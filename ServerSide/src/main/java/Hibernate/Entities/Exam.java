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

	public Exam() {
		this.questions = new ArrayList<Question>();
	}

	public Exam(Teacher creator, List<Integer> questionsPoints, int duration, Study study, Course course,
			String teacherComments, String studentComments) {
		this.duration = duration;
		TeacherComments = teacherComments;
		StudentComments = studentComments;
		setCreator(creator);
		setCourse(course);
		GenerateExamCode();
		this.questions = new ArrayList<Question>();
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
}
