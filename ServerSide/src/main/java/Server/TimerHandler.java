package Server;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.TimerTask;
import CloneEntities.CloneTest.TestStatus;
import Hibernate.HibernateMain;
import Hibernate.Entities.Test;

public class TimerHandler extends TimerTask {
	public void run() {
		List<Test> listFromDB = null;
		LocalTime currentTime = LocalTime.now();
		LocalDate currentDate = LocalDate.now();

		try {
			listFromDB = HibernateMain.getDataFromDB(Test.class);
			for (Test test : listFromDB) {
				if(test.getStatus() == TestStatus.Done)
					continue;
				
				LocalTime testStartTime = test.getTestTime();
				LocalDate testStartDate = test.getTestDate();
				
				// Check Test date
				if (testStartDate.equals(currentDate)) {
					
					// Check Test time
					if (test.getStatus() == TestStatus.Scheduled && testStartTime.isBefore(currentTime)) {
						// Test started
						test.setStatus(TestStatus.Ongoing);
						HibernateMain.UpdateDataInDB(test);
					}
					
					else if (test.getStatus() == TestStatus.Ongoing && testStartTime.plusMinutes(test.getTestDuration()).isAfter(currentTime)) {
						// Test finished
						test.setStatus(TestStatus.PendingApproval);
						HibernateMain.UpdateDataInDB(test);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}