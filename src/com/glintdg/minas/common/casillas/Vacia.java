package com.glintdg.minas.common.casillas;

import com.glintdg.minas.common.Constantes;
import com.glintdg.minas.common.Tablero;
import com.glintdg.minas.common.excepciones.MinaExplosionadaException;

public class Vacia extends Casilla
{
	/**
	 * Version de la clase para la serializacion
	 * 
	 * IMPORTANTE ACTUALIZAR ESTO AL HACER CAMBIOS
	 */
	private static final long serialVersionUID = 3L;
	
	/**
	 * Constructor
	 */
	public Vacia(Tablero tablero, int fila, int columna)
	{
		super(tablero, fila, columna);
	}
	
	@Override
	public void descubrir()
	{
		// no es necesario ejecutar esto si esta casilla ya ha sido descubierta
		if(this.isDescubierta() == true) return;
		
		// se marca como descubierta
		this.setDescubierta();
		this.getTablero().getPartida().givePuntos(Constantes.DISCOVER_POINTS);
		
		if(this.getMinasCercanas() > 0) return;
		
		// y se llama al descubrir de las casillas cercanas si esta no tiene minas cerca
		Casilla[] cercanas = this.getTablero().getCasillasNearby(this.getFila(), this.getColumna());
		
		for(Casilla casilla : cercanas)
		{
			try
			{
				casilla.descubrir();
			}
			catch (MinaExplosionadaException e)
			{
			}
		}
	}
	
	@Override
	public void marcar()
	{
		if(this.isMarcada() == false)
		{
			this.getTablero().getPartida().givePuntos(Constantes.WRONGMARK_POINTS);
		}
		
		super.marcar();
	}
}
