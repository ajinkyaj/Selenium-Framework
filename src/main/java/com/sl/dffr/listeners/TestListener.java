package com.sl.dffr.listeners;

import java.io.IOException;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.sl.dffr.testplan.BaseTest;
import com.sl.dffr.utilfactory.ConfigReader;
import com.sl.dffr.utilfactory.ExtentReportHelpers;

public class TestListener implements ITestListener {

	ExtentReportHelpers reporter = new ExtentReportHelpers();

	@Override
	public void onTestStart(ITestResult result) {
		BaseTest.log.info(result.getMethod().getDescription());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		reporter.startReporting(result);
		reporter.success(result);
	}

	@Override
	public void onTestFailure(ITestResult result) {
		reporter.startReporting(result);
		reporter.failure(result);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		try {
			if (Integer.parseInt(ConfigReader.readValue("MAX_RETRY_COUNT")) == 0) {
				reporter.startReporting(result);
				reporter.skip(result);
			}
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	@Override
	public void onStart(ITestContext context) {
		reporter.initialiseReport();
	}

	@Override
	public void onFinish(ITestContext context) {
		reporter.teardownReport();
	}

}
