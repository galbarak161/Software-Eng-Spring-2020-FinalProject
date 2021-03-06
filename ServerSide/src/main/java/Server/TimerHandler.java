package Server;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimerTask;

import CloneEntities.CloneStudentTest.StudentTestStatus;
import CloneEntities.CloneTest.TestStatus;
import Hibernate.HibernateMain;
import Hibernate.Entities.StudentTest;
import Hibernate.Entities.Test;

public class TimerHandler extends TimerTask {

	public void run() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.YYYY");
		List<Test> testsFromDB = null;
		List<StudentTest> studentTestsFromDB = null;

		LocalTime currentTime = LocalTime.now();
		String currentDate = formatter.format(LocalDate.now());

		try {
			testsFromDB = HibernateMain.getDataFromDB(Test.class);
			for (Test test : testsFromDB) {
				if (test.getStatus() == TestStatus.Done)
					continue;

				LocalTime testStartTime = test.getTestTime();
				String testStartDate = test.getTestDate();

				// Check Test date
				if (testStartDate.equals(currentDate)) {

					// Check Test time
					if (test.getStatus() == TestStatus.Scheduled && testStartTime.isBefore(currentTime)) {
						// Test started
						test.setStatus(TestStatus.Ongoing);
						HibernateMain.UpdateDataInDB(test);

						studentTestsFromDB = HibernateMain.getDataFromDB(StudentTest.class);

						// Update all student tests
						for (StudentTest studentTest : studentTestsFromDB) {
							if (studentTest.getTest().getId() == test.getId()) {
								studentTest.setStatus(StudentTestStatus.Ongoing);
								HibernateMain.UpdateDataInDB(studentTest);
							}
						}
					}

					else if (testStartTime.plusMinutes(test.getTestDuration()).isBefore(currentTime)) {
						// Test finished
						test.setStatus(TestStatus.PendingApproval);
						HibernateMain.UpdateDataInDB(test);

						studentTestsFromDB = HibernateMain.getDataFromDB(StudentTest.class);

						// Update all student tests
						for (StudentTest studentTest : studentTestsFromDB) {
							if (studentTest.getTest().getId() == test.getId()) {
								studentTest.setStatus(StudentTestStatus.WaitingForResult);
								HibernateMain.UpdateDataInDB(studentTest);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}