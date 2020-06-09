package UtilClasses;

import java.io.Serializable;

public class DataElements implements Serializable {

	private static final long serialVersionUID = -2986035827318115652L;

	/**
	 * GetAllExams (NULL)
	 * GetAllTests (NULL)
	 * GetAllQuestion (NULL) 
	 * GetAllQuestionInCourse (CloneCourse)
	 * GetAllCoursesOfTeacher (CloneUser) 
	 * GetAllTestsOfTeacher (CloneUser)
	 * GetAllTestsOfTeacherInCourse (CloneTeacherCourse)
	 * GetAllStudentTests (CloneUser) 
	 * GetAllRequests (NULL)
	 * GetAllTestRelatedToTeacher (CloneUser)
	 * GetAllStudntTestRelatedToTest (CloneTest)
	 * GetAllExamsOfTeacher(CloneUser)
	 * GetAllExamsOfTeacherInCourse(CloneTeacherCourse)
	 * GetAnswerToTimeExtensionRequest (CloneTimeExtensionRequest)
	 * GetStudentTestRelatedToStudentInExam (StudentExamCode)
	 * 
	 * UserLogIn (Login)
	 * UserLogOut (int userId)
	 * 
	 * CreateNewQuestion (CloneQuestion) 
	 * CreateNewExam (CloneExam) 
	 * CreateNewTest (CloneTest)
	 * CreateNewTimeExtensionRequest (CloneTimeExtensionRequest)
	 * 
	 * Error (NULL)
	 * 
	 * @author Gal
	 *
	 */
	public enum ClientToServerOpcodes {
		GetAllExams, GetAllTests, GetAllQuestion, GetAllQuestionInCourse, GetAllCoursesOfTeacher, GetAllTestsOfTeacher,
		GetAllTestsOfTeacherInCourse, GetAllStudentTests, GetAllRequests, GetAllTestRelatedToTeacher,
		GetAllStudntTestRelatedToTest, GetAllExamsOfTeacher, GetAllExamsOfTeacherInCourse,
		GetAnswerToTimeExtensionRequest,GetStudentTestRelatedToStudentInExam, 
		UserLogIn, UserLogOut, CreateNewQuestion, CreateNewExam, CreateNewTest,
		CreateNewStudentTest, CreateNewTimeExtensionRequest, Error;
	}

	/**
	 * SendAllExams (List <CloneExam>) 
	 * SendAllTests (List <CloneTest>) 
	 * SendAllQuestion (List <CloneQuestion>) 
	 * SendAllQuestionInCourse (List <CloneQuestion>) 
	 * SendAllCoursesOfTeacher (List <CloneCourse>) 
	 * SendAllTestsOfTeacher (List <CloneTest>) 
	 * SendAllExamsOfTeacherInCourse (List <CloneExam>) 
	 * SendAllStudentTests (List <CloneStudentTest>) 
	 * SendAllRequests (List <CloneTimeExtensionRequest>) 
	 * SendAllTestRelatedToTeacher (List <CloneTest>)
	 * SendAllStudntTestRelatedToTest (List <CloneStudentTest>)
	 * SendAllExamsOfTeacher(List<CloneExam>)
	 * SendAllExamsOfTeacherInCourse (List<CloneExam>)
	 * SendStudentTestRelatedToStudentInExam (CloneStudentTest)
	 * 
	 * UserLoggedIn  (Login)
	 * UserLoggedOut (int userId)
	 * 
	 * CreateNewQuestionResult (CloneQuestion)  
	 * CreateNewExamResult (CloneExam) 
	 * CreateNewTestResult (CloneTest) 
	 * CreateNewTimeExtensionRequestResult (CloneTimeExtensionRequest)
	 * 
	 * UpdateTimeExtensionRequest (CloneTimeExtensionRequest)
	 * ErrorExamUnavailable
	 * Error(NULL)
	 * 
	 * @author Gal
	 *
	 */
	public enum ServerToClientOpcodes {
		SendAllExams, SendAllTests, SendAllQuestion, SendAllQuestionInCourse, SendAllCoursesOfTeacher,
		SendAllTestsOfTeacher, SendAllTestsOfTeacherInCourse, SendAllStudentTests, SendAllRequests,
		SendAllTestRelatedToTeacher, SendAllStudntTestRelatedToTest, CreateNewStudentTestResult, SendAllExamsOfTeacher,
		SendAllExamsOfTeacherInCourse, SendStudentTestRelatedToStudentInExam,
		UserLoggedIn, UserLoggedOut, CreateNewQuestionResult, CreateNewExamResult,
		CreateNewTestResult, CreateNewTimeExtensionRequestResult, UpdateTimeExtensionRequest, Error ,ErrorExamUnavailable;
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