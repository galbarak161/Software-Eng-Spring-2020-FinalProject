package CloneEntities;

public class CloneTeacherCourse {

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
