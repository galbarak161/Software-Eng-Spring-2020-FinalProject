package CloneEntities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CloneCourse implements Serializable {

	private static final long serialVersionUID = -7891215908691101139L;

	private int id;

	private String courseName;

	public CloneCourse(int id, String courseName) {
		this.id = id;
		this.courseName = courseName;
	}

	public int getId() {
		return id;
	}

	public String getCourseName() {
		return courseName;
	}

	@Override
	public String toString() {
		return this.getCourseName();
	}
}
