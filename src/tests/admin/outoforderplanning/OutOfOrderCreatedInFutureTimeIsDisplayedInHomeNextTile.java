package tests.admin.outoforderplanning;

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
 * TC09: Verify that an Out Of Order created in the future is displayed in NEXT tile 
 * in the Home Page in Tablet on a room with no meetings
 * @author Yesica Acha
 *
 */
public class OutOfOrderCreatedInFutureTimeIsDisplayedInHomeNextTile {
	
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
	String roomName = testData.get(2).get("Room Name");
	String startDate = testData.get(2).get("Start date");
	String endDate = testData.get(2).get("End date");
	String startTime = testData.get(2).get("Start time (minutes to add)");
	String endTime = testData.get(2).get("End time (minutes to add)");
	String title = testData.get(2).get("Title");
	String description = testData.get(2).get("Description");
	
	@Test
	public void testOutOfOrderCreatedInFutureTimeIsDisplayedInHomeNextTile() {
				
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
		HomePage home = meetingMethods.getHomeForSpecificRoom(roomName);
				
		//Assert for TC
		Assert.assertEquals(home.getNextTileLbl(), title);
	}
	
	@AfterClass
	public void postCondition(){
		HomeAdminPage homeAdminPage = new HomeAdminPage();
		RootRestMethods.deleteOutOfOrder(roomName, title);
		homeAdminPage.getAdminHome();
	}
}
