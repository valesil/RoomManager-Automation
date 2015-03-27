package tests.admin.conferenceroomoutoforderplanning;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomOutOfOrderPlanningPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.pages.tablet.SettingsPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC12: Verify a created Out Of Order's information is not displayed in the Scheduler by 
 * clicking on it in Scheduler page in the Tablet
 * TC13: Verify a created Out Of Order's information cannot be changed in the Scheduler Page 
 * in the Tablet
 * @author Yesica Acha
 *
 */
public class OutOfOrderInformationIsNotDisplayedOrUpdatedInScheduler {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
	String roomName = testData.get(10).get("Room Name");
	String title = testData.get(10).get("Title");
	
	@Test(groups = "ACCEPTANCE")
	public void testOutOfOrderInformationIsNotDisplayedOrUpdatedInScheduler() {
		String startDate = testData.get(10).get("Start date");
		String endDate = testData.get(10).get("End date");
		String startTime = testData.get(10).get("Start time (minutes to add)");
		String endTime = testData.get(10).get("End time (minutes to add)");
		String description = testData.get(10).get("Description");
		
		//Out Of Order Creation in Admin
		HomeAdminPage homeAdminPage = new HomeAdminPage(); 
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		RoomOutOfOrderPlanningPage outOfOrderPage = roomInfoPage.clickOutOfOrderPlanningLink();
		roomsPage = outOfOrderPage
				.setOutOfOrderPeriodInformation(startDate, endDate, startTime, endTime, title, description)
				.activateOutOfOrder()
				.clickSaveOutOfOrderBtn();
		
		//Openning Tablet for assertions
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SettingsPage settingsPage = homeTabletPage.clickSettingsBtn();
		homeTabletPage = settingsPage.selectRoom(roomName);
		SchedulePage schedulerPage = homeTabletPage
				.clickScheduleBtn()
				.clickOverMeetingCreated(title);
		
		//Assertion for TC12
		Assert.assertTrue(schedulerPage.getMeetingOrganizerValue().isEmpty());
		Assert.assertTrue(schedulerPage.getMeetingSubjectValue().isEmpty());
		
		//Assertion for TC13
		Assert.assertFalse(schedulerPage.isUpdateBtnPresent());
	}
	
	@AfterClass
	public void deleteOutOfOrder() throws MalformedURLException, IOException{
		RootRestMethods.deleteOutOfOrder(roomName, title);
	}
}
