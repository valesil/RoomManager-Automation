package tests.admin.conferenceroomoutoforderplanning;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomOutOfOrderPlanningPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.rest.RootRestMethods;
import framework.utils.readers.ExcelReader;

/**
 * TC16: Verify an Out Of Order can be created when the start date is in the past 
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
	
	@Test(groups = "FUNCTIONAL")
	public void testOutOfOrderIsCreatedIfStartTimeIsInThePastAndEndTimeIsInTheFuture() throws JSONException, MalformedURLException, IOException {
		
		//Out Of Oder creation
		HomeAdminPage homeAdminPage = new HomeAdminPage(); 
		RoomsPage roomsPage = homeAdminPage.clickConferenceRoomsLink();
		RoomOutOfOrderPlanningPage outOfOrderPage = roomsPage
				.doubleClickOverRoomName(roomName)
				.clickOutOfOrderPlanningLink();
		roomsPage = outOfOrderPage
				.setOutOfOrderPeriodInformation(startDate, endDate, startTime, endTime, 
						title, description)
				.activateOutOfOrder()
				.clickSaveOutOfOrderBtn();
		
		//Assertion for TC16
		Assert.assertTrue(RootRestMethods.isOutOfOrderCreated(roomName, title));
		Assert.assertTrue(roomsPage.isMessagePresent());
		Assert.assertTrue(roomsPage.isOutOfOrderSuccessfullyCreatedMessageDisplayed());
		Assert.assertTrue(roomsPage.isOutOfOrderIconDisplayed(roomName));
	}
	
	@AfterClass
	public void postCondition() throws MalformedURLException, IOException{
		RootRestMethods.deleteOutOfOrder(roomName, title);
	}
}
