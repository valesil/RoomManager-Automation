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
import framework.utils.readers.ExcelReader;

/**
 * TC06: Verify that clicking the icon  (clock or calendar) in the column "Out of Order" 
 * disables out of order time of a Room
 * TC07: Verify that clicking the icon  (clock or calendar) in the column "Out of Order" 
 * enables out of order time of a Room
 * "Out of order was created successfully." is displayed
 * @author Yesica Acha
 *
 */
public class OutOfOrderIsEnabledOrDisabledByClickingIcon {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
	String roomName = testData.get(2).get("Room Name");
	String startDate = testData.get(3).get("Start date");
	String endDate = testData.get(3).get("End date");
	String startTime = testData.get(3).get("Start time (minutes to add)");
	String endTime = testData.get(3).get("End time (minutes to add)");
	String title = testData.get(3).get("Title");
	String description = testData.get(3).get("Description");

	@Test
	public void testOutOfOrderIsEnabledOrDisabledByClickingIcon() {
		HomeAdminPage homeAdminPage = new HomeAdminPage(); 
		RoomsPage rooms = homeAdminPage.clickConferenceRoomsLink();
		RoomOutOfOrderPlanningPage outOfOrder = rooms
				.doubleClickOverRoomName(roomName)
				.clickOutOfOrderPlanningLink();
		rooms = outOfOrder
				.setOutOfOrderPeriodInformation(startDate, endDate, startTime, endTime, title, description)
				.clickSaveOutOfOrderBtn()
				.waitForMessage();

		//Assert for TC06
		rooms.clickOutOfOrderIcon(roomName);
		Assert.assertTrue(RootRestMethods.isOutOfOrderEnable(roomName, title));

		//Assert for TC07
		rooms.clickOutOfOrderIcon(roomName);
		Assert.assertFalse(RootRestMethods.isOutOfOrderEnable(roomName, title));

	}

	@AfterMethod
	public void postCondition() {
		RootRestMethods.deleteOutOfOrder(roomName, title);
	}
}
