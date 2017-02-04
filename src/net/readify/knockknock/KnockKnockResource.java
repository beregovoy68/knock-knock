package net.readify.knockknock;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Path("/api")
@Produces(MediaType.TEXT_PLAIN)
public class KnockKnockResource {
	
	private static final String TOKEN = "418fb4e5-cbb9-4bde-9843-0143e12d46fa";
	
	@Autowired
	private KnockKnockService service;
	
	@GET
	@Path("/Fibonacci")
	public long fibonacci(@QueryParam("n")String number) {
		try {
			ensureArgumentNotNull(number, "number");

			int sequenceNumber = Integer.parseInt(number);
			
			// in order to mimic the original service adding sign and abs logic
			int sign = sequenceNumber >= 0 ? 1 : -1;
			
			long result = service.fibonacciNumber(Math.abs(sequenceNumber));
			
			return result * sign;
		}
		catch (NumberFormatException | LongOverflowException ex) {
			throw new ClientErrorException(Status.BAD_REQUEST);
		}
		catch (IllegalArgumentException ex) {
			// should be BAD_REQUEST, but the reference service responds this
			throw new NotFoundException(ex.getMessage());
		}
	}
	
	@GET
	@Path("/ReverseWords")
	public String reverseWords(@QueryParam("sentence")String sentence) {
		if (sentence == null)
			sentence = "";
	
		return postProcessString(service.reverseWords(sentence));
	}
	
	@GET
	@Path("/Token")
	public String token() {
		return postProcessString(TOKEN);
	}
	
	@GET
	@Path("/TriangleType")
	public String triangleType(@QueryParam("a")Integer lengthA, @QueryParam("b")Integer lengthB, 
			@QueryParam("c")Integer lengthC) {
		try {
			ensureArgumentNotNull(lengthA, "a");
			ensureArgumentNotNull(lengthB, "b");
			ensureArgumentNotNull(lengthC, "c");
			
			return postProcessString(service.triangleType(lengthA, lengthB, lengthC).toString());
		}
		catch (IllegalArgumentException ex) {
			// should be BAD_REQUEST, but the reference service responds this
			throw new NotFoundException(ex.getMessage());
		}
	}
	
	private static void ensureArgumentNotNull(Object value, String name) {
		if (value == null)
			throw new IllegalArgumentException(name + " can not be null");
	}
	
	private String postProcessString(String value) {
		return "\"" + StringEscapeUtils.escapeJava(value) + "\"";
	}
}
