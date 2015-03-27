package tests.admin.conferenceroomoutoforderplanning;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomOutOfOrderPlanningPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC04: Verify that Calendar icon appears in out of order column when the schedule to be 
 * out of order is not in the time yet
 * TC16:Verify that clicking the icon clock in "Out of Order" column  disables out of order 
 * time of a Room
 * TC17:Verify that clicking the icon clock in "Out of Order" column  enables out of order 
 * time of a Room
 * @author Yesica Acha
 *
 */
public class IfOutOfOrderIsCreatedInTheFutureCalendarIconEnablesOrDisablesIt {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
	String roomName = testData.get(2).get("Room Name");
	String title = testData.get(2).get("Title");

	@Test(groups = {"ACCEPTANCE", "FUNCTIONAL"})
	public void testIfOutOfOrderIsCreatedInTheFutureCalendarIconEnablesOrDisablesIt() {
		String startDate = testData.get(2).get("Start date");
		String endDate = testData.get(2).get("End date");
		String startTime = testData.get(2).get("Start time (minutes to add)");
		String endTime = testData.get(2).get("End time (minutes to add)");
		String description = testData.get(2).get("Description");
		String expectedIcon = testData.get(2).get("Icon").toLowerCase().replaceAll(" ", "");
		
		//Out Of Order creation
		HomeAdminPage homeAdminPage = new HomeAdminPage(); 
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		RoomOutOfOrderPlanningPage outOfOrderPage = roomInfoPage.clickOutOfOrderPlanningLink();
		roomsPage = outOfOrderPage
				.setOutOfOrderPeriodInformation(startDate, endDate, startTime, endTime, title, description)
				.clickSaveOutOfOrderBtn();

		//Assertion for TC04
		Assert.assertTrue(roomsPage.getOutOfOrderIconClass(roomName).contains(expectedIcon));	

		//Assertion for TC16
		roomsPage.clickOutOfOrderIcon(roomName);
		Assert.assertTrue(RootRestMethods.isOutOfOrderEnable(roomName, title));

		//Assertion for TC17
		roomsPage.clickOutOfOrderIcon(roomName);
		Assert.assertFalse(RootRestMethods.isOutOfOrderEnable(roomName, title));
	}

	@AfterMethod
	public void deleteOutOfOrder() throws MalformedURLException, IOException {
		RootRestMethods.deleteOutOfOrder(roomName, title);
	}
}
