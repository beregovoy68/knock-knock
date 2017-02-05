package net.readify.knockknock;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/*
 * Test does just quick basic checks.
 * More extensive testing is done by integration tests.
 */
public class DefaultKnockKnockServiceTest {
	
	private DefaultKnockKnockService service;
	
	@Before
	public void setUp() {
		service = new DefaultKnockKnockService();
	}

	@Test
	public void testFibonacci() throws Exception {
		int[] expected = new int[] {0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377};
		for (int i = 0; i < expected.length; i++) 
			Assert.assertEquals("sequence number: " + Integer.toString(i),
					expected[i], service.fibonacciNumber(i));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testFibonacciNegative() throws Exception {
		service.fibonacciNumber(-1);
	}
	
	@Test
	public void testReverseWords() throws Exception {
		Assert.assertEquals("eht xof", service.reverseWords("the fox"));
	}
	
	@Test
	public void testTriangleError() throws Exception {
		Assert.assertEquals(TriangleType.ERROR, service.triangleType(0, 1, 2));
		Assert.assertEquals(TriangleType.ERROR, service.triangleType(0, 0, 2));
		Assert.assertEquals(TriangleType.ERROR, service.triangleType(0, 1, 0));
		Assert.assertEquals(TriangleType.ERROR, service.triangleType(0, 0, 0));
	}
}
