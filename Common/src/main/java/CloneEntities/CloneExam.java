package CloneEntities;

import java.io.Serializable;
import java.util.List;

public class CloneExam implements Serializable {

	
	private static final long serialVersionUID = 1875843740998023504L;

	private int id;
	
	private List<CloneQuestionInExam> questions;


	private int duration;

	private String examName;

	private String TeacherComments;

	private String StudentComments;

	private int courseId;

	private String courseName;
	
	private int teacherId;

	public CloneExam() {
	}

	public CloneExam(List<CloneQuestionInExam> questions, int duration, String examName,
			String teacherComments, String studentComments, int courseId, String courseName, int teacherId) {
		this.questions = questions;

		this.duration = duration;
		this.examName = examName;
		TeacherComments = teacherComments;
		StudentComments = studentComments;
		this.courseId = courseId;
		this.courseName = courseName;
		this.teacherId = teacherId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public List<CloneQuestionInExam> getQuestions() {
		return questions;
	}

	public void setQuestions(List<CloneQuestionInExam> questions) {
		this.questions = questions;
	}


	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
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

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}
	
	@Override
	public String toString() {
		return examName;
	}

}
