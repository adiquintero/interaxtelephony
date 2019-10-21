
package com.interax.validation.fields.exception;


public class InvalidListValueException extends FieldValidatorException {

	private static final long serialVersionUID = 1L;

	// constructor without parameters
	public InvalidListValueException() {}

	// constructor for exception description
	public InvalidListValueException(String description)
    {
    super(description);
    }
	
}
