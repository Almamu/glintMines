package com.glintdg.minas.common;

import java.io.Serializable;

public class Partida implements Serializable
{
	/**
	 * Version de la clase para la serializacion
	 * 
	 * IMPORTANTE ACTUALIZAR ESTO AL HACER CAMBIOS
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Nombre del jugador
	 */
	private String mNombre = "";
	
	/**
	 * Puntos obtenidos en esta partida
	 */
	private float mPuntos = 0;
	
	/**
	 * Tablero asignado a la partida
	 */
	private Tablero mTablero = null;
	
	/**
	 * Constructor
	 * 
	 * @param nombre Nombre del jugador
	 */
	public Partida(String nombre)
	{
		this.setNombre(nombre);
		this.setPuntos(0.0f);
	}
	
	/**
	 * Suma la cantidad de puntos especificada
	 * 
	 * @param quantity
	 */
	public void givePuntos(float quantity)
	{
		this.mPuntos += quantity;
	}
	
	/**
	 * @return Cantidad de puntos obtenida hasta ahora
	 */
	public float getPuntos()
	{
		return this.mPuntos;
	}
	
	/**
	 * Actualiza los puntos
	 * 
	 * @param puntos
	 */
	protected void setPuntos(float puntos)
	{
		this.mPuntos = puntos;
	}
	
	/**
	 * @return Indica el nombre del jugador
	 */
	public String getNombre()
	{
		return this.mNombre;
	}
	
	/**
	 * Actualiza el nombre del jugador
	 * 
	 * @param nombre
	 */
	public void setNombre(String nombre)
	{
		this.mNombre = nombre;
	}
	
	/**
	 * Actualiza el tablero asignado a esta partida
	 * 
	 * @param tablero
	 */
	public void setTablero(Tablero tablero)
	{
		this.mTablero = tablero;
	}
	
	/**
	 * @return Indica el tablero asignado a esta partida
	 */
	public Tablero getTablero()
	{
		return this.mTablero;
	}
}
