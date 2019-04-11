package com.sl.dffr.uitests;

import java.util.List;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.pages.LandingPage;
import com.pages.LockProductPage;
import com.testplan.BaseTest;

public class CriticalFunction extends BaseTest{
	
	int copiedProduct[] = new int[10];
	int linkedProduct[] = new int[10];
	
	@Test
	public void inactiveProduct() throws Exception
	{
		LandingPage landing = (LandingPage) getPageObject("LandingPage");
		landing.searchProduct("57612");
		Thread.sleep(2000);
		Assert.assertEquals(landing.tosterMessage(), "Product is inactive");
		System.out.println(landing.tosterMessage());
	}
	
	@Test(enabled = true, description = "ADFFR-724, ADFFR-780, ADFFR-726, ADFFR-778"
			+ "Copy product / similar Product")
	public void ADFFR_213() throws Exception
	{
		LandingPage landing = (LandingPage) getPageObject("LandingPage");
		landing.searchProduct(copyProductCode);
		Thread.sleep(2000);
		
		Assert.assertEquals(landing.tosterMessage(), "Product has no shipping history");
		Assert.assertEquals(false, landing.applyForecastButtonStatus());
		
		landing.searchCopyProduct(productCode);
		Thread.sleep(2000);
		
		Assert.assertEquals(true, landing.applyForecastButtonStatus());
		
		landing.clickApplyForecast();
		Thread.sleep(2000);
		
		Assert.assertEquals(true, landing.getShippingUnitsTextStatus());
		Assert.assertEquals(false, landing.getShippingDollarsTextStatus());
		Assert.assertEquals(false, landing.getContributionTextStatus());
		Assert.assertEquals(false, landing.getDistributionTextStatus());
		
		for(int i = 0; i < monthsToEdit; i++ )
			copiedProduct[i] = landing.readForecastedShippingUnits(i);
		
		landing.searchProduct(productCode);
		Thread.sleep(2000);
		
		for(int i = 0; i < monthsToEdit; i++ )
			linkedProduct[i] = landing.readForecastedShippingUnits(i);
		
		//System.out.println("copiedProduct = " + copiedProduct + " linkedProduct = " + linkedProduct);
		for(int i = 0; i < monthsToEdit; i++ )
		{
			Assert.assertEquals(copiedProduct[i], linkedProduct[i]);
			//System.out.println("copiedProduct = " + copiedProduct[i] + " linkedProduct = " + linkedProduct[i]);
		}
	}
	
	@Test(enabled = true, description = "Copy Division")
	public void ADFFR_546() throws Exception
	{
		LandingPage landing = (LandingPage) getPageObject("LandingPage" );
		
		landing.searchProduct("2894");
		
		int parentDivisionShippingUnits[] = new int[5];
		int childDivisionShippingUnits[] = new int[5];
		
		for(int i=0; i<5; i++)
		{
			parentDivisionShippingUnits[i] = landing.readForecastedShippingUnits(i);
		}
		landing.click_copy_Division_Image();
		landing.click_copy_Division_Button();
		landing.click_copy_Division_Checkbox();
		
		Thread.sleep(3000);
		landing.click_copy_Division_Select();
		
		Thread.sleep(3000);
		landing.click_minimize_OpenDivision();
		
//		String divisionName = landing.getText_new_Copied_Division();
		
		landing.click_open_Copied_Division();
		
		Thread.sleep(3000);
		for(int i=0; i<5; i++)
		{
			childDivisionShippingUnits[i] = landing.readForecastedShippingUnitsBur(i);
			Assert.assertEquals(childDivisionShippingUnits[i], parentDivisionShippingUnits[i]);
		}
	}
	
	// Added on 10 April 2019 - AJ
	@Test(enabled = true, description = "ADFFR-TC4> Automation: Verify that on lock and unlock of product ,provided shipping forcast value should match.")
    public void TC4()throws Exception
    {
        LandingPage landing = (LandingPage) getPageObject("LandingPage");
        LockProductPage LPP = (LockProductPage) getPageObject("LockProductPage");
        
        int[] editedShippingUnits_UI = new int[10];
        int[] ShippingUnits_LockUi = new int[10];
        int[] ShippingUnits_OnUnlock = new int[10];
        
        landing.searchProduct(productCode);
       
        // Edit the forecast for 6 months
        Random random = new Random();
        landing.getShippingUnitsTextStatus();
        for(int i = 0; i < monthsToEdit; i++)
        {
        	landing.openEditPopUp(i);
        	
        	editedShippingUnits_UI[i] = random.nextInt(10000);
        	landing.editByAbsoluteVolume(editedShippingUnits_UI[i]);
        	landing.editTextUpdateForecastSupportingNotes("This is for test");
            landing.saveEditPopUp();
        }

        // Save the versions
        Thread.sleep(2000);
        landing.saveVersion();
        Thread.sleep(2000);

        // Lock the product
        landing.lockProductButton();  // click on lock product button
        LPP.loadReport("2019", "Feb", "9", "2019", "Dec", "31");
        LPP.lockProductAndClose();
      
        // Capture the value when product is locked
        for(int i = 0; i < monthsToEdit; i++)
        {
        	ShippingUnits_LockUi[i] = landing.readActualShippingUnitsLock(i);
        }
        
        // Unlock the product
        landing.unLockProduct();
        Thread.sleep(2000);
        landing.getUnlockButtonStatus();
        
        //Compare the values with at 3 stages
        for(int i = 0; i < monthsToEdit; i++)
        {
        	ShippingUnits_OnUnlock[i] = landing.readActualShippingUnitsUnlock(i);
        	Assert.assertEquals(editedShippingUnits_UI[i],ShippingUnits_OnUnlock[i]);
        	Assert.assertEquals(editedShippingUnits_UI[i],ShippingUnits_LockUi[i]);
        }

        
        

        
    }
}
