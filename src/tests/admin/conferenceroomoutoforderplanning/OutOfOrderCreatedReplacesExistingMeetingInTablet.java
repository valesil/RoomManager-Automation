package tests.admin.conferenceroomoutoforderplanning;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
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
 * TC14: Verify that an Out Of Order created in the same time a scheduled meeting replaces 
 * the meeting in Tablet's Scheduler 
 * TC15: Verify that an Out Of Order created in the same time a scheduled meeting replaces 
 * the meeting in Search Page in Tablet
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
	String meetingSubject;

	@BeforeClass
	public void selectRoominTablet() {
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SettingsPage settingsPage = homeTabletPage.clickSettingsBtn();
		homeTabletPage = settingsPage.selectRoom(roomName);
	}
	
	@Test(dataProvider = "OutOfOrderAndMeeting", dataProviderClass = DataProviders.class)
	public void testOutOfOrderCreatedReplacesExistingMeetingInTablet (String subject, 
			String meetingStartTime, String meetingEndTime, String outStartTime, String outEndTime ) {
		meetingSubject = subject;
		
		//Meeting creation in tablet
		HomeTabletPage homeTabletPage = new HomeTabletPage();
		SchedulePage schedulePage = homeTabletPage
				.clickScheduleBtn()
				.createMeeting(organizer, meetingSubject, meetingStartTime, 
						meetingEndTime, attendees, password);

		//Out Of Order Creation in Admin
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RoomsPage roomsPage = homeAdminPage
				.clickConferenceRoomsLink();
		RoomOutOfOrderPlanningPage outOfOrderPage = roomsPage
				.doubleClickOverRoomName(roomName)
				.clickOutOfOrderPlanningLink();
		roomsPage = outOfOrderPage.setStartTime(outStartTime)
				.setEndTime(outEndTime)
				.setTitleTxtBox(title)
				.activateOutOfOrder()
				.clickSaveBtn();

		homeTabletPage = new HomeTabletPage();
		SearchPage searchPage = homeTabletPage.clickSearchBtn();

		//Assertion for TC15
		Assert.assertTrue(searchPage.isOutOfOrderBoxDisplayed(title));
		Assert.assertTrue(searchPage.isMeetingBoxDisplayed(subject));

		//Assertion for TC14
		schedulePage = searchPage
				.clickBackBtn()
				.clickScheduleBtn();
		Assert.assertTrue(schedulePage.isOutOfOrderBoxDisplayed(title));
		Assert.assertTrue(schedulePage.isMeetingBoxDisplayed(subject));
	}

	@AfterMethod
	public void deleteOutOfOrderAndMeeting() throws MalformedURLException, IOException {
		RootRestMethods.deleteOutOfOrder(roomName, title);
		String authentication = organizer + ":" + password;
		RootRestMethods.deleteMeeting(roomName, meetingSubject, authentication);
	}
}
