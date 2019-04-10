package com.sl.dffr.utilfactory;

import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonHelpers {

	private static String value = null;

	public static String getValue(String key) {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(ConfigReader.readValue("TESTDATA_JSON")));
			JSONArray array = (JSONArray) obj;
			JSONObject jsonObject = (JSONObject) array.get(0);
			value = (String) jsonObject.get(key);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return value;
	}

	public static String getValue(int index, String key) {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(ConfigReader.readValue("TESTDATA_JSON")));
			JSONArray array = (JSONArray) obj;
			JSONObject jsonObject = (JSONObject) array.get(index);
			value = (String) jsonObject.get(key);

		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return value;
	}

}
