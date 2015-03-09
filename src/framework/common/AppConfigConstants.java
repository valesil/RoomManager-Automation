package framework.common;

import framework.utils.readers.JsonReader;


public final class AppConfigConstants {
	private static JsonReader value = new JsonReader();
	
	public static final String BROWSER = value.readJsonFile("browser", "navigator");
	public static final String URL_TABLET = value.readJsonFile("browser", "urlTablet");
	public static final String URL_ADMIN = value.readJsonFile("browser", "urlAdmin");
	public static final String CHROMEDRIVER_PATH = value.readJsonFile("confDriver", "chrome");
	public static final String IEDRIVER_PATH = value.readJsonFile("confDriver", "ie");
	public static final int IMPLICIT_WAIT = Integer.parseInt(value.readJsonFile("confDriver", 
																	"implicitWait"));
	public static final int EXPLICIT_WAIT = Integer.parseInt(value.readJsonFile("confDriver", 
																	"explicitWait"));
	public static final int WEBDRIVERWAIT_SLEEP = Integer.parseInt(value.readJsonFile("confDriver",
																	"webDriverWait_Sleep"));
}
