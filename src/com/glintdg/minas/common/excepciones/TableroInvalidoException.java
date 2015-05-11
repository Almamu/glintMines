package com.glintdg.minas.common.excepciones;

import com.glintdg.minas.common.idiomas.Traducciones;

public class TableroInvalidoException extends Exception
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public TableroInvalidoException()
	{
		super(Traducciones.EXCEPTION_TABLERO_INVALIDO);
	}
}
