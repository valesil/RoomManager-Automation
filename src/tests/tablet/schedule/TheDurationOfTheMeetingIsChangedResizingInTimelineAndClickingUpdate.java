package tests.tablet.schedule;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.pages.tablet.SearchPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * @title Verify the duration of the meeting can be changed by resizing the box in 
 * the timeline and clicking update
 * @author Jose Cabrera
 */
public class TheDurationOfTheMeetingIsChangedResizingInTimelineAndClickingUpdate {
	HomeTabletPage home = new HomeTabletPage();
	SchedulePage schedule = new SchedulePage();
	SearchPage search = new SearchPage();
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	String organizer = meetingData.get(1).get("Organizer");
	String subject = meetingData.get(1).get("Subject");
	String attendee = meetingData.get(1).get("Attendee");
	String password = meetingData.get(1).get("Password");
	
	@BeforeClass
	public void createNextMeeting() {
		String minStartTime = "20";
		String minEndTime = "55";
		home.clickScheduleBtn();
		schedule.createMeeting(organizer, subject, minStartTime, minEndTime, 
				attendee, password)
				.clickBackBtn();
	}
	
	@Test(groups = "UI")
	public void testMeetingDurationIsChangedResizingInTimelineAndClickingUpdate() {
		home.clickScheduleBtn();
		schedule.clickOverMeetingCreated(subject);
		String startTime = schedule.getStartTimeTxtBoxValue();
		schedule.resizeMeetingLeft(subject)
		.clickUpdateBtn()
		.confirmCredentials(password);
		schedule.isMessageMeetingUpdatedDisplayed();
		Assert.assertFalse(schedule.getStartTimeTxtBoxValue().equals(startTime));
	}

	@AfterClass
	public void toHome() throws MalformedURLException, IOException {
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData = excelReader.getMapValues("Search");
		String roomName = testData.get(0).get("Room Name");
		RootRestMethods.deleteMeeting(roomName, subject, "administrator:Control123");
		schedule.clickBackBtn();
	}
}
