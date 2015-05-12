package com.glintdg.minas.interfaz.swing;

import java.awt.EventQueue;

import javax.swing.JFrame;

import com.glintdg.minas.common.Controlador;
import com.glintdg.minas.common.Partida;
import com.glintdg.minas.common.Tablero;
import com.glintdg.minas.common.casillas.Casilla;
import com.glintdg.minas.common.excepciones.MinaExplosionadaException;
import com.glintdg.minas.common.excepciones.TableroInvalidoException;
import com.glintdg.minas.common.idiomas.TraduccionesSwing;

import javax.swing.JMenuBar;

import java.awt.BorderLayout;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

/**
 * Logica principal del juego
 * se encarga de configurar la interfaz, iniciar la aplicacion
 * y de mostrar los mensajes necesarios, asi como de controlar
 * que el juego este completo
 * 
 * @author Almamu
 */
public class JuegoSwing implements Controlador
{
	/**
	 * Numero de filas elegidas por el usuario
	 */
	public static int Filas = 0;
	
	/**
	 * Numero de columnas elegidas por el usuario
	 */
	public static int Columnas = 0;
	
	/**
	 * Numero de minas elegidas por el usuario
	 */
	public static int Minas = 0;
	
	/**
	 * Dialogo usado por el juego para mostrar la interfaz
	 */
	private JFrame frame;
	
	/**
	 * Tablero en uso por este controlador para la partida
	 */
	private Tablero mTablero = null;
	
	/**
	 * Layout usada para mostrar el juego
	 */
	private TableroSwing mGrid = null;
	
	/**
	 * Lanza la aplicacion
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					JuegoSwing window = new JuegoSwing();
					window.frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Constructor
	 */
	public JuegoSwing()
	{
		// es necesario que siempre tenga un valor y nunca nulo
		// ya que se eliminaran y añadiran componentes al contentPane() del mism
		// sin destruirlo
		frame = new JFrame();
		
		// prepara la ventana para el juego
		initialize();
	}

	/**
	 * Inicia el tablero de juego, usando la configuracion que el usuario introduzca
	 * 
	 * @param msg Mensaje que mostrar al jugador
	 * @param force Fuerza el mostrar el dialogo
	 */
	protected void configurarPartida(String msg, boolean force)
	{
		int filas = Filas, columnas = Columnas, minas = Minas;
		ConfigSwing config = new ConfigSwing(msg);
		Tablero tablero = null;

		do
		{			
			try
			{
				if(force == true || (Minas == 0 && Columnas == 0 && Filas == 0))
				{
					config.setVisible(true);
					filas = config.getFilas();
					columnas = config.getColumnas();
					minas = config.getMinas();
					
					if(config.wasCanceled() == true)
					{
						return;
					}
				}
				
				tablero = new Tablero(filas, columnas, minas, new Partida("Placeholder"));

				Filas = filas;
				Minas = minas;
				Columnas = columnas;
				this.mTablero = tablero;
			}
			catch (ArrayIndexOutOfBoundsException | TableroInvalidoException ex)
			{
				JOptionPane.showMessageDialog(
					frame,
					"El tablero ha de tener almenos " + Tablero.minimoMinas(columnas, filas) + " minas y no mas de " + Tablero.maximoMinas(columnas, filas),
					"Error",
					JOptionPane.OK_OPTION
				);
			}
		}
		while(tablero == null);
	}
	
	/**
	 * Muestra el dialogo para cambiar de dificultad la partida y reiniciarla
	 */
	protected void cambiarDificultad()
	{
		this.configurarPartida(TraduccionesSwing.ConfigWindow.LEVEL_CHANGE_MESSAGE, true);
		this.reiniciar();
	}
	
	/**
	 * Configura todos los elementos visuales del juego (codigo auto-generado por eclipse)
	 */
	private void initialize()
	{
		// configuración basica del dialogo usado para mostrar el juego
		frame.setTitle(TraduccionesSwing.MainWindow.TITLE);
		frame.setBounds(100, 100, 652, 496);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// inicia datos del juego como el tablero antes de mostrar la interfaz
		this.configurarPartida(TraduccionesSwing.ConfigWindow.WELCOME_MESSAGE, false);
		
		// es necesario asegurarse de que el jugador ha escogido configuracion
		if(this.mTablero == null)
		{
			this.cerrar();
		}
		
		// crea los controles del formulario
		this.createControls();
	}
	
	/**
	 * Reinicia el juego, reconstruyendo tambien la interfaz
	 */
	private void reiniciar()
	{
		frame.getContentPane().removeAll();
		frame.getContentPane().revalidate();
		frame.getContentPane().repaint();
		this.initialize();
	}
	
	/**
	 * Cierra el juego
	 */
	private void cerrar()
	{
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}
	
	/**
	 * Crea los controles para el juego y sus vistas (botones, vista, etc)
	 */
	private void createControls()
	{
		// barra de menu para el juego con acciones
		JMenuBar menuBar = new JMenuBar();
		frame.getContentPane().add(menuBar, BorderLayout.NORTH);
		
		// primera entrada del menu
		JMenu mnJuego = new JMenu(TraduccionesSwing.MainWindow.MainMenu.MENU_ENTRY1);
		menuBar.add(mnJuego);
		
		// opcion "Nuevo" de la primera entrada del menu
		JMenuItem mntmNuevo = new JMenuItem(TraduccionesSwing.MainWindow.MainMenu.Menu1.ENTRY1);
		mntmNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				reiniciar();
			}
		});
		mnJuego.add(mntmNuevo);
		
		// opcion "Cambiar de dificultad" de la primera entrada del menu
		JMenuItem mntmCambiarDificultad = new JMenuItem(TraduccionesSwing.MainWindow.MainMenu.Menu1.ENTRY2);
		mntmCambiarDificultad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				cambiarDificultad();
			}
		});
		mnJuego.add(mntmCambiarDificultad);
		
		// opcion "Salir" de la primera entrada del menu
		JMenuItem mntmSalir = new JMenuItem(TraduccionesSwing.MainWindow.MainMenu.Menu1.ENTRY3);
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				cerrar();
			}
		});
		mnJuego.add(mntmSalir);
		
		// segundo menu que muestra la ayuda del juego asi como la version del mismo
		JMenu mnAyuda = new JMenu(TraduccionesSwing.MainWindow.MainMenu.MENU_ENTRY2);
		menuBar.add(mnAyuda);

		// configura la vista a usar para mostrar las casillas disponibles
		this.mGrid = new TableroSwing(this, this.mTablero);
		frame.getContentPane().add(this.mGrid);
	}

	/**
	 * Evento llamado cuando una mina explosiona
	 * 
	 * @param fila Fila en la que se encuentra la mina
	 * @param columna Columna en la que se encuentra la mina
	 */
	@Override
	public void onMinaExplosionada(int fila, int columna)
	{
		this.mGrid.onMinaExplosionada();
		this.actualizar();
		
		int result = JOptionPane.showConfirmDialog(frame, String.format(TraduccionesSwing.MainWindow.LOOSE_TEXT, this.mTablero.getPartida().getPuntos()), TraduccionesSwing.MainWindow.LOOSE_TITLE, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
		
		if(result != JOptionPane.OK_OPTION)
		{
			cerrar();
		}
		else
		{
			this.reiniciar();
		}
	}

	/**
	 * Descubre la casilla especificada
	 * 
	 * @param fila Fila en la que se encuentra la casilla 
	 * @param columna Columna en la que se encuentra la casilla
	 * 
	 * @throws MinaExplosionadaException Excepcion generada si la casilla es una mina
	 */
	@Override
	public void descubrir(int fila, int columna) throws MinaExplosionadaException
	{
		try
		{
			Casilla casilla = this.getTablero().getCasillaAt(fila, columna);
			
			if(casilla.isMarcada() == true) return;
			
			casilla.descubrir();
			this.actualizar();
		}
		catch(MinaExplosionadaException ex)
		{
			this.onMinaExplosionada(fila, columna);
			throw ex;
		}
		
		this.comprobarFin();
	}
	
	/**
	 * Marca la casilla especificada como sospechosa
	 * 
	 * @param fila Fila en la que se encuentra la casilla
	 * @param columna Columna en la que se encuentra la casilla
	 */
	@Override
	public void marcar(int fila, int columna)
	{
		this.getTablero().getCasillaAt(fila, columna).marcar();
		this.comprobarFin();
	}
	
	/**
	 * Comprueba si el jugador ha completado con exito la partida
	 */
	public void comprobarFin()
	{
		if(this.getCasillasDescubiertas() == this.getNumeroCasillasVacias())
		{
			JOptionPane.showMessageDialog(frame, String.format(TraduccionesSwing.MainWindow.WIN_TEXT, this.mTablero.getPartida().getPuntos()), TraduccionesSwing.MainWindow.LOOSE_TITLE, JOptionPane.INFORMATION_MESSAGE);
			this.reiniciar();
		}
	}
	
	/**
	 * @return Indica el tablero usado para esta partida
	 */
	private Tablero getTablero()
	{
		return this.mTablero;
	}
	
	/**
	 * Actualiza todos los botones del tablero
	 */
	private void actualizar()
	{
		this.mGrid.actualizar();
	}

	/**
	 * @return Indica el numero de casillas descubiertas por el jugador
	 */
	@Override
	public int getCasillasDescubiertas()
	{
		return this.mGrid.getCasillasDescubiertas();
	}

	/**
	 * @return Indica el numero de minas que tiene la partida
	 */
	@Override
	public int getMinas()
	{
		return this.getTablero().getMinas();
	}

	/**
	 * @return Indica le numero de minas correctas que ha marcado el jugador
	 */
	@Override
	public int getMinasMarcadas()
	{
		return this.mGrid.getMinasMarcadas();
	}

	/**
	 * @return Indica el numero de casillas vacias disponibles
	 */
	public int getNumeroCasillasVacias()
	{
		return this.getTablero().getNumeroCasillasVacias();
	}
}
