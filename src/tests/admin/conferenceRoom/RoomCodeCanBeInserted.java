package tests.admin.conferenceRoom;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

import org.junit.Assert;
import org.testng.annotations.Test;

import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.utils.readers.ExcelReader;

/**
 * TC05: Verify that is allowed to insert a code for a room
 * @author Ruben Blanco
 *
 */
public class RoomCodeCanBeInserted {
	
	@Test(groups = {"FUNCTIONAL"})
	public void testRoomCodeCanBeInserted() throws InterruptedException,
	BiffException, IOException {
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData1 = excelReader.getMapValues("RoomInfo");
		String displayName =testData1.get(0).get("DisplayName");// "room01 changed";	  	  
		String roomCode = testData1.get(0).get("Code");

		HomeAdminPage homePage = new HomeAdminPage();
		RoomsPage confRoomPage = homePage.clickConferenceRoomsLink();
		RoomInfoPage roomInf = confRoomPage.doubleClickOverRoomName(displayName);
		roomInf
			.setRoomCode(roomCode)
			.clickSaveBtn();

		String getCode = confRoomPage.doubleClickOverRoomName(displayName)
			.getRoomCode();
		roomInf.clickCancelBtn();
		Assert.assertEquals(roomCode, getCode);
	}
}
