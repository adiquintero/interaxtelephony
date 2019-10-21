
package com.interax.validation.fields.exception;


public class InvalidFieldValueException extends FieldValidatorException {

	private static final long serialVersionUID = 1L;

	// constructor without parameters
	public InvalidFieldValueException() {}

	// constructor for exception description
	public InvalidFieldValueException(String description)
    {
    super(description);
    }
	
}
