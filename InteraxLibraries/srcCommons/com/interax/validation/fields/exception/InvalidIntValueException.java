
package com.interax.validation.fields.exception;


public class InvalidIntValueException extends FieldValidatorException {

	private static final long serialVersionUID = 1L;

	// constructor without parameters
	public InvalidIntValueException() {}

	// constructor for exception description
	public InvalidIntValueException(String description)
    {
    super(description);
    }
	
}
