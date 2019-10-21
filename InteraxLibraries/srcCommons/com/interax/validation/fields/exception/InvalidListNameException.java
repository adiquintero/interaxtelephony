
package com.interax.validation.fields.exception;


public class InvalidListNameException extends FieldValidatorException {

	private static final long serialVersionUID = 1L;

	// constructor without parameters
	public InvalidListNameException() {}

	// constructor for exception description
	public InvalidListNameException(String description)
    {
    super(description);
    }
	
}
