package tests.admin.conferenceroomoutoforderplanning;

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
 * TC05: Verify that Clock icon appears in out of order column when the schedule to be 
 * out of order is current time
 * @author Yesica Acha
 *
 */
public class IfOutOfOrderIsCreatedInThePresentClockIconIsDisplayed {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
	String roomName = testData.get(3).get("Room Name");
	String startDate = testData.get(3).get("Start date");
	String endDate = testData.get(3).get("End date");
	String startTime = testData.get(3).get("Start time (minutes to add)");
	String endTime = testData.get(3).get("End time (minutes to add)");
	String title = testData.get(3).get("Title");
	String description = testData.get(3).get("Description");
	String expectedIcon = testData.get(3).get("Icon").toLowerCase().replaceAll(" ", "");

	@Test
	public void testIfOutOfOrderIsCreatedInThePresentClockIconIsDisplayed() {

		//Out of Order creation
		HomeAdminPage homeAdminPage = new HomeAdminPage(); 
		RoomsPage conferenceRoom = homeAdminPage.clickConferenceRoomsLink();
		RoomOutOfOrderPlanningPage outOfOrder = conferenceRoom
				.doubleClickOverRoomName(roomName)
				.clickOutOfOrderPlanningLink();
		conferenceRoom = outOfOrder.setOutOfOrderPeriodInformation(startDate, endDate, 
				startTime, endTime, title, description)
				.clickSaveOutOfOrderBtn();

		//Assert for TC05
		Assert.assertTrue(conferenceRoom.getOutOfOrderIconClass(roomName).contains(expectedIcon));
	}

	@AfterMethod
	public void deleteOutOfOrder(){
		RootRestMethods.deleteOutOfOrder(roomName, title);
	}
}
