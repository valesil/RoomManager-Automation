package suits;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

import org.junit.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.utils.readers.ExcelReader;

public class RoomDisplayNameRoomsCanBeChanged {

	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData1 = excelReader.getMapValues("RoomInfo");
	String displayName =testData1.get(0).get("DisplayName");// "room01 changed";	  	  
	String capacity = testData1.get(0).get("Capacity");
	String roomCode = testData1.get(0).get("Code");
	String location = testData1.get(0).get("Location");

	@Test
	public void testDisplayNameRoomsCanBeChanged() throws IOException, InterruptedException, BiffException {

		HomeAdminPage homePage = new HomeAdminPage();
		RoomsPage confRoomPage = homePage.clickConferenceRoomsLink();
		RoomInfoPage roomInf = confRoomPage.doubleClickOverRoomName(
				confRoomPage.getRoomDisplayName(displayName));
		roomInf.setDisplayName(displayName)
		.setRoomCode(roomCode)
		.setRoomCapacity(capacity)
		.setLocation(location)
		.clickSaveBtn();

		confRoomPage.doubleClickOverRoomName(confRoomPage.getRoomDisplayName(displayName));
		//Test to verify if display name was saved
		Assert.assertEquals(roomInf.getRoomDisplayName(), displayName);

		//Test to verify if room code was saved
		Assert.assertEquals(roomInf.getRoomCode(), roomCode);

		//Test to verify if room capacity was saved
		Assert.assertEquals(roomInf.getRoomCapacity(), capacity);

		//Test to verify if room location was saved
		Assert.assertEquals(roomInf.getRoomLocation(), location);
	}

	@AfterTest
	public void postcondition() {
		RoomInfoPage roomInf = new RoomInfoPage();
		roomInf.setDisplayName(displayName)
		.setRoomCode("")
		.setRoomCapacity("")
		.clickSaveBtn();
	}
}
