package com.sl.dffr.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class BasePage {
	protected WebDriver driver = null;

	protected static Logger log = LogManager.getLogger(BasePage.class.getName());

	public BasePage(WebDriver driver) {
		this.driver = driver;
	}
}
