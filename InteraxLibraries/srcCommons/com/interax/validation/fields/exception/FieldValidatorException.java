
package com.interax.validation.fields.exception;


public class FieldValidatorException extends Exception {

	private static final long serialVersionUID = 1L;

	// constructor without parameters
	public FieldValidatorException() {}

	// constructor for exception description
	public FieldValidatorException(String description)
    {
    super(description);
    }
	
}
