package com.sl.dffr.utilfactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
	public static String readValue(String key) throws IOException {
		Properties prop = new Properties();
		FileInputStream configFilePath = new FileInputStream("src/main/resources/config.properties");
		prop.load(configFilePath);
		return prop.getProperty(key);
	}
}
