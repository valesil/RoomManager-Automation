package tests.admin.conferenceRoom;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.utils.readers.ExcelReader;

/**
 * TC04: Verify that is allowed to change the display name of rooms
 * TC05: Verify that is allowed to insert a code for a room
 * TC06: Verify that is allowed to insert the capacity of rooms 
 * TC07: Verify that is allowed to insert the location of a room
 * 
 * @author Ruben Blanco
 * 
 */
public class RoomDisplayNameCanBeChanged {

	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData1 = excelReader.getMapValues("RoomInfo");
	String displayName =testData1.get(0).get("DisplayName");  	  
	String capacity = testData1.get(0).get("Capacity");
	String roomCode = testData1.get(0).get("Code");
	String location = testData1.get(0).get("Location");
	String empty = "";

	@Test(groups = {"FUNCTIONAL"})
	public void testDisplayNameRoomsCanBeChanged() throws IOException,
	InterruptedException, BiffException {
		HomeAdminPage homePage = new HomeAdminPage();
		RoomsPage confRoomPage = homePage.clickConferenceRoomsLink();
		RoomInfoPage roomInf = confRoomPage.doubleClickOverRoomName(
		confRoomPage.getRoomDisplayName(displayName));
		roomInf
			.setDisplayName(displayName)
			.setRoomCode(roomCode)
			.setRoomCapacity(capacity)
			.setLocation(location)
			.clickSaveBtn();

		confRoomPage.doubleClickOverRoomName(confRoomPage.getRoomDisplayName(displayName));
		//Assertion for TC04
		Assert.assertEquals(roomInf.getRoomDisplayName(), displayName);

		//Assertion for TC05
		Assert.assertEquals(roomInf.getRoomCode(), roomCode);

		//Assertion for TC06
		Assert.assertEquals(roomInf.getRoomCapacity(), capacity);

		//Assertion for TC04
		Assert.assertEquals(roomInf.getRoomLocation(), location);
	}

	@AfterClass
	public void postcondition() {
		RoomInfoPage roomInf = new RoomInfoPage();
		roomInf
			.setDisplayName(displayName)
			.setRoomCode(empty)
			.setRoomCapacity(empty)
			.clickSaveBtn();
	}
}
