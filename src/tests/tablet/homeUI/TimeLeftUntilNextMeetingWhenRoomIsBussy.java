package tests.tablet.homeUI;

import static tests.tablet.homeUI.HomeTabletSettings.createMeetingFromExcel;
import static tests.tablet.homeUI.HomeTabletSettings.getAuthenticationValue;
import static tests.tablet.homeUI.HomeTabletSettings.getMeetingSubject;
import static tests.tablet.homeUI.HomeTabletSettings.getRoomDisplayName;
import static framework.utils.TimeManager.differenceBetweenTimes;
import static framework.utils.TimeManager.getCurrentDate;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.rest.RootRestMethods;

/**
 * TC04: This test case verifies that time left displayed in {Now}Tape 
 * is from current time until next meeting when a meeting is in progress in the room.
 * @author Eliana Navia
 * 
 */
public class TimeLeftUntilNextMeetingWhenRoomIsBussy {
	Logger log = Logger.getLogger(getClass());
	HomeTabletPage homeTabletPage = new HomeTabletPage();
	SchedulePage schedulePage;
	String currentMeetingSubject = getMeetingSubject(0);
	String nextMeetingSubject = getMeetingSubject(1);

	/**
	 * Meeting in progress.
	 * Meeting scheduled.
	 */
	@BeforeClass
	public void preconditions() {
		schedulePage = homeTabletPage.clickScheduleBtn();
		createMeetingFromExcel(0);
		createMeetingFromExcel(1);
	}

	@Test (groups = {"ACCEPTANCE"})
	public void testTimeLeftUntilNextMeetingWhenRoomIsBussy() {

		String startTime = schedulePage.clickOverMeetingCreated(nextMeetingSubject)
				.getStartTimeTxtBoxValue().substring(0, 5);
		String expectedTimeLeft = differenceBetweenTimes(getCurrentDate("HH:mm"), startTime);
		schedulePage.clickBackBtn();

		String actualTimeLeft = homeTabletPage.getTimeLeftLbl();

		Assert.assertEquals(actualTimeLeft, expectedTimeLeft );
	}

	/**
	 * deleteMeeting
	 */
	@AfterClass
	public void end() {
		PropertyConfigurator.configure("log4j.properties");
		try {
			RootRestMethods.deleteMeeting(getRoomDisplayName(), currentMeetingSubject, 
					getAuthenticationValue(0));
			log.info("The meeting:" + currentMeetingSubject + "has been deleted successfully.");
			RootRestMethods.deleteMeeting(getRoomDisplayName(), nextMeetingSubject, 
					getAuthenticationValue(0));
			log.info("The meeting:" + nextMeetingSubject + "has been deleted successfully.");			
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
