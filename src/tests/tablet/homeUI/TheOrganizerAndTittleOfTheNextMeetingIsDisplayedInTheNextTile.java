package tests.tablet.homeUI;

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
 * TC12: Verify that the name of the next meeting in the room is displayed in Next Tile
 * TC13: Verify that the organizer of the next meeting in the room is displayed in the Next Tile
 * @author Jose Cabrera
 */
public class TheOrganizerAndTittleOfTheNextMeetingIsDisplayedInTheNextTile {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	String organizer = meetingData.get(1).get("Organizer");
	String subject = meetingData.get(1).get("Subject");
	String attendee = meetingData.get(1).get("Attendee");
	String password = meetingData.get(1).get("Password");
	
	@BeforeClass
	public void createNextMeeting() {
		HomeTabletPage homePage = new HomeTabletPage();
		String minStartTime = "20";
		String minEndTime = "55";
		SchedulePage schedule = homePage.clickScheduleBtn();
		schedule.createMeeting(organizer, subject, minStartTime, minEndTime, 
				attendee, password).clickBackBtn();
	}
	
	@Test(groups = "UI")
	public void testOrganizerOfTheNextMeetingIsDisplayedInTheNextTile () {
		HomeTabletPage homePage = new HomeTabletPage();
		Assert.assertTrue(homePage.getNextMeetingOrganizerNameLbl().equals(organizer)
				&&homePage.getNextTileLbl().equals(subject));
	}

	@AfterClass
	public void toHome() throws MalformedURLException, IOException {
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData = excelReader.getMapValues("Search");
		String roomName = testData.get(0).get("Room Name");
		RootRestMethods.deleteMeeting(roomName, subject, "administrator:Control123");
	}
}
