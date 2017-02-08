package net.readify.knockknock;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Path("/api")
public class KnockKnockResource {
	
	private static final String TOKEN = "418fb4e5-cbb9-4bde-9843-0143e12d46fa";
	
	@Autowired
	private KnockKnockService service;
	
	@GET
	@Path("/fibonacci")
	public Response fibonacci(@QueryParam("n")String number) {
		try {
			ensureArgumentNotNull(number, "number");

			int sequenceNumber = Integer.parseInt(number);
			
			// in order to mimic the original service adding sign and abs logic
			int sign = sequenceNumber >= 0 ? 1 : -1;
			
			long result = service.fibonacciNumber(Math.abs(sequenceNumber));
			
			return createJsonResponse(Long.toString(result * sign));
		}
		catch (NumberFormatException | LongOverflowException ex) {
			throw new ClientErrorException(Status.BAD_REQUEST);
		}
		catch (IllegalArgumentException ex) {
			// should be BAD_REQUEST, but the reference service responds like this
			throw new NotFoundException(ex.getMessage());
		}
	}
	
	@GET
	@Path("/reversewords")
	public Response reverseWords(@QueryParam("sentence")String sentence) {
		if (sentence == null)
			sentence = "";
	
		return createResponse(service.reverseWords(sentence));
	}
	
	@GET
	@Path("/token")
	public Response token() {
		return createResponse(TOKEN);
	}
	
	@GET
	@Path("/triangletype")
	public Response triangleType(@QueryParam("a")Integer lengthA, @QueryParam("b")Integer lengthB, 
			@QueryParam("c")Integer lengthC) {
		try {
			ensureArgumentNotNull(lengthA, "a");
			ensureArgumentNotNull(lengthB, "b");
			ensureArgumentNotNull(lengthC, "c");
		}
		catch (IllegalArgumentException ex) {
			// should be BAD_REQUEST, but the reference service responds like this
			throw new NotFoundException(ex.getMessage());
		}
		
		return createResponse(service.triangleType(lengthA, lengthB, lengthC).toString());
	}
	
	private static void ensureArgumentNotNull(Object value, String name) {
		if (value == null)
			throw new IllegalArgumentException(name + " can not be null");
	}
	
	private String postProcessString(String value) {
		// it's not that easy to find a good JSON library that supports rfc-7159
		return "\"" + StringEscapeUtils.escapeJson(value) + "\"";
	}
	
	private Response createJsonResponse(Object entity) {
		return Response.ok(entity, MediaType.APPLICATION_JSON).build();
	}
	
	private Response createResponse(String value) {
		return createJsonResponse(postProcessString(value));
	}
}
