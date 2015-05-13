package com.glintdg.minas.interfaz.consola;

import java.util.Scanner;

import com.glintdg.minas.common.excepciones.TableroInvalidoException;
import com.glintdg.minas.common.idiomas.TraduccionesConsola;

/**
 * Cuerpo del codigo del juego para la consola
 * 
 * @author Almamu
 */
public class JuegoConsola extends ControladorConsola
{
	/**
	 * Scanner usado para la entrada de datos
	 */
	private Scanner mScanner = null;
	
	/**
	 * Funcion principal del juego en consola
	 */
	public static void main(String[] args)
	{
		// el controlador se va a encargar de toda la logica
		// aqui solo es necesario usarlo para que el juego fluya
		JuegoConsola controlador = new JuegoConsola();
		
		// inicia el juego
		controlador.jugar();
	}
	
	public JuegoConsola()
	{
		this.mScanner = new Scanner(System.in);
	}
	
	/**
	 * Ejecuta toda la logica del juego
	 */
	public void jugar()
	{
		System.out.print(TraduccionesConsola.WELCOME_MSG);
		String nombre = this.mScanner.nextLine();
		System.out.println(String.format(TraduccionesConsola.WELCOME_PLAYER, nombre));

		// tablero basico para pruebas
		while(this.puedeJugar() == false)
		{
			System.out.print(TraduccionesConsola.ASK_FILAS_TABLERO);
			int filas = this.mScanner.nextInt();
			
			System.out.print(TraduccionesConsola.ASK_COLUMNAS_TABLERO);
			int columnas = this.mScanner.nextInt();
			
			System.out.print(TraduccionesConsola.ASK_MINAS_TABLERO);
			int minas = this.mScanner.nextInt();
			
			try
			{
				this.prepararJuego(nombre, filas, columnas, minas);
			}
			catch (TableroInvalidoException e)
			{
				System.out.println(e.getMessage());
				System.out.println(TraduccionesConsola.ASK_ERROR);
			}
		}
		
		System.out.println(TraduccionesConsola.GAME_START);

		while(true)
		{
			// mostrar el tablero original puede ser necesario
			// dependiendo de la configuracion del programa
			this.mostrarTableroOriginal();
			
			// mostrar el tablero de juego es indispensable para el jugador
			this.mostrarTableroJuego();
			
			System.out.print(TraduccionesConsola.ASK_FILAS_DISCOVER);
			int fila = this.mScanner.nextInt();
			
			System.out.print(TraduccionesConsola.ASK_COLUMNAS_DISCOVER);
			int columna = this.mScanner.nextInt();
			
			try
			{
				this.descubrir(fila, columna);
			}
			catch(Exception ex)
			{
				break;
			}
			
			if(this.getCasillasDescubiertas() == this.getNumeroCasillasVacias())
			{
				System.out.println(TraduccionesConsola.GAME_COMPLETED);
				break;
			}
		}
		
		// un ultimo vistazo al tablero del juego
		// para que el jugador vea como ha quedado
		this.mostrarTableroJuego();
		
		System.out.println(String.format(TraduccionesConsola.GAME_OVER, this.getPuntos()));
		
		// esto ya no es necesario
		this.mScanner.close();
	}
}