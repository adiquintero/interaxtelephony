
package com.interax.validation.fields.exception;


public class InvalidBooleanValueException extends FieldValidatorException {

	private static final long serialVersionUID = 1L;

	// constructor without parameters
	public InvalidBooleanValueException() {}

	// constructor for exception description
	public InvalidBooleanValueException(String description)
    {
    super(description);
    }
	
}
