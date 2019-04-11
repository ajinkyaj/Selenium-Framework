package com.utilfactory;

import java.io.IOException;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryFailedTC implements IRetryAnalyzer {

	private int retryCount = 0;

	public boolean retry(ITestResult result) {
		if (!result.isSuccess()) {
			try {
				if (retryCount < Integer.parseInt(ConfigReader.readValue("MAX_RETRY_COUNT"))) {
					retryCount++;
					result.setStatus(ITestResult.FAILURE);
					return true;
				} else {
					result.setStatus(ITestResult.FAILURE);
				}
			} catch (NumberFormatException | IOException e) {
				e.printStackTrace();
			}
		} else {
			result.setStatus(ITestResult.SUCCESS);
		}
		return false;
	}

}
