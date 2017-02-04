package net.readify.knockknock;

public interface KnockKnockService {
	long fibonacciNumber(int sequenceNumber) throws LongOverflowException;
	String reverseWords(String sentence);
	TriangleType triangleType(int lengthA, int lengthB, int lengthC);
}
