package tests.admin.conferenceroomoutoforderplanning;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomOutOfOrderPlanningPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC24: Verify an Out Of Order cannot be created when end date (day, month, year) occurs 
 * before start date
 * TC33: Verify when end date (day, month, year) occurs before start date an error message 
 * that says "'To' field must be greater than 'From' field" is displayed.
 * @author Yesica Acha
 *
 */
public class OutOfOrderIsNotCreatedIfEndDateOccursBeforeStartDate {
	RoomOutOfOrderPlanningPage outOfOrderPage;
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
	String title = testData.get(0).get("Title");
	String startDate = testData.get(0).get("Start date");
	String endDate = testData.get(0).get("End date");
	String roomName = testData.get(0).get("Room Name");

	@Test(groups = {"FUNCTIONAL", "UI"})
	public void testOutOfOrderIsNotCreatedIfEndDateOccursBeforeStartDate() throws JSONException, MalformedURLException, IOException {

		//Out Of Order creation
		HomeAdminPage homeAdminPage = new HomeAdminPage(); 
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		outOfOrderPage = roomsPage
				.doubleClickOverRoomName(roomName)
				.clickOutOfOrderPlanningLink();
		outOfOrderPage = outOfOrderPage.setStartDateWithCalendar(startDate)
				.setEndDateWithCalendar(endDate)
				.clickSaveWithErrorBtn();

		//Assertion for TC24
		Assert.assertFalse(RootRestMethods.isOutOfOrderCreated(roomName, title));

		//Assertion for TC33
		Assert.assertTrue(outOfOrderPage.isErrorMessagePresent());
		Assert.assertTrue(outOfOrderPage.isToGreaterThanFromErrorDisplayed());
	}

	@AfterMethod
	public void closeOutOfOrderPage() {
		outOfOrderPage.clickCancelBtn();
	}
}
