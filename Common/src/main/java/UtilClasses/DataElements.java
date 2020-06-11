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
	 * GetAllTimeExtensionRequestRequests (NULL)
	 * GetAllTestRelatedToTeacher (CloneUser)
	 * GetAllStudntTestRelatedToTest (CloneTest)
	 * GetAllExamsOfTeacher(CloneUser)
	 * GetAllExamsOfTeacherInCourse(CloneTeacherCourse)
	 * GetAnswerToTimeExtensionRequest (CloneTimeExtensionRequest)
	 * GetStudentTestRelatedToStudentInExam (StudentExamCode)
	 * GetAllQuestionInExamRelatedToExam (CloneExam)
	 * 
	 * UserLogIn (Login)
	 * UserLogOut (int userId)
	 * 
	 * CreateNewQuestion (CloneQuestion) 
	 * CreateNewExam (CloneExam) 
	 * CreateNewTest (CloneTest)
	 * CreateNewTimeExtensionRequest (CloneTimeExtensionRequest)
	 * 
	 * UpdateTimeExtension (CloneTimeExtensionRequest)
	 * 
	 * StudentStartsTest (StudentStartTest)
	 * StudentFinishedTest (CloneStudentTest)
	 * 
	 * TeacherUpdateGrade(List<CloneStudentTest>)
	 * 
	 * Error (NULL)
	 * 
	 * @author Gal
	 *
	 */
	
	public enum ClientToServerOpcodes {
		GetAllExams, GetAllTests, GetAllQuestion, GetAllQuestionInCourse, GetAllCoursesOfTeacher, GetAllTestsOfTeacher,
		GetAllTestsOfTeacherInCourse, GetAllStudentTests, GetAllTimeExtensionRequestRequests, GetAllTestRelatedToTeacher,
		GetAllStudntTestRelatedToTest, GetAllExamsOfTeacher, GetAllExamsOfTeacherInCourse,
		GetAnswerToTimeExtensionRequest,GetStudentTestRelatedToStudentInExam, GetAllQuestionInExamRelatedToExam,
		UserLogIn, UserLogOut, CreateNewQuestion, CreateNewExam, CreateNewTest,
		CreateNewTimeExtensionRequest, UpdateTimeExtension, StudentStartsTest, StudentFinishedTest, TeacherUpdateGrade, Error;
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
	 * SendAllTimeExtensionRequestRequests (List <CloneTimeExtensionRequest>) 
	 * SendAllTestRelatedToTeacher (List <CloneTest>)
	 * SendAllStudntTestRelatedToTest (List <CloneStudentTest>)
	 * SendAllExamsOfTeacher(List<CloneExam>)
	 * SendAllExamsOfTeacherInCourse (List<CloneExam>)
	 * SendStudentTestRelatedToStudentInExam (CloneStudentTest)
	 * SendAllQuestionInExamRelatedToExam (List<QiestionInExam>)
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
	 * 
	 * StudentStartsTestResult (int)
	 * StudentFinishedTestResult (int)
	 * 
	 * TeacherUpdateGradeResult (int)
	 * 
	 * Error(NULL)
	 * 
	 * @author Gal
	 *
	 */
	public enum ServerToClientOpcodes {
		SendAllExams, SendAllTests, SendAllQuestion, SendAllQuestionInCourse, SendAllCoursesOfTeacher,
		SendAllTestsOfTeacher, SendAllTestsOfTeacherInCourse, SendAllStudentTests, SendAllTimeExtensionRequestRequests,
		SendAllTestRelatedToTeacher, SendAllStudntTestRelatedToTest, SendAllExamsOfTeacher,
		SendAllExamsOfTeacherInCourse, SendStudentTestRelatedToStudentInExam, SendAllQuestionInExamRelatedToExam,
		UserLoggedIn, UserLoggedOut, CreateNewQuestionResult, CreateNewExamResult, CreateNewTestResult,
		CreateNewTimeExtensionRequestResult, UpdateTimeExtensionRequest, StudentStartsTestResult, StudentFinishedTestResult, TeacherUpdateGradeResult, Error;
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