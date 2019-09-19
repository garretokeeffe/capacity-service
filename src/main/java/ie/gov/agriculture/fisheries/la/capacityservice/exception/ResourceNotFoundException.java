package ie.gov.agriculture.fisheries.la.capacityservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception to indicate a resource does not exist.
 * @author garret.okeeffe
 *
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception {
	private static final long serialVersionUID = 5634869299137716028L;

	public ResourceNotFoundException(String message) {
    	super(message);
    }
}