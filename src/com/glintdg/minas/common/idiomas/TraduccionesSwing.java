package com.glintdg.minas.common.idiomas;

/**
 * Localizacion de la interfaz grafica del juego
 * 
 * @author Almamu
 */
public class TraduccionesSwing
{
	public static class MainWindow
	{
		public static final String TITLE = "GlintMine v1.0 ByAlmamu";
		public static final String LOOSE_TITLE = "Game over";
		public static final String LOOSE_TEXT = "¡Lastima! Has pisado una mina. Tu puntuacion es de %d. ¿Quieres empezar de nuevo?";
		public static final String WIN_TEXT = "¡Enhorabuena! ¡Has completado el juego correctamente! Tu puntuacion es de %d puntos";
		public static final String RANKING_TITLE = "¡Has entrado en el ranking!";
		public static final String RANKING_TEXT = "La puntuación obtenida está dentro de las 10 mejores, concretamente estás en el puesto %d de 10. ¿Quieres guardar tu puntuación?";
		
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
		}
	}
	
	public static class ConfigWindow
	{
		public static final String TITLE = "Iniciar juego";
		public static final String OK_BUTTON = "Aceptar";
		public static final String CANCEL_BUTTON = "Salir";
		public static final String WELCOME_MESSAGE = "¡Bienvenido! "
				+ "Antes de empezar a jugar es necesario que introduzcas el nivel de dificultad deseado<br />"
				+ "especificando el numero de minas, filas y columnas que deseas para tu partida. <br/>"
				+ "Ten en cuenta que esto afectara a tu puntuación final. <br />"
				+ "¡Buena suerte!";
		public static final String LEVEL_CHANGE_MESSAGE = "Elije la nueva dificultad de juego";
	}
}
