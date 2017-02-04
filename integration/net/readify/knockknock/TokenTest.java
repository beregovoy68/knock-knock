package net.readify.knockknock;

import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;

import org.junit.Test;


public class TokenTest extends AbstractWebServiceTest {

	@Test
	public void testOk() throws Exception {
		super.given()
		.when()
			.get("/Token")
		.then()
			.assertThat()
				.statusCode(200)
				.body(not(isEmptyString())); //TODO: match with my own token
	}
}
