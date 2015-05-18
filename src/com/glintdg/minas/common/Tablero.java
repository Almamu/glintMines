package com.glintdg.minas.common;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Random;

import com.glintdg.minas.common.casillas.Casilla;
import com.glintdg.minas.common.casillas.Vacia;
import com.glintdg.minas.common.casillas.Mina;
import com.glintdg.minas.common.excepciones.TableroInvalidoException;
import com.glintdg.minas.common.idiomas.Traducciones;

/**
 * Clase que representa las funciones y configuracion del tablero
 * 
 * @author Almamu
 */
public class Tablero implements Externalizable
{
	/**
	 * Version de la clase para la serializacion
	 * 
	 * IMPORTANTE ACTUALIZAR ESTO AL HACER CAMBIOS
	 */
	private static final long serialVersionUID = 2L;

	/**
	 * Numero de filas
	 */
	private int mFilas = 0;
	
	/**
	 * Numero de columnas
	 */
	private int mColumnas = 0;
	
	/**
	 * Numero de minas
	 */
	private int mMinas = 0;
	
	/**
	 * Partida a la que esta asignado este tablero
	 */
	private Partida mPartida = null;
	
	/**
	 * Información de las casillas disponibles
	 */
	private Casilla[][] mCasillas = null;
	
	/**
	 * Constructor
	 * 
	 * @param filas Numero de filas
	 * @param columnas Numero de columnas
	 * @param minas Numero de minas
	 */
	public Tablero(int filas, int columnas, int minas, Partida partida) throws TableroInvalidoException, ArrayIndexOutOfBoundsException
	{
		if(filas < 0 || columnas < 0) throw new TableroInvalidoException();
		
		// primero es necesario almacenar ciertos parametros
		this.setFilas(filas);
		this.setColumnas(columnas);
		this.setMinas(minas);
		this.setPartida(partida);
		
		// una vez se han obtenido los datos basicos
		// es hora de generar el tablero completo
		this.generar();
	}

	public Tablero()
	{
	}
	
	/**
	 * Genera un tablero completamente nuevo eliminando el existente
	 */
	private void generar() throws TableroInvalidoException, ArrayIndexOutOfBoundsException
	{
		// unas comprobaciones de seguridad extra para evitar que el jugador lo haga muy
		// facil o muy dificil
		if(
			this.getMinas() < Tablero.minimoMinas(this.getColumnas(), this.getFilas()) ||
			this.getMinas() > Tablero.maximoMinas(this.getColumnas(), this.getFilas())
		  ) throw new TableroInvalidoException();
		
		// se asegura de que todas las casillas se dejan de usar
		// para que el recolector de basura las elimine
		if(this.mCasillas != null)
		{
			for(int fila = 0; fila < this.getFilas(); fila ++)
			{
				for(int columna = 0; columna < this.getColumnas(); columna ++)
				{
					this.mCasillas[fila][columna] = null;
				}
			}
		}
		
		this.mCasillas = new Casilla[this.getFilas()][this.getColumnas()];
		
		// una vez el tablero esta reiniciado
		// es hora de generar uno nuevo
		
		// el "algoritmo" a seguir sera añadir primero las minas
		// de forma que una vez esten todas en su lugar se podra
		// añadir las casillas vacias sin problema
		int minas = this.getMinas();
		Random rand = new Random();
		
		// la forma mas sencilla es hacer uso de un random
		// y asegurarse de no sobre-escribir una mina ya existente
		while(minas > 0)
		{
			int fila = rand.nextInt(this.getFilas());
			int columna = rand.nextInt(this.getColumnas());
			
			if(this.mCasillas[fila][columna] == null)
			{
				this.mCasillas[fila][columna] = new Mina(this, fila, columna);
				minas --;
			}
		}
		
		// hora de añadir las casillas vacias
		for(int fila = 0; fila < this.getFilas(); fila ++)
		{
			for(int columna = 0; columna < this.getColumnas(); columna ++)
			{
				if(this.mCasillas[fila][columna] == null)
				{
					this.mCasillas[fila][columna] = new Vacia(this, fila, columna);
				}
			}
		}
		
		// una vez el tablero esta generado es hora de actualizar la informacion
		// de cada casilla con datos como minas cercanas
		for(int fila = 0; fila < this.getFilas(); fila ++)
		{
			for(int columna = 0; columna < this.getColumnas(); columna ++)
			{
				Casilla[] cercanas = this.getCasillasNearby(fila, columna);
				int minasCercanas = 0;

				// una vez se tienen las casillas cercanas
				// es necesario ver de que tipo son
				// para contarlas y actualiar los datos necesarios
				for(Casilla casilla : cercanas)
				{
					if(casilla instanceof Mina)
					{
						minasCercanas ++;
					}
				}
				
				// System.out.println("Casilla en (" + columna + ", " + fila + ") tiene " + cercanas.length + " alrededor y " + minasCercanas + " minas");
				
				// por ultimo esta casilla ya puede ser actualizada con las minas cercanas
				this.mCasillas[fila][columna].setMinasCercanas(minasCercanas);
			}
		}
	}
	
	/**
	 * Indica el numero minimo de minas que puede tener un tablero
	 * con las proporciones indicadas
	 * 
	 * @param columnas Columnas que tiene el tablero
	 * @param filas Filas que tiene el tablero
	 * 
	 * @return Numero de minas minimo
	 */
	public static int minimoMinas(int columnas, int filas)
	{
		int casillas = columnas * filas;
		int casillas_base = 15 * 15;
		
		return (15 * casillas) / casillas_base;
	}

	/**
	 * Indica el numero maximo de minas que puede tener un tablero
	 * con las proporciones indicadas
	 * 
	 * @param columnas Columnas que tiene el tablero
	 * @param filas Filas que tiene el tablero
	 * 
	 * @return Numero de minas maximas
	 */
	public static int maximoMinas(int columnas, int filas)
	{
		int maximo = (int)(columnas * filas * 0.9f);
		int minimo = minimoMinas(columnas, filas);
		
		if(maximo < minimo)
		{
			return minimo;
		}
		
		return maximo;
	}
	
	/**
	 * Califica la dificultad del tablero  
	 *  
	 * @return
	 */
	public float getDificultad()
	{
		int casillas = this.getFilas() * this.getColumnas();
		return (float)this.getMinas() / (float)casillas * 10.0f;
	}
	
	/**
	 * @return Cantidad de minas
	 */
	public int getMinas()
	{
		return this.mMinas;
	}

	/**
	 * @param minas Minas que asignar al tablero
	 */
	private void setMinas(int minas)
	{
		this.mMinas = minas;
	}

	/**
	 * @return Cantidad de columnas
	 */
	public int getColumnas()
	{
		return this.mColumnas;
	}

	/**
	 * @param columnas Columnas que asignar al tablero
	 */
	private void setColumnas(int columnas)
	{
		this.mColumnas = columnas;
	}

	/**
	 * @return Cantidad de filas
	 */
	public int getFilas()
	{
		return this.mFilas;
	}

	/**
	 * @param filas El numero de filas que tiene 
	 */
	private void setFilas(int filas)
	{
		this.mFilas = filas;
	}
	
	/**
	 * Actualiza la partida asignada a este tablero
	 * 
	 * @param partida
	 */
	void setPartida(Partida partida)
	{
		this.mPartida = partida;
		this.mPartida.setTablero(this);
	}
	
	/**
	 * @return Indica la partida a la que está asignado este tablero
	 */
	public Partida getPartida()
	{
		return this.mPartida;
	}
	
	/**
	 * @return Indica el numero de casillas disponible en el tablero
	 */
	public int getNumeroCasillas()
	{
		return this.mFilas * this.mColumnas;
	}
	
	/**
	 * @return Indica el numero de casillas vacias disponibles
	 */
	public int getNumeroCasillasVacias()
	{
		return this.getNumeroCasillas() - this.getMinas();
	}
	
	/**
	 * Obtiene la informacion de la casilla especificada
	 * 
	 * @param fila Fila en la que se encuentra la casilla
	 * @param columna Columna en la que se encuentra la casilla
	 * 
	 * @return Informacion de la casilla especificada
	 * 
	 * @throws ArrayIndexOutOfBoundsException Fila y/o columna fuera de los limites del tablero
	 */
	public Casilla getCasillaAt(int fila, int columna) throws ArrayIndexOutOfBoundsException
	{
		if(this.getFilas() > fila && this.getColumnas() > columna)
		{
			return this.mCasillas[fila][columna];
		}
		
		throw new ArrayIndexOutOfBoundsException(String.format(Traducciones.EXCEPTION_OUT_OF_BOUNDS, fila, columna));
	}
	
	/**
	 * Obtiene las casillas alrededor del punto indicado
	 * 
	 * @param fila Fila del punto central
	 * @param columna Columna del punto central
	 * 
	 * @return Array con las casillas cercanas existentes
	 * 
	 * @throws ArrayIndexOutOfBoundsException Fila y/o columna fuera de los limites del tablero
	 */
	public Casilla[] getCasillasNearby(int fila, int columna) throws ArrayIndexOutOfBoundsException
	{
		if(this.getFilas() < fila || this.getColumnas() < columna)
		{
			throw new ArrayIndexOutOfBoundsException(String.format(Traducciones.EXCEPTION_OUT_OF_BOUNDS, fila, columna));
		}
		
		ArrayList<Casilla> casillas = new ArrayList<Casilla>();
		
		// System.out.print("Buscando desde (" + (columna - 1) + ", " + (fila - 1) + ") hasta (" + (columna + 1) + ", " + (fila + 1) + ") - ");
		
		for(int y = fila - 1; y <= fila + 1; y ++)
		{
			// es necesario asegurarse de que estamos en los limites
			// en caso de que el usuario busque cerca del punto (0,0)
			// o (filas,columnas)
			if(y < 0 || y >= this.getFilas()) continue;
			
			for(int x = columna - 1; x <= columna + 1; x ++)
			{
				// lo mismo que antes, pero ademas es innecesario contarse a si mismo
				if(
					x < 0 ||
					x >= this.getColumnas() ||
					(
						x == columna &&
						y == fila
					)
				)
				{
					continue;
				}
				
				casillas.add(this.getCasillaAt(y, x));
			}
		}
		
		Casilla[] salida = new Casilla[casillas.size()];
		
		return casillas.toArray(salida);
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
		stream.writeInt(this.getFilas());
		stream.writeInt(this.getColumnas());
		stream.writeInt(this.getMinas());
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
		this.setFilas(stream.readInt());
		this.setColumnas(stream.readInt());
		this.setMinas(stream.readInt());
	}
}
