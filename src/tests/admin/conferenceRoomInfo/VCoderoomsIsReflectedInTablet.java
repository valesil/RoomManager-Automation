package suits;

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
import framework.pages.tablet.HomePage;
import framework.pages.tablet.SettingsPage;
import framework.utils.readers.ExcelReader;

public class VCoderoomsIsReflectedInTablet {

	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData1 = excelReader.getMapValues("RoomInfo");
	String displayName =testData1.get(0).get("DisplayName");	  	  
	String roomCode = testData1.get(0).get("Code");
	
	@Test
	public void testChangesInRoomCodeAreReflectedIntablet() throws InterruptedException, IOException, BiffException {

		HomeAdminPage homePage = new HomeAdminPage();
		RoomsPage confRoomPage = homePage.clickConferenceRoomsLink();
		RoomInfoPage roomInf = confRoomPage.doubleClickOverRoomName(displayName);
		roomInf.setRoomCode(roomCode)
				.clickCancelBtn();
		String SavedCode = confRoomPage.doubleClickOverRoomName(displayName)
				.getRoomCode();  
		roomInf.clickCancelBtn();
		SettingsPage sett = new SettingsPage();
		HomePage home=sett.selectRoom(displayName);	   
		String tabletRoomCode = home.getRoomCodeLbl();
		Assert.assertEquals(SavedCode, tabletRoomCode);
	}
	
	@AfterClass
	public void postcondition() {
		HomeAdminPage homePage = new HomeAdminPage();
		homePage.getAdminHome();
		RoomsPage confRoomPage = homePage.clickConferenceRoomsLink();
		RoomInfoPage roomInf = confRoomPage.doubleClickOverRoomName(displayName);
		roomInf.setRoomCode("")
			.clickSaveBtn();
	}
}
