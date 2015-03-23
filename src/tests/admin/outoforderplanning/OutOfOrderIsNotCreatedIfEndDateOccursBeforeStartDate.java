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
 * TC20: Verify an Out Of Order cannot be created when end date (day, month, year) occurs 
 * before start date
 * TC39: Verify when end date (day, month, year) occurs before start date an error message 
 * that says "'To' field must be greater than 'From' field" is displayed.
 * @author Yesica Acha
 *
 */
public class OutOfOrderIsNotCreatedIfEndDateOccursBeforeStartDate {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
	String startDate = testData.get(0).get("Start date");
	String endDate = testData.get(0).get("End date");
	String roomName = testData.get(0).get("Room Name");
	
	@Test
	public void testOutOfOrderIsNotCreatedIfEndDateOccursBeforeStartDate() {
		HomeAdminPage homeAdminPage = new HomeAdminPage(); 
		RoomsPage rooms = homeAdminPage.clickConferenceRoomsLink();
		RoomOutOfOrderPlanningPage outOfOrder = rooms
				.doubleClickOverRoomName(roomName)
				.clickOutOfOrderPlanningLink();
		outOfOrder.setStartDateWithCalendar(startDate)
		.setEndDateWithCalendar(endDate)
		.clickSaveWithErrorBtn();
		
		//Assertion for TC20
		Assert.assertTrue(outOfOrder.isErrorMessagePresent());
		
		//Assertion for TC39
		Assert.assertTrue(outOfOrder.isToGreaterThanFromErrorDisplayed());
		outOfOrder.clickCancelBtn();
	}
}
