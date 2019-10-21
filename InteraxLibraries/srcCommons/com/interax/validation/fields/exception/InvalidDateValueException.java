
package com.interax.validation.fields.exception;


public class InvalidDateValueException extends FieldValidatorException {

	private static final long serialVersionUID = 1L;

	// constructor without parameters
	public InvalidDateValueException() {}

	// constructor for exception description
	public InvalidDateValueException(String description)
    {
    super(description);
    }
	
}
