package CloneEntities;


public class CloneStudentTest {

	private int grade;

	// private List<Integer> questionsAnswers; // each integer is between 1..4

	private String examCheckNotes;

	private CloneUser student;

	private CloneTest test;

	public CloneStudentTest() {
		super();
	}

	public CloneStudentTest(CloneUser student, CloneTest test) {
		super();
		this.grade = -1;
		this.examCheckNotes = "";
		setTest(test);
		setStudent(student);
	}


	public CloneTest getTest() {
		return test;
	}

	public void setTest(CloneTest test) {
		this.test = test;
	}

	public CloneUser getStudent() {
		return student;
	}

	public void setStudent(CloneUser student) {
		this.student = student;
	}
}