package com.sl.dffr.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.sl.dffr.utilfactory.SeleniumHelpers;


public class AssignSupplierPage extends BasePage {
	
	public AssignSupplierPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//input[contains(@placeholder,'Supplier Name')]")
	private WebElement ASP_SupplierName_Text;
	
	@FindBy(xpath = "//button[contains(text(),'Save Supplier Assignments')]")
	private WebElement ASP_SaveSupplierAssignment_Button;
	
	@FindBy(xpath = "//button[contains(text(),'Send Supplier Letters')]")
	private WebElement ASP_SendSupplierLetter_Button;
	
	@FindBy(xpath = "//button[contains(text(),'Download')]")
	private WebElement ASP_Download_Button;
	
	@FindBy(xpath = "//div[@class='col-md-10 float-right']//button[2]")
	private WebElement ASP_PrintAssignment_Button;
	
	@FindBy(xpath = "//button[contains(text(),'Print Assignments & Forecasts')]")
	private WebElement ASP_PrintAssignmentAndForecast_Button;
	
	@FindBy(xpath = "//button[contains(text(),'Assign Supplier')]")
	private WebElement ASP_AssignSupplier_Button;
	
	@FindBy(xpath = "//button[contains(text(),'Remove')]")
	private WebElement ASP_Remove_Button;
	
	@FindBy(xpath = "//table[@class='modal-table printSection']//thead//tr//th//input[@type='checkbox']")
	private WebElement ASP_Division_CheckBox;
	
	@FindBy(xpath = "//input[@value='1']")
	private WebElement ASP_DivisionLevel_CheckBox;
	
	@FindBy(xpath = "//a[contains(text(),'BAT')]")
	private WebElement ASP_BATDivision_HyperLink;
	
	@FindBy(xpath = "//tbody//tr[1]//td[2]")
	public WebElement ASP_SupplierName_Lable;
	
	@FindBy(xpath = "//tbody//tr[1]//td[4]")
	public WebElement ASP_Status_Lable;
	
	@FindBy(xpath = "//ngb-typeahead-window[@class='dropdown-menu show ng-star-inserted']//button[1]")
	private WebElement ASP_SelectSupplierName_Dropdown;
	
	@FindBy(xpath="//div[@class='toast-message ng-star-inserted']")
	private WebElement ASP_Toaster_Download;
	
	@FindBy(xpath="//span[@class='ng-tns-c21-276']")
	private WebElement ASP_Toaster_Close;
	
	public void AssigneSupplier() throws InterruptedException
	{
		SeleniumHelpers.enterText(ASP_SupplierName_Text, "A");
		SeleniumHelpers.click(ASP_SelectSupplierName_Dropdown);
		Thread.sleep(2000);
		SeleniumHelpers.click(ASP_DivisionLevel_CheckBox);
		SeleniumHelpers.click(ASP_AssignSupplier_Button);
		Thread.sleep(2000);
	}	
	
	//------------------------------------------------ get text -------------------------------------
	public String getText_ASP_SupplierName_Lable()
	{
		return SeleniumHelpers.getText(ASP_SupplierName_Lable);
	}
	
	public String getText_ASP_Status_Lable()
	{
		return SeleniumHelpers.getText(ASP_Status_Lable);
	}
	
	public String getText_ASP_Toaster_Download()
	{
		return SeleniumHelpers.getText(ASP_Toaster_Download);
	}
	//------------------------------------------------ Enable / is present -------------------------------------
	public boolean get_ASP_SaveSupplierAssignment_Button() {
		return SeleniumHelpers.isEnabled(ASP_SaveSupplierAssignment_Button);
	}
	
	public boolean get_ASP_SendSupplierLetter_Button() {
		return SeleniumHelpers.isEnabled(ASP_SendSupplierLetter_Button);
	}
	
	public boolean get_ASP_Download_Button() {
		return SeleniumHelpers.isEnabled(ASP_Download_Button);
	}
	
	public boolean get_ASP_PrintAssignment_Button() {
		return SeleniumHelpers.isEnabled(ASP_PrintAssignment_Button);
	}
	
	public boolean get_ASP_PrintAssignmentAndForecast_Button() {
		return SeleniumHelpers.isEnabled(ASP_PrintAssignmentAndForecast_Button);
	}
	
	public boolean get_ASP_AssignSupplier_Button() {
		return SeleniumHelpers.isEnabled(ASP_AssignSupplier_Button);
	}
	
	public boolean get_ASP_Remove_Button() {
		return SeleniumHelpers.isEnabled(ASP_Remove_Button);
	}
	
	public void get_ASP_Toaster_Close() {
		while( SeleniumHelpers.isEnabled(ASP_Toaster_Close))
		{
			SeleniumHelpers.click(ASP_Toaster_Close);
		}
	}
	
	//------------------------------------------------ Click -------------------------------------
	public void click_ASP_SaveSupplierAssignment_Button() {
		SeleniumHelpers.click(ASP_SaveSupplierAssignment_Button);
	}
	
	public void click_ASP_SendSupplierLetter_Button() {
		 SeleniumHelpers.click(ASP_SendSupplierLetter_Button);
	}
	
	public void click_ASP_Download_Button() {
		 SeleniumHelpers.click(ASP_Download_Button);
	}
	
	public void click_ASP_PrintAssignment_Button() {
		 SeleniumHelpers.click(ASP_PrintAssignment_Button);
	}
	
	public void click_ASP_PrintAssignmentAndForecast_Button() {
		 SeleniumHelpers.click(ASP_PrintAssignmentAndForecast_Button);
	}
	
	public void click_ASP_AssignSupplier_Button() {
		 SeleniumHelpers.click(ASP_AssignSupplier_Button);
	}
	
	public void click_ASP_Remove_Button() {
		SeleniumHelpers.click(ASP_Remove_Button);
	}
}
