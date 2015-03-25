package framework.common;

import framework.utils.readers.JsonReader;

/**
 * 
 * @author Asael Calizaya
 *
 */
public final class AppConfigConstants {
	private static JsonReader value = new JsonReader();

	public static final String BROWSER = value.readJsonFile("browser", "navigator", "\\appconfig.json");
	public static final String URL_TABLET = value.readJsonFile("browser", "urlTablet", "\\appconfig.json");
	public static final String URL_ADMIN = value.readJsonFile("browser", "urlAdmin", "\\appconfig.json");
	public static final String URL_REST = value.readJsonFile("browser", "urlRest", "\\appconfig.json");
	public static final String CHROMEDRIVER_PATH = value.readJsonFile("confDriver", "chrome", "\\appconfig.json");
	public static final String IEDRIVER_PATH = value.readJsonFile("confDriver", "ie", "\\appconfig.json");
	public static final int IMPLICIT_WAIT = Integer.parseInt(value.readJsonFile("confDriver", 
			"implicitWait", "\\appconfig.json"));
	public static final int EXPLICIT_WAIT = Integer.parseInt(value.readJsonFile("confDriver", 
			"explicitWait", "\\appconfig.json"));
	public static final int WEBDRIVERWAIT_SLEEP = Integer.parseInt(value.readJsonFile("confDriver",
			"webDriverWait_Sleep", "\\appconfig.json"));
	public static final String EXCEL_PATH = value.readJsonFile("ExternalFile", "excelPath", "\\appconfig.json");
	public static final String EXCEL_INPUT_DATA = value.readJsonFile("ExternalFile", "excelInputData", "\\appconfig.json");
	public static final String EXCEL_DATA_PROVIDER = value.readJsonFile("ExternalFile", "dataProviderData", "\\appconfig.json");

}
