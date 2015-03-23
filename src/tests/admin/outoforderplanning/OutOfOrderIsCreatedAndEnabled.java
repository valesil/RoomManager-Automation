package tests.admin.outoforderplanning;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomOutOfOrderPlanningPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC12: Verify an Out Of Order can be created and enabled in Out Of Order Planning
 * TC25: Verify that when an Out Of Order is created and enabled a message that says 
 * "Out of order was created successfully." is displayed
 * @author Yesica Acha
 *
 */
public class OutOfOrderIsCreatedAndEnabled {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
	String roomName = testData.get(4).get("Room Name");
	String startDate = testData.get(4).get("Start date");
	String endDate = testData.get(4).get("End date");
	String startTime = testData.get(4).get("Start time (minutes to add)");
	String endTime = testData.get(4).get("End time (minutes to add)");
	String title = testData.get(4).get("Title");
	String description = testData.get(4).get("Description");
	
	@Test
	public void testOutOfOrderIsCreatedAndEnabled() {
		HomeAdminPage homeAdminPage = new HomeAdminPage(); 
		RoomsPage rooms = homeAdminPage.clickConferenceRoomsLink();
		RoomOutOfOrderPlanningPage outOfOrder = rooms
				.doubleClickOverRoomName(roomName)
				.clickOutOfOrderPlanningLink();
		rooms = outOfOrder.setOutOfOrderPeriodInformation(startDate, endDate, 
				startTime, endTime, title, description)
				.activateOutOfOrder()
				.clickSaveOutOfOrderBtn();
		
		//Assertion for TC12
		Assert.assertTrue(rooms.isOutOfOrderIconDisplayed(roomName));
		
		//Assertion for TC25
		Assert.assertTrue(rooms.isMessagePresent());
		Assert.assertTrue(rooms.isOutOfOrderSuccessfullyCreatedMessageDisplayed());
	}
	
	@AfterMethod
	public void postCondition() {
		RootRestMethods.deleteOutOfOrder(roomName, title);
	}
}
