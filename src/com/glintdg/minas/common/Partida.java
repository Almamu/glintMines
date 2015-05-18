package com.glintdg.minas.common;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * Información de una partida en curso 
 * 
 * @author Almamu
 */
public class Partida implements Externalizable
{
	/**
	 * Version de la clase para la serializacion
	 * 
	 * IMPORTANTE ACTUALIZAR ESTO AL HACER CAMBIOS
	 */
	private static final long serialVersionUID = 3L;

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
	 * Constructor 
	 */
	public Partida()
	{
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
	
	/**
	 * @return Dificultad de la partida
	 */
	public float getDificultad()
	{
		return this.getTablero().getDificultad();
	}
	
	/**
	 * Personaliza la escritura de esta clase en un fichero
	 * 
	 * @param stream Stream usado para la escritura
	 * @throws IOException
	 */
	@Override
	public void writeExternal(ObjectOutput stream) throws IOException
	{
		if(this.getTablero() == null) System.out.println("ASDF");
		
		stream.writeUTF(this.getNombre());
		stream.writeFloat(this.getPuntos());
		this.getTablero().writeExternal(stream);
	}
	
	/**
	 * Personaliza la lectura de esta clase desde un fichero
	 * 
	 * @param stream Stream usado para la lectura
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@Override
	public void readExternal(ObjectInput stream) throws IOException, ClassNotFoundException
	{
		this.setNombre(stream.readUTF());
		this.setPuntos(stream.readFloat());
		this.setTablero(new Tablero());
		
		// es necesario actualizar el tablero para que refleje el estado correcto
		this.getTablero().readExternal(stream);
		this.getTablero().setPartida(this);
	}
}
