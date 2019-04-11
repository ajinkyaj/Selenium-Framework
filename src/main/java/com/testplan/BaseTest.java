package com.testplan;

import java.io.File;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.TemporaryFilesystem;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import com.utilfactory.ConfigReader;
import com.utilfactory.DatabaseHelpers;
import com.utilfactory.WebDriverFactory;

public class BaseTest {

	private WebDriverFactory wdf = new WebDriverFactory();

	public static WebDriver driver = null;

	public static Logger log = LogManager.getLogger(BaseTest.class.getName());

	public static SoftAssert softAssert = new SoftAssert();
	
	private static String environemnt;
	
	private static String pagesPackage = null;
	
	public String SQL;
	
	@BeforeSuite
	public void testInit() throws Exception {
		launchBrowser();
	}

	@BeforeMethod
	public void testSetup() throws Exception
	{
		openURL();
	}

	@AfterMethod
	public void testTearDown() throws Exception{	}

	@AfterSuite
	public void quit() throws Exception {
		tearDown();
	}

	private void launchBrowser() throws Exception {

		 switch (ConfigReader.readValue("TEST_BROWSER").toUpperCase()) {
		case "CHROME":
			driver = wdf.getChromeBrowser();
			break;
		case "FIREFOX":
			driver = wdf.getFireFoxBrowser();
			break;
		case "IE":
			driver = wdf.getIEBrowser();
			break;
		case "HEADLESS":
			driver = wdf.getHeadlessChromeBrowser();
			break;
		default:
			driver = wdf.getChromeBrowser();
			break;
		}
	}

	private void openURL() throws IOException {
		environemnt = System.getenv("ENV");
		if (environemnt != null) {
			switch (environemnt) {
			case "DEV":
				driver.get(ConfigReader.readValue("DEV_URL"));
				break;
			case "QA":
				driver.get(ConfigReader.readValue("QA_URL"));
				break;
			case "STAGING":
				driver.get(ConfigReader.readValue("STAGING_URL"));
				break;
			case "PROD":
				driver.get(ConfigReader.readValue("PROD_URL"));
				break;
			default:
				driver.get(ConfigReader.readValue("QA_URL"));
				break;
			}
		} else if (environemnt == null) {
			driver.get(ConfigReader.readValue("QA_URL"));
		}
	}

	private void tearDown() {
		if (driver != null) {
			//driver.quit();
		}
		TemporaryFilesystem tempFS = TemporaryFilesystem.getDefaultTmpFS();
		tempFS.deleteTemporaryFiles();
	}

	public Object getPageObject(String classname) throws Exception {
		String packageList = findFile(classname+".java", new File(System.getProperty("user.dir")));
		return PageFactory.initElements(driver, Class.forName(packageList + classname));
	}
	
	private static String findFile(String name, File file) {
		File[] list = file.listFiles();

		if (list != null) {
			for (File fil : list) {
				if (fil.isDirectory()) {
					findFile(name, fil);
				} else if (name.equalsIgnoreCase(fil.getName())) {
					pagesPackage = fil.getParentFile().toString();
					pagesPackage = pagesPackage.substring(pagesPackage.indexOf("com"));
					pagesPackage = pagesPackage.replace("\\", ".");
					pagesPackage = pagesPackage + ".";
				}
			}
		}
		return pagesPackage;
	}

}
