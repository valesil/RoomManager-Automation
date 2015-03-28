package tests.tablet.homeUI;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.rest.RootRestMethods;
import framework.utils.DataProviders;
import framework.utils.readers.ExcelReader;

/**
 * Data provider to verify that {now} tile and {next} tile displayed correctly meeting subject.
 * @author Eliana Navia
 *
 */
public class MeetingSubjectIsCorrectlyDisplayedInNowTile {	
	HomeTabletPage homeTabletPage = new HomeTabletPage();
	SchedulePage schedulePage;

	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	String password = meetingData.get(0).get("Password");
	String organizer = meetingData.get(0).get("Organizer");
	String authentication = organizer + ":" + password;

	/**
	 * @param organizer
	 * @param subject 1: simple, 2: 100 characters, 3: With space before characters  
	 * @param minutesFrom
	 * @param minutesTo
	 * @param attendee
	 * @param password
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	@Test(dataProvider="CurrentMeetingDataProvider", dataProviderClass = DataProviders.class, 
			groups = "Acceptance")
	public void testMeetingSubjectIsDisplayedCorrectlyOnNowTile(String organizer, String subject, 
			String minutesFrom, String minutesTo, String attendee, String password) 
					throws MalformedURLException, IOException {

		schedulePage = homeTabletPage.clickScheduleBtn();

		schedulePage.createMeeting(organizer, subject, minutesFrom, minutesTo, attendee, password)
		.clickBackBtn();

		Assert.assertEquals(homeTabletPage.getNowTileLbl(), subject);
		RootRestMethods.deleteMeeting(meetingData.get(0).get("Room"), subject, authentication);
	}
}
