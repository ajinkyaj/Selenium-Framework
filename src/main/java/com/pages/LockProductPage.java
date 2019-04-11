package com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.utilfactory.SeleniumHelpers;

public class LockProductPage extends BasePage
{

	public LockProductPage(WebDriver driver)
    {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//h4[@id='modal-basic-title']")
	private WebElement lockProductHeader_PopUp;

	@FindBy(xpath = "//div[@class='modal-header noprint']//span[@aria-hidden='true'][contains(text(),'�')]")
	private WebElement closeLockProductPopUp_Button;

	@FindBy(xpath = "//div[@class='row noprint']//input[@placeholder='mm-dd-yyyy']")
	private WebElement startDate_DatePicker;

	@FindBy(xpath = "//div[@class='row']//input[@placeholder='mm-dd-yyyy']")
	private WebElement endDate_DatePicker;

	@FindBy(xpath = "//span[@id='product']")
	private WebElement productDescription_Text;

	@FindBy(xpath = "//div[@class='row']/div[2]/span[1]")
	private WebElement buyer_Text;

	@FindBy(xpath = "/html[1]/body[1]/ngb-modal-window[1]/div[1]/div[1]/div[2]/app-lock-version[1]/div[1]/div[3]/span[1]")
	private WebElement version_Text;

	@FindBy(xpath = "//button[@class='btn btn-sm  btn-primary mr-4']")
	private WebElement loadReport_Button;

	@FindBy(xpath = "//select[@title='Select month']")
	private WebElement monthDropdown_Datepicker;

	@FindBy(xpath = "//select[@title='Select year']")
	private WebElement yearDropdown_Datepicker;

	@FindBy(xpath = "//div/span[@class='label' and 1]")
	private WebElement loadReportSummary_Text;

	@FindBy(xpath = "//button[@class='btn btn-sm btn-success']")
	private WebElement lockProductAndAssignSuppliers_Button;

	@FindBy(xpath = "//button[@class='btn btn-sm  btn-success']")
	private WebElement lockProductAndClose_Button;

	private String day_Datepicker = "//div[@class='btn-light ng-star-inserted'][contains(text(),'val')]";

	private WebElement prepareWebElementWithDynamicXpath(String xpathValue, String substitutionValue) {

		return driver.findElement(By.xpath(xpathValue.replace("val", substitutionValue)));
	}

	public void loadReport(String startYear, String startMonth, String startDay, String endYear, String endMonth,
			String endDay) throws InterruptedException {
		SeleniumHelpers.selectMonthYear(startDate_DatePicker, monthDropdown_Datepicker, yearDropdown_Datepicker,
				startYear, startMonth);
		WebElement startDay_Date = prepareWebElementWithDynamicXpath(day_Datepicker, startDay);
		startDay_Date.click();

		SeleniumHelpers.selectMonthYear(endDate_DatePicker, monthDropdown_Datepicker, yearDropdown_Datepicker, endYear,
				endMonth);
		WebElement endDay_Date = prepareWebElementWithDynamicXpath(day_Datepicker, endDay);
		endDay_Date.click();

		Assert.assertEquals(getLoadReportButtonStatus(), true);

		SeleniumHelpers.click(loadReport_Button);
		Thread.sleep(3000);

		Assert.assertEquals(SeleniumHelpers.isElementPresent(lockProductAndAssignSuppliers_Button), true);
		Assert.assertEquals((SeleniumHelpers.isElementPresent(lockProductAndClose_Button)), true);
	}

	public String getProductDesc() {
		return SeleniumHelpers.getText(productDescription_Text);
	}

	public String getBuyer() {
		return SeleniumHelpers.getText(buyer_Text);
	}

	public String getVersion() {
		return SeleniumHelpers.getText(version_Text);
	}

	public boolean isLockProductPopUpDisplayed() throws InterruptedException {
		Thread.sleep(1000);
		String txt = SeleniumHelpers.getText(lockProductHeader_PopUp);
		if (txt.equalsIgnoreCase("Lock Product")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean getLoadReportButtonStatus() {
		return SeleniumHelpers.isEnabled(loadReport_Button);
	}

	public String getLoadReportSummary() {
		return SeleniumHelpers.getText(loadReportSummary_Text);
	}

	public LandingPage lockProductAndClose() throws InterruptedException
    {
		SeleniumHelpers.click(lockProductAndClose_Button);
		Thread.sleep(3000);
		return new LandingPage(driver);
	}
/*//-----------------------------------Click's---------------------------------------------------


    public void lockProductAndClose()
    {
        SeleniumHelpers.click(lockProductAndClose_Button);
    }*/
}
