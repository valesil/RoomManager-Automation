package tests.admin.conferenceRoom;

import static framework.common.AppConfigConstants.EXCEL_INPUT_DATA;
import static framework.common.MessageConstants.ROOM_DISPLAY_NAME_EMPTY;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import jxl.read.biff.BiffException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import framework.pages.admin.HomeAdminPage;
import framework.pages.admin.conferencerooms.RoomInfoPage;
import framework.pages.admin.conferencerooms.RoomsPage;
import framework.utils.readers.ExcelReader;

/**
 * Verify that is not allowed to change the display name of rooms for blank text
 * @author Marco Llano
 *
 */
public class RoomDoesNotAllowsBlankTextForDisplayName {
	RoomsPage roomsPage;
	RoomInfoPage infoPage;

	@Test(groups = {"NEGATIVE"})
	public void testRoomDoesNotAllowsBlankTextForDisplayName() throws InterruptedException, BiffException, IOException {
		HomeAdminPage home = new HomeAdminPage();
		roomsPage = home.clickConferenceRoomsLink();
		ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
		List<Map<String, String>> testData = excelReader.getMapValues("Resources");
		String roomName = testData.get(0).get("Room Name");

		//Set room capacity to blank text
		roomsPage.clickConferenceRoomsLink();
		infoPage = roomsPage.doubleClickOverRoomName(roomName);
		infoPage.setDisplayName("")
				.clickSaveWithErrorBtn();
		String message = infoPage.getErrorMessageDisplayName();
		
		//Verify if error message is displayed when empty value is inserted
		Assert.assertEquals(message, ROOM_DISPLAY_NAME_EMPTY);		
	}

	@AfterMethod
	public void afterMethod() throws InterruptedException, BiffException, IOException {	
		roomsPage = infoPage.clickCancelBtn();
	}
}
