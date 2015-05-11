package com.glintdg.minas.interfaz.consola;

import com.glintdg.minas.common.Controlador;
import com.glintdg.minas.common.Partida;
import com.glintdg.minas.common.Tablero;
import com.glintdg.minas.common.casillas.Casilla;
import com.glintdg.minas.common.casillas.Mina;
import com.glintdg.minas.common.excepciones.MinaExplosionadaException;
import com.glintdg.minas.common.excepciones.TableroInvalidoException;
import com.glintdg.minas.common.idiomas.TraduccionesConsola;

/**
 * Controlador de juego para la consola
 * 
 * @author Almamu
 */
public abstract class ControladorConsola implements Controlador
{
	/**
	 * Tablero usado actualmente
	 */
	private Tablero mTablero = null;
	
	/**
	 * Indica si el programa ha de mostrar el tablero original
	 */
	private static boolean MostrarOriginal = true;
	
	/**
	 * Numero de casillas descubiertas
	 */
	private int mCasillasDescubiertas = 0;
	
	/**
	 * Constructor
	 */
	public ControladorConsola()
	{
	}
	
	/**
	 * Muestra el mensaje de bienvenida al jugador
	 * Y le solicita los datos necesarios para emepzar el juego
	 */
	public void prepararJuego(String nombre, int filas, int columnas, int minas) throws TableroInvalidoException
	{
		this.mTablero = new Tablero(filas, columnas, minas, new Partida(nombre));
	}

	/**
	 * @return Indica el tablero en uso
	 */
	private Tablero getTablero()
	{
		return this.mTablero;
	}
	
	/**
	 * @return Indica si se puede empezar a jugar
	 */
	public boolean puedeJugar()
	{
		return this.mTablero != null;
	}
	
	/**
	 * Muestra el tablero original si la opcion esta habilitada
	 */
	protected void mostrarTableroOriginal()
	{
		if(MostrarOriginal == false) return;
		
		// muestra informacion del tablero por consola, de forma simple y entendible
		// para poder asegurarnos de que todos los datos del tablero son correctos
		for(int y = 0; y < 15; y ++)
		{
			for(int x = 0; x < 15; x ++)
			{
				Casilla casilla = mTablero.getCasillaAt(y, x);
				
				if(casilla instanceof Mina)
				{
					System.out.print("M ");
				}
				else
				{
					System.out.print(casilla.getMinasCercanas() + " ");
				}
			}
			
			System.out.println();
		}
		
		System.out.println("---------------");
	}
	
	/**
	 * Muestra el tablero actual de juego
	 */
	protected void mostrarTableroJuego()
	{
		// lo siguiente es mostrar el estado real del tablero para el jugador
		for(int y = 0; y < 15; y ++)
		{
			for(int x = 0; x < 15; x ++)
			{
				Casilla casilla = mTablero.getCasillaAt(y, x);
				
				if(casilla.isDescubierta() == true)
				{
					if(casilla instanceof Mina)
					{
						System.out.print("x ");
					}
					else if(casilla.getMinasCercanas() > 0)
					{
						System.out.print(casilla.getMinasCercanas() + " ");
					}
					else
					{
						System.out.print("D ");
					}
				}
				else
				{
					System.out.print("* ");
				}
			}
			
			System.out.println();
		}
		
		System.out.println("---------------");
	}
	
	@Override
	public void onMinaExplosionada(int fila, int columna)
	{
		System.out.println(String.format(TraduccionesConsola.MINE_DISCOVER, fila, columna));
	}
	
	@Override
	public void descubrir(int fila, int columna) throws MinaExplosionadaException
	{
		Casilla casilla = this.getTablero().getCasillaAt(fila, columna);
		
		try
		{
			casilla.descubrir();
			this.mCasillasDescubiertas ++;
		}
		catch(MinaExplosionadaException ex)
		{
			this.onMinaExplosionada(fila, columna);
			throw ex;
		}
	}
	
	@Override
	public void marcar(int fila, int columna)
	{
		// no es posible en la version de consola
	}
	
	/**
	 * Numero de casillas descubiertas
	 */
	public int getCasillasDescubiertas()
	{
		return this.mCasillasDescubiertas;
	}
	
	/**
	 * Numero de minas existentes
	 */
	public int getMinas()
	{
		return this.getTablero().getMinas();
	}
	
	/**
	 * Numero de minas marcadas
	 */
	public int getMinasMarcadas()
	{
		return 0;
	}
	
	/**
	 * @return Indica el numero de casillas vacias disponibles
	 */
	public int getNumeroCasillasVacias()
	{
		return this.getTablero().getNumeroCasillasVacias();
	}
	
	/**
	 * @return Indica el numero de puntos que tiene el jugador
	 */
	public int getPuntos()
	{
		return this.getTablero().getPartida().getPuntos();
	}
}
