package com.sl.dffr.uitests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.pages.LandingPage;
import com.pages.LockProductPage;
import com.testplan.BaseTest;
import com.utilfactory.DatabaseHelpers;
import com.utilfactory.DffrHelpers;
import com.utilfactory.SeleniumHelpers;

import org.testng.mustache.Value;


public class SmokeTest extends BaseTest {

	@Test(enabled = true, description = "ADFFR-696> Automation: Verify that as a buyer, I should be able to search for a Product using Product Code.")
	public void ADFFR_696() throws Exception {
		LandingPage landing = (LandingPage) getPageObject("LandingPage");
		landing.searchProduct(productCode);

		String ProductDescription_UI = landing.getProductName();

		SQL = "select * from Product where ProductCode = " + productCode;
		String ProductDescription_DB = productCode + " - " + DatabaseHelpers.select(SQL, "Description");

		Assert.assertEquals(ProductDescription_UI.trim(), ProductDescription_DB.trim());
	}

	@Test(enabled = true, description = "ADFFR-64> Verify that as a Buyer, I should NOT see any product if I enter an invalid product code")
	public void ADFFR_64() throws Exception {
		LandingPage landing = (LandingPage) getPageObject("LandingPage");
		Assert.assertEquals(landing.Invalid_productCode("1238"),
				"The product code 1238 is not core/seasonal or does not exist");
	}

	@Test(enabled = true, description = "ADFFR-892> Automation: Shipping Units : Verify that shipping units are visible on UI when expanded the division.")
	public void ADFFR_892() throws Exception {
		LandingPage landing = (LandingPage) getPageObject("LandingPage");
		landing.searchProduct(productCode);

		int totalShippingUnits_UI = landing.getTotalShippingUnits();

		SQL = "select * from MonthlyShipping where productid = (select productid from product where productcode = "
				+ productCode + ") and MonthCalendarId  " + "between 201901 and 201912";

		int totalShippingUnits_DB = DatabaseHelpers.getSumInt(SQL, "Quantity");
		Assert.assertEquals(totalShippingUnits_UI, totalShippingUnits_DB);
	}

	@Test(enabled = true, description = "ADFFR-699> Automation: Verify that as a buyer, I should see forecasts of all the division where the Product is shipped.")
	public void ADFFR_699() throws Exception {
		LandingPage landing = (LandingPage) getPageObject("LandingPage");
		landing.searchProduct(productCode);

		int totalDivisions_UI = landing.getTotalDivisions();

		SQL = "select distinct d.DivisionCode from MonthlyShipping ms join Division d on d.DivisionId = ms.DivisionId"
				+ " join product p on ms.ProductId = p.ProductId "
				+ " where p.ProductCode = " + productCode;
		
		int totalDivisions_DB = DatabaseHelpers.getCount(SQL);
		Assert.assertEquals(totalDivisions_UI, totalDivisions_DB);
	}

	@Test(enabled = true, description = "ADFFR-846> Automation: Verify when product is not locked, only machine generated value is getting displayed as YTD.")
	public void ADFFR_846() throws Exception {
		LandingPage landing = (LandingPage) getPageObject("LandingPage");
		landing.searchProduct(productCode);
		Thread.sleep(2000);
		List<Integer> shippingUnitList_UI = landing.getShippingUnit();
		List<String> divisionCodeList_UI = landing.getAllDivisionCode();

		for (int index = 0; index < shippingUnitList_UI.size(); index++) {
			SQL = "select * from MonthlyShipping where productid = (select ProductId from Product where ProductCode = "
					+ productCode + ") and MonthCalendarId " + "between 201901 and 201912"
					+ "and divisionid = (select DivisionId from Division where DivisionCode = '"
					+ divisionCodeList_UI.get(index) + "' )";
			int shippingUnit_DB = DatabaseHelpers.getSumInt(SQL, "Quantity");
			int shippingUnit_UI = shippingUnitList_UI.get(index);
			Assert.assertEquals(shippingUnit_UI, shippingUnit_DB);
		}
	}

	@Test(enabled = true, description = "ADFFR-701> Automation: Verify that as a buyer, I should see Shipping $ YTD for all the divisions where the searched Product is shipped.")
	public void ADFFR_701() throws Exception {
		LandingPage landing = (LandingPage) getPageObject("LandingPage");
		landing.searchProduct(productCode);

		List<Float> shippingDollarList_UI = landing.getShippingDollar();
		List<String> divisionCodeList_UI = landing.getAllDivisionCode();

		for (int index = 0; index < shippingDollarList_UI.size(); index++) {
			SQL = "select * from MonthlyShipping where productid = (select ProductId from Product where ProductCode = "
					+ productCode + " ) and MonthCalendarId between 201901 and 201912 "
					+ "and divisionid = (select DivisionId from Division where DivisionCode = '"
					+ divisionCodeList_UI.get(index) + "' )";
			int shippingDollar_DB = Math.round(DatabaseHelpers.getSumFloat(SQL, "Retail"));
			Float shippingDollar_UI = shippingDollarList_UI.get(index);
			Assert.assertEquals(shippingDollar_DB, Math.round(shippingDollar_UI));
		}
	}

	@Test(enabled = true, description = "ADFFR-702> Automation: Verify that as a buyer, I should see forecast of [Current Month - 1], [Current Month], [Current Month + 13] in the default view when I search for a Product using Product Code.")
	public void ADFFR_702() throws Exception {
		LandingPage landing = (LandingPage) getPageObject("LandingPage");
		landing.searchProduct(productCode);

		for(int i = 0; i < 8; i++)
		{
			String monthDescription_UI = landing.getCurrentMonthDesc(i);

			SQL = "select * from MonthCalendar where MonthDesc = '" + monthDescription_UI + "'";
			String monthDescription_DB = DatabaseHelpers.select(SQL, "MonthDesc");

			Assert.assertEquals(monthDescription_UI, monthDescription_DB);
		}
	}

	@Test(enabled = true, description = "ADFFR-703> Automation: Verify that as a buyer, I should see the divisions sorted in ASC order based on SortID column in Division table when I search for a Product using Product Code.")
	public void ADFFR_703() throws Exception {

		List<Integer> divisionSortIdList_UI = new ArrayList<>();
		List<Integer> divisionSortIdList_DB = new ArrayList<>();

		LandingPage landing = (LandingPage) getPageObject("LandingPage");
		landing.searchProduct(productCode);

		List<String> divisionCodeList_UI = landing.getAllDivisionCode();

		for (int index = 0; index < divisionCodeList_UI.size(); index++) {
			SQL = "select *from Division where DivisionCode like '" + divisionCodeList_UI.get(index) + "'";
			String sortID = DatabaseHelpers.select(SQL, "SortId");
			divisionSortIdList_UI.add(Integer.parseInt(sortID));
		}

		divisionSortIdList_DB = divisionSortIdList_UI;
		Collections.sort(divisionSortIdList_DB);

		for (int index = 0; index < divisionSortIdList_UI.size(); index++) {
			Assert.assertEquals(divisionSortIdList_UI.get(index), divisionSortIdList_DB.get(index));
		}
	}

	@Test(enabled = true, description = "ADFFR-704> Automation: Verify that as a buyer, I should be able to edit the forecast by absolute volume and same should be applied to Sales$, GrossProfit$, Distribution & Contribution.")
	public void ADFFR_704() throws Exception {

		List<Integer> randomVolumeList = DffrHelpers.generateRandomNumberList(13);
		List<String> futureMonthPeriodList = DffrHelpers.getFutureMonthPeriodList(12);

		LandingPage landing = (LandingPage) getPageObject("LandingPage");
		landing.searchProduct(productCode);

		for (int i = 0; i < monthsToEdit; i++) {

			landing.openEditPopUp(i);
			landing.editByAbsoluteVolume(randomVolumeList.get(i));
			landing.saveEditPopUp();

			int editedShippingUnits_UI = landing.readEditedShippingUnits(i);
			int forecastedShippingUnits_UI = landing.readForecastedShippingUnits(i);
			float editedShippingDollars_UI = landing.readEditedShippingDollars(i);
			float editedGrossProfitDollars_UI = landing.readEditedGrossProfitDollarsDollars(i);
			float editedDistribution_UI = landing.readEditedDistribution(i);
			float editedContribution_UI = landing.readEditedContribution(i);

			int editedShippingUnits_DB = randomVolumeList.get(i);
			float editedShippingDollars_DB = DffrHelpers.calculateShippingDollars(editedShippingUnits_DB, productCode);
			float editedGrossProfitDollars_DB = DffrHelpers.calculateGrossProfitDollars(editedShippingUnits_DB,
					productCode);
			float editedDistribution_DB = DffrHelpers.calculateDistribution(editedShippingUnits_DB,
					forecastedShippingUnits_UI, productCode, futureMonthPeriodList.get(i));
			float editedContribution_DB = DffrHelpers.calculateContribution(editedShippingUnits_DB,
					forecastedShippingUnits_UI, productCode, futureMonthPeriodList.get(i), editedShippingUnits_UI);

			Assert.assertEquals(editedShippingUnits_UI, editedShippingUnits_DB);
			Assert.assertEquals(Math.round(editedShippingDollars_UI), Math.round(editedShippingDollars_DB));
			Assert.assertEquals(Math.round(editedGrossProfitDollars_UI), Math.round(editedGrossProfitDollars_DB));
			Assert.assertEquals(editedDistribution_UI, editedDistribution_DB);
			Assert.assertEquals(editedContribution_UI, editedContribution_DB);
		}
	}

	@Test(enabled = true, description = "ADFFR-705> Automation: Verify that as a buyer, I should be able to edit the forecast by percentage of last years sales volume and same should be applied to Sales$, GrossProfit$, Distribution & Contribution.")
	public void ADFFR_705() throws Exception {

		List<String> futureMonthPeriodList = DffrHelpers.getFutureMonthPeriodList(12);
		List<String> pastMonthPeriodList = DffrHelpers.getPastMonthPeriodList();

		LandingPage landing = (LandingPage) getPageObject("LandingPage");
		landing.searchProduct(productCode);

		for (int i = 0; i < monthsToEdit; i++) {

			landing.openEditPopUp(i);
			landing.editByPercentLastYearShippingVolume(percent);
			landing.saveEditPopUp();

			int editedShippingUnits_UI = landing.readEditedShippingUnits(i);
			int forecastedShippingUnits_UI = landing.readForecastedShippingUnits(i);
			float editedShippingDollars_UI = landing.readEditedShippingDollars(i);
			float editedGrossProfitDollars_UI = landing.readEditedGrossProfitDollarsDollars(i);
			float editedDistribution_UI = landing.readEditedDistribution(i);
			float editedContribution_UI = landing.readEditedContribution(i);

			int editedShippingUnits_DB = DffrHelpers.calculateShippingUnitsOnLastYearShippingUnits(percent,
					pastMonthPeriodList.get(i), productCode);
			float editedShippingDollars_DB = DffrHelpers.calculateShippingDollars(editedShippingUnits_DB, productCode);
			float editedGrossProfitDollars_DB = DffrHelpers.calculateGrossProfitDollars(editedShippingUnits_DB,
					productCode);
			float editedDistribution_DB = DffrHelpers.calculateDistribution(editedShippingUnits_DB,
					forecastedShippingUnits_UI, productCode, futureMonthPeriodList.get(i));
			float editedContribution_DB = DffrHelpers.calculateContribution(editedShippingUnits_DB,
					forecastedShippingUnits_UI, productCode, futureMonthPeriodList.get(i), editedShippingUnits_UI);

			Assert.assertEquals(editedShippingUnits_UI, editedShippingUnits_DB);
			Assert.assertEquals(Math.round(editedShippingDollars_UI), Math.round(editedShippingDollars_DB));
			Assert.assertEquals(Math.round(editedGrossProfitDollars_UI), Math.round(editedGrossProfitDollars_DB));
			Assert.assertEquals(editedDistribution_UI, editedDistribution_DB);
			Assert.assertEquals(editedContribution_UI, editedContribution_DB);
		}
	}

	@Test(enabled = true, description = "ADFFR-706> Automation: Verify that as a buyer, I should be able to edit the forecast by percentage of absolute volume and same should be applied to Sales$, GrossProfit$, Distribution & Contribution.")
	public void ADFFR_706() throws Exception {

		List<String> futureMonthPeriodList = DffrHelpers.getFutureMonthPeriodList(12);

		LandingPage landing = (LandingPage) getPageObject("LandingPage");
		landing.searchProduct(productCode);

		for (int i = 0; i < monthsToEdit; i++)
		{

			landing.openEditPopUp(i);
			int currentForecastedShippingUnits = landing.readForecastedShippingUnitsFromEditPopUp();
			landing.editByPercentForecastedVolume(percent);
			landing.saveEditPopUp();

			int editedShippingUnits_UI = landing.readEditedShippingUnits(i);
			int forecastedShippingUnits_UI = landing.readForecastedShippingUnits(i);
			float editedShippingDollars_UI = landing.readEditedShippingDollars(i);
			float editedGrossProfitDollars_UI = landing.readEditedGrossProfitDollarsDollars(i);
			float editedDistribution_UI = landing.readEditedDistribution(i);
			float editedContribution_UI = landing.readEditedContribution(i);

			int editedShippingUnits_DB = DffrHelpers.calculateShippingUnitsOnForecastedShippingUnits(percent,
					currentForecastedShippingUnits);
			float editedShippingDollars_DB = DffrHelpers.calculateShippingDollars(editedShippingUnits_DB, productCode);
			float editedGrossProfitDollars_DB = DffrHelpers.calculateGrossProfitDollars(editedShippingUnits_DB,
					productCode);
			float editedDistribution_DB = DffrHelpers.calculateDistribution(editedShippingUnits_DB,
					forecastedShippingUnits_UI, productCode, futureMonthPeriodList.get(i));
			float editedContribution_DB = DffrHelpers.calculateContribution(editedShippingUnits_DB,
					forecastedShippingUnits_UI, productCode, futureMonthPeriodList.get(i), editedShippingUnits_UI);

			Assert.assertEquals(editedShippingUnits_UI, editedShippingUnits_DB);
			Assert.assertEquals(Math.round(editedShippingDollars_UI), Math.round(editedShippingDollars_DB));
			Assert.assertEquals(Math.round(editedGrossProfitDollars_UI), Math.round(editedGrossProfitDollars_DB));
			Assert.assertEquals(editedDistribution_UI, editedDistribution_DB);
			Assert.assertEquals(editedContribution_UI, editedContribution_DB);
		}
	}

	@Test(enabled = true, description = "ADFFR-707> Automation: Verify that as a buyer, I should be able to edit the forecast by percentage of last years sales volume and apply to remaining months then same should be applied to Sales$, GrossProfit$, Distribution & Contribution.")
	public void ADFFR_707() throws Exception {

		List<String> futureMonthPeriodList = DffrHelpers.getFutureMonthPeriodList(12);
		List<String> pastMonthPeriodList = DffrHelpers.getPastMonthPeriodList();

		LandingPage landing = (LandingPage) getPageObject("LandingPage");
		landing.searchProduct(productCode);

		landing.openEditPopUp(0);
		landing.editByPercentLastYearShippingVolume(percent);
		landing.ApplyToRemainingMonths();
		landing.saveEditPopUp();

		for (int i = 0; i < monthsToEdit; i++) {

			int editedShippingUnits_UI = landing.readEditedShippingUnits(i);
			int forecastedShippingUnits_UI = landing.readForecastedShippingUnits(i);
			float editedShippingDollars_UI = landing.readEditedShippingDollars(i);
			float editedGrossProfitDollars_UI = landing.readEditedGrossProfitDollarsDollars(i);
			float editedDistribution_UI = landing.readEditedDistribution(i);
			float editedContribution_UI = landing.readEditedContribution(i);

			int editedShippingUnits_DB = DffrHelpers.calculateShippingUnitsOnLastYearShippingUnits(percent,
					pastMonthPeriodList.get(i), productCode);
			float editedShippingDollars_DB = DffrHelpers.calculateShippingDollars(editedShippingUnits_DB, productCode);
			float editedGrossProfitDollars_DB = DffrHelpers.calculateGrossProfitDollars(editedShippingUnits_DB,
					productCode);
			float editedDistribution_DB = DffrHelpers.calculateDistribution(editedShippingUnits_DB,
					forecastedShippingUnits_UI, productCode, futureMonthPeriodList.get(i));
			float editedContribution_DB = DffrHelpers.calculateContribution(editedShippingUnits_DB,
					forecastedShippingUnits_UI, productCode, futureMonthPeriodList.get(i), editedShippingUnits_UI);
			
			System.out.println("editedShippingUnits_UI = " + editedShippingUnits_UI);
			System.out.println("editedShippingUnits_DB = " + editedShippingUnits_DB);
			Assert.assertEquals(editedShippingUnits_UI, editedShippingUnits_DB);
			Assert.assertEquals(Math.round(editedShippingDollars_UI), Math.round(editedShippingDollars_DB));
			Assert.assertEquals(Math.round(editedGrossProfitDollars_UI), Math.round(editedGrossProfitDollars_DB));
			Assert.assertEquals(editedDistribution_UI, editedDistribution_DB);
			Assert.assertEquals(editedContribution_UI, editedContribution_DB);
		}
	}

	@Test(enabled = true, description = "ADFFR-708> Automation: Verify that as a buyer, I should be able to edit the forecast by percent of forecasted volume and apply it to all the remaining months.")
	public void ADFFR_708() throws Exception {

		List<String> futureMonthPeriodList = DffrHelpers.getFutureMonthPeriodList(15);
		List<Integer> currentForecastedShippingUnitsList = new ArrayList<>();

		LandingPage landing = (LandingPage) getPageObject("LandingPage");
		landing.searchProduct(productCode);

		for (int i = 0; i < monthsToEdit; i++) {

			landing.openEditPopUp(i);
			int currentForecastedShippingUnits = landing.readForecastedShippingUnitsFromEditPopUp();
			currentForecastedShippingUnitsList.add(currentForecastedShippingUnits);
			landing.cancelEditPopUp();
		}

		landing.openEditPopUp(0);
		landing.editByPercentForecastedVolume(percent);
		landing.ApplyToRemainingMonths();
		landing.saveEditPopUp();

		for (int i = 0; i < monthsToEdit; i++) {
			int editedShippingUnits_UI = landing.readEditedShippingUnits(i);
			int forecastedShippingUnits_UI = landing.readForecastedShippingUnits(i);
			float editedShippingDollars_UI = landing.readEditedShippingDollars(i);
			float editedGrossProfitDollars_UI = landing.readEditedGrossProfitDollarsDollars(i);
			float editedDistribution_UI = landing.readEditedDistribution(i);
			float editedContribution_UI = landing.readEditedContribution(i);
			int editedShippingUnits_DB = DffrHelpers.calculateShippingUnitsOnForecastedShippingUnits(percent,
					currentForecastedShippingUnitsList.get(i));
			float editedShippingDollars_DB = DffrHelpers.calculateShippingDollars(editedShippingUnits_DB, productCode);
			float editedGrossProfitDollars_DB = DffrHelpers.calculateGrossProfitDollars(editedShippingUnits_DB,
					productCode);
			float editedDistribution_DB = DffrHelpers.calculateDistribution(editedShippingUnits_DB,
					forecastedShippingUnits_UI, productCode, futureMonthPeriodList.get(i));
			float editedContribution_DB = DffrHelpers.calculateContribution(editedShippingUnits_DB,
					forecastedShippingUnits_UI, productCode, futureMonthPeriodList.get(i), editedShippingUnits_UI);
			
			Assert.assertEquals(editedShippingUnits_UI, editedShippingUnits_DB);
			Assert.assertEquals(Math.round(editedShippingDollars_UI), Math.round(editedShippingDollars_DB));
			Assert.assertEquals(Math.round(editedGrossProfitDollars_UI), Math.round(editedGrossProfitDollars_DB));
			Assert.assertEquals(editedDistribution_UI, editedDistribution_DB);
			Assert.assertEquals(editedContribution_UI, editedContribution_DB);
		}
	}

	@Test(enabled = true, description = "ADFFR-709> Automation: Verify that as a buyer, I should see forecasted data for 15 months once I search for a product using product code.")
	public void ADFFR_709() throws Exception {
		LandingPage landing = (LandingPage) getPageObject("LandingPage");
		landing.searchProduct(productCode);

		int totalMonthCount = landing.getTotalMonthCount();

		Assert.assertEquals(totalMonthCount, 9);
	}

	@Test(enabled = true, description = "ADFFR-710> Automation: Verify that as a buyer, I should be able to create different versions of a product and be able to switch different versions.")
	public void ADFFR_710() throws Exception {

		LandingPage landing = (LandingPage) getPageObject("LandingPage");
		landing.searchProduct(productCode);

		String selectedVersion = landing.getSelectedVersion();
		selectedVersion = selectedVersion.substring(0, 13);

		Assert.assertEquals(selectedVersion, "AutoGenerated");
		Assert.assertEquals(landing.isForecastEditable(0), true);

		landing.openEditPopUp(0);
		landing.editByAbsoluteVolume(9658);
		landing.saveEditPopUp();

		landing.lockProduct();

		Assert.assertEquals(landing.isDiscardChangesPopUpDisplayed(), true);
		landing.cancelDiscardChangesPopUp();

		landing.lockProduct();
		landing.acceptDiscardChangesPopUp();

		Assert.assertEquals(landing.isLockProductPopUpDisplayed(), true);

		landing.closeLockProductPopUp();
		Assert.assertEquals(landing.readEditedShippingUnits(0), 0);

		landing.openEditPopUp(0);
		landing.editByAbsoluteVolume(8523);
		landing.saveEditPopUp();

		landing.saveVersion();

		selectedVersion = landing.getSelectedVersion();
		selectedVersion = selectedVersion.substring(0, 2);

		Assert.assertEquals(selectedVersion, "V1");

		Assert.assertEquals(landing.isForecastEditable(5), true);
		Assert.assertEquals(landing.readEditedShippingUnits(0), 8523);

		landing.selectVersion(1);

		selectedVersion = landing.getSelectedVersion();
		selectedVersion = selectedVersion.substring(0, 13);

		Assert.assertEquals(selectedVersion, "AutoGenerated");
		Assert.assertEquals(landing.isForecastEditable(0), false);

		DffrHelpers.updateVersionDesc(productCode);

		SeleniumHelpers.refreshBrowser();

		landing.searchProduct(productCode);

		landing.openEditPopUp(0);
		landing.editByAbsoluteVolume(9513);
		landing.saveEditPopUp();

		landing.saveVersion();

		selectedVersion = landing.getSelectedVersion();
		selectedVersion = selectedVersion.substring(0, 2);

		Assert.assertEquals(selectedVersion, "V2");
		Assert.assertEquals(landing.isForecastEditable(0), true);
		Assert.assertEquals(landing.readEditedShippingUnits(0), 9513);

		landing.selectVersion(2);

		selectedVersion = landing.getSelectedVersion();
		selectedVersion = selectedVersion.substring(0, 13);

		Assert.assertEquals(selectedVersion, "AutoGenerated");
		Assert.assertEquals(landing.isForecastEditable(0), false);

		landing.selectVersion(1);

		selectedVersion = landing.getSelectedVersion();
		selectedVersion = selectedVersion.substring(0, 2);

		Assert.assertEquals(selectedVersion, "V1");
		Assert.assertEquals(landing.isForecastEditable(0), false);

	}

	 /*@Test(enabled = true, description = "ADFFR-TC1> Automation: Verify that on providing product number showing valid alert on UI or not")
	  public void ADFFR_TC1() throws Exception
	  {
		  LandingPage landing = (LandingPage) getPageObject("LandingPage");
		  landing.searchProduct(productCode);

		  String alertText_UI = landing.getAlertText();

  		  SQL ="select * from ProductAlert where productid in( 230)";

		  String alertText_DB = DatabaseHelpers.select(SQL, "Description");

		  Assert.assertEquals(alertText_UI, alertText_DB);
	  }*/


	@Test(enabled = true, description = "ADFFR-TC2> Automation : Verify that on click on DFFR Metrics, PDF shouldget open in new tab ")
	public void ADFFR_856() throws Exception
	{
		LandingPage landing = (LandingPage) getPageObject("LandingPage");
		Thread.sleep(3000);
		landing.clickDffrMetrics();

		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		driver.manage().window().maximize();
		driver.get("http://10.237.10.61//Content/documents/DFFR_Formulas.pdf");
		String getURL = driver.getCurrentUrl();
		Assert.assertTrue(getURL.contains(".pdf"));
	}

	@Test(enabled = true, description = "ADFFR-TC3> Automation:Verify that on edit of update forecast,actual  comment and  change history comment should get matched")
	public void ADFFR_TC3() throws Exception
	{
		List<Integer> randomVolumeList = DffrHelpers.generateRandomNumberList(13);
		LandingPage landing = (LandingPage) getPageObject("LandingPage");
		landing.searchProduct(productCode);
		//Thread.sleep(3000);

		landing.getShippingUnitsTextStatus();
		landing.openEditPopUp(2);
		Thread.sleep(2000);
		landing.editByAbsoluteVolume(randomVolumeList.get(2));

		String comment = "This is for test";

		landing.editTextUpdateForecastSupportingNotes(comment);
		landing.saveEditPopUp();

		landing.saveVersion();

		landing.getChangeHistoryTextStatus();
		landing.clickChangeHistoryText();

		boolean isCommentPresent = landing.textCommentAfterUpdateForecast(comment);

			//Assert.assertEquals(isCommentPresent, true);

		Assert.assertEquals(comment, landing.getTextafterEditForeCast());


	} 
}
