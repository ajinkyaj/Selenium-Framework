package com.utilfactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.listeners.EventListener;

public class WebDriverFactory {

	private int timeout = 10;
	private WebDriver w_driver;
	private EventFiringWebDriver driver;
	private EventListener listener;

	public WebDriver getChromeBrowser() throws IOException {
		String OS = System.getProperty("os.name");
		if (OS.contains("Windows")) {
			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		}
		w_driver = new ChromeDriver();
		driver = new EventFiringWebDriver(w_driver);
		listener = new EventListener();
		driver.register(listener);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
		return driver;
	}

	public WebDriver getFireFoxBrowser() {
		System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
		WebDriver driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
		return driver;
	}

	public WebDriver getIEBrowser() {
		System.setProperty("webdriver.ie.driver", "src/main/resources/IEDriverServer.exe");
		WebDriver driver = new InternetExplorerDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
		return driver;
	}

	public WebDriver getHeadlessChromeBrowser() throws IOException {
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--headless");
		WebDriver driver = new ChromeDriver(chromeOptions);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
		return driver;
	}

}
