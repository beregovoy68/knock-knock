package net.readify.knockknock;

public class LongOverflowException extends Exception {

	private static final long serialVersionUID = -3458643220301024065L;

	public LongOverflowException(String message) {
		super(message);
	}
}
