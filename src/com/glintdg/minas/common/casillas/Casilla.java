package com.glintdg.minas.common.casillas;

import java.io.Serializable;

import com.glintdg.minas.common.Tablero;
import com.glintdg.minas.common.excepciones.MinaExplosionadaException;

/**
 * Clase que representa una casilla
 * 
 * @author Almamu
 */
public abstract class Casilla implements Serializable
{
	/**
	 * Version de la clase para la serializacion
	 * 
	 * IMPORTANTE ACTUALIZAR ESTO AL HACER CAMBIOS
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Numero de minas cercanas
	 */
	private int mMinasCercanas = 0;
	
	/**
	 * Tablero al que esta casilla pertenece
	 */
	private Tablero mTablero = null;
	
	/**
	 * Fila en la que se encuentra esta casilla en el tablero actual
	 */
	private int mFila = 0;
	
	/**
	 * Columna en la que se encuentra esta casilla en el tablero actual
	 */
	private int mColumna = 0;
	
	/**
	 * Casilla marcada como sospechosa
	 */
	private boolean mMarcada = false;
	
	/**
	 * Indica si la casilla ha sido descubierta
	 */
	private boolean mDescubierta = false;
	
	/**
	 * Constructor
	 */
	public Casilla(Tablero tablero, int fila, int columna)
	{
		this.setTablero(tablero);
		this.setFila(fila);
		this.setColumna(columna);
	}
	
	/**
	 * Descubre el contenido de la casilla
	 * 
	 * @throws MinaExplosionadaException Indica que una mina ha sido descubierta
	 */
	public abstract void descubrir() throws MinaExplosionadaException;
	
	/**
	 * @return Indica si la casilla ya ha sido descubierta
	 */
	public final boolean isDescubierta()
	{
		return this.mDescubierta;
	}
	
	/**
	 * Marca la casilla como descubierta
	 */
	protected final void setDescubierta()
	{
		this.mDescubierta = true;
	}
	
	/**
	 * Marca la casilla como no descubierta
	 */
	protected final void clearDescubierta()
	{
		this.mDescubierta = false;
	}
	
	/**
	 * Marca la casilla como sospechosa de tener mina dentro
	 */
	protected final void setMarcada()
	{
		this.mMarcada = true;
	}
	
	/**
	 * Desmarca la casilla si ya estaba marcada como sospechosa
	 */
	protected final void clearMarcada()
	{
		this.mMarcada = false;
	}
	
	/**
	 * @return Indica si la casilla esta marcada como sospechosa
	 */
	public final boolean isMarcada()
	{
		return this.mMarcada;
	}
	
	/**
	 * Marca la casilla como sospechosa
	 */
	public void marcar()
	{
		if(this.isMarcada() == false)
		{
			this.setMarcada();
		}
		else
		{
			this.clearMarcada();
		}
	}
	
	/**
	 * @return Numero de minas cercanas
	 */
	public final int getMinasCercanas()
	{
		return this.mMinasCercanas;
	}
	
	/**
	 * Actualiza el numero de minas cercanas
	 * 
	 * @param minasCercanas Nuevo nuevo de minas cercanas
	 */
	public final void setMinasCercanas(int minasCercanas)
	{
		this.mMinasCercanas = minasCercanas;
	}
	
	/**
	 * Actualiza el tablero al que pertenece esta casilla
	 * 
	 * @param tablero Nuevo tablero
	 */
	protected final void setTablero(Tablero tablero)
	{
		this.mTablero = tablero;
	}
	
	/**
	 * @return Indica el tablero al que pertenece esta casilla
	 */
	public final Tablero getTablero()
	{
		return this.mTablero;
	}
	
	/**
	 * Actualiza la fila en la que se encuentra esta casilla
	 * 
	 * @param fila Nueva fila
	 */
	protected final void setFila(int fila)
	{
		this.mFila = fila;
	}
	
	/**
	 * @return Indica la fila en la que se encuentra la casila
	 */
	public final int getFila()
	{
		return this.mFila;
	}
	
	/**
	 * Actualiza la columna en la que se encuentra esta casilla
	 * 
	 * @param columna Nueva columna
	 */
	protected final void setColumna(int columna)
	{
		this.mColumna = columna;
	}
	
	/**
	 * @return Indica la columna en la que se encuentra la casilla
	 */
	public final int getColumna()
	{
		return this.mColumna;
	}
}
