package com.glintdg.minas.common.excepciones;

import com.glintdg.minas.common.casillas.Mina;
import com.glintdg.minas.common.idiomas.Traducciones;

public class MinaExplosionadaException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Mina que ha explotado
	 */
	private Mina mMina = null;
	
	/**
	 * Constructor
	 * 
	 * @param mina Mina que ha originado la excepcion
	 */
	public MinaExplosionadaException(Mina mina)
	{
		super(String.format(Traducciones.EXCEPTION_MINA_EXPLOSIONADA, mina.getColumna(), mina.getFila()));
		
		this.setMina(mina);
	}
	
	/**
	 * @param mina Mina que ha explotado
	 */
	private void setMina(Mina mina)
	{
		this.mMina = mina;
	}
	
	/**
	 * @return Indica la mina que ha explotado
	 */
	public Mina getMina()
	{
		return this.mMina;
	}
}
