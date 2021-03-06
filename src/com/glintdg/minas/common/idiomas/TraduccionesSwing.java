package com.glintdg.minas.common.idiomas;

/**
 * Localizacion de la interfaz grafica del juego
 * 
 * @author Almamu
 */
public class TraduccionesSwing
{
	public static final String ERROR_TITLE = "Error";
	
	public static class MainWindow
	{
		public static final String TITLE = "GlintMine v1.0 ByAlmamu";
		public static final String LOOSE_TITLE = "Game over";
		public static final String LOOSE_TEXT = "�Lastima! Has pisado una mina. Tu puntuacion es de %d. �Quieres empezar de nuevo?";
		public static final String WIN_TEXT = "�Enhorabuena! �Has completado el juego correctamente! Tu puntuacion es de %d puntos";
		public static final String TABLERO_ERROR = "El tablero ha de tener almenos %d minas y no mas de %d";
		
		public static class Ranking
		{
			public static final String RANKING_EMPTY_TEXT = "No existe ningun jugador en el ranking aun";
			public static final String RANKING_TITLE = "�Has entrado en el ranking!";
			public static final String RANKING_TEXT = "La puntuaci�n obtenida est� dentro de las 10 mejores, concretamente est�s en el puesto %d de 10. �Quieres guardar tu puntuaci�n?";
			public static final String INPUT_TITLE = "A�adir al ranking";
			public static final String INPUT_TEXT = "Introduce tu nombre: ";
		}
		
		public static class MainMenu
		{
			public static final String MENU_ENTRY1 = "Juego";
			public static final String MENU_ENTRY2 = "Ayuda";
			
			public static class Menu1
			{
				public static final String ENTRY1 = "Nuevo";
				public static final String ENTRY2 = "Cambiar dificultad";
				public static final String ENTRY3 = "Salir";
			}
			
			public static class Menu2
			{
				public static final String ENTRY1 = "Ranking";
				public static final String ENTRY2 = "Acerca de...";
			}
		}
	}
	
	public static class ConfigWindow
	{
		public static final String TITLE = "Iniciar juego";
		public static final String OK_BUTTON = "Aceptar";
		public static final String CANCEL_BUTTON = "Salir";
		public static final String WELCOME_MESSAGE = "�Bienvenido! "
				+ "Antes de empezar a jugar es necesario que introduzcas el nivel de dificultad deseado<br />"
				+ "especificando el numero de minas, filas y columnas que deseas para tu partida. <br/>"
				+ "Ten en cuenta que esto afectara a tu puntuaci�n final. <br />"
				+ "�Buena suerte!";
		public static final String LEVEL_CHANGE_MESSAGE = "Elije la nueva dificultad de juego";
	}
	
	public static class AboutWindow
	{
		public static final String TITLE = MainWindow.MainMenu.Menu2.ENTRY2;
		public static final String MESSAGE = "glintMines for swing v1.0 ByAlmamu<br />"
				+ "<br />"
				+ "Copyright &copy; 2015 - Glint Development Group<br />"
				+ "Copyright &copy; 2015 - EFA El Campico";
	}
}
