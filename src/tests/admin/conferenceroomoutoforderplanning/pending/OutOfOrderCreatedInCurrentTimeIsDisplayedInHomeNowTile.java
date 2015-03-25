package tests.admin.conferenceroomoutoforderplanning.pending;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import framework.common.MeetingMethods;
import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomOutOfOrderPlanningPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.tablet.HomePage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC04: Verify that Calendar icon appears in out of order column when the schedule to be 
 * out of order is not in the time yet
 * @author Yesica Acha
 *
 */
public class OutOfOrderCreatedInCurrentTimeIsDisplayedInHomeNowTile {
	
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
	String roomName = testData.get(3).get("Room Name");
	String startDate = testData.get(3).get("Start date");
	String endDate = testData.get(3).get("End date");
	String startTime = testData.get(3).get("Start time (minutes to add)");
	String endTime = testData.get(3).get("End time (minutes to add)");
	String title = testData.get(3).get("Title");
	String description = testData.get(3).get("Description");
	
	@Test
	public void testOutOfOrderCreatedInCurrentTimeIsDisplayedInHomeNowTile() {
				
		//Out Of Order Creation in Admin
		HomeAdminPage homeAdminPage = new HomeAdminPage(); 
		RoomsPage conferenceRoom = homeAdminPage.clickConferenceRoomsLink();
		RoomOutOfOrderPlanningPage outOfOrder = conferenceRoom
				.doubleClickOverRoomName(roomName)
				.clickOutOfOrderPlanningLink();
		conferenceRoom = outOfOrder
				.setOutOfOrderPeriodInformation(startDate, endDate, startTime, endTime, title, description)
				.activateOutOfOrder()
				.clickSaveOutOfOrderBtn();
		
		//Openning Tablet for assertions
		MeetingMethods meetingMethods = new MeetingMethods();
		HomePage home = meetingMethods.getTabletHomeForSpecificRoom(roomName);
				
		//Assertion for TC04
		Assert.assertEquals(home.getNowTileLbl(), title);
	}
	
	@AfterClass
	public void deleteOutOfOrder(){
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RootRestMethods.deleteOutOfOrder(roomName, title);
		homeAdminPage.getAdminHome();
	}
}
