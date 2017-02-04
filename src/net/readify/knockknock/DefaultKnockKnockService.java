package net.readify.knockknock;

import java.util.Stack;

import org.springframework.stereotype.Service;

@Service
public class DefaultKnockKnockService implements KnockKnockService {

	@Override
	public long fibonacciNumber(int sequenceNumber) throws LongOverflowException {
		if (sequenceNumber < 0)
			throw new IllegalArgumentException("sequence number can not be negative");
		
		if (sequenceNumber >= 93)
			throw new LongOverflowException("sequenceNumber is too big");
		
		long a = 0;
		long b = 1;
		for (int i = 0; i < sequenceNumber; i++) {
			long next = a + b;
			a = b;
			b = next;
		}

		return a;
	}

	@Override
	public String reverseWords(String sentence) {
		if (sentence == null)
			throw new IllegalArgumentException("sentence can not be null");
		
		StringBuilder sb = new StringBuilder(sentence.length());
		Stack<Character> word = new Stack<>();
		for (Character c : sentence.toCharArray()) {
			if (Character.isWhitespace(c)) {
				pushWordToStringBuilder(sb, word);
				word.clear();
				
				sb.append(c);
			}
			else
				word.push(c);
		}
		
		pushWordToStringBuilder(sb, word);
		
		return sb.toString();
	}

	@Override
	public TriangleType triangleType(int lengthA, int lengthB, int lengthC) {
		if (lengthA <= 0 || lengthB <= 0 || lengthC <= 0)
			return TriangleType.ERROR;
		
		if (lengthA == lengthB) {
			return lengthB == lengthC ? TriangleType.EQUILATERAL : TriangleType.ISOSCELES;
		}
		else if (lengthB == lengthC) {
			return TriangleType.ISOSCELES;
		}
		
		return TriangleType.SCALENE;
	}
	
	private void pushWordToStringBuilder(StringBuilder sb, Stack<Character> word) {
		while (!word.empty())
			sb.append(word.pop());
	}
}
