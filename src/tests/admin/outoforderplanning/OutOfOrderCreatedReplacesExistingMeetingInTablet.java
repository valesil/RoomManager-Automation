package tests.admin.outoforderplanning;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomOutOfOrderPlanningPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.tablet.HomePage;
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
	HomeAdminPage homeAdminPage = new HomeAdminPage();	
	HomePage home;

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

	@BeforeClass
	public void selectRoom() {
		SettingsPage settings = new SettingsPage();
		home = settings.selectRoom(roomName);
	}
	@Test(dataProvider = "OutOfOrderAndMeeting", dataProviderClass = DataProviders.class)
	public void testOutOfOrderCreatedReplacesExistingMeetingInScheduler (String meetingSubject, 
			String meetingStartTime, String meetingEndTime, String outStartTime, String outEndTime ) {
		home.getHome()
		.clickScheduleBtn()
		.createMeetingRequiredInformation(organizer, meetingSubject, meetingStartTime, 
				meetingEndTime, attendees, password);

		//Out Of Order Creation in Admin
		RoomsPage conferenceRoom = homeAdminPage
				.getAdminHome()
				.clickConferenceRoomsLink();
		RoomOutOfOrderPlanningPage outOfOrder = conferenceRoom
				.doubleClickOverRoomName(roomName)
				.clickOutOfOrderPlanningLink();
		outOfOrder.setStartTime(outStartTime)
		.setEndTime(outEndTime)
		.setTitleTxtBox(title)
		.activateOutOfOrder()
		.clickSaveOutOfOrderBtn();

		SearchPage search =
				home.getHome()
				.clickSearchBtn();

		//Assertion for TC06
		Assert.assertTrue(search.isOutOfOrderBoxDisplayed(title));
		Assert.assertTrue(search.isMeetingBoxDisplayed(meetingSubject));

		//Assertion for TC05
		SchedulePage scheduler = search
				.clickBackBtn()
				.clickScheduleBtn();
		Assert.assertTrue(scheduler.isOutOfOrderBoxDisplayed(title));
		Assert.assertTrue(scheduler.isMeetingBoxDisplayed(meetingSubject));
	}

	@AfterMethod
	public void postCondition() {
//		home.getHome().clickScheduleBtn()
//		.deleteMeeting(meetingSubject, password);
		RootRestMethods.deleteOutOfOrder(roomName, title);
	}

	@AfterClass
	public void goAdminPage() {
			homeAdminPage.getAdminHome();
	}
}
