
package com.interax.validation.fields.exception;


public class InvalidDoubleValueException extends FieldValidatorException {

	private static final long serialVersionUID = 1L;

	// constructor without parameters
	public InvalidDoubleValueException() {}

	// constructor for exception description
	public InvalidDoubleValueException(String description)
    {
    super(description);
    }
	
}
