package tests.tablet.search;


/**
 * Created by Jose Cabrera
 * 1/28/15
 * 
 */

import java.util.LinkedList;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import framework.pages.tablet.HomePage;
import framework.pages.tablet.SearchPage;
import framework.rest.RootRestMethods;

/**
 * @title Verify on search page that all rooms are displayed when search 
 * page is loaded
 * @author Jose Cabrera
 */
public class AllRoomsareDisplayedWhenPageisLoaded {
	public HomePage home = new HomePage();
	public SearchPage search = new SearchPage();
	public LinkedList<String> condition = RootRestMethods.getAllRooms();
	
	@Test(groups = {"UI"})
	public void testAllRoomsareDisplayedPageLoaded () {
		search = home.clickSearchBtn();
		Assert.assertTrue(search.roomsInList(condition));
	}
	
	@AfterClass
	public void toHome() {
		search.clickBackBtn();
		//RootRestMethods.deleteResource("telf");
	}
	
}
