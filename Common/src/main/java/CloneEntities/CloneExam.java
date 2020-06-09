package CloneEntities;

import java.io.Serializable;
import java.util.List;

import UtilClasses.CloneQuestionInExam;

public class CloneExam implements Serializable {
	
	private static final long serialVersionUID = 1875843740998023504L;

	private int id;

	private int duration;

	private String examName;

	private String teacherComments;

	private String studentComments;

	private int courseId;

	private String courseName;
	
	private int teacherId;
	
	private List<CloneQuestionInExam> questionInExam;

	public CloneExam(int id, int duration, String examName, String teacherComments, String studentComments,
			int courseId, String courseName, int teacherId ,List<CloneQuestionInExam> questionInExam) {
		this.id = id;
		this.duration = duration;
		this.examName = examName;
		this.teacherComments = teacherComments;
		this.studentComments = studentComments;
		this.courseId = courseId;
		this.courseName = courseName;
		this.teacherId = teacherId;
		setQuestionInExam(questionInExam);
	}

	public CloneExam(int duration, String examName, String teacherComments, String studentComments,
			int courseId, String courseName, int teacherId) {
		this.duration = duration;
		this.examName = examName;
		this.teacherComments = teacherComments;
		this.studentComments = studentComments;
		this.courseId = courseId;
		this.courseName = courseName;
		this.teacherId = teacherId;
	}

	public int getId() {
		return id;
	}

	public int getDuration() {
		return duration;
	}

	public String getExamName() {
		return examName;
	}

	public String getTeacherComments() {
		return teacherComments;
	}

	public String getStudentComments() {
		return studentComments;
	}

	public int getCourseId() {
		return courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public int getTeacherId() {
		return teacherId;
	}

	@Override
	public String toString() {
		return this.getExamName();
	}

	public List<CloneQuestionInExam> getQuestionInExam() {
		return questionInExam;
	}

	public void setQuestionInExam(List<CloneQuestionInExam> questionInExam) {
		this.questionInExam = questionInExam;
	}
	
	



}
