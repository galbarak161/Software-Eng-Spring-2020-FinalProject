package CloneEntities;

import java.io.Serializable;

public class CloneTestStatistics implements Serializable {

	private static final long serialVersionUID = -3931484769030591262L;

	private int numberOfStudentsInTest;

	private int numberOfStudentsThatFinishedInTime;

	private double averageGrade;

	public CloneTestStatistics(int numberOfStudentsInTest, int numberOfStudentsThatFinishedInTime,
			double averageGrade) {
		this.numberOfStudentsInTest = numberOfStudentsInTest;
		this.numberOfStudentsThatFinishedInTime = numberOfStudentsThatFinishedInTime;
		this.averageGrade = averageGrade;
	}

	public int getNumberOfStudentsInTest() {
		return numberOfStudentsInTest;
	}

	public int getNumberOfStudentsThatFinishedInTime() {
		return numberOfStudentsThatFinishedInTime;
	}

	public double getAverageGrade() {
		return averageGrade;
	}
}
