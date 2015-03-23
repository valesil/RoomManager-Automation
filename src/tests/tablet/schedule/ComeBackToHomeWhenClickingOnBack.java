package tests.tablet.schedule;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.pages.tablet.HomePage;

/**
 * Title: Verify that clicking the left arrow displays the home page
 * @author Asael Calizaya
 *
 */
public class ComeBackToHomeWhenClickingOnBack {
	@Test
	public void testComeBackToHomeWhenClickBackButton() {
		HomePage home = new HomePage();
		String actual = home
				.clickScheduleBtn()
				.clickBackBtn()
				.getRoomDisplayNameLbl();

		Assert.assertEquals("room03", actual);
	}
}
