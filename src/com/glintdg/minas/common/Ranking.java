package com.glintdg.minas.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

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
	 * Carga el ranking a partir de un fichero
	 * 
	 * @return Lista de los 10 mejores en el ranking
	 */
	private static ArrayList<Partida> loadRanking()
	{
		int partidasCargadas = 0;
		ArrayList<Partida> tmp = new ArrayList<Partida>();
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
		catch(ClassNotFoundException ex)
		{
			System.err.println("No se pudo cargar el ranking por completo");
		}
		catch(IOException ex)
		{
			System.err.println("No se pudo cargar el ranking por completo");
		}
		finally
		{
			try
			{
				obj.close();
				stream.close();
			}
			catch(IOException ex)
			{
				
			}
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
		
		if(mPartidas.get(0).getPuntos() < partida.getPuntos())
		{
			mPartidas.add(partida);
		}
		
		sort();
	}
}
