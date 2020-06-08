package UtilClasses;

import java.io.Serializable;

import CloneEntities.CloneCourse;
import CloneEntities.CloneUser;

public class TeacherCourse implements Serializable {

	private static final long serialVersionUID = -6816617808206845265L;

	CloneUser teacher;
	CloneCourse course;

	public TeacherCourse(CloneUser teacher, CloneCourse course) {
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