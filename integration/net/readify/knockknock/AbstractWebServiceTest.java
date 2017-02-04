package net.readify.knockknock;

import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;
import org.junit.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public abstract class AbstractWebServiceTest {
	
	@BeforeClass
	public static void setUpSuite() {
		//RestAssured.baseURI = "https://knockknock.readify.net:443";
		RestAssured.baseURI = "http://localhost:8090";
		RestAssured.basePath = "/api";
	}
	
	protected RequestSpecification given() {
		return io.restassured.RestAssured.given()
				.relaxedHTTPSValidation();
	}
	
	protected Matcher<String> expectString(String expectedValue) {
		return equalTo("\"" + expectedValue + "\"");
	}
}
