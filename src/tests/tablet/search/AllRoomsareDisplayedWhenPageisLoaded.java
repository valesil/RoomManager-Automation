package tests.tablet.search;


/**
 * Created by Jose Cabrera
 * 1/28/15
 * 
 */
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedList;

import org.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import framework.pages.tablet.HomeTabletPage;
import framework.pages.tablet.SearchPage;
import framework.rest.RootRestMethods;

/**
 * @title TC6:Verify on search page that all rooms are displayed when search 
 * page is loaded
 * @author Jose Cabrera
 */
public class AllRoomsareDisplayedWhenPageisLoaded {
	SearchPage search = new SearchPage();

	@Test(groups = "UI")
	public void testAllRoomsareDisplayedPageLoaded () throws JSONException, MalformedURLException, 
	IOException {
		HomeTabletPage home = new HomeTabletPage();
		LinkedList<String> condition = RootRestMethods.getAllRooms();
		search = home.clickSearchBtn();
		Assert.assertTrue(search.roomsInList(condition));
	}

	@AfterMethod
	public void toHome() {
		search.clickBackBtn();
	}

}
