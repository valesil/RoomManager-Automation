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
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC18: Verify the duration of the meeting cannot be changed by resizing the box in 
 * the timeline if update button is not clicked
 * @author Jose Cabrera
 */
public class TheDurationOfTheMeetingIsNotChangedResizingInTimelineButNotClickingUpdate {
	HomeTabletPage homePage = new HomeTabletPage();
	SchedulePage schedulePage = new SchedulePage();
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
		homePage.clickScheduleBtn();
		schedulePage.createMeeting(organizer, subject, minStartTime, minEndTime, 
				attendee, password)
				.clickBackBtn();
	}
	
	@Test(groups = "UI")
	public void testMeetingDurationIsNotChangedResizingInTimelineAndClickingUpdate () 
			throws InterruptedException {
		homePage.clickScheduleBtn();
		schedulePage.clickOverMeetingCreated(subject);
		String start = schedulePage.getStartTimeTxtBoxValue();
		schedulePage.resizeMeetingLeft(subject)
		.clickOverTimeline()
		.clickOverMeetingCreated(subject);
		Assert.assertTrue(schedulePage.getStartTimeTxtBoxValue().equals(start));
	}

	@AfterClass
	public void toHome() throws MalformedURLException, IOException {
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData = excelReader.getMapValues("Search");
		String roomName = testData.get(0).get("Room Name");
		RootRestMethods.deleteMeeting(roomName, subject, "administrator:Control123");
		schedulePage.clickBackBtn();
	}
}
