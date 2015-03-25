package tests.admin.conferenceroomoutoforderplanning.pending;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomOutOfOrderPlanningPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.utils.readers.ExcelReader;

/**
 * TC20: Verify an Out Of Order cannot be created when the start date and end date are in the past
 * TC29: Verify when the start date and end date are in the past the following message is displayed 
 * "Cannot establish out of order as a past event"
 * @author Yesica Acha
 *
 */
public class OutOfOrderIsNotCreatedIfEndTimeOccursBeforeStartTime {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
	String roomName = testData.get(1).get("Room Name");
	String startDate = testData.get(1).get("Start date");
	String endDate = testData.get(1).get("End date");
	String startTime = testData.get(1).get("Start time (minutes to add)");
	String endTime = testData.get(1).get("End time (minutes to add)");

	@Test
	public void testOutOfOrderIsNotCreatedIfEndTimeOccursBeforeStartTime() {

		//Out Or Order creation
		HomeAdminPage homeAdminPage = new HomeAdminPage(); 
		RoomsPage rooms = homeAdminPage.clickConferenceRoomsLink();
		RoomOutOfOrderPlanningPage outOfOrder = rooms
				.doubleClickOverRoomName(roomName)
				.clickOutOfOrderPlanningLink();
		outOfOrder
				.setStartDateWithCalendar(startDate)
				.setEndDateWithCalendar(endDate)
				.setStartTime(startTime)
				.setEndTime(endTime)
				.clickSaveWithErrorBtn();

		//Assertion for TC21
		Assert.assertTrue(outOfOrder.isErrorMessagePresent());

		//Assertion for TC40
		Assert.assertTrue(outOfOrder.isToGreaterThanFromErrorDisplayed());
		outOfOrder.clickCancelBtn();
	}
}
