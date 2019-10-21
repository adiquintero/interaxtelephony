
package com.interax.validation.fields.exception;


public class InvalidDataTypeException extends FieldValidatorException {

	private static final long serialVersionUID = 1L;

	// constructor without parameters
	public InvalidDataTypeException() {}

	// constructor for exception description
	public InvalidDataTypeException(String description)
    {
    super(description);
    }
	
}
