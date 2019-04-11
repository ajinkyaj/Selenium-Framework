package com.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;

public class EventListener extends AbstractWebDriverEventListener {

	private static Logger log = LogManager.getLogger(EventListener.class.getName());

	public void afterAlertAccept(WebDriver arg0) {
	}

	public void afterAlertDismiss(WebDriver arg0) {
	}

	public void afterChangeValueOf(WebElement arg0, WebDriver arg1, CharSequence[] arg2) {
	}

	public void afterClickOn(WebElement arg0, WebDriver arg1) {
		log.info("Clicked on: " + getString(arg0.toString()));
	}

	public void afterFindBy(By arg0, WebElement arg1, WebDriver arg2) {
	}

	public <X> void afterGetScreenshotAs(OutputType<X> arg0) {
	}

	public void afterGetText(WebElement arg0, WebDriver arg1, String arg2) {
		log.info("Text read: " + arg2.toString());
	}

	public void afterNavigateBack(WebDriver arg0) {
		log.info("Navigated back");
	}

	public void afterNavigateForward(WebDriver arg0) {
		log.info("Navigated forward");
	}

	public void afterNavigateRefresh(WebDriver arg0) {
		log.info("Page refreshed");
	}

	public void afterNavigateTo(String arg0, WebDriver arg1) {
		log.info("Navigated to URL: " + arg0.toString());
	}

	public void afterScript(String arg0, WebDriver arg1) {
	}

	public void afterSwitchToWindow(String arg0, WebDriver arg1) {
	}

	public void beforeAlertAccept(WebDriver arg0) {
	}

	public void beforeAlertDismiss(WebDriver arg0) {
	}

	public void beforeChangeValueOf(WebElement arg0, WebDriver arg1, CharSequence[] arg2) {
	}

	public void beforeClickOn(WebElement arg0, WebDriver arg1) {
		log.info("Trying to click on: " + getString(arg0.toString()));
	}

	public void beforeFindBy(By arg0, WebElement arg1, WebDriver arg2) {
	}

	public <X> void beforeGetScreenshotAs(OutputType<X> arg0) {
	}

	public void beforeGetText(WebElement arg0, WebDriver arg1) {
		log.info("Trying to read text: " + arg0.toString());
	}

	public void beforeNavigateBack(WebDriver arg0) {
		log.info("Before navigating back");
	}

	public void beforeNavigateForward(WebDriver arg0) {
		log.info("Before navigating forward");
	}

	public void beforeNavigateRefresh(WebDriver arg0) {
		log.info("Before page refresh");
	}

	public void beforeNavigateTo(String arg0, WebDriver arg1) {
		log.info("Before navigating to URL: " + arg0);
	}

	public void beforeScript(String arg0, WebDriver arg1) {
	}

	public void beforeSwitchToWindow(String arg0, WebDriver arg1) {
	}

	public void onException(Throwable arg0, WebDriver arg1) {
		log.info("Exception occurred: " + arg0);
	}

	private String getString(String str) {
		str = str.substring(str.indexOf('>') + 2, str.lastIndexOf(']'));
		return str;
	}

}
