package Qwerty.qwerty;

import org.json.simple.JSONObject;
import org.json.simple.JsonObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ResponseBodyExtractionOptions;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.XML;

public class RestApi {

	static String apiURL = "http://172.16.1.31:8080/lynx-api/login.do?callback=callback&userName=bharat%40dikshatech.com&password=Lynx&method=login&cType=LOGIN&device=html&origin=xmlp";

	@Test(priority = 0, enabled = false)
	public void restGet() {
		RestAssured.urlEncodingEnabled = false;

		Response resp = RestAssured.get(apiURL);
		int actual = resp.getStatusCode();
		System.out.println("Status code is : " + actual);
		Assert.assertEquals(actual, 200);
	}

	@Test(priority = 1, enabled = true)
	public void restBody() {
		RestAssured.urlEncodingEnabled = false;

		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city/";
		RequestSpecification request = RestAssured.given();
		Response response = request.get("Hyderabad");

		ResponseBody respbody = response.body();
		System.out.println(respbody.asString());
		Assert.assertEquals(respbody.asString().contains("Hyderabad"), true);

		JsonPath jsonPathEvaluator = response.jsonPath();

		String value = jsonPathEvaluator.get("City");

		System.out.println(value);
		Assert.assertEquals(value, "Hyderabad");

		Response exResp = extractResp(apiURL);
		
		JsonPath jsonPath=exResp.jsonPath();

		System.out.println(jsonPath.get("userName"));
/*		System.out.println(exResp);
		Map<String, String> jsonResponse = exResp.jsonPath().getMap("$");
		System.out.println(jsonResponse.size());
		Assert.assertEquals(jsonResponse.get("userName"), "Bharat Raj D");*/

	}

	public static Response extractResp(String endpoint) {
		RestAssured.defaultParser = Parser.JSON;

		return given().headers("Content-Type", "application/json", "Accept", "application/json").when().get(endpoint).then().contentType("text/html")
				.extract().response();
	}

	@Test(priority = 2, enabled = false)
	public void jsonData() {
		RestAssured.urlEncodingEnabled = false;

		RequestSpecification request = RestAssured.given();
		Response response = request.get(apiURL);
		ResponseBody respbody = response.getBody();
		int PRETTY_PRINT_INDENT_FACTOR = 4;
		String TEST_XML_STRING = respbody.asString();
		org.json.JSONObject xmlJSONObj = XML.toJSONObject(TEST_XML_STRING);
		String jsonString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);

		
		// System.out.println(jsonString);
		// String usernames = respbody.jsonPath().getString("userName");
		// System.out.println(usernames);
		
		 Map<String, Object> map = new HashMap<String, Object>();
	        Iterator<String> keys = xmlJSONObj.keys();
	        while(keys.hasNext()) {
	            String key = keys.next();
	            Object value = xmlJSONObj.get(key);
	            System.out.println(value);
	            if (value instanceof JSONArray) {
	                //value = toList((JSONArray) value);
	            } else if (value instanceof JsonObject) {
	                value = map.put(key, value);
	                System.out.println(value);
	            }   
	        }   
	        }
		 

	@Test(priority = 4, enabled = false)
	public static void report() {
		RequestSpecification request = RestAssured.given();
		Response response = request.get(apiURL);
		System.out.println("*** Report Method****");
		ResponseBody respbody = response.body();
		System.out.println(respbody.asString());
	}

	/*
	 * @Test public void test01() {
	 * 
	 * Response res = given().param("query", "Churchgate Station").param("key",
	 * "Xyz").when()
	 * .get("/maps/api/place/textsearch/xml").then().contentType(ContentType.XML).
	 * extract().response();
	 * 
	 * System.out.println(res.asString());
	 * 
	 * }
	 */
	/*
	 * @Test(priority= 1, enabled= true) public void restPost() {
	 * 
	 * RequestSpecification req = RestAssured.given(); req.header("Content-Type",
	 * "application/json"); JsonObject json = new JsonObject(); json.put("id",
	 * "18"); json.put("title", "Post Method"); json.put("author",
	 * "Automation Script");
	 * 
	 * req.body(json.toJSONString()); Response resp
	 * =req.post("http://localhost:3000/posts"); int actual = resp.getStatusCode();
	 * System.out.println("Status code is : " + actual); Assert.assertEquals(actual,
	 * 201); }
	 * 
	 * @Test(priority= 2, enabled= true) public void restPut() {
	 * RequestSpecification req = RestAssured.given(); req.header("Content-Type",
	 * "application/json"); JSONObject json = new JSONObject(); json.put("id", "1");
	 * json.put("title", "Put Method"); json.put("author", "Selenium Script");
	 * 
	 * req.body(json.toJSONString()); Response resp
	 * =req.put("http://localhost:3000/posts/1"); int actual = resp.getStatusCode();
	 * System.out.println("Status code is : " + actual); Assert.assertEquals(actual,
	 * 200);
	 * 
	 * }
	 * 
	 * @Test(priority= 3, enabled= true) public void restDelete() {
	 * 
	 * Response resp = RestAssured.delete("http://localhost:3000/posts/14"); int
	 * actual = resp.getStatusCode(); System.out.println("Status code is : " +
	 * actual); Assert.assertEquals(actual, 200); }
	 */}