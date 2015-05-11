package com.glintdg.minas.interfaz.swing;

import java.awt.GridLayout;

import javax.swing.JPanel;

import com.glintdg.minas.common.Controlador;
import com.glintdg.minas.common.Tablero;
import com.glintdg.minas.common.casillas.Casilla;
import com.glintdg.minas.common.casillas.Mina;

/**
 * Se encarga de mostrar todas las casillas en forma de botones
 * de forma ordenada y correcta, respetando las filas y columnas
 * especificadas por el jugador
 * 
 * @author Almamu
 */
public class TableroSwing extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Tablero usado para dibujar el juego 
	 */
	private Tablero mTablero = null;
	
	/**
	 * Lista de botones creados
	 */
	private CasillaSwing[][] mCasillas = null;
	
	/**
	 * Layout usada para este componente
	 */
	private GridLayout mLayout = null;
	
	/**
	 * Controlador del juego
	 */
	private Controlador mControlador = null;
	
	/**
	 * Numero de casillas descubiertas por el jugador
	 */
	private int mCasillasDescubiertas = 0;
	
	/**
	 * Numero de minas marcadas por el jugador
	 */
	private int mMinasMarcadas = 0;
	
	/**
	 * Constructor
	 * 
	 * @param tablero Tablero a usar
	 */
	public TableroSwing(Controlador controlador, Tablero tablero)
	{
		this.setTablero(tablero);
		this.setControlador(controlador);
		
		generar();
	}
	
	/**
	 * Actualiza el tablero usado para este grid
	 * 
	 * @param tablero
	 */
	private void setTablero(Tablero tablero)
	{
		this.mTablero = tablero;
	}
	
	/**
	 * @return Indica el tablero actual usado para dibujar este grid
	 */
	protected Tablero getTablero()
	{
		return this.mTablero;
	}

	/**
	 * @return Indica el controlador de la partida
	 */
	protected Controlador getControlador()
	{
		return this.mControlador;
	}
	
	/**
	 * Actualiza el controlador del juego usado para esta partida
	 * 
	 * @param controlador
	 */
	private void setControlador(Controlador controlador)
	{
		this.mControlador = controlador;
	}
	
	/**
	 * @return Boton preparado para el buscaminas
	 */
	private CasillaSwing prepararBoton(int fila, int columna)
	{
		return new CasillaSwing(this.getControlador(), this.getTablero().getCasillaAt(fila, columna));
	}
	
	/**
	 * Actualiza todos los botones para que reflejen el estado actual del tablero
	 */
	public void actualizar()
	{
		this.mMinasMarcadas = 0;
		this.mCasillasDescubiertas = 0;
		
		for(int fila = 0; fila < this.getTablero().getFilas(); fila ++)
		{
			for(int columna = 0; columna < this.getTablero().getColumnas(); columna ++)
			{
				this.mCasillas[fila][columna].actualizar();

				// es necesario mantener la cantidad de minas marcadas y casillas descubiertas actualizadas
				Casilla casilla = this.getTablero().getCasillaAt(fila, columna);
				
				if(casilla instanceof Mina && casilla.isMarcada() == true)
				{
					this.mMinasMarcadas ++;
				}
				else if(casilla.isDescubierta() == true)
				{
					this.mCasillasDescubiertas ++;
				}
			}
		}
	}

	/**
	 * Genera la vista para el tablero
	 */
	private void generar()
	{
		// configura el layout para que ordene los botones de forma correcta
		this.mLayout = new GridLayout(this.getTablero().getFilas(), this.getTablero().getColumnas());
		this.setLayout(this.mLayout);
		
		// se generan todas las casillas, una a una y se añaden al layout para que sean visibles
		// en el orden correcto
		this.mCasillas = new CasillaSwing[this.getTablero().getFilas()][this.getTablero().getColumnas()];
		
		for(int fila = 0; fila < this.getTablero().getFilas(); fila ++)
		{
			for(int columna = 0; columna < this.getTablero().getColumnas(); columna ++)
			{
				this.mCasillas[fila][columna] = this.prepararBoton(fila, columna);
				this.add(this.mCasillas[fila][columna]);
			}
		}
	}
	
	/**
	 * Evento lanzado cuando una mina explosiona
	 */
	public void onMinaExplosionada()
	{
		// cuando una mina explota, desactiva todos los botones para que no puedan ser usados mas
		for(int fila = 0; fila < this.getTablero().getFilas(); fila ++)
		{
			for(int columna = 0; columna < this.getTablero().getColumnas(); columna ++)
			{
				this.mCasillas[fila][columna].setEnabled(false);
			}
		}
	}
	
	/**
	 * @return Indica el numero de casillas descubiertas por el jugador
	 */
	public int getCasillasDescubiertas()
	{
		return this.mCasillasDescubiertas;
	}

	/**
	 * @return Indica le numero de minas correctas que ha marcado el jugador
	 */
	public int getMinasMarcadas()
	{
		return this.mMinasMarcadas;
	}

}
