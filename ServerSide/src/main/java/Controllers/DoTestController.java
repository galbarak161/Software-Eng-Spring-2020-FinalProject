package Controllers;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import CloneEntities.*;
import CloneEntities.CloneStudentTest.StudentTestStatus;
import CloneEntities.CloneTest.*;
import CloneEntities.CloneTimeExtensionRequest.RequestStatus;
import Hibernate.HibernateMain;
import Hibernate.Entities.*;
import Server.SendEmail;
import Server.SendEmail.MessageType;
import UtilClasses.StudentStartTest;

public class DoTestController {

	private ServerOperations serverHandler = null;

	public DoTestController() {
		serverHandler = ServerOperations.getInstance();
	}

	/**
	 * handleCreateNewTest(CloneTest newCloneTest)
	 * 
	 * creates new Test based on CloneTest
	 * 
	 * @param newCloneTest a Clone exam entity that has all exam details.
	 * @return null if information is not complete , otherwise it updates the DB
	 *         with new test
	 * @throws Exception
	 */
	public CloneTest handleCreateNewTest(CloneTest newCloneTest) throws Exception {
		Teacher t = (Teacher) serverHandler.getUserByCloneId(newCloneTest.getTeacherId());
		Exam e = serverHandler.getExmaByCloneId(newCloneTest.getExamToExecute().getId());

		if (t == null || e == null)
			return null;

		Test newTest = new Test(newCloneTest.getTestDate(), newCloneTest.getTestTime(), newCloneTest.getType(), t, e,
				new TestStatistics());

		List<Test> tests = HibernateMain.getDataFromDB(Test.class);
		Boolean flag = true;

		while (flag) {
			flag = false;
			for (int i = 0; i < tests.size(); i++) {
				if (tests.get(i).getExecutionCode() == newTest.getExecutionCode()) {
					flag = true;
					newTest.setExecutionCode(newTest.TestCodeGenerator());
				}
			}
		}

		HibernateMain.insertDataToDB(newTest);

		System.out.println("New test added. Test id = " + newTest.getId() + ". Test execution code = "
				+ newTest.getExecutionCode());

		Course course = serverHandler.getCourseByCloneId(newCloneTest.getExamToExecute().getCourseId());

		List<Student> students = course.getStudents();
		for (Student student : students) {
			HibernateMain.insertDataToDB(new StudentTest(student, newTest));

			SendEmail mailer = new SendEmail(student.getEmailAddress(), MessageType.NewTest);
			Thread mailThread = new Thread(mailer);
			mailThread.start();
		}

		return newTest.createClone();
	}

	/**
	 * handleStudentStartsTest (CloneStudentTest)
	 * 
	 * The function update StudentTest status and test statistics
	 * 
	 * @param studentExamCode
	 * @return 1 If succeeded (Others -1)
	 * @throws Exception
	 */
	public List<Object> handleStudentStartsTest(StudentStartTest studentStartTest) throws Exception {
		try {
			Test t = serverHandler.getTestByExamCode(studentStartTest.getEexecutionCode());
			StudentTest st = serverHandler.getStudntTestInTestIdByUserId(t, studentStartTest.getUserId());
			TestStatistics statistics = serverHandler.getTestStatisticsByTestId(st.getTest().getId());

			if(t == null || st == null || statistics == null)
				throw new Exception("Error. Invalid perrmitions to start test");
			
			st.setStartTime(LocalTime.now());
			statistics.increaseNumberOfStudentsInTest();

			HibernateMain.UpdateDataInDB(st);
			Thread.sleep(100);
			HibernateMain.UpdateDataInDB(statistics);

			List<Object> toSend = new ArrayList<>();
			toSend.add(st.createClone());
			List<CloneQuestionInExam> qToSend = new ArrayList<>();
			for (QuestionInExam q : st.getTest().getExamToExecute().getQuestionInExam())
				qToSend.add(q.createClone());
			toSend.add(qToSend);
			return toSend;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * handleStudntFinshedTest (CloneStudentTest)
	 * 
	 * The function update StudentTest status after student finished
	 * 
	 * @param studentTest
	 * @return 1 If succeeded (Others -1)
	 * @throws Exception
	 */
	public int handleStudentFinishedTest(CloneStudentTest studentTest) {
		int status = 1;
		try {
			StudentTest st = serverHandler.getStudntTestByCloneId(studentTest.getId());
			TestStatistics statistics = serverHandler.getTestStatisticsByTestId(st.getTest().getId());
			Exam e = serverHandler.getExmaByCloneId(st.getTest().getExamToExecute().getId());

			st.setStatus(StudentTestStatus.WaitingForResult);
			st.setActualTestDurationInMinutes(studentTest.getActualTestDurationInMinutes());

			if (st.getTest().getStatus() == TestStatus.Ongoing)
				statistics.increaseNumberOfStudentsThatFinishedInTime();

			int grade = 0;

			// Calculate grade for automatic test
			if (st.getTest().getType() == ExamType.Automated) {
				CloneAnswerToQuestion[] cloneAnswersArr = studentTest.getAnswers();
				for (int i = 0; i < e.getQuestionInExam().size(); i++) {
					AnswerToQuestion answer = new AnswerToQuestion(cloneAnswersArr[i].getStudentAnswer(), st,
							cloneAnswersArr[i].getQuestion().getQuestionCode(), i);
					HibernateMain.insertDataToDB(answer);
				}

				List<QuestionInExam> questions = st.getTest().getExamToExecute().getQuestionInExam();
				List<AnswerToQuestion> answers = st.getAnswers();

				if (questions.size() != answers.size())
					throw new Exception("QuestionInExam and AnswerToQuestion are not in the same size");

				for (int i = 0; i < questions.size(); i++) {
					QuestionInExam DBQestion = null;
					List<QuestionInExam> DBquestions = serverHandler
							.getExmaByCloneId(st.getTest().getExamToExecute().getId()).getQuestionInExam();
					for (QuestionInExam questionInDB : DBquestions) {
						if (answers.get(i).getQuestionCode() == questionInDB.getQuestion().getQuestionCode()) {
							DBQestion = questionInDB;
						}
					}

					if (DBQestion == null) {
						throw new Exception("no QuestionCode has been found");
					}
					AnswerToQuestion answer = answers.get(i);

					if (answer.getStudentAnswer() == DBQestion.getQuestion().getCorrectAnswer())
						grade += questions.get(i).getPointsForQuestion();
				}
			}

			// Keep copy of manual test
			else {
				st.setCopyOfManualTest(studentTest.getUploadedFile());
			}

			// Update student grade - for manual test the grade will be 0
			st.setGrade(grade);

			HibernateMain.UpdateDataInDB(st);

			HibernateMain.UpdateDataInDB(statistics);

		} catch (Exception e) {
			status = -1;
		}
		return status;
	}

	/**
	 * handleTeacherUpdateGrade (List<CloneStudentTest>)
	 * 
	 * get all CloneStudentTest with approved grades from teacher and updates them
	 * in DataBase
	 * 
	 * @param cloneStudentTests with updates and approved grades
	 * @return 1 if all correct -1 if an error happened
	 * @throws Exception
	 */
	public int handleTeacherUpdateGrade(List<CloneStudentTest> cloneStudentTests) throws Exception {
		if (cloneStudentTests.isEmpty()) {
			return -1;
		}

		Test test = serverHandler.getTestByCloneId(cloneStudentTests.get(0).getTest().getId());
		test.setStatus(TestStatus.Done);

		HibernateMain.UpdateDataInDB(test);

		List<StudentTest> studentTests = test.getStudents();

		for (StudentTest studentTest : studentTests) {
			for (CloneStudentTest cStudentTest : cloneStudentTests) {
				if (cStudentTest.getStudent().getId() == studentTest.getStudent().getId()) {
					studentTest.setGrade(cStudentTest.getGrade());
					studentTest.setStatus(StudentTestStatus.Done);

					HibernateMain.UpdateDataInDB(studentTest);

					SendEmail mailer = new SendEmail(studentTest.getStudent().getEmailAddress(),
							MessageType.TestFinished);
					Thread mailThread = new Thread(mailer);
					mailThread.start();
					break;
				}
			}
		}

		return 1;
	}

	/**
	 * handleCreateNewTimeExtensionRequest (CloneTimeExtensionRequest)
	 * 
	 * creates new TimeExtensionRequest based on CloneTimeExtensionRequest
	 * 
	 * @param newCloneTimeExtensionRequest a Clone TimeExtensionRequest entity that
	 *                                     has all TimeExtensionRequest details.
	 * @return null if information is not complete , otherwise it updates the DB
	 *         with new TimeExtensionRequest
	 * @throws Exception
	 * 
	 */
	public CloneTimeExtensionRequest handleCreateNewTimeExtensionRequest(
			CloneTimeExtensionRequest newCloneTimeExtensionRequest) throws Exception {
		TimeExtensionRequest timeExtensionRequest = new TimeExtensionRequest(newCloneTimeExtensionRequest.getBody(),
				newCloneTimeExtensionRequest.getTimeToExtenedInMinute());
		if (newCloneTimeExtensionRequest.getTest() == null) {
			return null;
		}
		Test test = serverHandler.getTestByCloneId(newCloneTimeExtensionRequest.getTest().getId());
		timeExtensionRequest.setTest(test);
		test.setExtensionRequests(timeExtensionRequest);
		test.setStatus(TestStatus.OngoingRequested);

		HibernateMain.insertDataToDB(timeExtensionRequest);
		HibernateMain.UpdateDataInDB(test);

		Principal p = serverHandler.getPrincipalUser();
		
		SendEmail mailer = new SendEmail(p.getEmailAddress(), MessageType.NewTimeExtensionRequest);
		Thread mailThread = new Thread(mailer);
		mailThread.start();

		return timeExtensionRequest.createClone();

	}

	/**
	 * handleUpdateTimeExtensionRequest (cloneTimeExtensionRequest)
	 * 
	 * function receives answer on time extension request from client and updates
	 * Test entity in DB
	 * 
	 * @param cloneTimeExtensionRequest the time request with answer
	 * @return CloneTest with updates time
	 * @throws Exception
	 */
	public CloneTimeExtensionRequest handleUpdateTimeExtensionRequest(
			CloneTimeExtensionRequest cloneTimeExtensionRequest) throws Exception {

		List<TimeExtensionRequest> timeExtensionRequests = HibernateMain.getDataFromDB(TimeExtensionRequest.class);
		TimeExtensionRequest request = null;
		for (TimeExtensionRequest timeExtensionRequest : timeExtensionRequests) {
			if (timeExtensionRequest.getId() == cloneTimeExtensionRequest.getId()) {
				request = timeExtensionRequest;
			}
		}

		if (request == null)
			return null;

		if (cloneTimeExtensionRequest.isRequestConfirmed() == false) {
			request.setStatus(RequestStatus.Denied);
			return request.createClone();
		} else {
			request.setStatus(RequestStatus.Confirmed);
			request.setRequestConfirmed(true);
		}

		request.getTest().setStatus(TestStatus.OngoingAnswered);

		HibernateMain.UpdateDataInDB(request);
		HibernateMain.UpdateDataInDB(request.getTest());

		return request.createClone();
	}

	/**
	 * handleSendAllRequests()
	 * 
	 * sends all time extension requests
	 * 
	 * @return List<CloneTimeExtensionRequest> send all time extension requests
	 * @throws Exception
	 */
	public List<CloneTimeExtensionRequest> handleSendAllTimeExtensionRequests() throws Exception {
		List<TimeExtensionRequest> listFromDB = new ArrayList<TimeExtensionRequest>();
		List<CloneTimeExtensionRequest> cloneRequests = new ArrayList<CloneTimeExtensionRequest>();
		listFromDB = HibernateMain.getDataFromDB(TimeExtensionRequest.class);
		try {
			for (TimeExtensionRequest Tex : listFromDB) {
				cloneRequests.add(Tex.createClone());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return cloneRequests;
	}

	public int handleSendTimeExtensionRequestsRelatedToTest(int testId) throws Exception {
		List<TimeExtensionRequest> listFromDB = HibernateMain.getDataFromDB(TimeExtensionRequest.class);
		try {
			for (TimeExtensionRequest timeExtension : listFromDB) {
				if (timeExtension.getTest().getId() == testId) {
					if (timeExtension.getStatus() == RequestStatus.Confirmed)
						return timeExtension.getTimeToExtenedInMinute();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
