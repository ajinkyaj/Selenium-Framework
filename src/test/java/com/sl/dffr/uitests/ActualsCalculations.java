package com.sl.dffr.uitests;

import java.io.IOException;
import java.sql.SQLException;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.pages.AssignSupplierPage;
import com.pages.LandingPage;
import com.pages.LockProductPage;
import com.testplan.BaseTest;
import com.utilfactory.DatabaseHelpers;
import com.utilfactory.DffrHelpers;

public class ActualsCalculations extends BaseTest{
	
	LandingPage landing;
	LockProductPage LPP;
	AssignSupplierPage ASP;
	
	double totalShippingDollars_Calculated;
	double totalGrossProfitDollars_Calculated;

	@BeforeTest
	public void ActualsCalculations1() throws Exception {
		landing = (LandingPage) getPageObject("LandingPage");
		LPP = (LockProductPage) getPageObject("LockProductPage");
		ASP = (AssignSupplierPage) getPageObject("AssignSupplierPage");
	}


	// Verify Actual Shipping dollars
	@Test(enabled = true, description = "ADFFR-831> Automation: Shipping Units : Verify that Shipping $ is getting calculated.")
	public void ADFFR_831() throws Exception {
	
		landing.searchProduct(productCode);

		float totalShippingDollars_UI = landing.getTotalShippingDollars();
		
		SQL = " select sum(retail) as retail from MonthlyShipping where productid =(select productid from product where productcode = "
				+ productCode + ")" + " and MonthCalendarId between 201901 and 201912 ";

		float totalShippingDollars_DB = Float.parseFloat(DatabaseHelpers.select(SQL, "retail"));
		//totalShippingDollars_Calculated = DffrHelpers.calculateShippingDollars(totalShippingUnits_DB,productCode);
		
		Assert.assertEquals(Math.round(totalShippingDollars_UI), Math.round(totalShippingDollars_DB));
	}
	
	@Test(description = "Actual Gross profit dollar calculation")
	public void actualGrossProfit() throws ClassNotFoundException, IOException, SQLException 
	{
		landing.searchProduct(productCode);
		
		for (int i = 0; i < 2; i++)
		{
		float actualGrossProfitDollarsDollar_UI = landing.readActualGrossProfitDollarsDollars(i);
		int shippingUnits_UI = landing.readActualShippingUnits(i);
		float shippingDollars_UI = landing.readActualShippingDollars(i);
		
		totalGrossProfitDollars_Calculated = DffrHelpers.calculateGrossProfitDollarsActuals(shippingUnits_UI, productCode, shippingDollars_UI);
		System.out.println("totalGrossProfitDollars_Calculated = " + totalGrossProfitDollars_Calculated);
		System.out.println("actualGrossProfitDollarsDollar_UI = " + actualGrossProfitDollarsDollar_UI);
		Assert.assertEquals(Math.round(actualGrossProfitDollarsDollar_UI), Math.round(totalGrossProfitDollars_Calculated));
		}
	}
	
	/*@Test
	public void actualDistribution ()
	{
		landing.searchProduct(productCode);
		
		int monthsActual = Integer.parseInt(DffrHelpers.getCurrentMonth());
		for (int i = 0; i < monthsActual; i++)
		{
		float actualGrossProfitDollarsDollar_UI = landing.readActualDistribution(i);
		int shippingUnits_UI = landing.readActualShippingUnits(i);
		
		System.out.println("shippingUnits_UI = " + shippingUnits_UI);
		totalGrossProfitDollars_Calculated = DffrHelpers.calculateGrossProfitDollars(shippingUnits_UI, productCode);
		
		System.out.println("actualGrossProfitDollarsDollar_UI = " + actualGrossProfitDollarsDollar_UI + " actualGrossProfitDollarsDollar_UI = "
				+ totalGrossProfitDollars_Calculated);
		
		Assert.assertEquals(Math.round(actualGrossProfitDollarsDollar_UI), Math.round(totalGrossProfitDollars_Calculated));
		}
	}*/
}
