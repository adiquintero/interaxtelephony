
package com.interax.validation.fields.exception;


public class InvalidStringValueException extends FieldValidatorException {

	private static final long serialVersionUID = 1L;

	// constructor without parameters
	public InvalidStringValueException() {}

	// constructor for exception description
	public InvalidStringValueException(String description)
    {
    super(description);
    }
	
}
