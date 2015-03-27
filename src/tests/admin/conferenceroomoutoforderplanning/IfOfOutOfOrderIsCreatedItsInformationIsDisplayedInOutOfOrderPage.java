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
import framework.utils.TimeManager;
import framework.utils.readers.ExcelReader;

/**
 * TC02: Verify Out of Order's information is displayed in Out Of Order planning if it is 
 * for current time or for future time
 * @author Yesica Acha
 *
 */
public class IfOfOutOfOrderIsCreatedItsInformationIsDisplayedInOutOfOrderPage {
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
	String roomName = testData.get(4).get("Room Name");
	String title = testData.get(4).get("Title");
	
	@Test(groups = "ACCEPTANCE")
	public void testIfOfOutOfOrderIsCreatedItsInformationIsDisplayedInOutOfOrderPage() {
		String startDate = testData.get(4).get("Start date");
		String endDate = testData.get(4).get("End date");
		String startTime = testData.get(4).get("Start time (minutes to add)");
		String endTime = testData.get(4).get("End time (minutes to add)");
		String description = testData.get(4).get("Description");
		
		//Changing date and time format to compare with actual values
		String expectedStartDate = TimeManager.changeDateFormat(startDate, "yyyy/MMM/dd", "MMM dd YYYY");
		String expectedEndDate = TimeManager.changeDateFormat(endDate, "yyyy/MMM/dd", "MMM dd YYYY");
		String expectedStartTime = TimeManager.getTime(Integer.parseInt(startTime), "hh:mm");
		String expectedEndTime = TimeManager.getTime(Integer.parseInt(endTime), "hh:mm");

		//Out Of Order Creation
		HomeAdminPage homeAdminPage = new HomeAdminPage(); 
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomInfoPage roomInfoPage = roomsPage.doubleClickOverRoomName(roomName);
		RoomOutOfOrderPlanningPage outOfOrderPage = roomInfoPage.clickOutOfOrderPlanningLink();
		roomsPage = outOfOrderPage.setOutOfOrderPeriodInformation(startDate, endDate, 
				startTime, endTime, title, description)
				.activateOutOfOrder()
				.selectEmailNotificationChkBox()
				.clickSaveOutOfOrderBtn();
		outOfOrderPage = roomsPage
				.doubleClickOverRoomName(roomName)
				.clickOutOfOrderPlanningLink();

		//Assertion for TC02
		Assert.assertEquals(outOfOrderPage.getStartDateValue(), expectedStartDate);
		Assert.assertEquals(outOfOrderPage.getEndDateValue(), expectedEndDate);
		Assert.assertEquals(outOfOrderPage.getStartTimeValue(), expectedStartTime);
		Assert.assertEquals(outOfOrderPage.getEndTimeValue(), expectedEndTime);
		Assert.assertEquals(outOfOrderPage.getTitleValue(), title);
		Assert.assertEquals(outOfOrderPage.getDescriptionValue(), description);
		Assert.assertTrue(outOfOrderPage.isOutOfOrderActivated());
	}

	@AfterMethod
	public void deleteOutOfOrder() throws MalformedURLException, IOException {
		RootRestMethods.deleteOutOfOrder(roomName, title);
	}
}
