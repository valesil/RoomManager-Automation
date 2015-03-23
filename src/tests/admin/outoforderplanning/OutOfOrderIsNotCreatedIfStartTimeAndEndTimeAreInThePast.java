package tests.admin.outoforderplanning;

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
 * TC18: Verify an Out Of Order cannot be created when the start date and end date are in the past
 * TC38: Verify when the start date and end date are in the past the following message is displayed 
 * "Cannot establish out of order as a past event" 
 * when the start time and end time are in the past
 * @author Yesica Acha
 *
 */
public class OutOfOrderIsNotCreatedIfStartTimeAndEndTimeAreInThePast {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
	String roomName = testData.get(5).get("Room Name");
	String startTime = testData.get(5).get("Start time (minutes to add)");
	String endTime = testData.get(5).get("End time (minutes to add)");

	@Test
	public void testOutOfOrderIsNotCreatedIfStartTimeAndEndTimeAreInThePastCorrectMessageIsDisplayed () {
		HomeAdminPage homeAdminPage = new HomeAdminPage(); 
		RoomsPage conferenceRoom = homeAdminPage.clickConferenceRoomsLink();
		RoomOutOfOrderPlanningPage outOfOrder = conferenceRoom
				.doubleClickOverRoomName(roomName)
				.clickOutOfOrderPlanningLink();
		outOfOrder.setStartTime(startTime)
		.setEndTime(endTime)
		.clickSaveWithErrorBtn();

		//Assertion for TC18
		Assert.assertTrue(outOfOrder.isErrorMessagePresent());

		//Assertion for TC38
		Assert.assertTrue(outOfOrder.isOutOfOrderInThePastErrorDisplayed());
		outOfOrder.clickCancelBtn();
	}
}
