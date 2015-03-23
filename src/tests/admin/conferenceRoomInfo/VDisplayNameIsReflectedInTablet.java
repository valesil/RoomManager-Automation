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

public class VDisplayNameIsReflectedInTablet {
	
	ExcelReader excelReader = new ExcelReader(EXCEL_INPUT_DATA);
	List<Map<String, String>> testData1 = excelReader.getMapValues("RoomInfo");
	String displayName = testData1.get(0).get("DisplayName");	  	  
	String newDisplayName = testData1.get(0).get("NewDisplayName");	  	  
	
	@Test
	public void testChangesInDisplayNameAreReflectedIntablet() throws InterruptedException, IOException, BiffException {
		
		HomeAdminPage homePage = new HomeAdminPage();
		RoomsPage confRoomPage = homePage.clickConferenceRoomsLink();
		RoomInfoPage roomInf = confRoomPage.doubleClickOverRoomName(displayName);
		roomInf.setDisplayName(newDisplayName)
			.clickSaveBtn();		
		String SavedDisplayName = confRoomPage.getRoomDisplayName(newDisplayName);
		SettingsPage sett = new SettingsPage();
		HomePage home = sett.selectRoom(newDisplayName);	   
		String tabletDisplayName = home.getRoomDisplayNameLbl();
		Assert.assertEquals(SavedDisplayName, tabletDisplayName);
	}
	
	@AfterClass
	public void preconditions() throws InterruptedException, BiffException, IOException{
		HomeAdminPage homePage = new HomeAdminPage();
		homePage.getAdminHome();
		RoomsPage confRoomPage = homePage.clickConferenceRoomsLink();
		RoomInfoPage roomInf = confRoomPage.doubleClickOverRoomName(newDisplayName);
		roomInf.setDisplayName(displayName)
			.clickSaveBtn();
	}
}
