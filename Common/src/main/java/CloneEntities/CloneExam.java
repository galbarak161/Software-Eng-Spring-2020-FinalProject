package CloneEntities;

import java.io.Serializable;
import java.util.List;

public class CloneExam implements Serializable {

	

	private static final long serialVersionUID = 1875843740998023504L;

	private List<CloneQuestion> questions;

	private List<Integer> points;

	private int duration;

	private String examName;

	private String TeacherComments;

	private String StudentComments;

	private int courseId;

	private int teacherId;

	public CloneExam() {
	}
	
	public CloneExam(List<CloneQuestion> questions, List<Integer> points, int duration, String examName,
			String teacherComments, String studentComments, int courseId, int teacherId) {
		this.questions = questions;
		this.points = points;
		this.duration = duration;
		this.examName = examName;
		TeacherComments = teacherComments;
		StudentComments = studentComments;
		this.courseId = courseId;
		this.teacherId = teacherId;
	}

	public List<CloneQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(List<CloneQuestion> questions) {
		this.questions = questions;
	}

	public List<Integer> getPoints() {
		return points;
	}

	public void setPoints(List<Integer> points) {
		this.points = points;
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
