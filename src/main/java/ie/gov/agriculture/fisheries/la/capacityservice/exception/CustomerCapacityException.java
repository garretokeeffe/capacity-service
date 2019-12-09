package ie.gov.agriculture.fisheries.la.capacityservice.exception;

/**
 * public class CustomerCapacityException extends Exception;
 * @author stephen.mccosker
 * Custom class indicating a failure processing customer capacity
 */
public class CustomerCapacityException extends RuntimeException {
	private static final long serialVersionUID = 5634869299137716017L;

	public CustomerCapacityException(String message) {
    	super(message);
    }
}