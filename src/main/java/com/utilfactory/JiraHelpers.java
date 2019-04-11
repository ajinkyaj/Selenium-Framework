package com.utilfactory;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import org.apache.http.auth.AuthenticationException;
import org.json.simple.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.Base64;

public class JiraHelpers {

	private static String auth = null;
	private static String data = null;
	private static String project = null;
	private static String issueType = null;
	private static String summary = null;
	private static String description = null;
	private static String componentType = null;
	private static int status;
	private static String id = null;
	
	public static String createIssue(String jiraProjectKey, String jiraIssueType, String jiraSummary, String jiraDescription, String jiraComponent)
			throws AuthenticationException, IOException {

		project = createJsonObject(jiraProjectKey, "key");
		issueType = createJsonObject(jiraIssueType, "name");
		summary = createJsonObject(jiraSummary, "summary");
		description = createJsonObject(jiraDescription, "description");
		componentType = createJsonObject(jiraComponent, "name");

		Client client = Client.create();

		WebResource webResource = client.resource(ConfigReader.readValue("CREATE_ISSUE_RESOURCE"));

		data = "{" + "\"fields\": {" + "\"project\":{" + project + "}," + summary + "," + description + ","
				+ "\"issuetype\": {" + issueType + "}," + "\"components\": [{" + componentType + "}]" + "}}";

		auth = new String(Base64
				.encode(ConfigReader.readValue("USERNAME") + ":" + ConfigReader.readValue("PASSWORD")));

		ClientResponse response = webResource.header("Authorization", "Basic " + auth).type("application/json")
				.accept("application/json").post(ClientResponse.class, data);

		int statusCode = response.getStatus();
		if (statusCode == 200 || statusCode == 201 || statusCode == 204) {
			String result = response.getEntity(String.class);
			return jiraProjectKey + "-" + result.substring(result.indexOf("-") + 1)
					.substring(0, result.substring(result.indexOf("-") + 1).indexOf("\"")).trim();
		} else {
			return null;
		}
	}
	
	public static boolean addComment(String jiraID, String jiraComment) throws AuthenticationException, IOException {
		Client client = Client.create();

		WebResource webResource = client
				.resource(ConfigReader.readValue("CREATE_ISSUE_RESOURCE") + jiraID + "/comment");

		data = "{\r\n" + "    \"body\": \"" + jiraComment + "\"\r\n" + "}";

		auth = new String(Base64
				.encode(ConfigReader.readValue("USERNAME") + ":" + ConfigReader.readValue("PASSWORD")));

		ClientResponse response = webResource.header("Authorization", "Basic " + auth).type("application/json")
				.accept("application/json").post(ClientResponse.class, data);

		int statusCode = response.getStatus();
		if (statusCode == 200 || statusCode == 201 || statusCode == 204) {
			return true;
		} else {
			System.out.println(statusCode);
			return false;
		}
	}
	
	public static boolean transitionIssue(String jiraID, String jiraStatus) throws AuthenticationException, IOException {
		if (jiraStatus.equalsIgnoreCase("OPEN")) {
			setJiraStatusCode(3);
		} else if (jiraStatus.equalsIgnoreCase("CLOSE")) {
			setJiraStatusCode(2);
		} else {
			return false;
		}
		Client client = Client.create();

		WebResource webResource = client
				.resource(ConfigReader.readValue("CREATE_ISSUE_RESOURCE") + jiraID + "/transitions");

		data = "{\r\n" + "  \"transition\": {\r\n" + "    \"id\": \"" + getJiraStatusCode() + "\"\r\n" + "  }\r\n"
				+ "}";

		auth = new String(Base64
				.encode(ConfigReader.readValue("USERNAME") + ":" + ConfigReader.readValue("PASSWORD")));

		ClientResponse response = webResource.header("Authorization", "Basic " + auth).type("application/json")
				.accept("application/json").post(ClientResponse.class, data);

		int statusCode = response.getStatus();
		if (statusCode == 200 || statusCode == 201 || statusCode == 204) {
			return true;
		} else {
			return false;
		}
	}

	public static void editIssue(String jiraID) throws AuthenticationException, IOException {
		Client client = Client.create();
		WebResource webResource = client.resource(ConfigReader.readValue("JIRA_BASE_URL") + ConfigReader.readValue("JIRA_CREATE_ISSUE_RESOURCE") + jiraID);

		data = "{\"fields\":{\"summary\":\"updated via java1\"}}}";

		auth = new String(Base64.encode(ConfigReader.readValue("JIRA_USERNAME") + ":" + ConfigReader.readValue("JIRA_PASSWORD")));
		ClientResponse response = webResource.header("Authorization", "Basic " + auth).type("application/json")
				.accept("application/json").put(ClientResponse.class, data);
		int statusCode = response.getStatus();
		if (statusCode == 401) {
			throw new AuthenticationException("Invalid Username or Password!");
		} else if (statusCode == 403) {
			throw new AuthenticationException("Forbidden!");
		} else if (statusCode == 200 || statusCode == 201 || statusCode == 204) {
			System.out.println("Ticket updated succesfully!");
		} else {
			System.out.print("Http Error : " + statusCode+"!");
		}
	}
	
	@SuppressWarnings("unchecked")
	private static String createJsonObject(String message, String parameter) throws IOException {
		JSONObject obj = new JSONObject();
		obj.put(parameter, message);
		StringWriter out = new StringWriter();
		obj.writeJSONString(out);
		String jsonObject = out.toString();
		jsonObject = jsonObject.substring(1, jsonObject.length() - 1);
		return jsonObject;
	}

	public static String searchSummary(String jiraSummary) throws AuthenticationException, IOException {
		Client client = Client.create();
		WebResource webResource = client.resource(
				ConfigReader.readValue("JQL_SEARCH_SUMMARY_RESOURCE") + jiraSummary.replace(" ", "%20") + "'");

		auth = new String(Base64
				.encode(ConfigReader.readValue("USERNAME") + ":" + ConfigReader.readValue("PASSWORD")));

		ClientResponse response = webResource.header("Authorization", "Basic " + auth).type("application/json")
				.accept("application/json").get(ClientResponse.class);

		String res = response.getEntity(String.class);

		int statusCode = response.getStatus();
		if (statusCode == 200 || statusCode == 201 || statusCode == 204) {

			String str = res.substring(res.indexOf("total") + 7);
			str = str.substring(0, str.indexOf(","));
			int count = Integer.parseInt(str);

			if (count > 0) {
				String result = res.substring(res.indexOf("key") + 6);
				result = result.substring(0, result.indexOf("\""));
				id = result;
			}
		}
		return id;
	}

	public static String getIssueStatus(String jiraID) throws AuthenticationException, IOException {
		
		Client client = Client.create();
		
		WebResource webResource = client
				.resource(ConfigReader.readValue("CREATE_ISSUE_RESOURCE") + jiraID + "/transitions");

		auth = new String(Base64
				.encode(ConfigReader.readValue("USERNAME") + ":" + ConfigReader.readValue("PASSWORD")));
		
		ClientResponse response = webResource.header("Authorization", "Basic " + auth).type("application/json")
				.accept("application/json").get(ClientResponse.class);

		int statusCode = response.getStatus();
		if (statusCode == 200 || statusCode == 201 || statusCode == 204) {
			String result = response.getEntity(String.class);
			result = result.substring(result.indexOf("id") + 5);
			result = result.substring(0, result.indexOf("\""));
			if (result.equalsIgnoreCase("3")) {
				result = "CLOSED";
			} else if (result.equalsIgnoreCase("4")) {
				result = "OPEN";
			} else {
				return null;
			}
			return result;
		} else {
			return null;
		}
	}
	
	public static int getJiraStatusCode() {
		return status;
	}

	public static void setJiraStatusCode(int jiraStatusCode) {
		JiraHelpers.status = jiraStatusCode;
	}

	public static boolean addAttachmentJira(String jiraID, String filePath) throws IOException, InterruptedException, UnirestException {
		auth = new String(Base64
				.encode(ConfigReader.readValue("USERNAME") + ":" + ConfigReader.readValue("PASSWORD")));

		HttpResponse<JsonNode> response = Unirest
				.post(ConfigReader.readValue("CREATE_ISSUE_RESOURCE")+jiraID+"/attachments")
				.header("Accept", "application/json").header("Authorization", "Basic " + auth)
				.header("X-Atlassian-Token", "no-check")
				.field("file", new File(filePath))
				.asJson();

		Unirest.shutdown();

		int statusCode = response.getStatus();
		if (statusCode == 200 || statusCode == 201 || statusCode == 204) {
			return true;
		} else {
			return false;
		}
	}
	
}
