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
import framework.utils.TimeManager;
import framework.utils.readers.ExcelReader;

/**
 * TC34: Verify that start date is set in current time when the room has no 
 * Out Of Order Period established
 * TC35: Verify that end date are set in the current time when the room has no 
 * Out Of Order Period established
 * @author Yesica Acha
 *
 */
public class StartAndEndDateAreSetForCurrentTimeByDefault {
	HomeAdminPage homeAdminPage = new HomeAdminPage(); 
	RoomOutOfOrderPlanningPage outOfOrderPage;
	RoomsPage roomsPage;
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData = excelReader.getMapValues("OutOfOrderPlanning");
	String roomName = testData.get(0).get("Room Name");
	
	
	@Test(groups = "UI")
	public void testStartAndEndDateIsSetForCurrentTimeByDefault() {
		String currentDate = TimeManager.getCurrentDate("MMM dd YYYY");
		roomsPage = homeAdminPage.clickConferenceRoomsLink();
		outOfOrderPage = roomsPage
				.doubleClickOverRoomName(roomName)
				.clickOutOfOrderPlanningLink();	
		
		//Assertion for TC34
		Assert.assertEquals(outOfOrderPage.getStartDateValue(), currentDate);
		
		//Assertion for TC35
		Assert.assertEquals(outOfOrderPage.getEndDateValue(), currentDate);
	}
	
	@AfterMethod
	public void closeOutOfOrderPage() {
		outOfOrderPage.clickCancelBtn();
	}
}
