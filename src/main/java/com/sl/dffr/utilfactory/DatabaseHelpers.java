package com.sl.dffr.utilfactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelpers {

	private static Connection conn = null;
	private static Statement stmt = null;
	private static ResultSet rs = null;
	private static String result = null;

	private static Connection getConnection() throws ClassNotFoundException, IOException, SQLException {

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(ConfigReader.readValue("DB_URL"), ConfigReader.readValue("DB_USERNAME"),
					ConfigReader.readValue("DB_PASSWORD"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	private static void closeConnection() throws ClassNotFoundException, IOException, SQLException {
		rs.close();
		stmt.close();
		conn.close();
	}

	private static ResultSet getResultSet(String SQL) throws SQLException {
		stmt = conn.createStatement();
		rs = stmt.executeQuery(SQL);
		return rs;

	}

	public static String select(String SQL, String columnName)
			throws ClassNotFoundException, IOException, SQLException {
		conn = getConnection();

		if (conn != null) {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				result = rs.getString(columnName);
			}
			closeConnection();
		}
		return result;
	}

	public static int getSumInt(String SQL, String columnName)
			throws ClassNotFoundException, IOException, SQLException {
		int sum = 0;
		conn = getConnection();

		if (conn != null) {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				result = rs.getString(columnName);
				sum = sum + Integer.parseInt(result);
			}
			closeConnection();
		}
		return sum;
	}
	
	public static long getSumLong(String SQL, String columnName)
			throws ClassNotFoundException, IOException, SQLException {
		long sum = 0;
		conn = getConnection();

		if (conn != null) {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				result = rs.getString(columnName);
				sum = sum + Long.parseLong(result);
			}
			closeConnection();
		}
		return sum;
	}
	
	public static double getSumDouble(String SQL, String columnName)
			throws ClassNotFoundException, IOException, SQLException {
		double sum = 0;
		conn = getConnection();

		if (conn != null) {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				result = rs.getString(columnName);
				sum = sum + Double.parseDouble(result);
			}
			closeConnection();
		}
		return sum;
	}

	public static float getSumFloat(String SQL, String columnName)
			throws ClassNotFoundException, IOException, SQLException {
		float sum = 0;
		conn = getConnection();

		if (conn != null) {
			rs = getResultSet(SQL);
			while (rs.next()) {
				result = rs.getString(columnName);
				float resultNumber = Float.parseFloat(result);
				sum = sum + resultNumber;
			}
			closeConnection();
		}

		return sum;
	}

	public static int getCount(String SQL) throws ClassNotFoundException, IOException, SQLException {
		int sum = 0;
		conn = getConnection();

		if (conn != null) {
			rs = getResultSet(SQL);
			while (rs.next()) {
				sum = sum + 1;
			}
			closeConnection();
		}

		return sum;
	}
	
	public static void delete(String SQL) throws ClassNotFoundException, IOException, SQLException {
		conn = getConnection();
		if (conn != null) {
			stmt = conn.createStatement();
			stmt.executeUpdate(SQL);
			stmt.close();
			conn.close();
		}
	}
	
	public static void update(String SQL) throws ClassNotFoundException, IOException, SQLException {
		conn = getConnection();
		if (conn != null) {
			stmt = conn.createStatement();
			stmt.executeUpdate(SQL);
			stmt.close();
			conn.close();
		}
	}
	
}
