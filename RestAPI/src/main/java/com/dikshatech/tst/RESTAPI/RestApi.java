package com.dikshatech.tst.RESTAPI;

import org.json.simple.JSONObject;
import org.json.simple.JsonObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.XML;

public class RestApi {

	public static final Logger logger = Logger.getLogger(RestApi.class);
	static String apiURL = "http://172.16.1.31:8080/lynx-api/login.do?callback=callback&userName=bharat%40dikshatech.com&password=Lynx&method=login&cType=LOGIN&device=html&origin=xmlp";

	/**
	 * Method to validate the get response Status Code using Rest Assured
	 **/
	@Test(priority = 0, enabled = true)
	public static void getloginStatus() {
		RestAssured.urlEncodingEnabled = false;

		Response resp = RestAssured.get(apiURL);
		int actual = resp.getStatusCode();
		logger.info("Status code is : " + actual);
		Assert.assertEquals(actual, 200);
	}

	/**
	 * Method to convert XML to JSON and validate the key components value.
	 **/
	@Test(priority = 1, enabled = true)
	public static void loginAPI() {
		RestAssured.urlEncodingEnabled = false;

		Response exResp = RestAssured.get(apiURL);
		ResponseBody respbody1 = exResp.getBody();
		String TEST_XML_STRING = respbody1.asString();
		org.json.JSONObject xmlJSONObj = XML.toJSONObject(TEST_XML_STRING);
		String lastName = xmlJSONObj.getJSONObject("actionForm").getString("lastName");
		logger.info("Lastname of the logged in user is " + lastName);
	}

	/**
	 * Method to receive the JSON response and validate the key components value.
	 **/
	@Test(priority = 2, enabled = true)
	public static void weatherResponse() {
		RestAssured.urlEncodingEnabled = false;

		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city/";
		RequestSpecification request = RestAssured.given();
		Response response = request.get("Hyderabad");

		ResponseBody respbody = response.body();
		logger.info(respbody.asString());
		Assert.assertEquals(respbody.asString().contains("Hyderabad"), true);

		JsonPath jsonPathEvaluator = response.jsonPath();

		String value = jsonPathEvaluator.get("City");

		logger.info(value);
		Assert.assertEquals(value, "Hyderabad");
	}

	/**
	 * Method to convert other format response to accept it as JSON
	 **/
	public static Response jsonConverter(String endpoint) {
		RestAssured.defaultParser = Parser.JSON;

		return given().headers("Content-Type", "application/json", "Accept", "application/json").when().get(endpoint)
				.then().contentType("text/html").extract().response();
	}

	/**
	 * Method to convert XML to JSON with an proper Indentation
	 **/
	@Test(priority = 3, enabled = false)
	public static void jsonData() {
		RestAssured.urlEncodingEnabled = false;

		RequestSpecification request = RestAssured.given();
		Response response = request.get(apiURL);
		ResponseBody respbody = response.getBody();
		int PRETTY_PRINT_INDENT_FACTOR = 4;
		String TEST_XML_STRING = respbody.asString();
		org.json.JSONObject xmlJSONObj = XML.toJSONObject(TEST_XML_STRING);
		logger.info(xmlJSONObj);
		String jsonString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
		logger.info(jsonString);
	}

}