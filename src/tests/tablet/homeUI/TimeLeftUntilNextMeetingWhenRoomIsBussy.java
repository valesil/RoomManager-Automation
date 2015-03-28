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
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

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

	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");

	//Current meeting values
	String organizerCurrentMeeting = meetingData.get(0).get("Organizer");
	String expectedCurrentMeetingSubject = meetingData.get(0).get("Subject");
	String attendeeCurrentMeeting = meetingData.get(0).get("Attendee");
	String minStartTimeCurrentMeeting = meetingData.get(0).get("Start time (minutes to add)");
	String minEndTimeCurrentMeeting = meetingData.get(0).get("End time (minutes to add)");
	String passwordCurrentMeeting = meetingData.get(0).get("Password");
	String authenticationCurrentMeeting = organizerCurrentMeeting + ":" + passwordCurrentMeeting;

	//Next meeting values
	String organizerNextMeeting = meetingData.get(1).get("Organizer");
	String expectedNextMeetingSubject = meetingData.get(1).get("Subject");
	String attendeeNextMeeting = meetingData.get(1).get("Attendee");
	String minStartTimeNextMeeting = meetingData.get(1).get("Start time (minutes to add)");
	String minEndTimeNextMeeting = meetingData.get(1).get("End time (minutes to add)");
	String passwordNextMeeting = meetingData.get(1).get("Password");
	String authenticationNextMeeting = organizerNextMeeting + ":" + passwordNextMeeting;

	@Test (groups = "ACCEPTANCE")
	public void testTimeLeftUntilNextMeetingWhenRoomIsBussy() {
		schedulePage = homeTabletPage.clickScheduleBtn();
		schedulePage.createMeeting(organizerCurrentMeeting, expectedCurrentMeetingSubject, 
				minStartTimeCurrentMeeting, minEndTimeCurrentMeeting, attendeeCurrentMeeting, 
				passwordCurrentMeeting);

		schedulePage.createMeeting(organizerNextMeeting, expectedNextMeetingSubject, 
				minStartTimeNextMeeting, minEndTimeNextMeeting, attendeeNextMeeting, 
				passwordNextMeeting);

		schedulePage.clickOverMeetingCreated(expectedNextMeetingSubject);
		String startTime =  schedulePage.getStartTimeTxtBoxValue().substring(0, 5);
		String endTime =  schedulePage.getEndTimeTxtBoxValue().substring(0, 5);	
		schedulePage.clickBackBtn();
		
		
		String actualTimeLeft = homeTabletPage.getTimeLeftLbl();
		String expectedTimeLeft = differenceBetweenTimes(getCurrentDate("HH:mm"), startTime);
		
		Assert.assertEquals(actualTimeLeft, expectedTimeLeft );

		/**Assertions when current meeting and next meeting have a buffer of time.*/
		String actualMessageNextTile = homeTabletPage.getNextTileLbl();
		String expectedMessageNextTile = "Available"; 

		//Available message displayed in next tile
		Assert.assertEquals(actualMessageNextTile, expectedMessageNextTile);

		String actualColorNextTile = homeTabletPage.getNextTileColor();
		String expectedColorNextTile = meetingData.get(0).get("Available color"); 

		//Available color in next tile
		Assert.assertEquals(actualColorNextTile, expectedColorNextTile);	

		String actualStartTime = homeTabletPage.getStartTimeNextMeetingLbl();	
		String actualEndTime = homeTabletPage.getEndTimeNextMeetingLbl();

		//Verify that the interval is correctly displayed in the next tile.
		Assert.assertEquals(actualStartTime, startTime );
		Assert.assertEquals(actualEndTime, endTime );
	}

	/**
	 * deleteMeeting
	 */
	@AfterClass (groups = "ACCEPTANCE")
	public void end() {
		PropertyConfigurator.configure("log4j.properties");
		try {
			RootRestMethods.deleteMeeting(meetingData.get(0).get("Room"), expectedNextMeetingSubject, 
					authenticationNextMeeting);
			log.info("The meeting:" + expectedNextMeetingSubject + "has been deleted successfully.");	
			
			RootRestMethods.deleteMeeting( meetingData.get(0).get("Room"), expectedCurrentMeetingSubject, 
					authenticationCurrentMeeting);
			log.info("The meeting:" + expectedCurrentMeetingSubject + "has been deleted successfully.");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
