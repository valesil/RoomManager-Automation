package tests.admin.conferenceroomoutoforderplanning.pending;

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
import framework.utils.TimeManager;
import framework.utils.readers.ExcelReader;

/**
 * TC12: Verify an Out Of Order can be created and enabled in Out Of Order Planning
 * TC25: Verify that when an Out Of Order is created and enabled a message that says 
 * "Out of order was created successfully." is displayed
 * @author Yesica Acha
 *
 */
public class IfOfOutOfOrderIsCreatedItsInformationIsDisplayedInOutOfOrderPage {
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
	public void testIfOfOutOfOrderIsCreatedItsInformationIsDisplayedInOutOfOrderPage() {
		String expectedStartDate = TimeManager.changeDateFormat(startDate, "yyyy/MMM/dd", "MMM dd YYYY");
		String expectedEndDate = TimeManager.changeDateFormat(endDate, "yyyy/MMM/dd", "MMM dd YYYY");
		String expectedStartTime = TimeManager.getTime(Integer.parseInt(startTime), "hh:mm");
		String expectedEndTime = TimeManager.getTime(Integer.parseInt(endTime), "hh:mm");
		HomeAdminPage homeAdminPage = new HomeAdminPage(); 
		RoomsPage rooms = homeAdminPage.clickConferenceRoomsLink();
		RoomOutOfOrderPlanningPage outOfOrder = rooms
				.doubleClickOverRoomName(roomName)
				.clickOutOfOrderPlanningLink();
		System.out.println(startDate);
		rooms = outOfOrder.setOutOfOrderPeriodInformation(startDate, endDate, 
				startTime, endTime, title, description)
				.activateOutOfOrder()
				.selectEmailNotificationChkBox()
				.clickSaveOutOfOrderBtn();
		outOfOrder = rooms
				.doubleClickOverRoomName(roomName)
				.clickOutOfOrderPlanningLink();
		
		Assert.assertEquals(outOfOrder.getStartDateValue(), expectedStartDate);
		Assert.assertEquals(outOfOrder.getEndDateValue(), expectedEndDate);
		Assert.assertEquals(outOfOrder.getStartTimeValue(), expectedStartTime);
		Assert.assertEquals(outOfOrder.getEndTimeValue(), expectedEndTime);
		Assert.assertEquals(outOfOrder.getTitleValue(), title);
		Assert.assertEquals(outOfOrder.getDescriptionValue(), description);
		Assert.assertTrue(outOfOrder.isOutOfOrderActivated());
	}
	
	@AfterMethod
	public void postCondition() {
		RootRestMethods.deleteOutOfOrder(roomName, title);
	}
}
