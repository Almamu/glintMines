package com.glintdg.minas.common.excepciones;

import com.glintdg.minas.common.idiomas.Traducciones;

/**
 * Excepci�n generada cuando la configuraci�n del tablero es incorrecta
 * 
 * @author Almamu
 */
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
