package net.readify.knockknock;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
}
