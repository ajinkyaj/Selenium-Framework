package com.sl.dffr.utilfactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DffrHelpers {

	public static String getTodaysDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date).toString();
	}

	private static int getCurrentYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	public static String getCurrentMonth() {
		String month = "0" + (Calendar.getInstance().get(Calendar.MONTH) + 1);
		month = month.substring(month.length() - 2, month.length());
		return month;
	}

	private static String getCurrentDayOfMonth() {
		String day = "0" + Calendar.getInstance().get(Calendar.DATE);
		day = day.substring(day.length() - 2, day.length());
		return day;
	}

	public static String getDailyCalendarId() {
		return getCurrentYear() + getCurrentMonth() + getCurrentDayOfMonth();
	}

	public static String getFirstDailyCalendarId() {
		return getCurrentYear() + "0101";
	}

	public static String getMonthCalendarId() {
		return getCurrentYear() + getCurrentMonth();
	}

	public static String getFirstDateOfMonth() {
		return getCurrentYear() + "-" + getCurrentMonth() + "-01";
	}

	public static List<String> getFutureMonthPeriodList(int noOfMonths) {
		List<String> futureMonthPeriodList = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		for (int i = 1; i <= noOfMonths + 1; i++) {
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.MONTH, i);
			c.add(Calendar.DATE, (c.get(Calendar.DATE) - 1) * -1);
			String monthPeriod = sdf.format(c.getTime());
			futureMonthPeriodList.add(monthPeriod);
		}
		return futureMonthPeriodList;
	}

	public static List<String> getPastMonthPeriodList() {
		List<String> pastMonthPeriodList = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		for (int i = 11; i >= -1; i--) {
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.add(Calendar.MONTH, -i);
			c.add(Calendar.DATE, (c.get(Calendar.DATE) - 1) * -1);
			String monthPeriod = sdf.format(c.getTime());
			pastMonthPeriodList.add(monthPeriod);
		}
		return pastMonthPeriodList;
	}

	public static List<Integer> generateRandomNumberList(int count) {
		List<Integer> randomNumberList = new ArrayList<>();
		for (int i = 1; i <= count; i++) {
			int random = (int) (Math.random() * 8999 + 1000);
			randomNumberList.add(random);
		}
		return randomNumberList;
	}

	public static int parseIntFromString(String value) {
		value = value.replaceAll("[^0-9-]", "");
		return Integer.parseInt(value);
	}
	
	public static long parseLongFromString(String value) {
		value = value.replaceAll("[^0-9-]", "");
		return Long.parseLong(value);
	}

	public static float parseFloatFromString(String value) {
		value = value.replaceAll("[^0-9.-]", "");
		return round(Float.parseFloat(value));
	}

	private static float round(float d) {
		float roundValue = BigDecimal.valueOf(d).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
		return roundValue;
	}

	public static float calculateShippingDollars(int units, String productCode)
			throws ClassNotFoundException, IOException, SQLException {
		String SQL = "select top(1) * from ProductRetailPrice where ProductId = (select ProductId from Product where ProductCode = "
				+ productCode + ") and DivisionId = 1 order by ProductRetailPriceId desc";
		String retailPrice = DatabaseHelpers.select(SQL, "RetailPrice");
		float shippingDollars = units * Float.parseFloat(retailPrice);
		shippingDollars = round(shippingDollars);
		return shippingDollars;
	}

	public static float calculateGrossProfitDollars(int units, String productCode)
			throws ClassNotFoundException, IOException, SQLException {
		String SQL = "select top(1) *from ProductCostPrice where ProductId = (select ProductId from Product where ProductCode = "
				+ productCode + ") and DivisionId = 1 order by ProductCostPriceId desc";
		String costPrice = DatabaseHelpers.select(SQL, "ProductCost");
		float grossProfitDollars = calculateShippingDollars(units, productCode) - (units * Float.parseFloat(costPrice));
		grossProfitDollars = round(grossProfitDollars);
		return grossProfitDollars;
	}
	
	public static float calculateGrossProfitDollarsActuals(int units, String productCode, float dollars)
			throws ClassNotFoundException, IOException, SQLException {
		String SQL = "select top(1) *from ProductCostPrice where ProductId = (select ProductId from Product where ProductCode = "
				+ productCode + ") and DivisionId = 1 order by ProductCostPriceId desc";
		String costPrice = DatabaseHelpers.select(SQL, "ProductCost");
		float grossProfitDollars = dollars - (units * Float.parseFloat(costPrice));
		grossProfitDollars = round(grossProfitDollars);
		return grossProfitDollars;
	}

	public static float calculateGrossProfitPercent(int units, String productCode)
			throws ClassNotFoundException, IOException, SQLException {
		float grossProfitPercent = (calculateGrossProfitDollars(units, productCode)
				/ calculateShippingDollars(units, productCode)) * 100;
		grossProfitPercent = round(grossProfitPercent);
		return grossProfitPercent;
	}

	public static float calculateDistribution(int editedShippingUnits, int forecastedShippingUnits, String productCode,
			String monthCalendarId) throws ClassNotFoundException, IOException, SQLException {
		String SQL = "select top(1) * from ProductRetailPrice where ProductId=230 and DivisionId=1 order by ProductRetailPriceId desc";
		double totalDemand = calculateTotalDemand(monthCalendarId)
				+ (calculateEditedForecastedShippingUnitsDiff(editedShippingUnits, forecastedShippingUnits)
						* Double.parseDouble(DatabaseHelpers.select(SQL, "RetailPrice")));
		double totalProductDemand = calculateProductDemand(productCode, monthCalendarId)
				+ (calculateEditedForecastedShippingUnitsDiff(editedShippingUnits, forecastedShippingUnits)
						* Double.parseDouble(DatabaseHelpers.select(SQL, "RetailPrice")));
		double d = (100 / totalDemand) * totalProductDemand;
		float distribution = (float) d;
		distribution = round(distribution);
		return distribution;
	}

	public static float calculateContribution(int editedShippingUnits, int forecastedShippingUnits, String productCode,
			String monthCalendarId, int units) throws ClassNotFoundException, IOException, SQLException {

		float distribution = calculateDistribution(editedShippingUnits, forecastedShippingUnits, productCode,
				monthCalendarId);
		float grossProfitPercent = calculateGrossProfitPercent(units, productCode);
		float contribution = distribution * (grossProfitPercent / 100);
		contribution = round(contribution);
		return contribution;
	}

	private static double calculateTotalDemand(String monthCalendarId)
			throws ClassNotFoundException, IOException, SQLException {
		String SQL = "select *from MonthlyAutomatedSalesForecast where MonthCalendarId = " + monthCalendarId
				+ " and DivisionId = 1";
		double totalDemand = DatabaseHelpers.getSumDouble(SQL, "Demand");
		return totalDemand;
	}

	private static double calculateProductDemand(String productCode, String monthCalendarId)
			throws ClassNotFoundException, IOException, SQLException {
		String SQL = "select *from MonthlyAutomatedSalesForecast where MonthCalendarId = " + monthCalendarId
				+ " and DivisionId = 1 and ProductId = (select ProductId from Product where ProductCode = "
				+ productCode + ")";
		double productDemand = DatabaseHelpers.getSumDouble(SQL, "Demand");
		return productDemand;
	}

	private static int calculateEditedForecastedShippingUnitsDiff(int editedShippingUnits,
			int forecastedShippingUnits) {
		int diffShippingUnits = editedShippingUnits - forecastedShippingUnits;
		return diffShippingUnits;
	}

	public static int calculateShippingUnitsOnLastYearShippingUnits(float percent, String lastYearsMonthCalandarId,
			String productCode) throws ClassNotFoundException, IOException, SQLException {
		String SQL = "select * from MonthlyShipping where ProductId = (select ProductId from Product where ProductCode = "
				+ productCode + ")" + " and DivisionId=1 and MonthCalendarId = " + lastYearsMonthCalandarId;
		int lastYearsShippingUnits = Integer.parseInt(DatabaseHelpers.select(SQL, "Quantity"));
		float percentLastYearsShippingUnits = ((lastYearsShippingUnits * percent) / 100) + lastYearsShippingUnits;
		return (int) Math.ceil(percentLastYearsShippingUnits);
	}

	public static int calculateShippingUnitsOnForecastedShippingUnits(float percent, int forecastedShippingUnits) {
		float percentForecastedShippingUnits = ((forecastedShippingUnits * percent) / 100) + forecastedShippingUnits;
		return (int) Math.ceil(percentForecastedShippingUnits);
	}

	public static void updateVersionDesc(String productCode) throws ClassNotFoundException, IOException, SQLException {
		String SQL = "select top(1) * from MonthlyForecastVersion where ProductId = (select ProductId from Product where ProductCode = "
				+ productCode + ") order by MonthlyForecastVersionId";

		String versionDesc = DatabaseHelpers.select(SQL, "VersionDesc");
		versionDesc = versionDesc.substring(0, versionDesc.lastIndexOf("-")) + "-Automation";

		String monthlyForecastVersionId = DatabaseHelpers.select(SQL, "monthlyForecastVersionId");

		SQL = "update MonthlyForecastVersion set VersionDesc = '" + versionDesc
				+ "', CreatedBy = 'Automation' where MonthlyForecastVersionId = " + monthlyForecastVersionId;

		DatabaseHelpers.update(SQL);
	}

}
