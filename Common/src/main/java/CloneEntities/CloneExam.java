package CloneEntities;

import java.io.Serializable;
import java.util.List;

public class CloneExam implements Serializable {

	private static final long serialVersionUID = 1875843740998023504L;

	private int id;

	// private CloneTeacher creator;

	private List<CloneQuestion> questions;

	private int duration;

	private String TeacherComments;

	private String StudentComments;

	private int examCode;

	private CloneCourse course;

	public CloneExam() {
		super();
	}

	public CloneExam(int id, int duration, String teacherComments, String studentComments, int examCode,
			CloneCourse course) {
		super();
		this.id = id;
		this.duration = duration;
		TeacherComments = teacherComments;
		StudentComments = studentComments;
		this.examCode = examCode;
		this.course = course;
	}

	public int getId() {
		return id;
	}

	public List<CloneQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(List<CloneQuestion> questions) {
		this.questions = questions;
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

	public int getExamCode() {
		return examCode;
	}

	public void setExamCode(int examCode) {
		this.examCode = examCode;
	}

	public CloneCourse getCourse() {
		return course;
	}

	public void setCourse(CloneCourse course) {
		this.course = course;
	}
}
