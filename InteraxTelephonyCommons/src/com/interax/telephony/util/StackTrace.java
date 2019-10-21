package com.interax.telephony.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class StackTrace {

	public static String ToString( Exception e )
	{
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		PrintStream p = new PrintStream( b );
		e.printStackTrace(p);
		p.flush();
		return b.toString();
	}
}
