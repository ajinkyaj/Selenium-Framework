package com.utilfactory;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class XmlHelpers {

	private static String value = null;

	public static String getValue(String tag, String key) {
		try {
			File fXmlFile = new File(ConfigReader.readValue("TESTDATA_XML"));
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName(tag);

				Node nNode = nList.item(0);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					value = eElement.getElementsByTagName(key).item(0).getTextContent();
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	public static String getValue(String tag, int index, String key) {
		try {
			File fXmlFile = new File(ConfigReader.readValue("TESTDATA_XML"));
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName(tag);

			Node nNode = nList.item(index);

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;

				value = eElement.getElementsByTagName(key).item(0).getTextContent();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
}
