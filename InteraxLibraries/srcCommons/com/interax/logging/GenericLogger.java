/**
 * Clase generica para encapsular la clase Logger de Java
 */
package com.interax.logging;

import java.util.logging.Logger;

/**
 * @author Amador Marín
 */
public class GenericLogger extends Logger {

	protected static final String NULL_ID = "-";
	protected static final String SEPARATOR = "|";
	
	/**
	 * @param name
	 */
	public GenericLogger(String name) {
		super(name, null); // no se usa el resourceBundle para log en distintos idiomas
	}

}
