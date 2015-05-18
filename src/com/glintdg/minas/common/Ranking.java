package com.glintdg.minas.common;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import com.glintdg.minas.common.excepciones.FueraDeRankingException;

/**
 * Gestion del ranking general de juego
 * 
 * @author Almamu
 */
public class Ranking
{
	/**
	 * Lista con todas las partidas guardadas en el Ranking
	 */
	private static ArrayList<Partida> mPartidas = Ranking.loadRanking();
	
	/**
	 * Cierra un InputStream de forma "bonita" (para evitar usar excepciones)
	 * 
	 * @param stream Stream a cerrar
	 */
	private static void closeQuietly(InputStream stream)
	{
		if(stream != null)
		{
			try
			{
				stream.close();
			}
			catch(IOException ex)
			{
				
			}
		}
	}
	
	/**
	 * Cierra un OutputStream de forma "bonita" (para evitar usar excepciones)
	 * 
	 * @param stream Stream a cerrar
	 */
	private static void closeQuietly(OutputStream stream)
	{
		if(stream != null)
		{
			try
			{
				stream.close();
			}
			catch(IOException ex)
			{
				
			}
		}
	}
	
	/**
	 * Carga el ranking a partir de un fichero
	 * 
	 * @return Lista de los 10 mejores en el ranking
	 */
	private static ArrayList<Partida> loadRanking()
	{
		int partidasCargadas = 0;
		ArrayList<Partida> tmp = new ArrayList<Partida>(Constantes.RANKING_SIZE);
		FileInputStream stream = null;
		ObjectInputStream obj = null;
		
		try
		{
			stream = new FileInputStream(Constantes.RANKING_FILE);
			obj = new ObjectInputStream(stream);
			
			while(partidasCargadas < Constantes.RANKING_SIZE)
			{
				Object data = obj.readObject();
				
				if(data instanceof Partida)
				{
					tmp.add(partidasCargadas ++, (Partida) data);
				}
			}
		}
		catch(EOFException ex)
		{
			System.out.println("Cargadas " + partidasCargadas + " partidas");
		}
		catch(ClassNotFoundException ex)
		{
			System.err.println("No se pudo interpretar todo el fichero de partidas");
		}
		catch(IOException ex)
		{
			System.err.println("No se pudo interpretar todo el fichero de partidas");
		}
		finally
		{
			closeQuietly(obj);
			closeQuietly(stream);
		}
		
		return tmp;
	}
	
	/**
	 * Ordena los puestos del ranking para mantener a los mejores abajo
	 */
	private static void sort()
	{
		// algoritmo de ordenacion simple
		// comprueba si la siguiente entrada tiene menos puntos
		// y si es asi las intercambia
		for(int i = 0; i < mPartidas.size() - 1; i ++)
		{
			if(mPartidas.get(i).getPuntos() > mPartidas.get(i + 1).getPuntos())
			{
				Partida tmp = mPartidas.get(i);
				mPartidas.set(i, mPartidas.get(i + 1));
				mPartidas.set(i + 1, tmp);
			}
		}
	}
	
	/**
	 * Añade una partida al ranking
	 * 
	 * @param partida
	 */
	public static void addToRanking(Partida partida)
	{
		sort();
		
		if(mPartidas.size() < Constantes.RANKING_SIZE)
		{
			mPartidas.add(partida);
		}
		else if(mPartidas.get(0).getPuntos() < partida.getPuntos())
		{
			mPartidas.add(0, partida);
		}
		
		save();
	}
	
	/**
	 * Guarda el ranking al fichero de texto correspondiente
	 */
	public static void save()
	{
		sort();
		
		FileOutputStream stream = null;
		ObjectOutputStream obj = null;
		
		try
		{
			stream = new FileOutputStream(Constantes.RANKING_FILE);
			obj = new ObjectOutputStream(stream);
			
			for(int i = 0; i < mPartidas.size(); i ++)
			{
				obj.writeObject(mPartidas.get(i));
			}
			
			obj.flush();
		}
		catch(IOException ex)
		{
			System.out.println("No se puede guardar el ranking por completo");
		}
		finally
		{
			closeQuietly(obj);
			closeQuietly(stream);
		}
	}
	
	/**
	 * Comprueba si la partida puede entrar dentro del Ranking
	 * 
	 * @param partida Partida a comprobar
	 * @return Posicion en la que entra
	 * 
	 * @throws FueraDeRankingException Excepcion lanzada cuando la partida no entra en el Ranking
	 */
	public static int checkRanking(Partida partida) throws FueraDeRankingException
	{
		sort();
		
		if(mPartidas.size() < Constantes.RANKING_SIZE)
		{
			return mPartidas.size();
		}
		
		for(int i = 0; i < mPartidas.size(); i ++)
		{
			if(mPartidas.get(i).getPuntos() < partida.getPuntos())
			{
				return i;
			}
		}
		
		throw new FueraDeRankingException();
	}
	
	/**
	 * @return Array con los datos del ranking
	 */
	public static ArrayList<Partida> get()
	{
		return mPartidas;
	}
}
