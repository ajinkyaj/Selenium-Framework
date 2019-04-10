package com.sl.dffr.utilfactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.sl.dffr.testplan.BaseTest;

public class SeleniumHelpers {

	public static WebDriver driver = BaseTest.driver;

	public static int waitUntilXseconds = 5;

	public static void click(WebElement element) {
		highlightWebElement(element);
		element.click();
	}
	
	public static void pressEnterKey(WebElement element) {
		element.sendKeys(Keys.ENTER);
	}

	public static void clear(WebElement element) {
		highlightWebElement(element);
		element.clear();
	}
	
	public static void scrollToTop() {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, -document.body.scrollHeight)");
	}
	
	public static void scrollToBottom() {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	public static void scroll(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
	}

	public static void enterText(WebElement element, String value) {
		highlightWebElement(element);
		if (element.getAttribute("value").isEmpty()) {
			element.sendKeys(value);
		} else {
			element.clear();
			element.sendKeys(value);
		}
	}

	public static int getElementHeight(WebElement element) {
		return element.getSize().getHeight();
	}

	public static int getElementWidth(WebElement element) {
		return element.getSize().getWidth();
	}

	public static String getText
			(WebElement element) {
		return element.getText();
	}

	public static void populateDropDownByValue(WebElement element, String value) {
		highlightWebElement(element);
		Select select = new Select(element);
		select.selectByValue(value);
	}

	public static void populateDropDownByVisibleText(WebElement element, String value) {
		highlightWebElement(element);
		Select select = new Select(element);
		select.selectByVisibleText(value);
	}

	public static void populateDropDownByIndex(WebElement element, int index) {
		highlightWebElement(element);
		Select select = new Select(element);
		select.selectByIndex(index);
	}
	
	public static int getCountOfDropDownValues(WebElement element) {
		highlightWebElement(element);
		Select select = new Select(element);
		return select.getOptions().size();
	}
	
	public static String getDropdownSelectedText(WebElement element) {
		highlightWebElement(element);
		Select select = new Select(element);
		return select.getFirstSelectedOption().getText();
	}

	public static void waitUntilClickable(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, waitUntilXseconds);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public static void waitUntilVisible(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, waitUntilXseconds);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public static WebDriverWait waitFor(int waitTime) {
		WebDriverWait wait = new WebDriverWait(driver, waitTime);
		return wait;
	}

	public static void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static boolean isElementPresent(WebElement element) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, waitUntilXseconds);
			wait.until(ExpectedConditions.visibilityOf(element));
			return true;
		} catch (TimeoutException | NoSuchElementException e) {
			return false;
		}
	}
	
	public static void refreshBrowser() {
		driver.navigate().refresh();
	}

	public static boolean isEnabled(WebElement element) {
		return (element.isEnabled());
	}

	public static boolean isDisabled(WebElement element) {
		return (!element.isEnabled());
	}

	public static boolean isVisible(WebElement element) {
		return (element.isDisplayed());
	}

	public static boolean isInvisible(WebElement element) {
		return (!element.isDisplayed());
	}

	public static String captureScreenshot() throws IOException {
		File scrFile = ((TakesScreenshot) BaseTest.driver).getScreenshotAs(OutputType.FILE);
		String filename = getCurrentTimeStamp() + ".jpg";
		File targetFile = new File(ConfigReader.readValue("SCREENSHOT_LOCATION") + filename);
		FileUtils.copyFile(scrFile, targetFile);
		return filename;
	}

	public static boolean isTextPresent(String text) {
		try {
			boolean presence = driver.getPageSource().contains(text);
			return presence;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isTextPresent(WebElement element, String text) {
		try {
			String textInHTML = element.getText();
			if (textInHTML.toLowerCase().contains(text.toLowerCase())) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			return false;
		}
	}

	public static boolean acceptJSAlert() {
		try {
			Thread.sleep(50);
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (Exception e) {
		}
		return true;
	}

	public static boolean rejectJSAlert() {
		try {
			Thread.sleep(50);
			Alert alert = driver.switchTo().alert();
			alert.dismiss();
		} catch (Exception e) {
		}
		return true;
	}

	public static String getCurrentTimeStamp() {
		DateFormat sdf = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");
		Date date = new Date();
		return sdf.format(date);
	}

	public static String captureBase64Screenshot() {
		File scrFile = ((TakesScreenshot) BaseTest.driver).getScreenshotAs(OutputType.FILE);
		String encodedBase64 = null;
		FileInputStream fileInputStreamReader = null;
		try {
			fileInputStreamReader = new FileInputStream(scrFile);
			byte[] bytes = new byte[(int) scrFile.length()];
			fileInputStreamReader.read(bytes);
			encodedBase64 = new String(Base64.encodeBase64(bytes));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "data:image/png;base64," + encodedBase64;
	}

	public static String getPageTitle() {
		return driver.getTitle();
	}

	@SuppressWarnings("unused")
	private static void log(WebElement element) {
		String name = Thread.currentThread().getStackTrace()[3].getMethodName();
		String name1 = Thread.currentThread().getStackTrace()[2].getMethodName();
		BaseTest.log.info(name + ": " + name1 + ": " + element.toString());
	}

	public static void highlightWebElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'background: ; border: 2px solid red;');", element);
	}
	
	public static void selectMonthYear(WebElement elementDatepicker, WebElement elementMonth, WebElement elementYear, String year, String month) {
		elementDatepicker.click();
		populateDropDownByVisibleText(elementMonth, month);
		populateDropDownByVisibleText(elementYear, year);
	}







}