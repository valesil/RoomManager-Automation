package tests.admin.outoforderplanning;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomOutOfOrderPlanningPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC19: Verify an Out Of Order can be created when the start date is in the past 
 * but the end date is in the future
 * @author Yesica Acha
 *
 */
public class OutOfOrderIsCreatedIfStartTimeIsInThePastAndEndTimeIsInTheFuture {

	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
	String roomName = testData.get(9).get("Room Name");
	String startDate = testData.get(9).get("Start date");
	String endDate = testData.get(9).get("End date");
	String startTime = testData.get(9).get("Start time (minutes to add)");
	String endTime = testData.get(9).get("End time (minutes to add)");
	String title = testData.get(9).get("Title");
	String description = testData.get(9).get("Description");
	
	@Test
	public void testOutOfOrderIsCreatedIfStartTimeIsInThePastAndEndTimeIsInTheFuture() {
		HomeAdminPage homeAdminPage = new HomeAdminPage(); 
		RoomsPage rooms = homeAdminPage.clickConferenceRoomsLink();
		RoomOutOfOrderPlanningPage outOfOrder = rooms
				.doubleClickOverRoomName(roomName)
				.clickOutOfOrderPlanningLink();
		rooms = outOfOrder
				.setOutOfOrderPeriodInformation(startDate, endDate, startTime, endTime, 
						title, description)
				.activateOutOfOrder()
				.clickSaveOutOfOrderBtn();
		Assert.assertTrue(rooms.isMessagePresent());
		Assert.assertTrue(rooms.isOutOfOrderSuccessfullyCreatedMessageDisplayed());
		Assert.assertTrue(rooms.isOutOfOrderIconDisplayed(roomName));
	}
	
	@AfterClass
	public void postCondition(){
		RootRestMethods.deleteOutOfOrder(roomName, title);
	}
}
