package tests.tablet.schedule;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import framework.common.MeetingMethods;
import framework.pages.tablet.HomePage;
import framework.pages.tablet.SchedulePage;
import framework.pages.tablet.SearchPage;
import framework.utils.readers.ExcelReader;

/**
 * @title Verify the duration of the meeting cannot be changed by resizing the box in 
 * the timeline if update button is not clicked
 * @author Jose Cabrera
 */
public class TheDurationOfTheMeetingIsNotChangedResizingInTimelineButNotClickingUpdate {
	public HomePage home = new HomePage();
	public SchedulePage schedule = new SchedulePage();
	public SearchPage search = new SearchPage();
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	String organizer = meetingData.get(1).get("Organizer");
	String subject = meetingData.get(1).get("Subject");
	String attendee = meetingData.get(1).get("Attendee");
	String password = meetingData.get(1).get("Password");
	@BeforeClass
	public void createNextMeeting() {
		int minStartTime = 20;
		int minEndTime = 55;
		home.clickScheduleBtn();
		schedule.createMeeting(organizer, subject, minStartTime, minEndTime, 
				attendee, password)
				.clickBackBtn();
	}
	
	@Test(groups = {"UI"})
	public void testMeetingDurationIsNotChangedResizingInTimelineAndClickingUpdate () throws InterruptedException{
		home.clickScheduleBtn();
		schedule.clickOverMeetingCreated(subject);
		String start = schedule.getStartTimeTxtBoxValue();
		schedule.resizeMeetingLeft(subject)
		.clickOverTimeline()
		.clickOverMeetingCreated(subject);
		Assert.assertTrue(schedule.getStartTimeTxtBoxValue().equals(start));
	}

	@AfterClass
	public void toHome() {
		schedule.clickBackBtn();
		MeetingMethods meeting = new MeetingMethods();
		meeting.deleteMeeting(subject, password).clickBackBtn();
	}
}