package net.readify.knockknock;

import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class FibonacciTest extends AbstractWebServiceTest {
	
	@Test
	public void testSuccessStory() throws Exception {
		createSpec("3")
			.statusCode(200)
			.body(equalTo("2"));
	}
	
	@Test
	public void testIntMax() throws Exception {
		createSpec(Integer.toString(Integer.MAX_VALUE))
			.statusCode(400);
	}
	
	@Test
	public void testLongOverflow() throws Exception {
		createSpec("93")
			.statusCode(400);
	}
	
	@Test
	public void testBadArgument() throws Exception {
		createSpec("asdfag")
			.statusCode(400);
	}
	
	@Test
	public void testNegativeArgument() throws Exception {
		createSpec("-2")
			.statusCode(200)
			.body(equalTo("-1"));
	}
	
	@Test
	public void testZeroArgument() throws Exception {
		createSpec("0")
			.statusCode(200)
			.body(equalTo("0"));
	}
	
	@Test
	public void testNoArgument() throws Exception {
		createSpec(null)
			.statusCode(404);
	}
	
	private ValidatableResponse createSpec(String parameterValue) {
		RequestSpecification result = super.given();
		if (parameterValue != null)
			result = result.param("n", parameterValue);
		
		return result.when()
				.get("/fibonacci")
			.then();
	}
}
