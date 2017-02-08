package net.readify.knockknock;

import org.junit.Test;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class TriangleTypeTest extends AbstractWebServiceTest {
	
	@Test
	public void testUsualTriangle() throws Exception {
		createSpec(4, 5, 6)
			.assertThat()
				.statusCode(200)
				.body(expectString("Scalene"));
	}
	
	@Test
	public void testIsoscelesTriangle() throws Exception {
		createSpec(4, 4, 6)
			.assertThat()
				.statusCode(200)
				.body(expectString("Isosceles"));
	}
	
	@Test
	public void testEquilateralTriangle() throws Exception {
		createSpec(4, 4, 4)
			.assertThat()
				.statusCode(200)
				.body(expectString("Equilateral"));
	}
	
	@Test
	public void testRightAngled() throws Exception {
		createSpec(3, 4, 5)
			.assertThat()
				.statusCode(200)
				.body(expectString("Scalene"));
	}
	
	@Test
	public void testParameterMissing() throws Exception {
		createSpec(3, 4, null)
			.assertThat()
				.statusCode(404);
	}
	
	@Test
	public void testNoParameters() throws Exception {
		createSpec(null, null, null)
			.assertThat()
				.statusCode(404);
	}
	
	@Test
	public void testNegativeLength() throws Exception {
		createSpec(3, 4, -5)
			.assertThat()
				.statusCode(200)
				.body(expectString("Error"));
	}
	
	@Test
	public void testOneZeroParameter() throws Exception {
		createSpec(4, 0, 6)
			.assertThat()
				.statusCode(200)
				.body(expectString("Error"));
	}
	
	@Test
	public void testTwoZeroParameters() throws Exception {
		createSpec(4, 0, 0)
			.assertThat()
				.statusCode(200)
				.body(expectString("Error"));
	}
	
	@Test
	public void testAllZeroParameters() throws Exception {
		createSpec(0, 0, 0)
			.assertThat()
				.statusCode(200)
				.body(expectString("Error"));
	}
	
	private ValidatableResponse createSpec(Integer lengthA, Integer lengthB, Integer lengthC) {
		RequestSpecification result = super.given();
		result = addParameter(result, "a", lengthA);
		result = addParameter(result, "b", lengthB);
		result = addParameter(result, "c", lengthC);
		
		return result.when()
				.get("/triangletype")
			.then();
	}
	
	private RequestSpecification addParameter(RequestSpecification spec, String name, Object value) {
		return value != null ? spec.param(name, value) : spec;
	}
}
