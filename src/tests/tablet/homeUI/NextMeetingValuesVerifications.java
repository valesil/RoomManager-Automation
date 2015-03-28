package tests.tablet.homeUI;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;
import static framework.utils.TimeManager.differenceBetweenTimes;
import static framework.utils.TimeManager.getCurrentDate;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * This TCs verify that {now} tile is set whit correct information
 * when a meeting created in the current time.
 * TC06: Interval hours of next meeting.
 * TC03: Time left.
 * TC32: Busy color of next tile.
 * @author Eliana Navia
 */
public class NextMeetingValuesVerifications {
	Logger log = Logger.getLogger(getClass());
	HomeTabletPage homeTabletPage = new HomeTabletPage();
	SchedulePage schedulePage;

	//Data to create and use to assertions
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	String organizer = meetingData.get(1).get("Organizer");
	String expectedMeetingSubject = meetingData.get(1).get("Subject");
	String attendee = meetingData.get(1).get("Attendee");
	String minStartTime = meetingData.get(1).get("Start time (minutes to add)");
	String minEndTime = meetingData.get(1).get("End time (minutes to add)");
	String password = meetingData.get(1).get("Password");
	String authentication = organizer + ":" + password;

	String expectedStartTime = "";
	String expectedEndTime = "";

	/**
	 * Meeting scheduled in the room.
	 */
	@BeforeClass (groups = "ACCEPTANCE")
	public void init() {
		schedulePage = homeTabletPage.clickScheduleBtn();
		schedulePage.createMeeting(organizer, expectedMeetingSubject, 
				minStartTime, minEndTime, attendee, password)

				.clickOverMeetingCreated(expectedMeetingSubject);
		expectedStartTime =  schedulePage.getStartTimeTxtBoxValue().substring(0, 5);
		expectedEndTime =  schedulePage.getEndTimeTxtBoxValue().substring(0, 5);
		schedulePage.clickBackBtn();	
	}

	/**
	 * TC06: This test case verifies that interval of beginning-end time 
	 * of next meeting scheduled for the room is displayed in the {Next} Tape.
	 * 
	 */
	@Test (groups = "ACCEPTANCE")
	public void testIntervalTimeDisplayedForNextMeetingInRoom() {

		String actualStartTime = homeTabletPage.getStartTimeNextMeetingLbl();	
		String actualEndTime = homeTabletPage.getEndTimeNextMeetingLbl();

		Assert.assertEquals(actualStartTime, expectedStartTime );
		Assert.assertEquals(actualEndTime, expectedEndTime );
	}

	/**
	 * TC03: This test case verifies that time left displayed in {Now}Tape 
	 * is from current time until next meeting when any meeting is in progress.
	 * 
	 */
	@Test (groups = "ACCEPTANCE")
	public void testTimeLeftDisplayTimeUntilNextMeetingWhenRoomIsFreeBeforeInNowTape() {

		String expectedTimeLeft = differenceBetweenTimes(getCurrentDate("HH:mm"),
				expectedStartTime);
		String actualTimeLeft = homeTabletPage.getTimeLeftLbl();

		//Current time - start time of next meeting
		//Fails the rest is less a minute.
		Assert.assertEquals(actualTimeLeft, expectedTimeLeft );
	}

	/**
	 * TC32: Verify that next title changes the color 
	 * when a meeting is set to occur after some minutes of current time in the room.
	 * 
	 */
	@Test (groups = "ACCEPTANCE")
	public void testBussyColorIsDisplayedOnNextTileWhenAMeetingIsSetInTheRoom() {

		String actualBusyColorValue = homeTabletPage.getNextTileColor();
		String expectedBusyColorValue =  meetingData.get(0).get("Busy next color");

		Assert.assertEquals(actualBusyColorValue, expectedBusyColorValue);
	}

	/**
	 * Delete meeting by rest API.
	 */
	@AfterClass (groups = "ACCEPTANCE")
	public void end() {
		PropertyConfigurator.configure("log4j.properties");
		try {
			RootRestMethods.deleteMeeting( meetingData.get(0).get("Room"), expectedMeetingSubject, 
					authentication);
			log.info("The meeting:" + expectedMeetingSubject + "has been deleted successfully.");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
