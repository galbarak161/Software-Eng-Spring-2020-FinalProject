package CloneEntities;

import java.io.Serializable;

public class CloneTeacherCourse implements Serializable{


	private static final long serialVersionUID = -6816617808206845265L;
	
	CloneUser teacher;
	CloneCourse course;

	public CloneTeacherCourse(CloneUser teacher, CloneCourse course) {
		this.teacher = teacher;
		this.course = course;
	}

	public CloneUser getTeacher() {
		return teacher;
	}

	public CloneCourse getCourse() {
		return course;
	}
	
	
}
