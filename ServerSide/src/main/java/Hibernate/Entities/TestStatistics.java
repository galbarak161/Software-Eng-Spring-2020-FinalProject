package Hibernate.Entities;

import javax.persistence.*;

@Entity
@Table(name = "Test_Statistics")
public class TestStatistics {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TestStatisticsId")
	private int id;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "testId")
	private Test test;
	
	@Column(name = "numberOfStudentsInTest")
	private int numberOfStudentsInTest;
	
	@Column(name = "numberOfStudentsThatFinishedInTime")
	private int numberOfStudentsThatFinishedInTime;
	
	@Column(name = "averageGrade")
	private double averageGrade;
	
	public TestStatistics() {
	}

	public TestStatistics(Test test) {
		setTest(test);
	}

	public int getId() {
		return id;
	}
	
	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
		test.setStatistics(this);
	}

	public int getNumberOfStudentsInTest() {
		return numberOfStudentsInTest;
	}

	public void increaseNumberOfStudentsInTest() {
		this.numberOfStudentsInTest++;
	}

	public int getNumberOfStudentsThatFinishedInTime() {
		return numberOfStudentsThatFinishedInTime;
	}

	public void increaseNumberOfStudentsThatFinishedInTime() {
		this.numberOfStudentsThatFinishedInTime++;
	}

	public double getAverageGrade() {
		return averageGrade;
	}

	public void setAverageGrade(double averageGrade) {
		this.averageGrade = averageGrade;
	}


	
	
	
	
	
}
