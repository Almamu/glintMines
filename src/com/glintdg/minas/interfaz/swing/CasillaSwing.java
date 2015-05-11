package com.glintdg.minas.interfaz.swing;

import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import com.glintdg.minas.common.Controlador;
import com.glintdg.minas.common.casillas.Casilla;
import com.glintdg.minas.common.casillas.Mina;
import com.glintdg.minas.common.excepciones.MinaExplosionadaException;

/**
 * Representa una casilla en la interfaz de juego
 * como si fuese un boton
 * 
 * @author Almamu
 */
public class CasillaSwing extends JButton
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Casilla del tablero a la que este boton apunta
	 */
	private Casilla mCasilla = null;
	
	/**
	 * Controlador del juego
	 */
	private Controlador mControlador = null;
	
	/**
	 * Constructor
	 * 
	 * @param casilla
	 */
	public CasillaSwing(Controlador controlador, Casilla casilla)
	{
		this.setCasilla(casilla);
		this.setControlador(controlador);
		this.setMargin(new Insets(1, 1, 1, 1));
		this.setup();
	}
	
	/**
	 * Configura el boton para que tenga todas las opciones necesarias
	 * para que el juego se lleve a cabo
	 */
	private void setup()
	{
		// evento de raton asignado al boton que controlamos
		// de esta forma es posible recibir las pulsaciones y
		// pasarlas al controlador de juego para que se encargue de actualizarlo
		// boton derecho -> Marca casilla como sospechosa
		// boton izquierdo -> Descubre la casilla
		this.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent event)
			{
				if(event.getButton() == MouseEvent.BUTTON3)
				{
					getControlador().marcar(getCasilla().getFila(), getCasilla().getColumna());
				}
				else if(event.getButton() == MouseEvent.BUTTON1)
				{
					try
					{
						getControlador().descubrir(getCasilla().getFila(), getCasilla().getColumna());
					}
					catch (MinaExplosionadaException e)
					{
					}
				}
				
				actualizar();
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
			}

			@Override
			public void mousePressed(MouseEvent e)
			{
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
			}
		});
		
		this.actualizar();
	}
	
	/**
	 * Actualiza el texto de este boton y su estado de acuerdo al estado de la casilla
	 * a la que ha sido asignado
	 */
	public void actualizar()
	{
		if(this.getCasilla().isDescubierta() == false)
		{
			if(this.getCasilla().isMarcada() == true)
			{
				this.setText("M");
			}
			else
			{
				this.setText("*");
			}
			
			return;
		}
		
		if(this.getCasilla() instanceof Mina)
		{
			this.setText("X");
		}
		else if(this.getCasilla().getMinasCercanas() > 0)
		{
			this.setText(this.getCasilla().getMinasCercanas() + "");
		}
		else
		{
			this.setText("");
		}
		
		this.setEnabled(false);
	}
	
	/**
	 * Actualiza la casilla que este boton controla
	 * 
	 * @param casilla
	 */
	private void setCasilla(Casilla casilla)
	{
		this.mCasilla = casilla;
	}
	
	/**
	 * @return Indica la casilla que este boton controla
	 */
	private Casilla getCasilla()
	{
		return this.mCasilla;
	}
	
	/**
	 * @return Indica el controlador asignado a este boton
	 */
	private Controlador getControlador()
	{
		return this.mControlador;
	}
	
	/**
	 * Actualiza el controlador asignado a este boton
	 * 
	 * @param controlador
	 */
	private void setControlador(Controlador controlador)
	{
		this.mControlador = controlador;
	}
}
