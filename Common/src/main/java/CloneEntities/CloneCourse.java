package CloneEntities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CloneCourse implements Serializable {

	private static final long serialVersionUID = -7891215908691101139L;

	private int id;

	private String courseName;

	private List<CloneQuestion> questions;

	private CloneStudy study;
	
	public CloneCourse() {
		this.questions = new ArrayList<CloneQuestion>();
	}

	public CloneCourse(String courseName) {
		this.courseName = courseName;
		this.questions = new ArrayList<CloneQuestion>();
	}

	@Override
	public String toString() {
		return this.getCourseName();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public List<CloneQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(List<CloneQuestion> questions) {
		this.questions = questions;
	}

	public CloneStudy getStudy() {
		return study;
	}

	public void setStudy(CloneStudy study) {
		this.study = study;
	}

}
