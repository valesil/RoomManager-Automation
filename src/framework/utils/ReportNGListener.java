package framework.utils;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import framework.common.UIMethods;
import framework.selenium.SeleniumDriverManager;

public class ReportNGListener implements ITestListener {
	WebDriver driver = SeleniumDriverManager.getManager().getDriver();

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void onTestFailure(ITestResult result) {
		try {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		String filePath = System.getProperty("user.dir") + "/screenshots/";
		String failureImageFileName =  result.getName() + ".png"; 
		UIMethods.takeScreenShot(filePath, failureImageFileName);
		Reporter.log("<a href=\"" + filePath + failureImageFileName 
				+ "\"><img src=\"file:///" + filePath + failureImageFileName 
				+ "\" alt=\"\"" + "height='100' width='100'/> " + "<br />"); 
		Reporter.setCurrentTestResult(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
	}	
}
