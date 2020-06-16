package CloneEntities;

import java.io.Serializable;

public class CloneTestStatistics implements Serializable {

	private static final long serialVersionUID = -3931484769030591262L;

	private int numberOfStudentsInTest;

	private int numberOfStudentsThatFinishedInTime;

	public CloneTestStatistics(int numberOfStudentsInTest, int numberOfStudentsThatFinishedInTime) {
		this.numberOfStudentsInTest = numberOfStudentsInTest;
		this.numberOfStudentsThatFinishedInTime = numberOfStudentsThatFinishedInTime;
	}

	public int getNumberOfStudentsInTest() {
		return numberOfStudentsInTest;
	}

	public int getNumberOfStudentsThatFinishedInTime() {
		return numberOfStudentsThatFinishedInTime;
	}
}
