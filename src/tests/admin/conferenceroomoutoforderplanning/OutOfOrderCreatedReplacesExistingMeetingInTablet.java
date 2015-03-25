package tests.admin.conferenceroomoutoforderplanning;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomOutOfOrderPlanningPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SchedulePage;
import framework.pages.tablet.SearchPage;
import framework.pages.tablet.SettingsPage;
import framework.rest.RootRestMethods;
import framework.utils.DataProviders;
import framework.utils.readers.ExcelReader;

/**
 * TC05: Verify that an Out Of Order created in the same time a scheduled meeting 
 * replaces the meeting in Tablet's Scheduler
 * @author Yesica Acha
 *
 */
public class OutOfOrderCreatedReplacesExistingMeetingInTablet {

	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> outOfOrderData = excelReader.getMapValues("OutOfOrderPlanning");
	String roomName = outOfOrderData.get(10).get("Room Name");
	String startDate = outOfOrderData.get(10).get("Start date");
	String endDate = outOfOrderData.get(10).get("End date");
	String title = outOfOrderData.get(10).get("Title");

	List<Map<String, String>> meetingData = excelReader.getMapValues("MeetingData");
	String organizer = meetingData.get(7).get("Organizer");
	String attendees = meetingData.get(7).get("Attendee");
	String body = meetingData.get(7).get("Body");
	String password = meetingData.get(7).get("Password");
	String meetingSubject = meetingData.get(7).get("Subject");

	@Test(dataProvider = "OutOfOrderAndMeeting", dataProviderClass = DataProviders.class)
	public void testOutOfOrderCreatedReplacesExistingMeetingInScheduler (String meetingSubject, 
			String meetingStartTime, String meetingEndTime, String outStartTime, String outEndTime ) {
	
		//Meeting creation in tablet
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SettingsPage settingsPage = homeTabletPage.clickSettingsBtn();
		homeTabletPage = settingsPage.selectRoom(roomName);
		SchedulePage schedulePage = homeTabletPage
				.clickScheduleBtn()
				.createMeeting(organizer, meetingSubject, meetingStartTime, 
						meetingEndTime, attendees, password);

		//Out Of Order Creation in Admin
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage conferenceRoom = homeAdminPage
				.clickConferenceRoomsLink();
		RoomOutOfOrderPlanningPage outOfOrder = conferenceRoom
				.doubleClickOverRoomName(roomName)
				.clickOutOfOrderPlanningLink();
		conferenceRoom = outOfOrder.setStartTime(outStartTime)
				.setEndTime(outEndTime)
				.setTitleTxtBox(title)
				.activateOutOfOrder()
				.clickSaveBtn();

		homeTabletPage = new HomeTabletPage();
		SearchPage searchPage = homeTabletPage.clickSearchBtn();

		//Assertion for TC06
		Assert.assertTrue(searchPage.isOutOfOrderBoxDisplayed(title));
		Assert.assertTrue(searchPage.isMeetingBoxDisplayed(meetingSubject));

		//Assertion for TC05
		schedulePage = searchPage
				.clickBackBtn()
				.clickScheduleBtn();
		Assert.assertTrue(schedulePage.isOutOfOrderBoxDisplayed(title));
		Assert.assertTrue(schedulePage.isMeetingBoxDisplayed(meetingSubject));
	}

	@AfterMethod
	public void postCondition() {
		RootRestMethods.deleteOutOfOrder(roomName, title);
	}
}
