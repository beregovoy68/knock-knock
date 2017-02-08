package net.readify.knockknock;

import org.junit.Test;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class ReverseWordsTest extends AbstractWebServiceTest {

	@Test
	public void testOk() throws Exception {
		createSpec(" Minnie \t Mouse ")
			.assertThat()
				.statusCode(200)
				.body(expectString(" einniM \\t esuoM "));
	}
	
	@Test
	public void testPunctuation() throws Exception {
		createSpec("the!red;fox-jumps.over*lazy^dog")
			.assertThat()
				.statusCode(200)
				.body(expectString("god^yzal*revo.spmuj-xof;der!eht"));
	}
	
	@Test
	public void testSpecialCharacters() throws Exception {
		createSpec("the\tred\rfox\njumps")
			.assertThat()
				.statusCode(200)
				.body(expectString("eht\\tder\\rxof\\nspmuj"));
	}
	
	@Test
	public void testNoParameter() throws Exception {
		createSpec(null)
			.assertThat()
				.statusCode(200)
				.body(expectString(""));
	}
	
	private ValidatableResponse createSpec(String parameterValue) {
		RequestSpecification result = super.given();
		if (parameterValue != null)
			result = result.param("sentence", parameterValue);
		
		return result.when()
				.get("/reversewords")
			.then();
	}
}
