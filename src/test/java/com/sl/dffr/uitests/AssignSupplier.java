package com.sl.dffr.uitests;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.pages.AssignSupplierPage;
import com.pages.LandingPage;
import com.pages.LockProductPage;
import com.testplan.BaseTest;

public class AssignSupplier extends BaseTest {

	LandingPage landing;
	LockProductPage LPP;
	AssignSupplierPage ASP;

	@BeforeTest
	public void AssignSupplier1() throws Exception
	{
		landing = (LandingPage) getPageObject("LandingPage" );
		LPP = (LockProductPage) getPageObject("LockProductPage");
		ASP = (AssignSupplierPage) getPageObject("AssignSupplierPage");
	}

	@Test(enabled = true, description = "Assign to Supplier Features = ADFFR-572, ADFFR-544, ADFFR-542, ADFFR-543, ADFR-765"
			+ "Test cases = ")
	public void ADDFR_572() throws Exception {
		landing.searchProduct(productCode);
		landing.lockProduct();
		LPP.loadReport("2019", "Feb", "8", "2019", "Feb", "20");
		LPP.lockProductAndClose();
		landing.SendToSupplier();
		Thread.sleep(2000);

		// Check button status
		Assert.assertEquals(false, ASP.get_ASP_SaveSupplierAssignment_Button());
		Assert.assertEquals(false, ASP.get_ASP_SendSupplierLetter_Button());
		Assert.assertEquals(false, ASP.get_ASP_Download_Button());
		Assert.assertEquals(true, ASP.get_ASP_PrintAssignment_Button());
		Assert.assertEquals(true, ASP.get_ASP_PrintAssignmentAndForecast_Button());

		ASP.AssigneSupplier();

		// Check supplier name & status
		Assert.assertEquals(ASP.getText_ASP_SupplierName_Lable(), "A to Z Meats, Inc.");
		Assert.assertEquals(ASP.getText_ASP_Status_Lable(), "Assigned, Not Saved");

		ASP.click_ASP_SaveSupplierAssignment_Button();
		
		ADFFR_544();
		Assert.assertEquals(true, ASP.get_ASP_SendSupplierLetter_Button());
		//ADFFR_681();

	}
	
	// Description = Download supplier letter, Test cases : ADFFR-635, ADFFR-597
	public void ADFFR_544() throws InterruptedException
	{
		Thread.sleep(15000);
		ASP.click_ASP_Download_Button();
		Thread.sleep(2000);
		Assert.assertEquals(ASP.getText_ASP_Toaster_Download(), "File download completed");
	}
	
	// Description = Email supplier letter to supplier : ADFFR-681, ADFFR-597
	public void ADFFR_681() throws InterruptedException
	{
		Thread.sleep(15000);
		ASP.click_ASP_SendSupplierLetter_Button();
		Assert.assertEquals(ASP.getText_ASP_Toaster_Download(), "File download completed");
	}

}
