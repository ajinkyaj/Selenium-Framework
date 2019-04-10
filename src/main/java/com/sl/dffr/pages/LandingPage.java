package com.sl.dffr.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.sl.dffr.utilfactory.DffrHelpers;
import com.sl.dffr.utilfactory.SeleniumHelpers;

public class LandingPage extends BasePage {

	public LandingPage(WebDriver driver)
	{
		super(driver);
		PageFactory.initElements(driver, this);
	}


	// ------------------------------------------------- Text Box -------------------------------------------------
	@FindBy(id = "inputProductCode")
	private WebElement productCode_TextBox;

	@FindBy(xpath = "//div[@class='col-sm-12']//input[@type='number']")
	private WebElement edit_TextBox;

	@FindBy(xpath = "//textarea[@placeholder='Supporting notes']")
	private WebElement updateForecastSupportingNotes;

//	@FindBy(xpath = "//td[contains(text(),'This is for test')]")
	@FindBy(xpath = "/html[1]/body[1]/ngb-modal-window[1]/div[1]/div[1]/div[2]/table[1]/tbody[1]/tr[1]/td[6]")
	private WebElement changeHistoryCommentText;

	// ------------------------------------------------- Text -------------------------------------------------
	@FindBy (xpath = "//span[@class='alert-danger']")
	private WebElement Invalid_productCode_Text;

	@FindBy(xpath = "/html[1]/body[1]/app-root[1]/div[1]/div[1]/main[1]/div[2]/app-demand-forcast[1]/div[1]/h3[1]")
	private WebElement productDescription_Text;

	@FindBy(xpath = "//div[@class='row values']/div[1]")
	private WebElement totalShippingUnits_Text;

	@FindBy(xpath = "//div[@class='row values']/div[2]")
	private WebElement totalShippingDollars_Text;

	@FindBy (xpath = "//div[@class='form-group col-md-3']")
	private WebElement Left_productCode_Text;

	@FindBy(xpath = "//span[@aria-hidden='true']")
	private WebElement cancelWarning_Text;

	@FindBy(xpath = "//div[3]/label[@class='radioLabel' and 1]")
	private WebElement forecastedShippingUnitOnEditPopUp_Text;

	@FindBy(xpath = "//tr[@class='ng-star-inserted']//td[5]//button[1]")
	private WebElement EditedShippingUnits;

	@FindBy(xpath = "//td[contains(text(),'Shipping Units')]")
	private WebElement ShippingUnits_Text;

	@FindBy(xpath = "//td[contains(text(),'Shipping�$')]")
	private WebElement ShippingDollars_Text;

	@FindBy(xpath = "//td[@class='distributioncolumn text-left']")
	private WebElement Distribution_Text;

	@FindBy(xpath = "//td[@class='contributioncolumn text-left']")
	private WebElement Contribution_Text;

	@FindBy(xpath = "//div[contains(text(),'Forecast Shipping: $')]")
	private WebElement ForecastedShippingDollars_Text;

	@FindBy(xpath = "//span[@class='a-style ng-star-inserted']")
	private WebElement  ChangeHistory_Text;

	@FindBy(xpath = "//td[contains(text(),'test')]")
	private WebElement ChangeHistoryText_CommentAfterEdit;


	//------------------------------------------- Top Bands -------------------------------------
	@FindBy(xpath = "//div[1]/div[2]/div[3]/div[1]")
	private WebElement topband_TotalShippingUnits;

	@FindBy(xpath = "//div[1]/div[2]/div[3]/div[2]")
	private WebElement topband_TotalShippingDollar;

	@FindBy(xpath = "//div[1]/div[2]/div[3]/div[3]")
	private WebElement topband_ProjectedTotalShippingUnits;

	@FindBy(xpath = "//div[1]/div[2]/div[3]/div[4]")
	private WebElement topband_ProjectedTotalShippingDollar;

	// ------------------------------------------------- Copy Division -------------------------------------------------
	@FindBy(xpath = "//tr[1]//img")
	private WebElement copy_Division_Image;

	@FindBy(xpath = "//button[contains(text(),'Use this division to add another')]")
	private WebElement copy_Division_Button;

	@FindBy(xpath = "//input[@value='4']")
	private WebElement copy_Division_Checkbox;

	@FindBy(xpath = "//img[@class='mr-1']")
	private WebElement copy_Division_Select;

	@FindBy (xpath = "//tr[3]//div[3]")
	private WebElement new_Copied_Division;

	@FindBy (xpath = "//tr[3]")
	private WebElement open_Copied_Division;

	@FindBy (xpath = "//td[1]/div[1]/div[6]")
	private WebElement minimize_OpenDivision;
	// ------------------------------------------------- Button -------------------------------------------------
	@FindBy(xpath = "//input[@value='Search']")
	private WebElement searchProduct_Button;

	@FindBy(xpath = "//div[@class='col-sm-9']//img[@class='mr-1']")
	private WebElement tick_button;

	@FindBy(xpath = "//div/img[1]")
	private WebElement cross_Button;

	@FindBy(xpath = "//button[contains(text(),'No')]")
	private WebElement cancelDiscardChangesPopUp_Button;

	@FindBy(xpath = "//button[contains(text(),'Yes')]")
	private WebElement acceptDiscardChangesPopUp_Button;

	@FindBy(xpath = "//button[contains(text(),'Lock Product')]")
	private WebElement lockProduct_Button;

	@FindBy(xpath = "//div[@class='modal-header noprint']//span[@aria-hidden='true'][contains(text(),'�')]")
	private WebElement closeLockProductPopUp_Button;

	@FindBy(xpath = "//button[contains(text(),'Save')]")
	private WebElement saveVersion_Button;

	@FindBy(xpath = "//button[contains(text(),'Unlock Product')]")
	private WebElement unlockProduct_Button;

	@FindBy(xpath = "//button[contains(text(),'Send to Supplier')]")
	private WebElement sendToSupplier_Button;

	@FindBy(xpath = "//div[@class='toast-message ng-star-inserted']")
	private WebElement top_Toast;


	//----------------------------------------- Copy Product Screen -----------------------------

	@FindBy(xpath = "//app-search-product[@class='search_product mr-2 ng-star-inserted']//input[@id='inputProductCode']")
	private WebElement copy_ProductCode_Textbox;

	@FindBy(xpath = "//button[@id='ngb-typeahead-1-0']")
	private WebElement copy_ProductCode_Select;

	@FindBy(xpath = "//input[@type='image']")
	private WebElement confirmCopyProductCode_CheckButton;

	@FindBy(xpath = "//input[@value='Apply Forecast']")
	private WebElement applyForecast_Button;

	// ------------------------------------------------- Dropdown -------------------------------------------------
	@FindBy(css = "#ngb-typeahead-0-0")
	private WebElement searchResult_Dropdown;

	@FindBy(xpath = "//select[@name='selectedVersion']")
	private WebElement version_Dropdown;

	@FindBy(className = "divisionrow")
	private List<WebElement> division_accordian;

	/*@FindBy(className = "tbl-header")
	private List<WebElement> month_Header;*/


	//--------------------------------------------------Alerts-------------------
    @FindBy(xpath = "//p[@class='mb-0 ng-star-inserted']")
    private WebElement alert_Text;

	//------------------------------------------------- Others -------------------------------------------------

	@FindBy(xpath = "//input[@id='yearsSales']")
	private WebElement lastYearsShippingUnits_Radio;

	@FindBy (xpath = "//th")
	private WebElement month_count;

	@FindBy(xpath = "//input[@id='forecastedVolume']")
	private WebElement forecastedVolume_Radio;

	@FindBy(xpath = "//input[@id='applyToRemainingMonths']")
	private WebElement applyToRemainingMonths_Checkbox;

	@FindBy(xpath = "//h4[@class='modal-title']")
	private WebElement discardChangesHeader_PopUp;

	@FindBy(xpath = "//h4[@id='modal-basic-title']")
	private WebElement lockProductHeader_PopUp;

	@FindBy(xpath = "//a[@title='View Metrics Calculations']")
    private WebElement dffrMetrics_Button;

	private String editButtonXpath = "//td[val]/button";

	private String editedShippingDollars = "//tr[3]/td[val]/span[1]";

	private String editedGrossProfitDollars = "//tr[4]/td[val]/span[1]";

	private String actualGrossProfitDollars = "//tr[4]/td[val]/span[1]";

	private String actualDistribution = "//tr[5]/td[val]/span[1]";

	private String editedDistribution = "//tr[5]/td[val]/span[1]";

	private String editedContribution = "//tr[6]/td[val]/span[1]";

	private String forecastedShippingUnits = "//tr[2]/td[val]/span";

	private String forecastedShippingUnits_Bur = "//tr[4]/td[val]/span";

	private String actualShippingUnits = "//tr[2]/td[val]/span[1]";

	private String actualShippingDollars = "//tr[3]/td[val]/span[1]";

	private String month_Header = "//th[val]/span[1]";

	//------------------------------------ Functions -----------------------------------

	private WebElement prepareWebElementWithDynamicXpath(String xpathValue, String substitutionValue) {

		return driver.findElement(By.xpath(xpathValue.replace("val", substitutionValue)));
	}

	public void searchProduct(String productCode) {
		SeleniumHelpers.enterText(productCode_TextBox, productCode);
		SeleniumHelpers.pressEnterKey(searchResult_Dropdown);
		SeleniumHelpers.click(searchProduct_Button);
	}

	public String Invalid_productCode(String productCode) throws InterruptedException
	{	SeleniumHelpers.enterText(productCode_TextBox, productCode);
		Thread.sleep(3000);
		SeleniumHelpers.click(Left_productCode_Text);
		return Invalid_productCode_Text.getText();
	}

	//------------------------------------------------- Get Text or Data -------------------------------------------------
	public String getProductName() {
		return SeleniumHelpers.getText(productDescription_Text);
	}

	public int getTotalShippingUnits() {
		return DffrHelpers.parseIntFromString(SeleniumHelpers.getText(totalShippingUnits_Text));
	}

	public float getTotalShippingDollars()
	{
		String str = SeleniumHelpers.getText(totalShippingDollars_Text);
		return DffrHelpers.parseFloatFromString(str.substring(1));
	}

	public int gettopband_TotalShippingUnits()
	{
		String str = SeleniumHelpers.getText(topband_TotalShippingUnits);
		return DffrHelpers.parseIntFromString(str);
	}

	public long gettopband_TotalShippingDollars()
	{
		String str = SeleniumHelpers.getText(topband_TotalShippingDollar);
		return DffrHelpers.parseLongFromString(str);
	}

	public int gettopband_ProjectedTotalShippingUnits()
	{
		String str = SeleniumHelpers.getText(topband_ProjectedTotalShippingUnits);
		return DffrHelpers.parseIntFromString(str);
	}

	public int gettopband_ProjectedTotalShippingDollars()
	{
		String str = SeleniumHelpers.getText(topband_ProjectedTotalShippingDollar);
		return DffrHelpers.parseIntFromString(str);
	}

	public int getTotalDivisions() {
		return division_accordian.size();
	}

	public List<Integer> getShippingUnit()
	{
		List<Integer> shippingUnitList = new ArrayList<>();

		for (WebElement element : division_accordian) {
			String str = element.getText();
			str = str.substring(19);
			str = str.substring(0, str.indexOf("("));
			int shippingUnit = DffrHelpers.parseIntFromString(str.trim());
			shippingUnitList.add(shippingUnit);
		}
		return shippingUnitList;
	}

	public List<Float> getShippingDollar() {
		List<Float> shippingDollarList = new ArrayList<>();

		for (WebElement element : division_accordian) {
			String str = element.getText();
			str = str.substring(str.indexOf("$") + 4);
			str = str.substring(0, str.indexOf("("));
			float shippingUnit = DffrHelpers.parseFloatFromString(str.trim());
			shippingDollarList.add(shippingUnit);
		}

		return shippingDollarList;
	}

	public List<String> getAllDivisionCode() {
		List<String> divisionCodeList = new ArrayList<>();

		for (WebElement element : division_accordian) {
			String str = element.getText();
			str = str.substring(0, 3);
			divisionCodeList.add(str);
		}

		return divisionCodeList;
	}

	/*public String getCurrentMonthDesc(int index) {
		return month_Header.get(index).getText();
	}*/

	public int getTotalMonthCount() {
		List<WebElement> links = driver.findElements(By.xpath("//th"));
		//WebElement month_Header_count = month_count;
		return links.size() - 2;
	}

	public String getCurrentMonthDesc(int value)
	{
		WebElement month_Header_Text = prepareWebElementWithDynamicXpath(month_Header,
				Integer.toString(value + 4));
		return SeleniumHelpers.getText(month_Header_Text).toString();
	}

	public int readEditedShippingUnits(int value) throws InterruptedException {
		Thread.sleep(3000);
		WebElement edit_Button = prepareWebElementWithDynamicXpath(editButtonXpath, Integer.toString(value + 5));
		return DffrHelpers.parseIntFromString(SeleniumHelpers.getText(edit_Button).toString());
	}

	public float readEditedShippingDollars(int value) {
		WebElement editedShippingDollars_Text = prepareWebElementWithDynamicXpath(editedShippingDollars,
				Integer.toString(value + 5));
		return DffrHelpers.parseFloatFromString(SeleniumHelpers.getText(editedShippingDollars_Text).toString());
	}

	public float readEditedGrossProfitDollarsDollars(int value) {
		WebElement editedGrossProfitDollars_Text = prepareWebElementWithDynamicXpath(editedGrossProfitDollars,
				Integer.toString(value + 5));
		return DffrHelpers.parseFloatFromString(SeleniumHelpers.getText(editedGrossProfitDollars_Text).toString());
	}

	public float readActualGrossProfitDollarsDollars(int value) {
		WebElement actualGrossProfitDollars_Text = prepareWebElementWithDynamicXpath(actualGrossProfitDollars,
				Integer.toString(value + 3));
		return DffrHelpers.parseFloatFromString(SeleniumHelpers.getText(actualGrossProfitDollars_Text).toString());
	}

	public int readActualShippingUnits(int value)
	{
		WebElement actualGrossProfitDollars_Text = prepareWebElementWithDynamicXpath(actualShippingUnits,
				Integer.toString(value + 3));
		return DffrHelpers.parseIntFromString((SeleniumHelpers.getText(actualGrossProfitDollars_Text).toString()));
	}

		public int readActualShippingUnitsLock(int value)
	{
		 WebElement actualGrossProfitDollars_Text = prepareWebElementWithDynamicXpath(actualShippingUnits,
				Integer.toString(value + 5));
		return DffrHelpers.parseIntFromString((SeleniumHelpers.getText(actualGrossProfitDollars_Text).toString()));
	}

	public int readActualShippingUnitsUnlock(int value)
	{
		WebElement actualGrossProfitDollars_Text = prepareWebElementWithDynamicXpath(editButtonXpath,
				Integer.toString(value + 5));
		return DffrHelpers.parseIntFromString((SeleniumHelpers.getText(actualGrossProfitDollars_Text).toString()));
	}

	public float readActualShippingDollars(int value) {
		WebElement actualGrossProfitDollars_Text = prepareWebElementWithDynamicXpath(actualShippingDollars,
				Integer.toString(value + 3));
		return DffrHelpers.parseIntFromString((SeleniumHelpers.getText(actualGrossProfitDollars_Text).toString()));
	}

	public float readEditedDistribution(int value) {
		WebElement editedDistribution_Text = prepareWebElementWithDynamicXpath(editedDistribution,
				Integer.toString(value + 5));
		return DffrHelpers.parseFloatFromString(SeleniumHelpers.getText(editedDistribution_Text).toString());
	}

	public float readActualDistribution(int value) {
		WebElement actualDistribution_Text = prepareWebElementWithDynamicXpath(actualDistribution,
				Integer.toString(value + 3));
		return DffrHelpers.parseFloatFromString(SeleniumHelpers.getText(actualDistribution_Text).toString());
	}

	public float readEditedContribution(int value) {
		WebElement editedContribution_Text = prepareWebElementWithDynamicXpath(editedContribution,
				Integer.toString(value + 5));
		return DffrHelpers.parseFloatFromString(SeleniumHelpers.getText(editedContribution_Text).toString());
	}

	public int readForecastedShippingUnits(int value) {
		WebElement forecastedShippingUnits_Text = prepareWebElementWithDynamicXpath(forecastedShippingUnits,
				Integer.toString(value + 5));
		return DffrHelpers.parseIntFromString(SeleniumHelpers.getText(forecastedShippingUnits_Text).toString());
	}

	public int readForecastedShippingUnitsBur(int value) {
		WebElement forecastedShippingUnits_Text = prepareWebElementWithDynamicXpath(forecastedShippingUnits_Bur,
				Integer.toString(value + 5));
		return DffrHelpers.parseIntFromString(SeleniumHelpers.getText(forecastedShippingUnits_Text).toString());
	}

	public int readForecastedShippingUnitsFromEditPopUp() {
		String txt = SeleniumHelpers.getText(forecastedShippingUnitOnEditPopUp_Text);
		txt = txt.substring(txt.indexOf("(") + 1, txt.indexOf(")"));
		return DffrHelpers.parseIntFromString(txt);
	}


	public int readForecastedShippingUnitsOnUnlock(int value) {
		WebElement forecastedShippingUnits_Text = prepareWebElementWithDynamicXpath(forecastedShippingUnits,
				Integer.toString(value + 5));
		return DffrHelpers.parseIntFromString(SeleniumHelpers.getText(forecastedShippingUnits_Text).toString());
	}
	public int readForecastedShippingUnitsOnLock(int value)
	{
		WebElement forecastedShippingUnits_Text = prepareWebElementWithDynamicXpath(forecastedShippingUnits,
				Integer.toString(value + 5));
		return DffrHelpers.parseIntFromString(SeleniumHelpers.getText(forecastedShippingUnits_Text).toString());
	}

	public String getSelectedVersion() {
		SeleniumHelpers.scrollToTop();
		return SeleniumHelpers.getDropdownSelectedText(version_Dropdown);
	}

	public String getTextOnEditForeCast() // Return text provided while editing
	{
		return SeleniumHelpers.getText(updateForecastSupportingNotes);
	}

	public String getTextafterEditForeCast()
	{
		return SeleniumHelpers.getText(changeHistoryCommentText); //Return text after edit
	}

	//------------------------------------------------- Enter text or Send Data -------------------------------------------------
	public void editByAbsoluteVolume(int value) {
		SeleniumHelpers.clear(edit_TextBox);
		SeleniumHelpers.enterText(edit_TextBox, Integer.toString(value));
	}

	public void editByPercentLastYearShippingVolume(int percent) {
		SeleniumHelpers.click(lastYearsShippingUnits_Radio);
		SeleniumHelpers.enterText(edit_TextBox, Integer.toString(percent));
	}

	public void editByPercentForecastedVolume(int percent) {
		SeleniumHelpers.click(forecastedVolume_Radio);
		SeleniumHelpers.enterText(edit_TextBox, Integer.toString(percent));
	}

	public void editTextUpdateForecastSupportingNotes(String value)
	{
		SeleniumHelpers.enterText(updateForecastSupportingNotes,value);
	}

	public boolean textCommentAfterUpdateForecast(String value)
	{
		//SeleniumHelpers.isTextPresent(changeHistoryCommentText,value);
		System.out.println(SeleniumHelpers.getText(changeHistoryCommentText));
		return SeleniumHelpers.isTextPresent(value);
	}

	//------------------------------------------------- Click's -------------------------------------------------
	public void ApplyToRemainingMonths()
	{
		SeleniumHelpers.click(applyToRemainingMonths_Checkbox);
	}

	public void saveEditPopUp() throws InterruptedException {
		SeleniumHelpers.click(tick_button);
		Thread.sleep(2000);
	}

	public void cancelEditPopUp() throws InterruptedException {
		SeleniumHelpers.click(cross_Button);
		Thread.sleep(1000);
	}

	public void cancelDiscardChangesPopUp() {
		SeleniumHelpers.click(cancelDiscardChangesPopUp_Button);
	}

	public void acceptDiscardChangesPopUp() {
		SeleniumHelpers.click(acceptDiscardChangesPopUp_Button);
	}

	public void closeLockProductPopUp() {
		SeleniumHelpers.click(closeLockProductPopUp_Button);
	}

	public void clickSearch() {
		SeleniumHelpers.click(searchProduct_Button);
	}

	public LockProductPage lockProduct() {
		SeleniumHelpers.scrollToTop();
		SeleniumHelpers.click(lockProduct_Button);
		return new LockProductPage(driver);
	}

	public AssignSupplierPage SendToSupplier()
	{
		SeleniumHelpers.scrollToTop();
		SeleniumHelpers.click(sendToSupplier_Button);
		return new AssignSupplierPage(driver);
	}

	public void unLockProduct()
	{
		SeleniumHelpers.click(unlockProduct_Button);
	}

	public void openEditPopUp(int value) {
		WebElement edit_Button = prepareWebElementWithDynamicXpath(editButtonXpath, Integer.toString(value + 5));
		SeleniumHelpers.click(edit_Button);
	}

	public void clickDffrMetrics()
    {
        SeleniumHelpers.click(dffrMetrics_Button);
    }

    public void clickChangeHistoryText()
	{
		SeleniumHelpers.click(ChangeHistory_Text);
	}


	// ------------------------------------------------- Versions -------------------------------------------------
	public void saveVersion() throws InterruptedException {
		SeleniumHelpers.scrollToTop();
		SeleniumHelpers.click(saveVersion_Button);
		Thread.sleep(5000);
	}

	public void selectVersion(int index) throws InterruptedException {
		SeleniumHelpers.scrollToTop();
		SeleniumHelpers.populateDropDownByIndex(version_Dropdown, index);
		Thread.sleep(1000);
	}




	// ------------------------------------------------- Element present / Displayed -------------------------------------------------
	public boolean getLockProductButtonStatus() {
		return SeleniumHelpers.isElementPresent(lockProduct_Button);
	}

	public boolean applyForecastButtonStatus() {
		return SeleniumHelpers.isEnabled(applyForecast_Button);
	}

	public boolean getUnlockButtonStatus() {
		return SeleniumHelpers.isElementPresent(unlockProduct_Button);
	}

	public boolean getSendToSupplierButtonStatus() {
		return SeleniumHelpers.isElementPresent(sendToSupplier_Button);
	}

	public boolean getShippingUnitsTextStatus()
	{
		return SeleniumHelpers.isElementPresent(ShippingUnits_Text);
	}

	public boolean getShippingDollarsTextStatus() {
		return SeleniumHelpers.isElementPresent(ShippingDollars_Text);
	}

	public boolean getDistributionTextStatus() {
		return SeleniumHelpers.isElementPresent(Distribution_Text);
	}

	public boolean getContributionTextStatus() {
		return SeleniumHelpers.isElementPresent(Contribution_Text);
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

	public boolean isDiscardChangesPopUpDisplayed() {
		String txt = SeleniumHelpers.getText(discardChangesHeader_PopUp);
		if (txt.equalsIgnoreCase("Discard changes?")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean getChangeHistoryTextStatus()
	{
		return SeleniumHelpers.isElementPresent(ChangeHistory_Text);
	}
	public boolean getChangeHistoryComment_edit()
	{
		return SeleniumHelpers.isElementPresent(ChangeHistoryText_CommentAfterEdit);
	}

	// ------------------------------------------------- Other Function -------------------------------------------------
	public boolean isForecastEditable(int value) {
		try {
			prepareWebElementWithDynamicXpath(editButtonXpath, Integer.toString(value + 5));
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public String tosterMessage()
	{
		String txt = SeleniumHelpers.getText(top_Toast);
		return txt;

	}


	public LockProductPage lockProductButton() // function to click on lock product button
	{
		//SeleniumHelpers.scrollToTop();
		SeleniumHelpers.click(lockProduct_Button);
		return new LockProductPage(driver);
	}

	//----------------------------------------- Copy Product Screen -----------------------------
	public void searchCopyProduct(String productCode) {
		SeleniumHelpers.enterText(copy_ProductCode_Textbox, productCode);
		//SeleniumHelpers.pressEnterKey(searchResult_Dropdown);
		SeleniumHelpers.click(copy_ProductCode_Select);
		SeleniumHelpers.click(confirmCopyProductCode_CheckButton);
	}

	public void clickApplyForecast()
	{
		SeleniumHelpers.click(applyForecast_Button);
	}


	//----------------------------------------------------- Copy Division --------------------------------------

	public void click_copy_Division_Image()
	{
		SeleniumHelpers.click(copy_Division_Image);
	}

	public void click_copy_Division_Button()
	{
		SeleniumHelpers.click(copy_Division_Button);
	}

	public void click_copy_Division_Checkbox()
	{
		SeleniumHelpers.click(copy_Division_Checkbox);
	}

	public void click_copy_Division_Select()
	{
		SeleniumHelpers.click(copy_Division_Select);
	}

	public void click_open_Copied_Division()
	{
		SeleniumHelpers.click(open_Copied_Division);
	}

	public void click_minimize_OpenDivision()
	{
		SeleniumHelpers.click(minimize_OpenDivision);
	}

	public String getText_new_Copied_Division()
	{
		return SeleniumHelpers.getText(new_Copied_Division);
	}

//-------------------------------AlertFunction-----------------


    public String getAlertText()
    {
        return SeleniumHelpers.getText(alert_Text);
    }

}
