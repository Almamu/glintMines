package com.glintdg.minas.common.casillas;

import com.glintdg.minas.common.Constantes;
import com.glintdg.minas.common.Tablero;
import com.glintdg.minas.common.excepciones.MinaExplosionadaException;

/**
 * Clase que representa una casilla con una mina dentro
 * 
 * @author Almamu
 */
public class Mina extends Casilla
{
	/**
	 * Version de la clase para la serializacion
	 * 
	 * IMPORTANTE ACTUALIZAR ESTO AL HACER CAMBIOS
	 */
	private static final long serialVersionUID = 2L;
	
	/**
	 * Constructor
	 */
	public Mina(Tablero tablero, int fila, int columna)
	{
		super(tablero, fila, columna);
	}
	
	@Override
	public void descubrir() throws MinaExplosionadaException
	{
		this.setDescubierta();
		throw new MinaExplosionadaException(this);
	}
	
	@Override
	public void marcar()
	{
		if(this.isMarcada() == true)
		{
			this.getTablero().getPartida().givePuntos(-(Constantes.MINEMARK_POINTS * this.getTablero().getDificultad()));
		}
		else
		{
			this.getTablero().getPartida().givePuntos(Constantes.MINEMARK_POINTS * this.getTablero().getDificultad());
		}
		
		super.marcar();
	}
}
