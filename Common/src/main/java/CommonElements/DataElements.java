package CommonElements;

import java.io.Serializable;

public class DataElements implements Serializable {

	private static final long serialVersionUID = -2986035827318115652L;

	// Opcodes 1-99

	/**
	 * GetAllExams (NULL)
	 * GetAllTests (NULL)
	 * GetAllQuestion (NULL) 
	 * GetAllQuestionInCourse (CloneCourse)
	 * GetAllCoursesOfTeacher (CloneUser) 
	 * GetAllTestsOfTeacher (CloneUser)
	 * GetAllTestsOfTeacherInCourse (CloneTeacherCourse)
	 * GetAllStudentTests (CloneUser) 
	 * 
	 * UserLogin (Login)
	 * 
	 * CreateNewQuestion (CloneQuestion) 
	 * CreateNewExam (CloneExam) 
	 * CreateNewTest (CloneTest)
	 * GetAllRequests(NULL)
	 * GetAllTestRelatedToTeacher(CloneUser)
	 * GetAllStudntTestRelatedToTest(CloneTest)
	 * 
	 * Error (NULL)
	 * 
	 * @author Gal
	 *
	 */
	public enum ClientToServerOpcodes {
		GetAllExams(1), GetAllTests(2), GetAllQuestion(3), GetAllQuestionInCourse(4), GetAllCoursesOfTeacher(5),
		GetAllTestsOfTeacher(6), GetAllTestsOfTeacherInCourse (7), GetAllStudentTests(8), UserLogin(9), CreateNewQuestion(10),
		CreateNewExam(11), CreateNewTest(12), GetAllRequests(13) , GetAllTestRelatedToTeacher(14), GetAllStudntTestRelatedToTest(15),Error(-1);

		public int value;

		private ClientToServerOpcodes(int value) {
			this.value = value;
		}
	}

	// Opcodes 101-199
	/**
	 * SendAllExams (List <CloneExam>) 
	 * SendAllTests (List <CloneTest>) 
	 * SendAllQuestion (List <CloneQuestion>) 
	 * SendAllQuestionInCourse (List <CloneQuestion>) 
	 * SendAllCoursesOfTeacher (List <CloneCourse>) 
	 * SendAllTestsOfTeacher (List <CloneTest>) 
	 * SendAllExamsOfTeacherInCourse (List <CloneExam>) 
	 * SendAllStudentTests (List <CloneStudentTest>) 
	 * 
	 * UserLoggedIn  (Login)
	 * 
	 * CreateNewQuestionResult (CloneQuestion)  
	 * CreateNewExamResult (CloneExam) 
	 * CreateNewTestResult (CloneTest) 
	 * SendAllRequests(List <CloneTimeExtensionRequest>) 
	 * SendAllTestRelatedToTeacher(List <CloneTest>)
	 * SendAllStudntTestRelatedToTest(List <CloneStudentTest>)
	 * 
	 * Error(NULL)
	 * 
	 * @author Gal
	 *
	 */
	public enum ServerToClientOpcodes {
		SendAllExams(101), SendAllTests(102), SendAllQuestion(103), SendAllQuestionInCourse(104),
		SendAllCoursesOfTeacher(105), SendAllTestsOfTeacher(106), SendAllTestsOfTeacherInCourse(107),
		SendAllStudentTests(108), UserLoggedIn(109), CreateNewQuestionResult(110), CreateNewExamResult(111),
		CreateNewTestResult(112), SendAllRequests(113), SendAllTestRelatedToTeacher(114),SendAllStudntTestRelatedToTest (115),Error(-1);

		public int value;

		private ServerToClientOpcodes(int value) {
			this.value = value;
		}
	}

	private ClientToServerOpcodes opcodeFromClient;
	private ServerToClientOpcodes opCodeFromServer;
	private Object data;

	public DataElements() {
		this.opcodeFromClient = ClientToServerOpcodes.Error;
		this.opCodeFromServer = ServerToClientOpcodes.Error;
		this.data = null;
	}

	public DataElements(ClientToServerOpcodes opCodeFromClient, Object data) {
		this.opcodeFromClient = opCodeFromClient;
		this.data = data;
	}

	public DataElements(ServerToClientOpcodes opCodeFromServer, Object data) {
		this.opCodeFromServer = opCodeFromServer;
		this.data = data;
	}

	public ClientToServerOpcodes getOpcodeFromClient() {
		return opcodeFromClient;
	}

	public void setOpcodeFromClient(ClientToServerOpcodes opcodeFromClient) {
		this.opcodeFromClient = opcodeFromClient;
	}

	public ServerToClientOpcodes getOpCodeFromServer() {
		return opCodeFromServer;
	}

	public void setOpCodeFromServer(ServerToClientOpcodes opCodeFromServer) {
		this.opCodeFromServer = opCodeFromServer;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}