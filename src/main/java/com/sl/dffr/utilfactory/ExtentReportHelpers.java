package com.sl.dffr.utilfactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.http.auth.AuthenticationException;
import org.testng.ITestResult;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentReportHelpers {

	private String jiraID;

	private String jiraStatus;

	private String interimReport;

	private Path finalReportDirectory;

	private Path finalReportPath;

	private Path interimReportPath;

	private ExtentReports extent;

	private ExtentTest test;

	public void initialiseReport() {
		try {
			interimReport = ConfigReader.readValue("EXTENT_REPORT_LOCATION") + "ExtentReport"
					+ SeleniumHelpers.getCurrentTimeStamp() + ".html";
			extent = new ExtentReports(interimReport, true);
			extent.loadConfig(new File(ConfigReader.readValue("EXTENT_REPORT_CONFIG")));
			if (System.getenv("ENV") == null) {
				extent.addSystemInfo("Environment", "Unknown");
			} else {
				extent.addSystemInfo("Environment", System.getenv("ENV"));
			}
			extent.addSystemInfo("Browser", ConfigReader.readValue("TEST_BROWSER"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void teardownReport() {
		extent.flush();
		extent.close();

		interimReportPath = Paths.get(interimReport);
		try {
			finalReportDirectory = Paths.get(ConfigReader.readValue("REPORT_PATH"));
			finalReportPath = Paths
					.get(ConfigReader.readValue("REPORT_PATH") + "/" + ConfigReader.readValue("REPORT_FILENAME"));

			if (Files.notExists(finalReportDirectory)) {
				Files.createDirectories(Paths.get(ConfigReader.readValue("REPORT_PATH")));
				Files.copy(interimReportPath, finalReportPath);
			} else if (Files.exists(finalReportDirectory)) {
				if (Files.exists(finalReportPath)) {
					Files.delete(finalReportPath);
					Files.copy(interimReportPath, finalReportPath);
				} else if (Files.notExists(finalReportPath)) {
					Files.copy(interimReportPath, finalReportPath);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void success(ITestResult result) {
		try {
			if (ConfigReader.readValue("ENABLE_JIRA_INTEGRATION").equalsIgnoreCase("Y")) {
				jiraID = JiraHelpers.searchSummary("AutomatedTestFailure: " + result.getName().toString());
				if (jiraID != null) {
					jiraStatus = JiraHelpers.getIssueStatus(jiraID);
					if (jiraStatus != null) {
						switch (jiraStatus) {
						case "OPEN":
							JiraHelpers.addAttachmentJira(jiraID, ConfigReader.readValue("SCREENSHOT_LOCATION")
									+ SeleniumHelpers.captureScreenshot());
							JiraHelpers.addComment(jiraID, "PASSED (" + SeleniumHelpers.getCurrentTimeStamp() + ")");
							if (JiraHelpers.transitionIssue(jiraID, "CLOSE") == true) {
							} else if (JiraHelpers.transitionIssue(jiraID, "CLOSE") == false) {
							}
							break;
						case "CLOSED":
							break;
						default:
							break;
						}
					}
				} else {
				}
			}
		} catch (IOException | AuthenticationException | InterruptedException | UnirestException e) {
			e.printStackTrace();
		}
		test.log(LogStatus.PASS, "Test Case Passed",
				test.addBase64ScreenShot(SeleniumHelpers.captureBase64Screenshot()));
		extent.endTest(test);
	}

	public void failure(ITestResult result) {
		try {
			if (ConfigReader.readValue("ENABLE_JIRA_INTEGRATION").equalsIgnoreCase("Y")) {
				jiraID = JiraHelpers.searchSummary("AutomatedTestFailure: " + result.getName().toString());
				if (jiraID != null) {
					jiraStatus = JiraHelpers.getIssueStatus(jiraID);
					if (jiraStatus != null) {
						switch (jiraStatus) {
						case "OPEN":
							JiraHelpers.addAttachmentJira(jiraID, ConfigReader.readValue("SCREENSHOT_LOCATION")
									+ SeleniumHelpers.captureScreenshot());
							JiraHelpers.addComment(jiraID, "FAILED (" + SeleniumHelpers.getCurrentTimeStamp() + ") "
									+ result.getThrowable().toString());
							break;
						case "CLOSED":
							if (JiraHelpers.transitionIssue(jiraID, "OPEN") == true) {
								JiraHelpers.addAttachmentJira(jiraID, ConfigReader.readValue("SCREENSHOT_LOCATION")
										+ SeleniumHelpers.captureScreenshot());
								JiraHelpers.addComment(jiraID, "FAILED (" + SeleniumHelpers.getCurrentTimeStamp() + ") "
										+ result.getThrowable().toString());
							} else if (JiraHelpers.transitionIssue(jiraID, "OPEN") == false) {
							}
							break;
						default:
							break;
						}
					} else if (jiraStatus == null) {
					}
				} else if (jiraID == null) {
					jiraID = JiraHelpers.createIssue("CLEAR", "Bug",
							"AutomatedTestFailure: " + result.getMethod().getDescription(), " ", "Endorsements");
					if (jiraID != null) {
						JiraHelpers.addAttachmentJira(jiraID,
								ConfigReader.readValue("SCREENSHOT_LOCATION") + SeleniumHelpers.captureScreenshot());
						JiraHelpers.addComment(jiraID, "FAILED (" + SeleniumHelpers.getCurrentTimeStamp() + ") "
								+ result.getThrowable().toString());
					} else if (jiraID == null) {
					}
				}
			}

		} catch (IOException | AuthenticationException | InterruptedException | UnirestException e) {
			e.printStackTrace();
		}
		test.log(LogStatus.FAIL, result.getThrowable().toString(),
				test.addBase64ScreenShot(SeleniumHelpers.captureBase64Screenshot()));
		extent.endTest(test);
	}

	public void skip(ITestResult result) {
		test.log(LogStatus.SKIP, "Test Case Skipped", test.addBase64ScreenShot(SeleniumHelpers.captureBase64Screenshot()));
		extent.endTest(test);
	}
	
	public void startReporting(ITestResult result) {
		test = extent.startTest(result.getInstanceName().substring(result.getInstanceName().lastIndexOf(".") + 1)
				+ " > " + result.getMethod().getDescription());
	}
}
