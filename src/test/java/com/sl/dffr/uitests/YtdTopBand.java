package com.sl.dffr.uitests;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.sl.dffr.pages.AssignSupplierPage;
import com.sl.dffr.pages.LandingPage;
import com.sl.dffr.pages.LockProductPage;
import com.sl.dffr.testplan.BaseTest;
import com.sl.dffr.utilfactory.DatabaseHelpers;
import com.sl.dffr.utilfactory.DffrHelpers;
import com.sl.dffr.utilfactory.SeleniumHelpers;

public class YtdTopBand extends BaseTest{
	
	LandingPage landing;
	LockProductPage LPP;
	AssignSupplierPage ASP;
	
	@BeforeTest
	public void ObjectCreation() throws Exception {
		landing = (LandingPage) getPageObject("LandingPage");
		LPP = (LockProductPage) getPageObject("LockProductPage");
		ASP = (AssignSupplierPage) getPageObject("AssignSupplierPage");
	}
	/*
	@Test
	public void forecastedDivisionShippingRetail() throws InterruptedException
	{
		landing.searchProduct(productCode);

		Assert.assertEquals(true, landing.getLockProductButtonStatus());
		
		landing.lockProduct();
		
		Assert.assertEquals(LPP.isLockProductPopUpDisplayed(), true);
		LPP.loadReport("2019", "Apr", "1", "2019", "May", "31");
		LPP.lockProductAndClose();
		
		Thread.sleep(3000);
	}*/
	
	@Test(description = "ADFFR-90, ADFFR-36 : Verify after unlocking the product values of autogen and version 1 are matching for Top band")
	public void ADFFR_1065() throws InterruptedException
	{
		landing.searchProduct(productCode);
		
		int actualUnit = landing.gettopband_TotalShippingUnits();
		long actualDollar = landing.gettopband_TotalShippingDollars();
		int projectedUnit = landing.gettopband_ProjectedTotalShippingUnits();
		long projectedDollar = landing.gettopband_ProjectedTotalShippingDollars();
		
		landing.lockProduct();
		LPP.loadReport("2019", "Apr", "1", "2019", "May", "31");
		LPP.lockProductAndClose();
		landing.unLockProduct();
		
		Thread.sleep(4000);
		Assert.assertEquals(actualUnit, landing.gettopband_TotalShippingUnits());
		Assert.assertEquals(actualDollar, landing.gettopband_TotalShippingDollars());
		Assert.assertEquals(projectedUnit, landing.gettopband_ProjectedTotalShippingUnits());
		Assert.assertEquals(projectedDollar, landing.gettopband_ProjectedTotalShippingDollars());
	}

}
