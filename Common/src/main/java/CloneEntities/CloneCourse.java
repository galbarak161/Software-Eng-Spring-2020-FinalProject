package CloneEntities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CloneCourse implements Serializable {

	private static final long serialVersionUID = -7891215908691101139L;

	private int id;

	private String courseName;

	private List<CloneQuestion> questions;

	private List<CloneStudy> studies;

	public CloneCourse() {
		this.questions = new ArrayList<CloneQuestion>();
		this.studies = new ArrayList<CloneStudy>();
	}

	public CloneCourse(int id, String courseName) {
		this.id = id;
		this.courseName = courseName;
		this.questions = new ArrayList<CloneQuestion>();
		this.studies = new ArrayList<CloneStudy>();
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

	public List<CloneStudy> getStudies() {
		return studies;
	}

	public void setStudies(List<CloneStudy> studies) {
		this.studies = studies;
	}
}
